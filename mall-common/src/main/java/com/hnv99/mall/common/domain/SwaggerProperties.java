package com.hnv99.mall.common.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
@Builder
public class SwaggerProperties {
    /**
     * API document generation base path
     */
    private String apiBasePackage;
    /**
     * Whether to enable login authentication
     */
    private boolean enableSecurity;
    /**
     * Document title
     */
    private String title;
    /**
     * Document description
     */
    private String description;
    /**
     * Document version
     */
    private String version;
    /**
     * Document contact person name
     */
    private String contactName;
    /**
     * Document contact person website
     */
    private String contactUrl;
    /**
     * Document contact person email
     */
    private String contactEmail;
}
