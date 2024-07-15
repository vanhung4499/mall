package com.union.mall.oms.model.form;

import com.union.mall.oms.enums.PaymentMethodEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
@Schema(description = "Order payment form object")
public class OrderPaymentForm {

    @Schema(description = "Order number")
    private String orderSn;

    @Schema(description = "Mini program AppId")
    String appId;

    @Schema(description = "Payment method")
    private PaymentMethodEnum paymentMethod;

}
