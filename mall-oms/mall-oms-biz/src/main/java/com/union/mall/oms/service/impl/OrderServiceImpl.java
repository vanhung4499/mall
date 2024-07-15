package com.union.mall.oms.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.union.mall.common.core.result.Result;
import com.union.mall.common.security.util.SecurityUtils;
import com.union.mall.oms.constant.OrderConstants;
import com.union.mall.oms.converter.OrderConverter;
import com.union.mall.oms.converter.OrderItemConverter;
import com.union.mall.oms.enums.OrderStatusEnum;
import com.union.mall.oms.enums.PaymentMethodEnum;
import com.union.mall.oms.mapper.OrderMapper;
import com.union.mall.oms.model.bo.OrderBO;
import com.union.mall.oms.model.dto.CartItemDto;
import com.union.mall.oms.model.dto.OrderItemDTO;
import com.union.mall.oms.model.entity.OmsOrder;
import com.union.mall.oms.model.entity.OmsOrderItem;
import com.union.mall.oms.model.form.OrderPaymentForm;
import com.union.mall.oms.model.form.OrderSubmitForm;
import com.union.mall.oms.model.query.OrderPageQuery;
import com.union.mall.oms.model.vo.OrderConfirmVO;
import com.union.mall.oms.model.vo.OrderPageVO;
import com.union.mall.oms.service.CartService;
import com.union.mall.oms.service.OrderItemService;
import com.union.mall.oms.service.OrderService;
import com.union.mall.pms.api.SkuFeignClient;
import com.union.mall.pms.model.dto.LockSkuDTO;
import com.union.mall.pms.model.dto.SkuInfoDTO;
import com.union.mall.ums.api.MemberFeignClient;
import com.union.mall.ums.dto.MemberAddressDTO;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl extends ServiceImpl<OrderMapper, OmsOrder> implements OrderService {

    private final CartService cartService;
    private final OrderItemService orderItemService;
    private final RabbitTemplate rabbitTemplate;
    private final StringRedisTemplate redisTemplate;
    private final ThreadPoolExecutor threadPoolExecutor;
    private final MemberFeignClient memberFeignClient;
    private final SkuFeignClient skuFeignClient;
    private final RedissonClient redissonClient;
    private final OrderConverter orderConverter;
    private final OrderItemConverter orderItemConverter;

    /**
     * Order pagination list
     */
    @Override
    public IPage<OrderPageVO> getOrderPage(OrderPageQuery queryParams) {
        Page<OrderBO> boPage = this.baseMapper.getOrderPage(
                new Page<>(queryParams.getPageNum(), queryParams.getPageSize()),
                queryParams);
        return orderConverter.toVoPageForApp(boPage);
    }

    /**
     * Order confirmation → Enter order creation page
     * <p>
     * Obtain purchase item details, user default shipping address, and prevent duplicate submission unique token
     * There are two entry points for entering the order creation page: 1. Buy now; 2. Cart settlement
     *
     * @param skuId Product ID (pass value directly for direct purchase)
     * @return {@link OrderConfirmVO}
     */
    @Override
    public OrderConfirmVO confirmOrder(Long skuId) {

        Long memberId = SecurityUtils.getMemberId();

        // Resolve the issue where child threads cannot access data from the HttpServletRequest request object
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        RequestContextHolder.setRequestAttributes(attributes, true);

        // Retrieve order items
        CompletableFuture<List<OrderItemDTO>> getOrderItemsFuture = CompletableFuture.supplyAsync(
                        () -> this.getOrderItems(skuId, memberId), threadPoolExecutor)
                .exceptionally(ex -> {
                    log.error("Failed to get order items: {}", ex.toString());
                    return Collections.emptyList();
                });

        // User shipping addresses
        CompletableFuture<List<MemberAddressDTO>> getMemberAddressFuture = CompletableFuture.supplyAsync(() -> {
            Result<List<MemberAddressDTO>> getMemberAddressResult = memberFeignClient.listMemberAddresses(memberId);
            if (Result.isSuccess(getMemberAddressResult)) {
                return getMemberAddressResult.getData();
            }
            return null;
        }, threadPoolExecutor).exceptionally(ex -> {
            log.error("Failed to get addresses for memberId {}: {}", memberId, ex.toString());
            return Collections.emptyList();
        });

        // Generate a unique token to prevent duplicate submission (Principle: submitting will consume the token, and the token consumed cannot be submitted again)
        CompletableFuture<String> generateOrderTokenFuture = CompletableFuture.supplyAsync(() -> {
            String orderToken = this.generateTradeNo(memberId);
            redisTemplate.opsForValue().set(OrderConstants.ORDER_TOKEN_PREFIX + orderToken, orderToken);
            return orderToken;
        }, threadPoolExecutor).exceptionally(ex -> {
            log.error("Failed to generate order token.");
            return null;
        });

        CompletableFuture.allOf(getOrderItemsFuture, getMemberAddressFuture, generateOrderTokenFuture).join();
        OrderConfirmVO orderConfirmVO = new OrderConfirmVO();
        orderConfirmVO.setOrderItems(getOrderItemsFuture.join());
        orderConfirmVO.setAddresses(getMemberAddressFuture.join());
        orderConfirmVO.setOrderToken(generateOrderTokenFuture.join());

        log.info("Order confirm response for skuId {}: {}", skuId, orderConfirmVO);
        return orderConfirmVO;
    }

    /**
     * Order submission
     *
     * @param submitForm {@link OrderSubmitForm}
     * @return Order number
     */
    @Override
    @GlobalTransactional
    public String submitOrder(OrderSubmitForm submitForm) {
        log.info("Order submission parameters:{}", JSONUtil.toJsonStr(submitForm));
        String orderToken = submitForm.getOrderToken();

        // 1. Check if the order is submitted repeatedly (Atomicity guaranteed by LUA script for getting and deleting, returns 1 if successful, otherwise returns 0)
        String lockAcquireScript = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Long lockAcquired = this.redisTemplate.execute(
                new DefaultRedisScript<>(lockAcquireScript, Long.class),
                Collections.singletonList(OrderConstants.ORDER_TOKEN_PREFIX + orderToken),
                orderToken
        );
        Assert.isTrue(lockAcquired != null && lockAcquired.equals(1L), "Order already submitted, please refresh the page and try again");

        // 2. Validate order items (PS: Validate changes in items (price, availability) from order confirmation page to submission process)
        List<OrderSubmitForm.OrderItem> orderItems = submitForm.getOrderItems();
        List<Long> skuIds = orderItems.stream()
                .map(OrderSubmitForm.OrderItem::getSkuId)
                .collect(Collectors.toList());
        List<SkuInfoDTO> skuList;
        try {
            skuList = skuFeignClient.getSkuInfoList(skuIds);
        } catch (Exception e) {
            log.error("Failed to get sku info list: {}", e.toString());
            skuList = Collections.emptyList();
        }
        for (OrderSubmitForm.OrderItem item : orderItems) {
            SkuInfoDTO skuInfo = skuList.stream().filter(sku -> sku.getId().equals(item.getSkuId()))
                    .findFirst()
                    .orElse(null);
            Assert.isTrue(skuInfo != null, "Product ({}) is no longer available or has been deleted");
            Assert.isTrue(item.getPrice().compareTo(skuInfo.getPrice()) == 0, "Price of product ({}) has changed, please refresh the page", item.getSkuName());
        }

        // 3. Validate and lock inventory
        List<LockSkuDTO> lockSkuList = orderItems.stream()
                .map(item -> new LockSkuDTO(item.getSkuId(), item.getQuantity()))
                .collect(Collectors.toList());

        boolean lockStockResult = skuFeignClient.lockStock(orderToken, lockSkuList);
        Assert.isTrue(lockStockResult, "Order submission failed: Failed to lock product inventory!");

        // 4. Generate order
        boolean result = this.saveOrder(submitForm);
        log.info("Order ({}) creation result:{}", orderToken, result);
        return orderToken;
    }

    /**
     * Create order
     *
     * @param submitForm Order submission form object
     * @return
     */
    private boolean saveOrder(OrderSubmitForm submitForm) {
        OmsOrder order = orderConverter.form2Entity(submitForm);
        order.setStatus(OrderStatusEnum.UNPAID.getValue());
        order.setMemberId(SecurityUtils.getMemberId());
        order.setSource(submitForm.getOrderSource().getValue());
        boolean result = this.save(order);

        Long orderId = order.getId();
        if (result) {
            // Save order details
            List<OmsOrderItem> orderItemEntities = orderItemConverter.item2Entity(submitForm.getOrderItems());
            orderItemEntities.forEach(item -> item.setOrderId(orderId));
            orderItemService.saveBatch(orderItemEntities);

            // Cancel order if not paid on time
            rabbitTemplate.convertAndSend("order.exchange", "order.close.delay", submitForm.getOrderToken());
        }
        return result;
    }

    /**
     * Order payment
     * <p>
     * Balance payment: Handle inventory, balance, and order processing
     * WeChat payment: Generate WeChat payment invocation parameters, handle orders, inventory, and balance in payment callback
     */
    @Override
    @GlobalTransactional
    public <T> T payOrder(OrderPaymentForm paymentForm) {
        String orderSn = paymentForm.getOrderSn();
        OmsOrder order = this.getOne(new LambdaQueryWrapper<OmsOrder>().eq(OmsOrder::getOrderSn, orderSn));
        Assert.isTrue(order != null, "Order does not exist");

        Assert.isTrue(OrderStatusEnum.UNPAID.getValue().equals(order.getStatus()), "Order cannot be paid, please check the order status");

        RLock lock = redissonClient.getLock(OrderConstants.ORDER_LOCK_PREFIX + order.getOrderSn());
        try {
            lock.lock();
            T result = null;
            switch (paymentForm.getPaymentMethod()) {
                case MOMO:
                    // TODO: Implement Momo payment
                    // result = (T) momoPayApi(paymentForm.getAppId(), order.getOrderSn(), order.getPaymentAmount());
                    break;
                case ZALOPAY:
                    // TODO: Implement ZaloPay payment
                    // result = (T) zaloPayApi(paymentForm.getAppId(), order.getOrderSn(), order.getPaymentAmount());
                    break;
                default:
                    result = (T) balancePay(order);
                    break;
            }
            return result;
        } finally {
            // Release the lock
            if (lock.isLocked()) {
                lock.unlock();
            }
        }
    }


    /**
     * Balance payment
     *
     * @param order
     * @return
     */
    private Boolean balancePay(OmsOrder order) {
        // Deduct balance
        Long memberId = order.getMemberId();
        Long payAmount = order.getPaymentAmount();
        Result<?> deductBalanceResult = memberFeignClient.deductBalance(memberId, payAmount);
        Assert.isTrue(Result.isSuccess(deductBalanceResult), "Failed to deduct account balance");

        // Deduct inventory
        skuFeignClient.deductStock(order.getOrderSn());

        // Update order status
        order.setStatus(OrderStatusEnum.PAID.getValue());
        order.setPaymentMethod(PaymentMethodEnum.BALANCE.getValue());
        order.setPaymentTime(new Date());
        this.updateById(order);
        // Remove checked items from cart upon successful payment
        cartService.removeCheckedItem();
        return Boolean.TRUE;
    }

    /**
     * Close order
     *
     * @param orderSn Order number
     * @return Whether successful true/false
     */
    @Override
    public boolean closeOrder(String orderSn) {
        return this.update(new LambdaUpdateWrapper<OmsOrder>()
                .eq(OmsOrder::getOrderSn, orderSn)
                .eq(OmsOrder::getStatus, OrderStatusEnum.UNPAID.getValue())
                .set(OmsOrder::getStatus, OrderStatusEnum.CANCELED.getValue())
        );
    }

    /**
     * Delete order
     *
     * @param orderId Order ID
     * @return true/false
     */
    @Override
    public boolean deleteOrder(Long orderId) {
        OmsOrder order = this.getById(orderId);
        Assert.isTrue(order != null, "Deletion failed, order does not exist!");

        Assert.isTrue(
                OrderStatusEnum.CANCELED.getValue().equals(order.getStatus())
                        || OrderStatusEnum.UNPAID.getValue().equals(order.getStatus())
                ,
                "Cannot delete orders in current status"
        );

        return this.removeById(orderId);
    }

    /**
     * Get order item details
     * <p>
     * Two ways to create orders: 1. Direct purchase; 2. Shopping cart settlement
     *
     * @param skuId    Required for direct purchase, not required for shopping cart settlement
     * @param memberId Member ID
     * @return List of order item DTOs
     */
    private List<OrderItemDTO> getOrderItems(Long skuId, Long memberId) {
        List<OrderItemDTO> orderItems;
        if (skuId != null) {  // Direct purchase
            orderItems = new ArrayList<>();
            SkuInfoDTO skuInfoDTO = skuFeignClient.getSkuInfo(skuId);
            OrderItemDTO orderItemDTO = new OrderItemDTO();
            orderItemDTO.setSkuId(skuId);
            BeanUtil.copyProperties(skuInfoDTO, orderItemDTO);
            orderItemDTO.setSkuId(skuInfoDTO.getId());
            orderItemDTO.setQuantity(1); // Quantity of directly purchased items is 1
            orderItems.add(orderItemDTO);
        } else { // Shopping cart settlement
            List<CartItemDto> cartItems = cartService.listCartItems(memberId);
            orderItems = cartItems.stream()
                    .filter(CartItemDto::getChecked)
                    .map(cartItem -> {
                        OrderItemDTO orderItemDTO = new OrderItemDTO();
                        BeanUtil.copyProperties(cartItem, orderItemDTO);
                        return orderItemDTO;
                    }).collect(Collectors.toList());
        }
        return orderItems;
    }

    /**
     * Generate merchant order number
     *
     * @param memberId Member ID
     * @return Generated merchant order number
     */
    private String generateTradeNo(Long memberId) {
        // Prefix with wxo (wx order) to distinguish orders for placing or refunding, and their origins from different payment institutions
        // The order number composition rule is based on <a href="https://tech.meituan.com/2016/11/18/dianping-order-db-sharding.html">Meituan-Dianping</a>
        String userIdFilledZero = String.format("%05d", memberId);
        String fiveDigitsUserId = userIdFilledZero.substring(userIdFilledZero.length() - 5);
        return System.currentTimeMillis() + RandomUtil.randomNumbers(3) + fiveDigitsUserId;
    }

}