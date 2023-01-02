package com.sparta.team7_project.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {
    TOKEN_FALSE("TOKEN_ERROR", "사용자 권한이 없습니다."),
    ENTITY_NOT_FOUND("NOT_FOUND", "데이터가 존재하지 않습니다."),
    INVALID_ERROR("INVALID_ERROR", "에러 발생"),
    PASSWORD_FALSE("PASSWORD_FALSE", "Password가 일치하지 않습니다.");

    private final String code;
    private final String message;

    ErrorCode(String code, String message){
        this.code =code;
        this.message = message;
    }
}
