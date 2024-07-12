package com.union.mall.common.core.result;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 404 bug!
 *
 * @author vanhung4499
 */
@AllArgsConstructor
@NoArgsConstructor
public enum ResultCode implements IResultCode, Serializable {

    SUCCESS("00000", "All is well"),

    USER_ERROR("A0001", "User-side error"),
    USER_LOGIN_ERROR("A0200", "User login exception"),

    USER_NOT_EXIST("A0201", "User does not exist"),
    USER_ACCOUNT_LOCKED("A0202", "User account is locked"),
    USER_ACCOUNT_INVALID("A0203", "User account is invalid"),

    USERNAME_OR_PASSWORD_ERROR("A0210", "Username or password error"),
    PASSWORD_ENTER_EXCEED_LIMIT("A0211", "Password entry exceeds limit"),
    CLIENT_AUTHENTICATION_FAILED("A0212", "Client authentication failed"),
    TOKEN_INVALID("A0230", "Token invalid or expired"),
    TOKEN_ACCESS_FORBIDDEN("A0231", "Token access forbidden"),

    AUTHORIZED_ERROR("A0300", "Access authorization exception"),
    ACCESS_UNAUTHORIZED("A0301", "Unauthorized access"),
    FORBIDDEN_OPERATION("A0302", "Demo environment prohibits adding, modifying, or deleting important data. Please deploy locally for testing"),
    REPEAT_SUBMIT_ERROR("A0303", "Your request has been submitted. Please do not resubmit or wait a moment and try again."),

    PARAM_ERROR("A0400", "User request parameter error"),
    RESOURCE_NOT_FOUND("A0401", "Requested resource not found"),
    PARAM_IS_NULL("A0410", "Required parameter is null"),

    USER_UPLOAD_FILE_ERROR("A0700", "User file upload exception"),
    USER_UPLOAD_FILE_TYPE_NOT_MATCH("A0701", "User uploaded file type does not match"),
    USER_UPLOAD_FILE_SIZE_EXCEEDS("A0702", "User uploaded file size exceeds limit"),
    USER_UPLOAD_IMAGE_SIZE_EXCEEDS("A0703", "User uploaded image size exceeds limit"),

    SYSTEM_EXECUTION_ERROR("B0001", "System execution error"),
    SYSTEM_EXECUTION_TIMEOUT("B0100", "System execution timeout"),
    SYSTEM_ORDER_PROCESSING_TIMEOUT("B0100", "System order processing timeout"),

    SYSTEM_DISASTER_RECOVERY_TRIGGER("B0200", "System disaster recovery triggered"),
    FLOW_LIMITING("B0210", "System flow limiting"),
    DEGRADATION("B0220", "System function degradation"),

    SYSTEM_RESOURCE_ERROR("B0300", "System resource exception"),
    SYSTEM_RESOURCE_EXHAUSTION("B0310", "System resource exhaustion"),
    SYSTEM_RESOURCE_ACCESS_ERROR("B0320", "System resource access exception"),
    SYSTEM_READ_DISK_FILE_ERROR("B0321", "Failed to read disk file"),

    CALL_THIRD_PARTY_SERVICE_ERROR("C0001", "Error calling third-party service"),
    MIDDLEWARE_SERVICE_ERROR("C0100", "Middleware service error"),
    INTERFACE_NOT_EXIST("C0113", "Interface does not exist"),

    MESSAGE_SERVICE_ERROR("C0120", "Message service error"),
    MESSAGE_DELIVERY_ERROR("C0121", "Message delivery error"),
    MESSAGE_CONSUMPTION_ERROR("C0122", "Message consumption error"),
    MESSAGE_SUBSCRIPTION_ERROR("C0123", "Message subscription error"),
    MESSAGE_GROUP_NOT_FOUND("C0124", "Message group not found"),

    DATABASE_ERROR("C0300", "Database service error"),
    DATABASE_TABLE_NOT_EXIST("C0311", "Table does not exist"),
    DATABASE_COLUMN_NOT_EXIST("C0312", "Column does not exist"),
    DATABASE_DUPLICATE_COLUMN_NAME("C0321", "Multiple columns with the same name in multi-table join"),
    DATABASE_DEADLOCK("C0331", "Database deadlock"),
    DATABASE_PRIMARY_KEY_CONFLICT("C0341", "Primary key conflict");

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }

    private String code;

    private String msg;

    @Override
    public String toString() {
        return "{" +
                "\"code\":\"" + code + '\"' +
                ", \"msg\":\"" + msg + '\"' +
                '}';
    }

    public static ResultCode getValue(String code){
        for (ResultCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return SYSTEM_EXECUTION_ERROR; // Default to system execution error
    }
}
