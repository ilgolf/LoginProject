package com.login.project.loginProject.domain.member.exception;

import com.login.project.loginProject.global.error.exception.EntityNotFoundException;
import com.login.project.loginProject.global.error.exception.ErrorCode;

public class MemberNotFoundException extends EntityNotFoundException {

    public MemberNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
