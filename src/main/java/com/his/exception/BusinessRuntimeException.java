package com.his.exception;

import lombok.Getter;

/**
 * 业务运行时异常类
 * 用于抛出业务逻辑相关的异常
 *
 * @author HIS Team
 * @since 2025-12-27
 */
@Getter
public class BusinessRuntimeException extends RuntimeException {

    private final int code;
    private final BusinessException errorEnum;

    public BusinessRuntimeException(BusinessException errorEnum) {
        super(errorEnum.getMessage());
        this.code = errorEnum.getCode();
        this.errorEnum = errorEnum;
    }

    public BusinessRuntimeException(BusinessException errorEnum, String customMessage) {
        super(customMessage);
        this.code = errorEnum.getCode();
        this.errorEnum = errorEnum;
    }

    public BusinessRuntimeException(BusinessException errorEnum, Object... messageArgs) {
        super(errorEnum.formatMessage(messageArgs));
        this.code = errorEnum.getCode();
        this.errorEnum = errorEnum;
    }
}
