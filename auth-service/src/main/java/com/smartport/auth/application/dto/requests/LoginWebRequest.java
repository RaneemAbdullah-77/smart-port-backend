package com.smartport.auth.application.dto.requests;

import jakarta.validation.constraints.NotBlank;

public record LoginWebRequest(
        @NotBlank(message = "Email is required.") String email,
        @NotBlank(message = "Password is required.") String password,
        boolean rememberMe) {
}
