package com.union.mall.oms.converter;

import com.union.mall.oms.model.entity.OmsOrderItem;
import com.union.mall.oms.model.form.OrderSubmitForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper(componentModel = "spring")
public interface OrderItemConverter {

    @Mappings({
            @Mapping(target = "totalAmount", expression = "java(item.getPrice() * item.getQuantity())"),
    })
    OmsOrderItem item2Entity(OrderSubmitForm.OrderItem item);

    List<OmsOrderItem> item2Entity(List<OrderSubmitForm.OrderItem> list);

}