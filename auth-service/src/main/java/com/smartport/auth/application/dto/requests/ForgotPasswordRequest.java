package com.smartport.auth.application.dto.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record ForgotPasswordRequest(
        @NotBlank(message = "Email is required.") @Email(message = "Email is not valid.") String email,
        String countryCode,
        String phoneNumber) {
}
