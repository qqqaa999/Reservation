package com.pjh.reservation.user.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserUpdateRequest(
        @Size(max = 100)
        String name,

        @Size(max = 20)
        String phone,

        @Email
        @Size(max = 100)
        String email
) {
}
