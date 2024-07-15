package com.union.mall.system.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.union.mall.common.core.result.PageResult;
import com.union.mall.common.core.result.Result;
import com.union.mall.common.web.annotation.PreventDuplicateResubmit;
import com.union.mall.system.listener.UserImportListener;
import com.union.mall.system.model.dto.UserAuthInfo;
import com.union.mall.system.model.entity.SysUser;
import com.union.mall.system.model.form.UserForm;
import com.union.mall.system.model.form.UserRegisterForm;
import com.union.mall.system.model.query.UserPageQuery;
import com.union.mall.system.model.vo.*;
import com.union.mall.system.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.List;


/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Tag(name = "01. User API")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class SysUserController {

    private final SysUserService userService;

    @Operation(summary = "User Pagination List")
    @GetMapping("/page")
    public PageResult<UserPageVO> getUserPage(
            @ParameterObject UserPageQuery queryParams
    ) {
        IPage<UserPageVO> result = userService.getUserPage(queryParams);
        return PageResult.success(result);
    }

    @Operation(summary = "Add User")
    @PostMapping
    @PreAuthorize("@ss.hasPerm('sys:user:add')")
    @PreventDuplicateResubmit
    public Result saveUser(
            @RequestBody @Valid UserForm userForm
    ) {
        boolean result = userService.saveUser(userForm);
        return Result.judge(result);
    }

    @Operation(summary = "User Form Data")
    @GetMapping("/{userId}/form")
    public Result<UserForm> getUserForm(
            @Parameter(description = "User ID") @PathVariable Long userId
    ) {
        UserForm formData = userService.getUserFormData(userId);
        return Result.success(formData);
    }

    @Operation(summary = "Update User")
    @PutMapping(value = "/{userId}")
    @PreAuthorize("@ss.hasPerm('sys:user:edit')")
    public Result updateUser(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @RequestBody @Validated UserForm userForm
    ) {
        boolean result = userService.updateUser(userId, userForm);
        return Result.judge(result);
    }

    @Operation(summary = "Delete Users")
    @DeleteMapping("/{ids}")
    @PreAuthorize("@ss.hasPerm('sys:user:delete')")
    public Result deleteUsers(
            @Parameter(description = "User ID(s), separated by commas if multiple") @PathVariable String ids
    ) {
        boolean result = userService.deleteUsers(ids);
        return Result.judge(result);
    }

    @Operation(summary = "Update User Password")
    @PatchMapping(value = "/{userId}/password")
    @PreAuthorize("@ss.hasPerm('sys:user:reset_pwd')")
    public Result updatePassword(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @RequestParam String password
    ) {
        boolean result = userService.updatePassword(userId, password);
        return Result.judge(result);
    }

    @Operation(summary = "Update User Status")
    @PatchMapping(value = "/{userId}/status")
    public Result updateUserStatus(
            @Parameter(description = "User ID") @PathVariable Long userId,
            @Parameter(description = "User status (1: enabled; 0: disabled)") @RequestParam Integer status
    ) {
        boolean result = userService.update(new LambdaUpdateWrapper<SysUser>()
                .eq(SysUser::getId, userId)
                .set(SysUser::getStatus, status)
        );
        return Result.judge(result);
    }

    @Operation(summary = "Get User Authentication Information", hidden = true)
    @GetMapping("/{username}/authInfo")
    public Result<UserAuthInfo> getUserAuthInfo(
            @Parameter(description = "Username") @PathVariable String username
    ) {
        UserAuthInfo userAuthInfo = userService.getUserAuthInfo(username);
        return Result.success(userAuthInfo);
    }

    @Operation(summary = "Get Current User Information")
    @GetMapping("/me")
    public Result<UserInfoVO> getCurrentUserInfo() {
        UserInfoVO userInfoVO = userService.getCurrentUserInfo();
        return Result.success(userInfoVO);
    }

    @Operation(summary = "Logout")
    @DeleteMapping("/logout")
    public Result logout() {
        boolean result = userService.logout();
        return Result.judge(result);
    }

    @Operation(summary = "Download User Import Template")
    @GetMapping("/template")
    public void downloadTemplate(HttpServletResponse response) throws IOException {
        String fileName = "User_Import_Template.xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

        String fileClassPath = "excel-templates" + File.separator + fileName;
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(fileClassPath);

        ServletOutputStream outputStream = response.getOutputStream();
        ExcelWriter excelWriter = EasyExcel.write(outputStream).withTemplate(inputStream).build();

        excelWriter.finish();
    }

    @Operation(summary = "Import Users")
    @PostMapping("/import")
    public Result importUsers(
            @Parameter(description = "Department ID") Long deptId,
            MultipartFile file
    ) throws IOException {
        UserImportListener listener = new UserImportListener(deptId);
        EasyExcel.read(file.getInputStream(), UserImportVO.class, listener).sheet().doRead();
        String msg = listener.getMsg();
        return Result.success(msg);
    }

    @Operation(summary = "Export Users")
    @GetMapping("/export")
    public void exportUsers(
            @ParameterObject UserPageQuery queryParams,
            HttpServletResponse response
    ) throws IOException {
        String fileName = "User_List.xlsx";
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));

        List<UserExportVO> exportUserList = userService.listExportUsers(queryParams);
        EasyExcel.write(response.getOutputStream(), UserExportVO.class).sheet("User List")
                .doWrite(exportUserList);
    }

    @Operation(summary = "Register User")
    @PostMapping("/register")
    public Result registerUser(
            @RequestBody @Valid UserRegisterForm userRegisterForm
    ) {
        boolean result = userService.registerUser(userRegisterForm);
        return Result.judge(result);
    }

//    @Operation(summary = "Send Registration SMS Verification Code")
//    @PostMapping("/register/sms_code")
//    public Result sendRegistrationSmsCode(
//            @Parameter(description = "Mobile Number") @RequestParam String mobile
//    ) {
//        boolean result = userService.sendRegistrationSmsCode(mobile);
//        return Result.judge(result);
//    }

    @Operation(summary = "Get User Profile Information")
    @GetMapping("/profile")
    public Result getUserProfile() {
        UserProfileVO userProfile = userService.getUserProfile();
        return Result.success(userProfile);
    }
}
