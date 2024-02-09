package com.hnv99.mall.portal.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.github.pagehelper.PageHelper;
import com.hnv99.mall.common.api.CommonPage;
import com.hnv99.mall.common.exception.Asserts;
import com.hnv99.mall.common.service.RedisService;
import com.hnv99.mall.mapper.*;
import com.hnv99.mall.model.*;
import com.hnv99.mall.portal.component.CancelOrderSender;
import com.hnv99.mall.portal.dao.PortalOrderDao;
import com.hnv99.mall.portal.dao.PortalOrderItemDao;
import com.hnv99.mall.portal.dao.SmsCouponHistoryDao;
import com.hnv99.mall.portal.domain.*;
import com.hnv99.mall.portal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OmsPortalOrderServiceImpl implements OmsPortalOrderService {
    @Autowired
    private UmsMemberService memberService;
    @Autowired
    private OmsCartItemService cartItemService;
    @Autowired
    private UmsMemberReceiveAddressService memberReceiveAddressService;
    @Autowired
    private UmsMemberCouponService memberCouponService;
    @Autowired
    private UmsIntegrationConsumeSettingMapper integrationConsumeSettingMapper;
    @Autowired
    private PmsSkuStockMapper skuStockMapper;
    @Autowired
    private SmsCouponHistoryDao couponHistoryDao;
    @Autowired
    private OmsOrderMapper orderMapper;
    @Autowired
    private PortalOrderItemDao orderItemDao;
    @Autowired
    private SmsCouponHistoryMapper couponHistoryMapper;
    @Autowired
    private RedisService redisService;
    @Value("${redis.key.orderId}")
    private String REDIS_KEY_ORDER_ID;
    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Autowired
    private PortalOrderDao portalOrderDao;
    @Autowired
    private OmsOrderSettingMapper orderSettingMapper;
    @Autowired
    private OmsOrderItemMapper orderItemMapper;
    @Autowired
    private CancelOrderSender cancelOrderSender;

    @Override
    public ConfirmOrderResult generateConfirmOrder(List<Long> cartIds) {
        ConfirmOrderResult result = new ConfirmOrderResult();
        // Get shopping cart information
        UmsMember currentMember = memberService.getCurrentMember();
        List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(currentMember.getId(),cartIds);
        result.setCartPromotionItemList(cartPromotionItemList);
        // Get user's delivery address list
        List<UmsMemberReceiveAddress> memberReceiveAddressList = memberReceiveAddressService.list();
        result.setMemberReceiveAddressList(memberReceiveAddressList);
        // Get user's available coupon list
        List<SmsCouponHistoryDetail> couponHistoryDetailList = memberCouponService.listCart(cartPromotionItemList, 1);
        result.setCouponHistoryDetailList(couponHistoryDetailList);
        // Get user's points
        result.setMemberIntegration(currentMember.getIntegration());
        // Get rules for using points
        UmsIntegrationConsumeSetting integrationConsumeSetting = integrationConsumeSettingMapper.selectByPrimaryKey(1L);
        result.setIntegrationConsumeSetting(integrationConsumeSetting);
        // Calculate total amount, promotion discount, and payable amount
        ConfirmOrderResult.CalcAmount calcAmount = calcCartAmount(cartPromotionItemList);
        result.setCalcAmount(calcAmount);
        return result;
    }


    @Override
    public Map<String, Object> generateOrder(OrderParam orderParam) {
        List<OmsOrderItem> orderItemList = new ArrayList<>();
        // Validate delivery address
        if(orderParam.getMemberReceiveAddressId()==null){
            Asserts.fail("Please select a delivery address!");
        }
        // Get shopping cart and promotion information
        UmsMember currentMember = memberService.getCurrentMember();
        List<CartPromotionItem> cartPromotionItemList = cartItemService.listPromotion(currentMember.getId(), orderParam.getCartIds());
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            // Generate order item information
            OmsOrderItem orderItem = new OmsOrderItem();
            orderItem.setProductId(cartPromotionItem.getProductId());
            orderItem.setProductName(cartPromotionItem.getProductName());
            orderItem.setProductPic(cartPromotionItem.getProductPic());
            orderItem.setProductAttr(cartPromotionItem.getProductAttr());
            orderItem.setProductBrand(cartPromotionItem.getProductBrand());
            orderItem.setProductSn(cartPromotionItem.getProductSn());
            orderItem.setProductPrice(cartPromotionItem.getPrice());
            orderItem.setProductQuantity(cartPromotionItem.getQuantity());
            orderItem.setProductSkuId(cartPromotionItem.getProductSkuId());
            orderItem.setProductSkuCode(cartPromotionItem.getProductSkuCode());
            orderItem.setProductCategoryId(cartPromotionItem.getProductCategoryId());
            orderItem.setPromotionAmount(cartPromotionItem.getReduceAmount());
            orderItem.setPromotionName(cartPromotionItem.getPromotionMessage());
            orderItem.setGiftIntegration(cartPromotionItem.getIntegration());
            orderItem.setGiftGrowth(cartPromotionItem.getGrowth());
            orderItemList.add(orderItem);
        }
        // Check if all items in the cart are in stock
        if (!hasStock(cartPromotionItemList)) {
            Asserts.fail("Insufficient stock, unable to place order");
        }
        // Check if a coupon was used
        if (orderParam.getCouponId() == null) {
            // No coupon used
            for (OmsOrderItem orderItem : orderItemList) {
                orderItem.setCouponAmount(new BigDecimal(0));
            }
        } else {
            // Coupon used
            SmsCouponHistoryDetail couponHistoryDetail = getUseCoupon(cartPromotionItemList, orderParam.getCouponId());
            if (couponHistoryDetail == null) {
                Asserts.fail("This coupon is not available");
            }
            // Handle the coupon amount for the order items
            handleCouponAmount(orderItemList, couponHistoryDetail);
        }
        // Check if points were used
        if (orderParam.getUseIntegration() == null||orderParam.getUseIntegration().equals(0)) {
            // No points used
            for (OmsOrderItem orderItem : orderItemList) {
                orderItem.setIntegrationAmount(new BigDecimal(0));
            }
        } else {
            // Points used
            BigDecimal totalAmount = calcTotalAmount(orderItemList);
            BigDecimal integrationAmount = getUseIntegrationAmount(orderParam.getUseIntegration(), totalAmount, currentMember, orderParam.getCouponId() != null);
            if (integrationAmount.compareTo(new BigDecimal(0)) == 0) {
                Asserts.fail("Points not available");
            } else {
                // When points are available, distribute them among the available items
                for (OmsOrderItem orderItem : orderItemList) {
                    BigDecimal perAmount = orderItem.getProductPrice().divide(totalAmount, 3, RoundingMode.HALF_EVEN).multiply(integrationAmount);
                    orderItem.setIntegrationAmount(perAmount);
                }
            }
        }
        // Calculate the actual payment amount for the order items
        handleRealAmount(orderItemList);
        // Lock the stock
        lockStock(cartPromotionItemList);
        // Calculate the payable amount based on the total amount, freight, promotion discount, coupon, and points
        OmsOrder order = new OmsOrder();
        order.setDiscountAmount(new BigDecimal(0));
        order.setTotalAmount(calcTotalAmount(orderItemList));
        order.setFreightAmount(new BigDecimal(0));
        order.setPromotionAmount(calcPromotionAmount(orderItemList));
        order.setPromotionInfo(getOrderPromotionInfo(orderItemList));
        if (orderParam.getCouponId() == null) {
            order.setCouponAmount(new BigDecimal(0));
        } else {
            order.setCouponId(orderParam.getCouponId());
            order.setCouponAmount(calcCouponAmount(orderItemList));
        }
        if (orderParam.getUseIntegration() == null) {
            order.setIntegration(0);
            order.setIntegrationAmount(new BigDecimal(0));
        } else {
            order.setIntegration(orderParam.getUseIntegration());
            order.setIntegrationAmount(calcIntegrationAmount(orderItemList));
        }
        order.setPayAmount(calcPayAmount(order));
        // Convert to order information and insert into the database
        order.setMemberId(currentMember.getId());
        order.setCreateTime(new Date());
        order.setMemberUsername(currentMember.getUsername());
        // Payment method: 0->unpaid; 1->Alipay; 2->WeChat
        order.setPayType(orderParam.getPayType());
        // Order source: 0->PC order; 1->app order
        order.setSourceType(1);
        // Order status: 0->pending payment; 1->pending delivery; 2->delivered; 3->completed; 4->closed; 5->invalid order
        order.setStatus(0);
        // Order type: 0->normal order; 1->flash sale order
        order.setOrderType(0);
        // Receiver information: name, phone, zip code, address
        UmsMemberReceiveAddress address = memberReceiveAddressService.getItem(orderParam.getMemberReceiveAddressId());
        order.setReceiverName(address.getName());
        order.setReceiverPhone(address.getPhoneNumber());
        order.setReceiverPostCode(address.getPostCode());
        order.setReceiverProvince(address.getProvince());
        order.setReceiverCity(address.getCity());
        order.setReceiverRegion(address.getRegion());
        order.setReceiverDetailAddress(address.getDetailAddress());
        // 0->unconfirmed; 1->confirmed
        order.setConfirmStatus(0);
        order.setDeleteStatus(0);
        // Calculate gift points
        order.setIntegration(calcGifIntegration(orderItemList));
        // Calculate gift growth
        order.setGrowth(calcGiftGrowth(orderItemList));
        // Generate order number
        order.setOrderSn(generateOrderSn(order));
        // Set auto confirm days
        List<OmsOrderSetting> orderSettings = orderSettingMapper.selectByExample(new OmsOrderSettingExample());
        if(CollUtil.isNotEmpty(orderSettings)){
            order.setAutoConfirmDay(orderSettings.get(0).getConfirmOvertime());
        }
        // TODO: bill_*,delivery_*
        // Insert into order table and order_item table
        orderMapper.insert(order);
        for (OmsOrderItem orderItem : orderItemList) {
            orderItem.setOrderId(order.getId());
            orderItem.setOrderSn(order.getOrderSn());
        }
        orderItemDao.insertList(orderItemList);
        // If a coupon was used, update the coupon usage status
        if (orderParam.getCouponId() != null) {
            updateCouponStatus(orderParam.getCouponId(), currentMember.getId(), 1);
        }
        // If points were used, deduct points
        if (orderParam.getUseIntegration() != null) {
            order.setUseIntegration(orderParam.getUseIntegration());
            if(currentMember.getIntegration()==null){
                currentMember.setIntegration(0);
            }
            memberService.updateIntegration(currentMember.getId(), currentMember.getIntegration() - orderParam.getUseIntegration());
        }
        // Delete the ordered items from the shopping cart
        deleteCartItemList(cartPromotionItemList, currentMember);
        // Send delay message to cancel the order
        sendDelayMessageCancelOrder(order.getId());
        Map<String, Object> result = new HashMap<>();
        result.put("order", order);
        result.put("orderItemList", orderItemList);
        return result;
    }

    @Override
    public Integer paySuccess(Long orderId, Integer payType) {
        // Update order payment status
        OmsOrder order = new OmsOrder();
        order.setId(orderId);
        order.setStatus(1);
        order.setPaymentTime(new Date());
        order.setPayType(payType);
        orderMapper.updateByPrimaryKeySelective(order);
        // Restore all ordered products' locked inventory, deduct actual inventory
        OmsOrderDetail orderDetail = portalOrderDao.getDetail(orderId);
        int count = portalOrderDao.updateSkuStock(orderDetail.getOrderItemList());
        return count;
    }


    @Override
    public Integer cancelTimeOutOrder() {
        Integer count = 0;
        OmsOrderSetting orderSetting = orderSettingMapper.selectByPrimaryKey(1L);
        // Query for overdue, unpaid orders and their details
        List<OmsOrderDetail> timeOutOrders = portalOrderDao.getTimeOutOrders(orderSetting.getNormalOrderOvertime());
        if (CollectionUtils.isEmpty(timeOutOrders)) {
            return count;
        }
        // Change order status to transaction cancelled
        List<Long> ids = new ArrayList<>();
        for (OmsOrderDetail timeOutOrder : timeOutOrders) {
            ids.add(timeOutOrder.getId());
        }
        portalOrderDao.updateOrderStatus(ids, 4);
        for (OmsOrderDetail timeOutOrder : timeOutOrders) {
            // Release order product inventory lock
            portalOrderDao.releaseSkuStockLock(timeOutOrder.getOrderItemList());
            // Update coupon usage status
            updateCouponStatus(timeOutOrder.getCouponId(), timeOutOrder.getMemberId(), 0);
            // Return used points
            if (timeOutOrder.getUseIntegration() != null) {
                UmsMember member = memberService.getById(timeOutOrder.getMemberId());
                memberService.updateIntegration(timeOutOrder.getMemberId(), member.getIntegration() + timeOutOrder.getUseIntegration());
            }
        }
        return timeOutOrders.size();
    }

    @Override
    public void cancelOrder(Long orderId) {
        // Query for unpaid, cancellable orders
        OmsOrderExample example = new OmsOrderExample();
        example.createCriteria().andIdEqualTo(orderId).andStatusEqualTo(0).andDeleteStatusEqualTo(0);
        List<OmsOrder> cancelOrderList = orderMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(cancelOrderList)) {
            return;
        }
        OmsOrder cancelOrder = cancelOrderList.get(0);
        if (cancelOrder != null) {
            // Change order status to cancelled
            cancelOrder.setStatus(4);
            orderMapper.updateByPrimaryKeySelective(cancelOrder);
            OmsOrderItemExample orderItemExample = new OmsOrderItemExample();
            orderItemExample.createCriteria().andOrderIdEqualTo(orderId);
            List<OmsOrderItem> orderItemList = orderItemMapper.selectByExample(orderItemExample);
            // Release order product inventory lock
            if (!CollectionUtils.isEmpty(orderItemList)) {
                portalOrderDao.releaseSkuStockLock(orderItemList);
            }
            // Update coupon usage status
            updateCouponStatus(cancelOrder.getCouponId(), cancelOrder.getMemberId(), 0);
            // Return used points
            if (cancelOrder.getUseIntegration() != null) {
                UmsMember member = memberService.getById(cancelOrder.getMemberId());
                memberService.updateIntegration(cancelOrder.getMemberId(), member.getIntegration() + cancelOrder.getUseIntegration());
            }
        }
    }

    @Override
    public void sendDelayMessageCancelOrder(Long orderId) {
        // Get order timeout setting
        OmsOrderSetting orderSetting = orderSettingMapper.selectByPrimaryKey(1L);
        long delayTimes = orderSetting.getNormalOrderOvertime() * 60 * 1000;
        // Send delay message
        cancelOrderSender.sendMessage(orderId, delayTimes);
    }

    @Override
    public void confirmReceiveOrder(Long orderId) {
        // Get the current member (logged-in user)
        UmsMember member = memberService.getCurrentMember();
        // Get the order with the provided order ID
        OmsOrder order = orderMapper.selectByPrimaryKey(orderId);
        // Check if the logged-in user is the one who placed the order
        if(!member.getId().equals(order.getMemberId())){
            Asserts.fail("Cannot confirm others' orders!");
        }
        // Check if the order has been shipped
        if(order.getStatus()!=2){
            Asserts.fail("The order has not been shipped yet!");
        }
        // Change the order status to "received"
        order.setStatus(3);
        // Mark the order as "confirmed"
        order.setConfirmStatus(1);
        // Set the receipt time to the current time
        order.setReceiveTime(new Date());
        // Update the order in the database
        orderMapper.updateByPrimaryKey(order);
    }

    @Override
    public CommonPage<OmsOrderDetail> list(Integer status, Integer pageNum, Integer pageSize) {
        // If status is -1, treat it as null
        if(status == -1){
            status = null;
        }
        // Get the current member (logged-in user)
        UmsMember member = memberService.getCurrentMember();
        // Start a new page for the result set
        PageHelper.startPage(pageNum, pageSize);
        // Create an example (template) for the order
        OmsOrderExample orderExample = new OmsOrderExample();
        // Create criteria for the example
        OmsOrderExample.Criteria criteria = orderExample.createCriteria();
        // Set criteria to select orders that are not deleted and belong to the current user
        criteria.andDeleteStatusEqualTo(0)
                .andMemberIdEqualTo(member.getId());
        // If a status is provided, add it to the criteria
        if(status != null){
            criteria.andStatusEqualTo(status);
        }
        // Order the result set by creation time in descending order
        orderExample.setOrderByClause("create_time desc");
        // Select orders that match the criteria
        List<OmsOrder> orderList = orderMapper.selectByExample(orderExample);
        // Create a common page for the orders
        CommonPage<OmsOrder> orderPage = CommonPage.restPage(orderList);
        // Set up pagination information
        CommonPage<OmsOrderDetail> resultPage = new CommonPage<>();
        resultPage.setPageNum(orderPage.getPageNum());
        resultPage.setPageSize(orderPage.getPageSize());
        resultPage.setTotal(orderPage.getTotal());
        resultPage.setTotalPage(orderPage.getTotalPage());
        // If there are no orders, return the page without data
        if(CollUtil.isEmpty(orderList)){
            return resultPage;
        }
        // Set up data information
        List<Long> orderIds = orderList.stream().map(OmsOrder::getId).collect(Collectors.toList());
        OmsOrderItemExample orderItemExample = new OmsOrderItemExample();
        orderItemExample.createCriteria().andOrderIdIn(orderIds);
        List<OmsOrderItem> orderItemList = orderItemMapper.selectByExample(orderItemExample);
        List<OmsOrderDetail> orderDetailList = new ArrayList<>();
        for (OmsOrder omsOrder : orderList) {
            OmsOrderDetail orderDetail = new OmsOrderDetail();
            BeanUtil.copyProperties(omsOrder, orderDetail);
            List<OmsOrderItem> relatedItemList = orderItemList.stream().filter(item -> item.getOrderId().equals(orderDetail.getId())).collect(Collectors.toList());
            orderDetail.setOrderItemList(relatedItemList);
            orderDetailList.add(orderDetail);
        }
        resultPage.setList(orderDetailList);
        return resultPage;
    }

    @Override
    public OmsOrderDetail detail(Long orderId) {
        OmsOrder omsOrder = orderMapper.selectByPrimaryKey(orderId);
        OmsOrderItemExample example = new OmsOrderItemExample();
        example.createCriteria().andOrderIdEqualTo(orderId);
        List<OmsOrderItem> orderItemList = orderItemMapper.selectByExample(example);
        OmsOrderDetail orderDetail = new OmsOrderDetail();
        BeanUtil.copyProperties(omsOrder,orderDetail);
        orderDetail.setOrderItemList(orderItemList);
        return orderDetail;
    }

    @Override
    public void deleteOrder(Long orderId) {
        // Get the current member (logged-in user)
        UmsMember member = memberService.getCurrentMember();
        // Get the order with the provided order ID
        OmsOrder order = orderMapper.selectByPrimaryKey(orderId);
        // Check if the logged-in user is the one who placed the order
        if(!member.getId().equals(order.getMemberId())){
            Asserts.fail("Cannot delete others' orders!");
        }
        // Check if the order is completed or closed
        if(order.getStatus()==3 || order.getStatus()==4){
            // Mark the order as "deleted"
            order.setDeleteStatus(1);
            // Update the order in the database
            orderMapper.updateByPrimaryKey(order);
        } else {
            Asserts.fail("Only completed or closed orders can be deleted!");
        }
    }

    @Override
    public void paySuccessByOrderSn(String orderSn, Integer payType) {
        OmsOrderExample example =  new OmsOrderExample();
        example.createCriteria()
                .andOrderSnEqualTo(orderSn)
                .andStatusEqualTo(0)
                .andDeleteStatusEqualTo(0);
        List<OmsOrder> orderList = orderMapper.selectByExample(example);
        if(CollUtil.isNotEmpty(orderList)){
            OmsOrder order = orderList.get(0);
            paySuccess(order.getId(),payType);
        }
    }

    /**
     * Generates an 18-digit order number: 8-digit date + 2-digit platform number + 2-digit payment method + 6-digit (or more) auto-increment ID
     */
    private String generateOrderSn(OmsOrder order) {
        // Create a StringBuilder to build the order number
        StringBuilder sb = new StringBuilder();
        // Get the current date in "yyyyMMdd" format
        String date = new SimpleDateFormat("yyyyMMdd").format(new Date());
        // Create a key for the order ID in Redis
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ORDER_ID + date;
        // Increment the order ID in Redis
        Long increment = redisService.incr(key, 1);
        // Append the date to the order number
        sb.append(date);
        // Append the platform number to the order number, formatted as a 2-digit number
        sb.append(String.format("%02d", order.getSourceType()));
        // Append the payment method to the order number, formatted as a 2-digit number
        sb.append(String.format("%02d", order.getPayType()));
        // Convert the increment to a string
        String incrementStr = increment.toString();
        // If the increment is a 6-digit number or less
        if (incrementStr.length() <= 6) {
            // Append the increment to the order number, formatted as a 6-digit number
            sb.append(String.format("%06d", increment));
        } else {
            // If the increment is more than a 6-digit number, append it as is
            sb.append(incrementStr);
        }
        // Return the order number
        return sb.toString();
    }


    /**
     * Deletes the shopping cart information of the ordered items
     */
    private void deleteCartItemList(List<CartPromotionItem> cartPromotionItemList, UmsMember currentMember) {
        // Create a list to hold the IDs of the cart items
        List<Long> ids = new ArrayList<>();
        // Iterate over the cart promotion items
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            // Add the ID of each cart promotion item to the list
            ids.add(cartPromotionItem.getId());
        }
        // Call the cart item service to delete the cart items with the specified IDs for the current member
        cartItemService.delete(currentMember.getId(), ids);
    }

    /**
     * Calculates the total gift growth points awarded for an order
     */
    private Integer calcGiftGrowth(List<OmsOrderItem> orderItemList) {
        // Initialize the sum of the gift growth points
        Integer sum = 0;
        // Iterate over the order items
        for (OmsOrderItem orderItem : orderItemList) {
            // Add the product of the gift growth points and the quantity of the order item to the sum
            sum = sum + orderItem.getGiftGrowth() * orderItem.getProductQuantity();
        }
        // Return the sum of the gift growth points
        return sum;
    }

    /**
     * Calculates the total gift integration points awarded for an order
     */
    private Integer calcGifIntegration(List<OmsOrderItem> orderItemList) {
        // Initialize the sum of the gift integration points
        int sum = 0;
        // Iterate over the order items
        for (OmsOrderItem orderItem : orderItemList) {
            // Add the product of the gift integration points and the quantity of the order item to the sum
            sum += orderItem.getGiftIntegration() * orderItem.getProductQuantity();
        }
        // Return the sum of the gift integration points
        return sum;
    }

    /**
     * Changes the coupon information to a specified status
     *
     * @param couponId  The coupon ID
     * @param memberId  The member ID
     * @param useStatus 0->not used; 1->used
     */
    private void updateCouponStatus(Long couponId, Long memberId, Integer useStatus) {
        // If the coupon ID is null, exit the method
        if (couponId == null) return;
        // Query for the first coupon
        SmsCouponHistoryExample example = new SmsCouponHistoryExample();
        example.createCriteria().andMemberIdEqualTo(memberId)
                .andCouponIdEqualTo(couponId).andUseStatusEqualTo(useStatus == 0 ? 1 : 0);
        List<SmsCouponHistory> couponHistoryList = couponHistoryMapper.selectByExample(example);
        // If the coupon history list is not empty
        if (!CollectionUtils.isEmpty(couponHistoryList)) {
            // Get the first coupon history
            SmsCouponHistory couponHistory = couponHistoryList.get(0);
            // Set the use time to the current date and time
            couponHistory.setUseTime(new Date());
            // Set the use status to the specified status
            couponHistory.setUseStatus(useStatus);
            // Update the coupon history in the database
            couponHistoryMapper.updateByPrimaryKeySelective(couponHistory);
        }
    }

    private void handleRealAmount(List<OmsOrderItem> orderItemList) {
        // Iterate over the order items
        for (OmsOrderItem orderItem : orderItemList) {
            // Calculate the real amount as the original price minus the promotion amount,
            // the coupon amount, and the integration amount
            BigDecimal realAmount = orderItem.getProductPrice()
                    .subtract(orderItem.getPromotionAmount())
                    .subtract(orderItem.getCouponAmount())
                    .subtract(orderItem.getIntegrationAmount());
            // Set the real amount of the order item
            orderItem.setRealAmount(realAmount);
        }
    }

    /**
     * Retrieves the order promotion information
     */
    private String getOrderPromotionInfo(List<OmsOrderItem> orderItemList) {
        // Initialize a StringBuilder to build the promotion information string
        StringBuilder sb = new StringBuilder();
        // Iterate over the order items
        for (OmsOrderItem orderItem : orderItemList) {
            // Append the promotion name of the order item to the StringBuilder
            sb.append(orderItem.getPromotionName());
            sb.append(";");
        }
        // Convert the StringBuilder to a string
        String result = sb.toString();
        // If the string ends with a semicolon
        if (result.endsWith(";")) {
            // Remove the trailing semicolon
            result = result.substring(0, result.length() - 1);
        }
        // Return the promotion information string
        return result;
    }

    /**
     * Calculates the payable amount for an order
     */
    private BigDecimal calcPayAmount(OmsOrder order) {
        // Calculate the payable amount as the total amount plus the freight amount minus the promotion amount,
        // the coupon amount, and the integration amount
        BigDecimal payAmount = order.getTotalAmount()
                .add(order.getFreightAmount())
                .subtract(order.getPromotionAmount())
                .subtract(order.getCouponAmount())
                .subtract(order.getIntegrationAmount());
        // Return the payable amount
        return payAmount;
    }

    /**
     * Calculates the total integration amount for an order
     */
    private BigDecimal calcIntegrationAmount(List<OmsOrderItem> orderItemList) {
        // Initialize the integration amount to 0
        BigDecimal integrationAmount = new BigDecimal(0);
        // Iterate over the order items
        for (OmsOrderItem orderItem : orderItemList) {
            // If the integration amount of the order item is not null
            if (orderItem.getIntegrationAmount() != null) {
                // Add the product of the integration amount and the quantity of the order item to the integration amount
                integrationAmount = integrationAmount.add(orderItem.getIntegrationAmount().multiply(new BigDecimal(orderItem.getProductQuantity())));
            }
        }
        // Return the total integration amount
        return integrationAmount;
    }

    /**
     * Calculates the total coupon amount for an order
     */
    private BigDecimal calcCouponAmount(List<OmsOrderItem> orderItemList) {
        // Initialize the coupon amount to 0
        BigDecimal couponAmount = new BigDecimal(0);
        // Iterate over the order items
        for (OmsOrderItem orderItem : orderItemList) {
            // If the coupon amount of the order item is not null
            if (orderItem.getCouponAmount() != null) {
                // Add the product of the coupon amount and the quantity of the order item to the coupon amount
                couponAmount = couponAmount.add(orderItem.getCouponAmount().multiply(new BigDecimal(orderItem.getProductQuantity())));
            }
        }
        // Return the total coupon amount
        return couponAmount;
    }

    /**
     * Calculates the total promotion amount for an order
     */
    private BigDecimal calcPromotionAmount(List<OmsOrderItem> orderItemList) {
        // Initialize the promotion amount to 0
        BigDecimal promotionAmount = new BigDecimal(0);
        // Iterate over the order items
        for (OmsOrderItem orderItem : orderItemList) {
            // If the promotion amount of the order item is not null
            if (orderItem.getPromotionAmount() != null) {
                // Add the product of the promotion amount and the quantity of the order item to the promotion amount
                promotionAmount = promotionAmount.add(orderItem.getPromotionAmount().multiply(new BigDecimal(orderItem.getProductQuantity())));
            }
        }
        // Return the total promotion amount
        return promotionAmount;
    }

    /**
     * Retrieves the usable integration amount
     *
     * @param useIntegration The number of integrations to use
     * @param totalAmount    The total amount of the order
     * @param currentMember  The current user
     * @param hasCoupon      Whether a coupon has been used
     */
    private BigDecimal getUseIntegrationAmount(Integer useIntegration, BigDecimal totalAmount, UmsMember currentMember, boolean hasCoupon) {
        // Initialize a zero amount
        BigDecimal zeroAmount = new BigDecimal(0);
        // If the user doesn't have enough integrations
        if (useIntegration.compareTo(currentMember.getIntegration()) > 0) {
            // Return the zero amount
            return zeroAmount;
        }
        // Get the integration consume setting
        UmsIntegrationConsumeSetting integrationConsumeSetting = integrationConsumeSettingMapper.selectByPrimaryKey(1L);
        // If a coupon has been used and the coupon status is 0
        if (hasCoupon && integrationConsumeSetting.getCouponStatus().equals(0)) {
            // Return the zero amount
            return zeroAmount;
        }
        // If the number of integrations to use is less than the use unit
        if (useIntegration.compareTo(integrationConsumeSetting.getUseUnit()) < 0) {
            // Return the zero amount
            return zeroAmount;
        }
        // Calculate the integration amount and the maximum percent
        BigDecimal integrationAmount = new BigDecimal(useIntegration).divide(new BigDecimal(integrationConsumeSetting.getUseUnit()), 2, RoundingMode.HALF_EVEN);
        BigDecimal maxPercent = new BigDecimal(integrationConsumeSetting.getMaxPercentPerOrder()).divide(new BigDecimal(100), 2, RoundingMode.HALF_EVEN);
        // If the integration amount is greater than the product of the total amount and the maximum percent
        if (integrationAmount.compareTo(totalAmount.multiply(maxPercent)) > 0) {
            // Return the zero amount
            return zeroAmount;
        }
        // Return the integration amount
        return integrationAmount;
    }

    /**
     * Handles the coupon amount for an order
     *
     * @param orderItemList       The list of order items
     * @param couponHistoryDetail The available coupon details
     */
    private void handleCouponAmount(List<OmsOrderItem> orderItemList, SmsCouponHistoryDetail couponHistoryDetail) {
        // Get the coupon
        SmsCoupon coupon = couponHistoryDetail.getCoupon();
        // If the use type of the coupon is 0
        if (coupon.getUseType().equals(0)) {
            // The coupon is applicable to all items
            calcPerCouponAmount(orderItemList, coupon);
        } else if (coupon.getUseType().equals(1)) {
            // The coupon is applicable to specific categories
            List<OmsOrderItem> couponOrderItemList = getCouponOrderItemByRelation(couponHistoryDetail, orderItemList, 0);
            calcPerCouponAmount(couponOrderItemList, coupon);
        } else if (coupon.getUseType().equals(2)) {
            // The coupon is applicable to specific items
            List<OmsOrderItem> couponOrderItemList = getCouponOrderItemByRelation(couponHistoryDetail, orderItemList, 1);
            calcPerCouponAmount(couponOrderItemList, coupon);
        }
    }

    /**
     * Calculates the coupon amount per order item
     *
     * @param orderItemList The list of order items that the coupon can be applied to
     * @param coupon        The coupon
     */
    private void calcPerCouponAmount(List<OmsOrderItem> orderItemList, SmsCoupon coupon) {
        // Calculate the total amount of the order items
        BigDecimal totalAmount = calcTotalAmount(orderItemList);
        // Iterate over the order items
        for (OmsOrderItem orderItem : orderItemList) {
            // Calculate the coupon amount as the product of the price of the order item divided by the total amount
            // and the amount of the coupon
            BigDecimal couponAmount = orderItem.getProductPrice().divide(totalAmount, 3, RoundingMode.HALF_EVEN).multiply(coupon.getAmount());
            // Set the coupon amount of the order item
            orderItem.setCouponAmount(couponAmount);
        }
    }

    /**
     * Gets the order items related to a coupon
     *
     * @param couponHistoryDetail The coupon details
     * @param orderItemList       The list of order items
     * @param type                The relation type: 0->related categories; 1->specific items
     */
    private List<OmsOrderItem> getCouponOrderItemByRelation(SmsCouponHistoryDetail couponHistoryDetail, List<OmsOrderItem> orderItemList, int type) {
        // Initialize the result list
        List<OmsOrderItem> result = new ArrayList<>();
        // If the relation type is 0
        if (type == 0) {
            // Get the category IDs related to the coupon
            List<Long> categoryIdList = new ArrayList<>();
            for (SmsCouponProductCategoryRelation productCategoryRelation : couponHistoryDetail.getCategoryRelationList()) {
                categoryIdList.add(productCategoryRelation.getProductCategoryId());
            }
            // Iterate over the order items
            for (OmsOrderItem orderItem : orderItemList) {
                // If the category of the order item is related to the coupon
                if (categoryIdList.contains(orderItem.getProductCategoryId())) {
                    // Add the order item to the result list
                    result.add(orderItem);
                } else {
                    // Set the coupon amount of the order item to 0
                    orderItem.setCouponAmount(new BigDecimal(0));
                }
            }
            // If the relation type is 1
        } else if (type == 1) {
            // Get the product IDs related to the coupon
            List<Long> productIdList = new ArrayList<>();
            for (SmsCouponProductRelation productRelation : couponHistoryDetail.getProductRelationList()) {
                productIdList.add(productRelation.getProductId());
            }
            // Iterate over the order items
            for (OmsOrderItem orderItem : orderItemList) {
                // If the product of the order item is related to the coupon
                if (productIdList.contains(orderItem.getProductId())) {
                    // Add the order item to the result list
                    result.add(orderItem);
                } else {
                    // Set the coupon amount of the order item to 0
                    orderItem.setCouponAmount(new BigDecimal(0));
                }
            }
        }
        // Return the result list
        return result;
    }

    /**
     * Gets the coupon that a user can use
     *
     * @param cartPromotionItemList The list of cart promotion items
     * @param couponId              The ID of the coupon to use
     */
    private SmsCouponHistoryDetail getUseCoupon(List<CartPromotionItem> cartPromotionItemList, Long couponId) {
        // Get the list of available coupons
        List<SmsCouponHistoryDetail> couponHistoryDetailList = memberCouponService.listCart(cartPromotionItemList, 1);
        // Iterate over the available coupons
        for (SmsCouponHistoryDetail couponHistoryDetail : couponHistoryDetailList) {
            // If the ID of the coupon matches the given ID
            if (couponHistoryDetail.getCoupon().getId().equals(couponId)) {
                // Return the coupon
                return couponHistoryDetail;
            }
        }
        // If no matching coupon is found, return null
        return null;
    }

    /**
     * Calculates the total amount of an order
     *
     * @param orderItemList The list of order items
     */
    private BigDecimal calcTotalAmount(List<OmsOrderItem> orderItemList) {
        // Initialize the total amount to 0
        BigDecimal totalAmount = new BigDecimal("0");
        // Iterate over the order items
        for (OmsOrderItem item : orderItemList) {
            // Add the product of the price and the quantity of the order item to the total amount
            totalAmount = totalAmount.add(item.getProductPrice().multiply(new BigDecimal(item.getProductQuantity())));
        }
        // Return the total amount
        return totalAmount;
    }

    /**
     * Locks the stock of all order items
     *
     * @param cartPromotionItemList The list of cart promotion items
     */
    private void lockStock(List<CartPromotionItem> cartPromotionItemList) {
        // Iterate over the cart promotion items
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            // Get the SKU stock of the cart promotion item
            PmsSkuStock skuStock = skuStockMapper.selectByPrimaryKey(cartPromotionItem.getProductSkuId());
            // Increase the lock stock by the quantity of the cart promotion item
            skuStock.setLockStock(skuStock.getLockStock() + cartPromotionItem.getQuantity());
            // Update the SKU stock
            skuStockMapper.updateByPrimaryKeySelective(skuStock);
        }
    }

    /**
     * Checks if all order items have stock
     *
     * @param cartPromotionItemList The list of cart promotion items
     */
    private boolean hasStock(List<CartPromotionItem> cartPromotionItemList) {
        // Iterate over the cart promotion items
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            // If the real stock is null, less than or equal to 0, or less than the quantity of the cart promotion item
            if (cartPromotionItem.getRealStock()==null // Check if the real stock is null
                    ||cartPromotionItem.getRealStock() <= 0 // Check if the real stock is less than or equal to 0
                    || cartPromotionItem.getRealStock() < cartPromotionItem.getQuantity()) // Check if the real stock is less than the quantity of the cart promotion item
            {
                // Return false
                return false;
            }
        }
        // If all cart promotion items have stock, return true
        return true;
    }

    /**
     * Calculates the amount of the items in the cart
     *
     * @param cartPromotionItemList The list of cart promotion items
     */
    private ConfirmOrderResult.CalcAmount calcCartAmount(List<CartPromotionItem> cartPromotionItemList) {
        // Initialize the calculation result
        ConfirmOrderResult.CalcAmount calcAmount = new ConfirmOrderResult.CalcAmount();
        calcAmount.setFreightAmount(new BigDecimal(0));
        BigDecimal totalAmount = new BigDecimal("0");
        BigDecimal promotionAmount = new BigDecimal("0");
        // Iterate over the cart promotion items
        for (CartPromotionItem cartPromotionItem : cartPromotionItemList) {
            // Add the product of the price and the quantity of the cart promotion item to the total amount
            totalAmount = totalAmount.add(cartPromotionItem.getPrice().multiply(new BigDecimal(cartPromotionItem.getQuantity())));
            // Add the product of the reduce amount and the quantity of the cart promotion item to the promotion amount
            promotionAmount = promotionAmount.add(cartPromotionItem.getReduceAmount().multiply(new BigDecimal(cartPromotionItem.getQuantity())));
        }
        // Set the total amount, the promotion amount, and the pay amount (total amount - promotion amount)
        calcAmount.setTotalAmount(totalAmount);
        calcAmount.setPromotionAmount(promotionAmount);
        calcAmount.setPayAmount(totalAmount.subtract(promotionAmount));
        // Return the calculation result
        return calcAmount;
    }

}
