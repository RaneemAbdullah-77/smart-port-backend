package com.smartport.auth.presentation.rest;

import com.smartport.auth.application.dto.requests.*;
// import com.smartport.auth.application.dto.responses.*;
import com.smartport.auth.application.usecases.LoginWebCommand;
import jakarta.validation.Valid;
import java.util.LinkedHashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginWebController {

    private final LoginWebCommand loginWebCommand;
    private final RefreshTokenCookie refreshTokenCookie;

    @PostMapping("/login/web")
    public ResponseEntity<?> loginWeb(@Valid @RequestBody LoginWebRequest request) {
        var response = loginWebCommand.execute(request);

        if (!response.success()) {
            return ResponseEntity.badRequest().body(response);
        }

        // persistent cookie only when "remember me" is ticked, otherwise a session cookie
        var cookie = refreshTokenCookie.create(
                response.refreshToken(),
                response.rememberMe() ? response.refreshTokenExpiresAt() : null);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("success", response.success());
        body.put("message", response.message());
        body.put("accessToken", response.accessToken());
        body.put("accessTokenExpiresAt", response.accessTokenExpiresAt());

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, cookie.toString())
                .body(body);
    }
}
