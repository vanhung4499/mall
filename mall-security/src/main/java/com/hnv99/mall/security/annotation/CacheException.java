package com.hnv99.mall.security.annotation;

import java.lang.annotation.*;

/**
 * Custom cache exception annotation, cache methods with this annotation will throw an exception
 * Created by hnv99 on 2024/1/6.
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}
