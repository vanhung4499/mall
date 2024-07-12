package com.union.mall.common.core.constant;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
public interface RedisConstants {

    /**
     * Prefix for blacklist token keys
     */
    String TOKEN_BLACKLIST_PREFIX = "token:blacklist:";

    /**
     * Prefix for captcha code keys
     */
    String CAPTCHA_CODE_PREFIX = "captcha_code:";

    /**
     * Prefix for login SMS verification code keys
     */
    String LOGIN_SMS_CODE_PREFIX = "sms_code:login";

    /**
     * Prefix for register SMS verification code keys
     */
    String REGISTER_SMS_CODE_PREFIX = "sms_code:register";

    /**
     * Prefix for role and permissions cache keys
     */
    String ROLE_PERMS_PREFIX = "role_perms:";

    /**
     * Key for JWT key set (contains public and private keys)
     */
    String JWK_SET_KEY = "jwk_set";

}
