package com.union.mall.auth.model;

import com.union.mall.common.core.constant.GlobalConstants;
import com.union.mall.ums.dto.MemberAuthDTO;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
public class MemberDetails implements UserDetails {

    /**
     * Member ID
     */
    private Long id;

    /**
     * Member username (openid/mobile)
     */
    private String username;

    /**
     * Member status
     */
    private Boolean enabled;

    /**
     * Extension field: authentication identity, enum values include:
     * - [Define your enum values here]
     */
    private String authenticationIdentity;

    /**
     * Constructor for MemberDetails based on member authentication information.
     *
     * @param memAuthInfo Member authentication information
     */
    public MemberDetails(MemberAuthDTO memAuthInfo) {
        this.setId(memAuthInfo.getId());
        this.setUsername(memAuthInfo.getUsername());
        this.setEnabled(GlobalConstants.STATUS_YES.equals(memAuthInfo.getStatus()));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Currently returning an empty set; modify this method to return actual authorities if needed
        return Collections.EMPTY_SET;
    }

    @Override
    public String getPassword() {
        // Return the password if needed; currently returning null
        return null;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        // Return true if the account is not expired
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // Return true if the account is not locked
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // Return true if the credentials are not expired
        return true;
    }

    @Override
    public boolean isEnabled() {
        // Return the enabled status of the member
        return this.enabled;
    }
}
