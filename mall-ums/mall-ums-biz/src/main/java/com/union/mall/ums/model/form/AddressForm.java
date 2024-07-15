package com.union.mall.ums.model.form;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Pattern;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Schema(description = "Address Form Object")
@Data
public class AddressForm {

    @Schema(description="Address ID")
    private Long id;

    @Schema(description="Consignee Name")
    private String consigneeName;

    @Schema(description="Consignee Mobile")
    @Pattern(regexp = "^1(3\\d|4[5-9]|5[0-35-9]|6[2567]|7[0-8]|8\\d|9[0-35-9])\\d{8}$", message = "{phone.valid}")
    private String consigneeMobile;

    @Schema(description="Province")
    private String province;

    @Schema(description="City")
    private String city;

    @Schema(description="Area")
    private String area;

    @Schema(description="Detailed Address")
    @Length(min = 1, max = 100, message = "{text.length.min}, {text.length.max}")
    private String detailAddress;

    @Schema(description="Is Default Address (1: Yes; 0: No)")
    private Integer defaulted;

}

