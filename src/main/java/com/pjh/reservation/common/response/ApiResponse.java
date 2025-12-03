package com.pjh.reservation.common.response;

public record ApiResponse<T>(
        boolean success,
        T data,
        String errorCode,
        String errorMessage
) {

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, data, null, null);
    }

    public static <T> ApiResponse<T> error(String errorCode, String errorMessage) {
        return new ApiResponse<>(false, null, errorCode, errorMessage);
    }
}
