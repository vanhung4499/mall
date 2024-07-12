package com.union.mall.system.model.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
@ColumnWidth(20)
public class UserExportVO {

    @ExcelProperty(value = "Username")
    private String username;

    @ExcelProperty(value = "Nickname")
    private String nickname;

    @ExcelProperty(value = "Department")
    private String deptName;

    @ExcelProperty(value = "Gender")
    private String gender;

    @ExcelProperty(value = "Mobile")
    private String mobile;

    @ExcelProperty(value = "Email")
    private String email;

    @ExcelProperty(value = "Create Time")
    @DateTimeFormat("yyyy/MM/dd HH:mm:ss")
    private LocalDateTime createTime;

}
