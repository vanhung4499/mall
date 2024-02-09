package com.hnv99.mall.portal.service;

import com.hnv99.mall.model.UmsMember;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

public interface UmsMemberService {
    /**
     * Retrieve a member by their username
     */
    UmsMember getByUsername(String username);

    /**
     * Retrieve a member by their ID
     */
    UmsMember getById(Long id);

    /**
     * Register a new user
     */
    @Transactional
    void register(String username, String password, String telephone, String authCode);

    /**
     * Generate an authentication code
     */
    String generateAuthCode(String telephone);

    /**
     * Update a user's password
     */
    @Transactional
    void updatePassword(String telephone, String password, String authCode);

    /**
     * Get the currently logged-in member
     */
    UmsMember getCurrentMember();

    /**
     * Update a member's integration points by their ID
     */
    void updateIntegration(Long id, Integer integration);

    /**
     * Load a user's details by their username
     */
    UserDetails loadUserByUsername(String username);

    /**
     * Get a token after login
     */
    String login(String username, String password);

    /**
     * Refresh a token
     */
    String refreshToken(String token);
}
