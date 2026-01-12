package com.pjh.reservation.global.exception;

import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException {

    private final UserErrorCode errorCode;

    private UserException(UserErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public static UserException of(UserErrorCode errorCode, String message) {
        return new UserException(errorCode, message);
    }

    public HttpStatus status() {
        return errorCode.status();
    }

    public String code() {
        return errorCode.code();
    }
}
