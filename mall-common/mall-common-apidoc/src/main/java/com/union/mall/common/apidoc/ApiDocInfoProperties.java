package com.union.mall.common.apidoc;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@Data
@ConfigurationProperties(prefix = "springdoc.info")
public class ApiDocInfoProperties {

    /**
     * API documentation title
     */
    private String title;

    /**
     * API documentation version
     */
    private String version;

    /**
     * API documentation description
     */
    private String description;

    /**
     * Contact information
     */
    private Contact contact;

    /**
     * License information
     */
    private License license;

    @Data
    public static class Contact {
        /**
         * Contact person's name
         */
        private String name;

        /**
         * Contact person's URL
         */
        private String url;

        /**
         * Contact person's email
         */
        private String email;
    }

    /**
     * License information
     */
    @Data
    public static class License {
        /**
         * License name
         */
        private String name;

        /**
         * License URL
         */
        private String url;
    }
}
