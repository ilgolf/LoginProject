package com.login.project.loginProject.global.error;

import com.login.project.loginProject.global.error.exception.BusinessException;
import com.login.project.loginProject.global.error.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ResponseError {

    private String errorMessage;
    private int status;
    private String code;

    private ResponseError(final ErrorCode code) {
        this.errorMessage = code.getMessage();
        this.status = code.getStatus();
        this.code = code.getCode();
    }

    public static ResponseError of(ErrorCode code) {
        return new ResponseError(code);
    }
}
