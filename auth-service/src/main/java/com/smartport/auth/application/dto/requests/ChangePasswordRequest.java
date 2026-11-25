package com.smartport.auth.application.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(
        @NotBlank(message = "Token is required.") String token,
        @NotBlank(message = "New password is required.") String newPassword) {
}
