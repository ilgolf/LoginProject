package com.login.project.loginProject.global.error.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

@Getter
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ErrorCode {

    // 공통 예외
    INVALID_INPUT_VALUE(400, "A001", "잘못된 입력 값 입니다."),
    ENTITY_NOT_FOUND(400, "A002", "개체를 찾을 수 없습니다."),
    METHOD_NOT_ALLOWED(405, "A003", "잘못된 요청 입니다."),
    INTERNAL_SERVER_ERROR(500, "A004", "서버 오류"),
    ACCESS_DENIED_ERROR(403, "A005", "잘못된 접근 입니다."),

    // 유저 예외
    EMAIL_DUPLICATION(400, "M001", "이미 존재하는 이메일 입니다."),
    LOGIN_INPUT_INVALID(400, "M002", "이메일과 아이디를 확인해주세요")
    ;

    private final String code;
    private final String message;
    private int status;

    ErrorCode(final int status, final String code, final String message) {
        this.status = status;
        this.message = message;
        this.code = code;
    }
}
