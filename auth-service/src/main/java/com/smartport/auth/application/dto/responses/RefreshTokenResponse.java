package com.smartport.auth.application.dto.responses;

import java.time.Instant;

public record RefreshTokenResponse(String accessToken, String refreshToken, Instant refreshTokenExpiresAt) {
}
