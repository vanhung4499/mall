package com.union.mall.common.web.annotation;

import java.lang.annotation.*;

/**
 * Prevent Duplicate Resubmit Annotation
 *
 * @author vanhung4499
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface PreventDuplicateResubmit {

    /**
     * Expiration time (seconds) for preventing duplicate resubmits.
     * <p>
     * Defaults to 5 seconds to disallow duplicate submissions within this timeframe.
     */
    int expire() default 5;

}
