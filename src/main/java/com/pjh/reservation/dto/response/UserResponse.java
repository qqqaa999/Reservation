package com.pjh.reservation.dto.response;

import com.pjh.reservation.entity.User;

import java.time.LocalDateTime;

public record UserResponse (
        String loginId,
        String name,
        String phone,
        String email,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {

    public static UserResponse from(User user) {
        return new UserResponse(
                user.getLoginId(),
                user.getName(),
                user.getPhone(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }


}
