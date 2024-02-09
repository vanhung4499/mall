package com.hnv99.mall.portal.service;

import com.hnv99.mall.model.UmsMember;

public interface UmsMemberCacheService {
    /**
     * Delete member user cache
     */
    void delMember(Long memberId);

    /**
     * Get member user cache
     */
    UmsMember getMember(String username);

    /**
     * Set member user cache
     */
    void setMember(UmsMember member);

    /**
     * Set verification code
     */
    void setAuthCode(String telephone, String authCode);

    /**
     * Get verification code
     */
    String getAuthCode(String telephone);
}
