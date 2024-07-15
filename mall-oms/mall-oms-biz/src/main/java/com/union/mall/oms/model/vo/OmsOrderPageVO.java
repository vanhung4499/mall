package com.union.mall.oms.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Admin - Order pagination view object")
@Data
public class OmsOrderPageVO {

    @Schema(description = "Order ID")
    private Long id;

    @Schema(description = "Order number")
    private String orderSn;

    @Schema(description = "Total order amount (in cents)")
    private BigDecimal totalAmount;

    @Schema(description = "Payment amount (in cents)")
    private Long paymentAmount;

    @Schema(description = "Payment method label")
    private String paymentMethodLabel;

    @Schema(description = "Order status")
    private Integer status;

    @Schema(description = "Order status label")
    private String statusLabel;

    @Schema(description = "Total quantity of products")
    private Integer totalQuantity;

    @Schema(description = "Order creation time")
    @JsonFormat(pattern = "yyyy/MM/dd HH:mm:ss")
    private Date createTime;

    @Schema(description = "Order source label")
    private String sourceLabel;

    @Schema(description = "Order remarks")
    private String remark;

    @Schema(description = "Order item collection")
    private List<OrderItem> orderItems;

    @Schema(description = "Order item details")
    @Data
    public static class OrderItem {

        @Schema(description = "Product ID")
        private Long skuId;

        @Schema(description = "Product specification name")
        private String skuName;

        @Schema(description = "Image URL")
        private String picUrl;

        @Schema(description = "Product price")
        private Long price;

        @Schema(description = "Product quantity")
        private Integer quantity;

        @Schema(description = "Total amount of the product (in cents)")
        private Long totalAmount;

    }

}
