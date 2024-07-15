package com.union.mall.oms.converter;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.mall.oms.model.bo.OrderBO;
import com.union.mall.oms.model.entity.OmsOrder;
import com.union.mall.oms.model.form.OrderSubmitForm;
import com.union.mall.oms.model.vo.OmsOrderPageVO;
import com.union.mall.oms.model.vo.OrderPageVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper(componentModel = "spring")
public interface OrderConverter {

    @Mappings({
            @Mapping(target = "orderSn", source = "orderToken"),
            @Mapping(target = "totalQuantity",
                    expression = "java(orderSubmitForm.getOrderItems().stream().map(OrderSubmitForm.OrderItem::getQuantity).reduce(0, Integer::sum))"),
            @Mapping(target = "totalAmount",
                    expression = "java(orderSubmitForm.getOrderItems().stream().map(item -> item.getPrice() * item.getQuantity()).reduce(0L, Long::sum))"),
            @Mapping(target = "source", expression = "java(orderSubmitForm.getOrderSource().getValue())"),
    })
    OmsOrder form2Entity(OrderSubmitForm orderSubmitForm);

    @Mappings({
            @Mapping(
                    target = "paymentMethodLabel",
                    expression = "java(com.union.mall.common.core.base.IBaseEnum.getLabelByValue(bo.getPaymentMethod(), com.union.mall.oms.enums.PaymentMethodEnum.class))"
            ),
            @Mapping(
                    target = "sourceLabel",
                    expression = "java(com.union.mall.common.core.base.IBaseEnum.getLabelByValue(bo.getSource(), com.union.mall.oms.enums.OrderSourceEnum.class))"
            ),
            @Mapping(
                    target = "statusLabel",
                    expression = "java(com.union.mall.common.core.base.IBaseEnum.getLabelByValue(bo.getStatus(), com.union.mall.oms.enums.OrderStatusEnum.class))"
            ),
            @Mapping(
                    target = "orderItems",
                    source = "orderItems"
            )
    })
    OmsOrderPageVO toVoPage(OrderBO bo);

    Page<OmsOrderPageVO> toVoPage(Page<OrderBO> boPage);

    OmsOrderPageVO.OrderItem toVoPageOrderItem(OrderBO.OrderItem orderItem);


    @Mappings({
            @Mapping(
                    target = "paymentMethodLabel",
                    expression = "java(com.union.mall.common.core.base.IBaseEnum.getLabelByValue(bo.getPaymentMethod(), com.union.mall.oms.enums.PaymentMethodEnum.class))"
            ),
            @Mapping(
                    target = "sourceLabel",
                    expression = "java(com.union.mall.common.core.base.IBaseEnum.getLabelByValue(bo.getSource(), com.union.mall.oms.enums.OrderSourceEnum.class))"
            ),
            @Mapping(
                    target = "statusLabel",
                    expression = "java(com.union.mall.common.core.base.IBaseEnum.getLabelByValue(bo.getStatus(), com.union.mall.oms.enums.OrderStatusEnum.class))"
            ),
            @Mapping(
                    target = "orderItems",
                    source = "orderItems"
            )
    })
    OrderPageVO toVoPageForApp(OrderBO bo);

    Page<OrderPageVO> toVoPageForApp(Page<OrderBO> boPage);

    OrderPageVO.OrderItem toVoPageOrderItemForApp(OrderBO.OrderItem orderItem);
}