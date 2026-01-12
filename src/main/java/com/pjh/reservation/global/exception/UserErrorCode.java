package com.pjh.reservation.global.exception;

import org.springframework.http.HttpStatus;

public enum UserErrorCode {

    USER_DUPLICATED(HttpStatus.CONFLICT, "USER_DUPLICATED"),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_NOT_FOUND");

    private final HttpStatus status;
    private final String code;

    UserErrorCode(HttpStatus status, String code) {
        this.status = status;
        this.code = code;
    }

    public HttpStatus status() {
        return status;
    }

    public String code() {
        return code;
    }
}
