package com.union.mall.system.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
public class UserImportVO {

    @ExcelProperty(value = "Username")
    private String username;

    @ExcelProperty(value = "Nickname")
    private String nickname;

    @ExcelProperty(value = "Gender")
    private String gender;

    @ExcelProperty(value = "Mobile")
    private String mobile;

    @ExcelProperty(value = "Email")
    private String email;

    @ExcelProperty("Role Codes")
    private String roleCodes;

}
