package com.union.mall.oms.model.form;

import com.union.mall.oms.enums.OrderSourceEnum;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
@ToString
public class OrderSubmitForm {

    @Schema(description = "Token issued by the order confirmation page (to prevent duplicate submissions)")
    @NotBlank(message = "Order token cannot be blank")
    private String orderToken;

    @Schema(description = "Order source")
    @NotNull(message = "Order source cannot be null")
    private OrderSourceEnum orderSource;

    @Schema(description = "Order item details")
    @NotEmpty(message = "Order items cannot be empty")
    private List<OrderItem> orderItems;

    @Schema(description = "Payment amount (in cents)")
    @NotNull(message = "Payment amount cannot be null")
    private Long paymentAmount;

    @Schema(description = "Shipping address")
    @NotNull(message = "Shipping address cannot be null")
    private ShippingAddress shippingAddress;

    @Schema(description = "Order remark")
    @Size(max = 500, message = "Order remark length cannot exceed 500")
    private String remark;

    @Schema(description = "Shipping address")
    @Data
    public static class ShippingAddress {

        @Schema(description = "Consignee's name")
        private String consigneeName;

        @Schema(description = "Consignee's mobile number")
        private String consigneeMobile;

        @Schema(description = "Province")
        private String province;

        @Schema(description = "City")
        private String city;

        @Schema(description = "District")
        private String district;

        @Schema(description = "Detailed address")
        private String detailAddress;
    }

    @Schema(description = "Order item")
    @Data
    public static class OrderItem {

        @Schema(description = "SKU ID")
        private Long skuId;

        @Schema(description = "SKU code")
        private String skuSn;

        @Schema(description = "SKU name")
        private String skuName;

        @Schema(description = "Product image URL")
        private String picUrl;

        @Schema(description = "Product price (in cents)")
        private Long price;

        @Schema(description = "Product name")
        private String spuName;

        @Schema(description = "Product quantity")
        private Integer quantity;
    }
}
