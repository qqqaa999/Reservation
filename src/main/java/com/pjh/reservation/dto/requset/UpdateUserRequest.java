package com.pjh.reservation.dto.requset;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateUserRequest(
        @NotBlank @Size(max = 255) String password,
        @NotBlank @Size(max = 100) String name,
        @Size(max = 20) String phone,
        @Email @Size(max = 100) String email
) {}
