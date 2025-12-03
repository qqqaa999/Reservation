package com.pjh.reservation.user.web;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String loginId,
        String name,
        String phone,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
}