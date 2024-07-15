package com.union.mall.auth.model;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.union.mall.common.core.enums.StatusEnum;
import com.union.mall.system.dto.UserAuthInfo;
import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * System user details (includes username, password, and authorities).
 * <p>
 * Username and password are used for authentication, and upon successful authentication, authorities are granted.
 *
 * @author haoxr
 * @since 3.0.0
 */
@Data
public class SysUserDetails implements UserDetails, CredentialsContainer {

    /**
     * Extension field: User ID
     */
    private Long userId;

    /**
     * Extension field: Department ID
     */
    private Long deptId;

    /**
     * User role data scope collection
     */
    private Integer dataScope;

    /**
     * Default fields
     */
    private String username;
    private String password;
    private Boolean enabled;
    private Collection<GrantedAuthority> authorities;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private Set<String> perms;

    /**
     * System management user
     */
    public SysUserDetails(UserAuthInfo user) {
        this.setUserId(user.getUserId());
        this.setUsername(user.getUsername());
        this.setDeptId(user.getDeptId());
        this.setDataScope(user.getDataScope());
        this.setPassword("{bcrypt}" + user.getPassword());
        this.setEnabled(StatusEnum.ENABLE.getValue().equals(user.getStatus()));
        if (CollectionUtil.isNotEmpty(user.getRoles())) {
            authorities = user.getRoles().stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }
        this.setPerms(user.getPerms());
    }

    public SysUserDetails(
            Long userId,
            String username,
            String password,
            Integer dataScope,
            Long deptId,
            boolean enabled,
            boolean accountNonExpired,
            boolean credentialsNonExpired,
            boolean accountNonLocked,
            Set<? extends GrantedAuthority> authorities
    ) {
        Assert.isTrue(username != null && !username.isEmpty() && password != null,
                "Cannot pass null or empty values to constructor");
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.dataScope = dataScope;
        this.deptId = deptId;
        this.enabled = enabled;
        this.accountNonExpired = accountNonExpired;
        this.credentialsNonExpired = credentialsNonExpired;
        this.accountNonLocked = accountNonLocked;
        this.authorities = Collections.unmodifiableSet(authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }
}
