package com.smartport.auth.application.usecases;

import com.smartport.auth.application.exception.AuthException;
import com.smartport.auth.application.ports.outbound.AuthenticationRepository;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LogoutCommand {

    private final AuthenticationRepository authenticationRepository;

    @Transactional
    public void execute(String refreshToken) {
        if (refreshToken == null || refreshToken.isBlank()) {
            throw AuthException.unauthorized("Refresh token is missing.");
        }

        var token = authenticationRepository.getRefreshToken(refreshToken)
                .orElseThrow(() -> AuthException.unauthorized("Invalid refresh token."));

        // getRefreshToken only returns un-revoked tokens, so a revoked one is reported as invalid
        if (!token.getExpireAt().isAfter(Instant.now())) {
            throw AuthException.unauthorized("Refresh token expired.");
        }

        token.setRevoked(true);
        token.setRevokedAt(Instant.now());

        authenticationRepository.updateRefreshToken(token);
    }
}
