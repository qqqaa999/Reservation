package com.pjh.reservation.user.web;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UserCreateRequest(
        @NotBlank
        @Size(max = 50)
        String loginId,

        @NotBlank
        @Size(min = 4, max = 100)
        String password,

        @NotBlank
        @Size(max = 100)
        String name,

        @Size(max = 20)
        String phone,

        @Email
        @Size(max = 100)
        String email
) {
}
