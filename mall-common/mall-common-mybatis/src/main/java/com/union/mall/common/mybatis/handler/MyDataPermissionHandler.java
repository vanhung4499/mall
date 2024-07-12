package com.union.mall.common.mybatis.handler;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.handler.DataPermissionHandler;
import com.union.mall.common.core.base.IBaseEnum;
import com.union.mall.common.mybatis.enums.DataScopeEnum;
import com.union.mall.common.mybatis.annotation.DataPermission;
import com.union.mall.common.security.util.SecurityUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.operators.conditional.AndExpression;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;

import java.lang.reflect.Method;


/**
 * Data permission handler.
 * <p>
 * This class provides methods to apply data permission filters to SQL queries based on user roles.
 *
 * @author vanhung4499
 */

@Slf4j
public class MyDataPermissionHandler implements DataPermissionHandler {

    /**
     * Retrieves the SQL segment with applied data permission filters.
     *
     * @param where              Current SQL expression for WHERE clause
     * @param mappedStatementId  Mapped statement ID
     * @return Expression with applied data permission filters
     */
    @Override
    @SneakyThrows
    public Expression getSqlSegment(Expression where, String mappedStatementId) {

        // Extract class and method names from mappedStatementId
        Class<?> clazz = Class.forName(mappedStatementId.substring(0, mappedStatementId.lastIndexOf(".")));
        String methodName = mappedStatementId.substring(mappedStatementId.lastIndexOf(".") + 1);

        // Retrieve all methods declared in the class
        Method[] methods = clazz.getDeclaredMethods();

        // Iterate through methods to find data permission annotation
        for (Method method : methods) {
            DataPermission annotation = method.getAnnotation(DataPermission.class);

            // If the method is not annotated with @DataPermission, skip it
            if (annotation == null) {
                return where;
            }

            // Skip data permission filtering for super administrators
            if (SecurityUtils.isRoot()) {
                return where;
            }

            // Apply data permission filter if the method matches the mappedStatementId
            if (ObjectUtils.isNotEmpty(annotation) &&
                    (method.getName().equals(methodName) || (method.getName() + "_COUNT").equals(methodName))) {
                return dataScopeFilter(annotation.deptAlias(), annotation.deptIdColumnName(),
                        annotation.userAlias(), annotation.userIdColumnName(), where);
            }
        }

        // Return original WHERE expression if no matching method or annotation found
        return where;
    }

    /**
     * Constructs the data scope filter condition for SQL queries.
     *
     * @param deptAlias         Alias for department column
     * @param deptIdColumnName  Column name for department ID
     * @param userAlias         Alias for user column
     * @param userIdColumnName  Column name for user ID
     * @param where             Current WHERE expression
     * @return Constructed WHERE expression with data scope filter
     */
    @SneakyThrows
    public static Expression dataScopeFilter(String deptAlias, String deptIdColumnName,
                                             String userAlias, String userIdColumnName, Expression where) {

        // Construct column names with aliases if provided
        String deptColumnName = StrUtil.isNotBlank(deptAlias) ? (deptAlias + "." + deptIdColumnName) : deptIdColumnName;
        String userColumnName = StrUtil.isNotBlank(userAlias) ? (userAlias + "." + userIdColumnName) : userIdColumnName;

        // Retrieve data scope level for current user
        Integer dataScope = SecurityUtils.getDataScope();
        DataScopeEnum dataScopeEnum = IBaseEnum.getEnumByValue(dataScope, DataScopeEnum.class);

        Long deptId, userId;
        String appendSqlStr;

        // Apply data scope filtering based on data scope level
        switch (dataScopeEnum) {
            case ALL -> {
                return where; // No data scope filtering applied
            }
            case DEPT -> {
                deptId = SecurityUtils.getDeptId();
                appendSqlStr = deptColumnName + " = " + deptId; // Filter by department ID
            }
            case SELF -> {
                userId = SecurityUtils.getUserId();
                appendSqlStr = userColumnName + " = " + userId; // Filter by user ID
            }
            default -> {
                // Default case: department and sub-department data scope filtering
                deptId = SecurityUtils.getDeptId();
                appendSqlStr = deptColumnName + " IN ( SELECT id FROM sys_dept WHERE id = " + deptId +
                        " OR find_in_set( " + deptId + " , tree_path ) )";
            }
        }

        // If appendSqlStr is blank, return original WHERE expression
        if (StrUtil.isBlank(appendSqlStr)) {
            return where;
        }

        // Parse the appended SQL string to create a new expression
        Expression appendExpression = CCJSqlParserUtil.parseCondExpression(appendSqlStr);

        // Combine original WHERE expression with the new appended expression using AND
        if (where == null) {
            return appendExpression;
        } else {
            return new AndExpression(where, appendExpression);
        }
    }
}
