package com.union.mall.pms.model.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
@Schema(description = "Product details")
public class SpuDetailVO {

    @Schema(description="Basic product information")
    private GoodsInfo goodsInfo;

    @Schema(description="List of product attributes")
    private List<Attribute> attributeList;

    @Schema(description="List of product specifications")
    private List<Specification> specList;

    @Schema(description="List of product SKUs")
    private List<Sku> skuList;

    @Data
    @Schema(description = "Product information")
    public static class GoodsInfo {

        @Schema(description="Product ID")
        private Long id;

        @Schema(description="Product name")
        private String name;

        @Schema(description="Original price of the product (in cents)")
        private Long originPrice;

        @Schema(description="Retail price of the product (in cents)")
        private Long price;

        @Schema(description="Sales volume")
        private Integer sales;

        @Schema(description="Album of product images")
        private List<String> album;

        @Schema(description="Product details")
        private String detail;
    }


    @Data
    @Schema(description = "Attribute information")
    public static class Attribute {
        @Schema(description="Attribute ID")
        private Long id;
        @Schema(description="Attribute name")
        private String name;
        @Schema(description="Attribute value")
        private String value;
    }

    @Data
    @Schema(description = "Specification information")
    public static class Specification {

        @Schema(description = "Specification name", example = "Color")
        private String name;

        @Schema(description = "List of specification values", example = "Black, White")
        private List<Value> values;

        @Data
        @Schema(description = "Specification value")
        public static class Value {
            @Schema(description="Specification value ID")
            private Long id;

            @Schema(description="Specification value")
            private String value;
        }
    }

    @Data
    @Schema(description = "Product SKU")
    public static class Sku {
        @Schema(description="SKU ID")
        private Long id;

        @Schema(description="SKU name")
        private String name;

        @Schema(description="Comma-separated list of specification value IDs")
        private String specIds;

        @Schema(description="Price")
        private Long price;

        @Schema(description="Stock")
        private Integer stock;

        @Schema(description="Product image URL")
        private String picUrl;

    }
}
