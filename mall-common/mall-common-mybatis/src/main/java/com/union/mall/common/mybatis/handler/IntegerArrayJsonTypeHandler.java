package com.union.mall.common.mybatis.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

/**
 * Converts Integer array types to JSON.
 * <p>
 * Mainly used for object data; primitive type wrappers are not recommended.
 *
 * @author vanhung4499
 */
@Slf4j
@Component
@MappedTypes(value = {Integer[].class})
@MappedJdbcTypes(value = {JdbcType.VARCHAR}, includeNullJdbcType = true)
public class IntegerArrayJsonTypeHandler extends ArrayObjectJsonTypeHandler<Integer> {
    public IntegerArrayJsonTypeHandler() {
        super(Integer[].class);
    }
}
