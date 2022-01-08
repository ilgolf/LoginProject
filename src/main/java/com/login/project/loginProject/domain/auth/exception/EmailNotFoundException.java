package com.login.project.loginProject.domain.auth.exception;

import com.login.project.loginProject.global.error.exception.BusinessException;
import com.login.project.loginProject.global.error.exception.ErrorCode;

public class EmailNotFoundException extends BusinessException {

    public EmailNotFoundException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }
}
