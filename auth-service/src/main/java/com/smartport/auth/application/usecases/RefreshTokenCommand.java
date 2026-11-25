package com.smartport.auth.application.usecases;

import com.smartport.auth.application.dto.requests.*;
import com.smartport.auth.application.dto.responses.*;
import com.smartport.auth.application.exception.AuthException;
import com.smartport.auth.application.ports.outbound.AuthenticationRepository;
import com.smartport.auth.application.ports.outbound.JwtService;
import java.time.Instant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RefreshTokenCommand {

    private final AuthenticationRepository repository;
    private final JwtService jwtService;

    @Transactional
    public RefreshTokenResponse execute(RefreshTokenRequest request) {
        var refreshToken = repository.getRefreshToken(request.refreshToken())
                .orElseThrow(() -> AuthException.unauthorized("Invalid Refresh Token"));

        if (refreshToken.getExpireAt().isBefore(Instant.now())) {
            throw AuthException.unauthorized("Refresh Token Expired");
        }

        String accessToken = jwtService.generateAccessToken(refreshToken.getUserId());
        String newRefresh = jwtService.generateRefreshToken();

        // token rotation: the same row gets a new token value and a new expiry
        refreshToken.setToken(newRefresh);
        refreshToken.setExpireAt(jwtService.getRefreshTokenExpiration());

        repository.updateRefreshToken(refreshToken);

        return new RefreshTokenResponse(accessToken, newRefresh, refreshToken.getExpireAt());
    }
}
