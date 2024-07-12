package com.union.mall.common.mybatis.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;


/**
 * MyBatis-Plus meta object handler for automatic field population.
 * <p>
 * Handles insertion and update of metadata fields like createTime and updateTime.
 * Implements {@link MetaObjectHandler} to provide automatic field population functionality.
 * <p>
 * For more details, see: https://mp.baomidou.com/guide/auto-fill-metainfo.html
 * </p>
 *
 * @author vanhung4499
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    /**
     * Automatically populates createTime during insertion.
     *
     * @param metaObject MetaObject instance carrying entity information.
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        this.strictInsertFill(metaObject, "createTime", () -> LocalDateTime.now(), LocalDateTime.class);
        this.strictUpdateFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class);
    }

    /**
     * Automatically populates updateTime during update.
     *
     * @param metaObject MetaObject instance carrying entity information.
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.strictUpdateFill(metaObject, "updateTime", () -> LocalDateTime.now(), LocalDateTime.class);
    }

}
