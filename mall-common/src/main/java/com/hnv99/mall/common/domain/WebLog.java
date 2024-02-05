package com.hnv99.mall.common.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class WebLog {
    /**
     * Operation description
     */
    private String description;

    /**
     * Operating user
     */
    private String username;

    /**
     * Operation time
     */
    private Long startTime;

    /**
     * Time consumed
     */
    private Integer spendTime;

    /**
     * Base path
     */
    private String basePath;

    /**
     * URI
     */
    private String uri;

    /**
     * URL
     */
    private String url;

    /**
     * Request type
     */
    private String method;

    /**
     * IP address
     */
    private String ip;

    /**
     * Request parameters
     */
    private Object parameter;

    /**
     * Return result
     */
    private Object result;
}
