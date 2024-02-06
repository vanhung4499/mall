package com.hnv99.mall.security.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * Interface for dynamic permission related services
 * Created by hnv99 on 2024/2/6.
 */
public interface DynamicSecurityService {
    /**
     * Load resource ANT wildcard and resource corresponding MAP
     */
    Map<String, ConfigAttribute> loadDataSource();
}
