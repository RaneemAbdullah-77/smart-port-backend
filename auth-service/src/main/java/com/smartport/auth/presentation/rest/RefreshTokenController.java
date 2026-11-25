package com.smartport.auth.presentation.rest;

import com.smartport.auth.application.dto.requests.*;
// import com.smartport.auth.application.dto.responses.*;
import com.smartport.auth.application.usecases.RefreshTokenCommand;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshTokenCommand refreshTokenCommand;
    private final RefreshTokenCookie refreshTokenCookie;

    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(
            @CookieValue(name = RefreshTokenCookie.NAME, required = false) String refreshToken) {

        if (refreshToken == null || refreshToken.isEmpty()) {
            return ResponseEntity.status(401).body(Map.of("success", false, "message", "Refresh token missing"));
        }

        var result = refreshTokenCommand.execute(new RefreshTokenRequest(refreshToken));

        // replace the cookie with the rotated refresh token
        var cookie = refreshTokenCookie.create(result.refreshToken(), result.refreshTokenExpiresAt());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(Map.of("accessToken", result.accessToken()));
    }
}
