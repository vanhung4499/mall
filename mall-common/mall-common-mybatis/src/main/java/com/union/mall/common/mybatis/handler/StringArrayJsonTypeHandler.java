package com.union.mall.common.mybatis.handler;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;
import org.springframework.stereotype.Component;

/**
 * Type handler for converting String arrays to JSON format for database storage.
 * <p>
 * This class extends {@link ArrayObjectJsonTypeHandler} and specifies String[] as the generic type.
 * It handles mapping String arrays to and from JSON strings when interacting with the database.
 * <p>
 * For more details on how to use custom type handlers with MyBatis-Plus, refer to:
 * <a href="https://www.jianshu.com/p/ab832f3fe81c">https://www.jianshu.com/p/ab832f3fe81c</a>
 * </p>
 *
 * @author vanhung4499
 */
@Slf4j
@Component
@MappedTypes(value = {String[].class})
@MappedJdbcTypes(value = {JdbcType.OTHER}, includeNullJdbcType = true)
public class StringArrayJsonTypeHandler extends ArrayObjectJsonTypeHandler<String> {

    /**
     * Constructor that specifies the array type as String[].
     */
    public StringArrayJsonTypeHandler() {
        super(String[].class);
    }
}
