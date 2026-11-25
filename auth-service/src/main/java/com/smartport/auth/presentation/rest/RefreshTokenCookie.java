package com.smartport.auth.presentation.rest;

import com.smartport.auth.presentation.config.CookieProperties;
import java.time.Duration;
import java.time.Instant;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;

/** Builds the HttpOnly "refreshToken" cookie used by login, refresh and logout. */
@Component
public class RefreshTokenCookie {

    public static final String NAME = "refreshToken";

    private final CookieProperties properties;

    public RefreshTokenCookie(CookieProperties properties) {
        this.properties = properties;
    }

    /** @param expiresAt null => session cookie (removed when the browser closes) */
    public ResponseCookie create(String token, Instant expiresAt) {
        ResponseCookie.ResponseCookieBuilder builder = base(token);
        if (expiresAt != null) {
            builder.maxAge(Math.max(0, Duration.between(Instant.now(), expiresAt).getSeconds()));
        }
        return builder.build();
    }

    public ResponseCookie delete() {
        return base("").maxAge(0).build();
    }

    private ResponseCookie.ResponseCookieBuilder base(String value) {
        return ResponseCookie.from(NAME, value)
                .httpOnly(true)
                .secure(properties.secure())
                .sameSite(properties.sameSite())
                .path("/");
    }
}
