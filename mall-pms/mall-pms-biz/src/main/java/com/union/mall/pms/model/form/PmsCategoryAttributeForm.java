package com.union.mall.pms.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
@Schema(description = "Attribute Form")
public class PmsCategoryAttributeForm {

    @Schema(description="Category ID")
    @NotNull
    private Long categoryId;

    @Schema(description="Attribute type (1: Specification; 2: Attribute)")
    @NotNull
    private Integer type;

    @Schema(description="List of attributes")
    @NotEmpty
    private List<Attribute> attributes;

    @Data
    public static class Attribute {

        @Schema(description="Attribute ID")
        private Long id;

        @Schema(description="Attribute name")
        @NotBlank
        private String name;
    }
}
