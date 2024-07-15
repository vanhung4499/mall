package com.union.mall.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.union.mall.common.mybatis.annotation.DataPermission;
import com.union.mall.system.model.bo.UserBO;
import com.union.mall.system.model.bo.UserFormBO;
import com.union.mall.system.model.bo.UserProfileBO;
import com.union.mall.system.model.dto.UserAuthInfo;
import com.union.mall.system.model.entity.SysUser;
import com.union.mall.system.model.query.UserPageQuery;
import com.union.mall.system.model.vo.UserExportVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser> {

    /**
     * Get a paginated list of users.
     *
     * @param page        Pagination parameters
     * @param queryParams Query parameters
     * @return {@link List<UserBO>}
     */
    @DataPermission(deptAlias = "u")
    Page<UserBO> getUserPage(Page<UserBO> page, UserPageQuery queryParams);

    /**
     * Get user details based on user ID.
     *
     * @param userId User ID
     * @return {@link UserFormBO}
     */
    UserFormBO getUserDetail(Long userId);

    /**
     * Retrieve authentication information based on username.
     *
     * @param username Username
     * @return {@link UserAuthInfo}
     */
    UserAuthInfo getUserAuthInfo(String username);

    /**
     * Get the list of users for export.
     *
     * @param queryParams Query parameters
     * @return {@link List<UserExportVO>}
     */
    @DataPermission(deptAlias = "u")
    List<UserExportVO> listExportUsers(UserPageQuery queryParams);

    /**
     * Get user profile information for the user center.
     *
     * @param userId User ID
     * @return {@link UserProfileBO}
     */
    UserProfileBO getUserProfile(Long userId);
}
