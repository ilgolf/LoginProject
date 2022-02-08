package com.login.project.loginProject.domain.auth.exception;

import com.login.project.loginProject.global.error.exception.EntityNotFoundException;
import com.login.project.loginProject.global.error.exception.ErrorCode;

public class EmailNotFoundException extends EntityNotFoundException {

    public EmailNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
