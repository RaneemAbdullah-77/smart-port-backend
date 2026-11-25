package com.smartport.auth.application.ports.outbound;

import java.time.Instant;
import java.util.UUID;

/** Merges the old IJwtService and IJwtServiceWeb (they only differed by the remember-me overload). */
public interface JwtService {

    String generateAccessToken(UUID userId);

    String generateRefreshToken();

    Instant getAccessTokenExpiration();

    /** Default refresh-token lifetime (jwt.refresh-token-minutes). */
    Instant getRefreshTokenExpiration();

    /** Remember-me aware lifetime: full lifetime when true, a short session otherwise. */
    Instant getRefreshTokenExpiration(boolean rememberMe);
}
