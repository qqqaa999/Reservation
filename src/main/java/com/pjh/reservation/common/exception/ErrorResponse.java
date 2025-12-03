package com.pjh.reservation.common.exception;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public record ErrorResponse(
        int status,          // HTTP 상태 코드 (예: 400, 403, 500)
        String code,         // 상태 이름 또는 내부 코드 (예: BAD_REQUEST, FORBIDDEN)
        String message,      // 에러 메시지
        LocalDateTime timestamp
) {

    public static ErrorResponse of(HttpStatus status, String message) {
        return new ErrorResponse(
                status.value(),   // 400, 403, 500 ...
                status.name(),    // BAD_REQUEST, FORBIDDEN, INTERNAL_SERVER_ERROR ...
                message,
                LocalDateTime.now()
        );
    }

    public static ErrorResponse of(HttpStatus status, String code, String message) {
        return new ErrorResponse(
                status.value(),
                code,
                message,
                LocalDateTime.now()
        );
    }
}
