package com.union.mall.common.mybatis.annotation;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */

import java.lang.annotation.*;

/**
 * MP data permission annotation.
 * <p>
 * Link: https://gitee.com/baomidou/mybatis-plus/issues/I37I90
 *
 * @author <a href="mailto:2256222053@qq.com">zc</a>
 * @since 2021-12-10 15:48
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface DataPermission {

    /**
     * Data permission interceptor {@link com.baomidou.mybatisplus.extension.plugins.inner.DataPermissionInterceptor}
     */
    String deptAlias() default "";

    String deptIdColumnName() default "dept_id";
    String userAlias() default "";

    String userIdColumnName() default "create_by";

}
