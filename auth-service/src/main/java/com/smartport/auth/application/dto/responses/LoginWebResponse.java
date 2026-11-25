package com.smartport.auth.application.dto.responses;

import java.time.Instant;

public record LoginWebResponse(
        boolean success,
        String message,
        String accessToken,
        Instant accessTokenExpiresAt,
        String refreshToken,
        Instant refreshTokenExpiresAt,
        boolean rememberMe) {

    public static LoginWebResponse failure(String message) {
        return new LoginWebResponse(false, message, "", null, "", null, false);
    }
}
