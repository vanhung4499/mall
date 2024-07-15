package com.union.mall.system.listener;

/**
 * User Import Listener
 * <p>
 * The simplest read listener: https://easyexcel.opensource.alibaba.com/docs/current/quickstart/read
 *
 * @author vanhung4499
 */

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.union.mall.common.core.base.IBaseEnum;
import com.union.mall.common.core.constant.SystemConstants;
import com.union.mall.common.core.enums.GenderEnum;
import com.union.mall.common.core.enums.StatusEnum;
import com.union.mall.system.converter.UserConverter;
import com.union.mall.system.model.entity.SysRole;
import com.union.mall.system.model.entity.SysUser;
import com.union.mall.system.model.entity.SysUserRole;
import com.union.mall.system.model.vo.UserImportVO;
import com.union.mall.system.service.SysRoleService;
import com.union.mall.system.service.SysUserRoleService;
import com.union.mall.system.service.SysUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;

/**

 * @author haoxr
 * @since 2022/4/10 20:49
 */
@Slf4j
public class UserImportListener extends MyAnalysisEventListener<UserImportVO> {

    // Valid count
    private int validCount;

    // Invalid count
    private int invalidCount;

    // Import return message
    StringBuilder msg = new StringBuilder();

    // Department ID
    private final Long deptId;

    private final SysUserService userService;

    private final PasswordEncoder passwordEncoder;

    private final UserConverter userConverter;

    private final SysRoleService roleService;

    private final SysUserRoleService userRoleService;

    public UserImportListener(Long deptId) {
        this.deptId = deptId;
        this.userService = SpringUtil.getBean(SysUserService.class);
        this.passwordEncoder = SpringUtil.getBean(PasswordEncoder.class);
        this.roleService = SpringUtil.getBean(SysRoleService.class);
        this.userRoleService = SpringUtil.getBean(SysUserRoleService.class);
        this.userConverter = SpringUtil.getBean(UserConverter.class);
    }

    /**
     * Called for each data parsing
     * <p>
     * 1. Data validation; full field validation
     * 2. Data persistence;
     *
     * @param userImportVO    A row of data, similar to {@link AnalysisContext#readRowHolder()}
     * @param analysisContext
     */
    @Override
    public void invoke(UserImportVO userImportVO, AnalysisContext analysisContext) {
        log.info("Parsed one user data: {}", JSONUtil.toJsonStr(userImportVO));
        // Data validation
        StringBuilder validationMsg = new StringBuilder();

        String username = userImportVO.getUsername();
        if (StrUtil.isBlank(username)) {
            validationMsg.append("Username is empty;");
        } else {
            long count = userService.count(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, username));
            if (count > 0) {
                validationMsg.append("Username already exists;");
            }
        }

        String nickname = userImportVO.getNickname();
        if (StrUtil.isBlank(nickname)) {
            validationMsg.append("Nickname is empty;");
        }

        String mobile = userImportVO.getMobile();
        if (StrUtil.isBlank(mobile)) {
            validationMsg.append("Mobile number is empty;");
        } else {
            if (!Validator.isMobile(mobile)) {
                validationMsg.append("Mobile number is incorrect;");
            }
        }

        if (validationMsg.length() == 0) {
            // Validation passed, persist to database
            SysUser entity = userConverter.importVo2Entity(userImportVO);
            entity.setDeptId(deptId);   // Department
            entity.setPassword(passwordEncoder.encode(SystemConstants.DEFAULT_PASSWORD));   // Default password
            // Translate gender
            String genderLabel = userImportVO.getGender();
            if (StrUtil.isNotBlank(genderLabel)) {
                Integer genderValue = (Integer) IBaseEnum.getValueByLabel(genderLabel, GenderEnum.class);
                entity.setGender(genderValue);
            }

            // Role parsing
            String roleCodes = userImportVO.getRoleCodes();
            List<Long> roleIds = null;
            if (StrUtil.isNotBlank(roleCodes)) {
                roleIds = roleService.list(
                                new LambdaQueryWrapper<SysRole>()
                                        .in(SysRole::getCode, roleCodes.split(","))
                                        .eq(SysRole::getStatus, StatusEnum.ENABLE.getValue())
                                        .select(SysRole::getId)
                        ).stream()
                        .map(role -> role.getId())
                        .collect(Collectors.toList());
            }


            boolean saveResult = userService.save(entity);
            if (saveResult) {
                validCount++;
                // Save user role associations
                if (CollectionUtil.isNotEmpty(roleIds)) {
                    List<SysUserRole> userRoles = roleIds.stream()
                            .map(roleId -> new SysUserRole(entity.getId(), roleId))
                            .collect(Collectors.toList());
                    userRoleService.saveBatch(userRoles);
                }
            } else {
                invalidCount++;
                msg.append("Row " + (validCount + invalidCount) + " data save failed;<br/>");
            }
        } else {
            invalidCount++;
            msg.append("Row " + (validCount + invalidCount) + " data validation failed: ").append(validationMsg + "<br/>");
        }
    }

    /**
     * Called after all data parsing is completed
     *
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }

    @Override
    public String getMsg() {
        // Summary information
        String summaryMsg = StrUtil.format("User import completed: Successful {} entries, Failed {} entries;<br/>{}", validCount, invalidCount, msg);
        return summaryMsg;
    }
}
