package com.union.mall.common.web.exception;

import com.union.mall.common.core.result.IResultCode;
import lombok.Getter;

/**
 * Custom business exception
 *
 * @author vanhung4499
 */
@Getter
public class BizException extends RuntimeException {

    public IResultCode resultCode;

    public BizException(IResultCode errorCode) {
        super(errorCode.getMsg());
        this.resultCode = errorCode;
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }
}
