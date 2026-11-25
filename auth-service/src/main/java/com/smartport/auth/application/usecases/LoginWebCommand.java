package com.smartport.auth.application.usecases;

import com.smartport.auth.application.dto.requests.*;
import com.smartport.auth.application.dto.responses.*;
import com.smartport.auth.application.ports.outbound.AuthenticationRepository;
import com.smartport.auth.application.ports.outbound.JwtService;
import com.smartport.auth.application.ports.outbound.PasswordHasher;
import com.smartport.auth.domain.entities.RefreshToken;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginWebCommand {

    private static final String INVALID_CREDENTIALS = "Invalid email or password.";

    private final AuthenticationRepository authenticationRepository;
    private final PasswordHasher passwordHasher;
    private final JwtService jwtService;

    public LoginWebResponse execute(LoginWebRequest request) {
        var user = authenticationRepository.getByEmail(request.email()).orElse(null);

        if (user == null || !passwordHasher.verify(request.password(), user.passwordHash())) {
            return LoginWebResponse.failure(INVALID_CREDENTIALS);
        }

        String accessToken = jwtService.generateAccessToken(user.userId());
        String refreshToken = jwtService.generateRefreshToken();

        var refreshTokenEntity = new RefreshToken();
        refreshTokenEntity.setRefreshTokenId(UUID.randomUUID());
        refreshTokenEntity.setUserId(user.userId());
        refreshTokenEntity.setToken(refreshToken);
        refreshTokenEntity.setExpireAt(jwtService.getRefreshTokenExpiration(request.rememberMe()));
        refreshTokenEntity.setRevoked(false);
        refreshTokenEntity.setRevokedAt(null);

        authenticationRepository.saveRefreshToken(refreshTokenEntity);

        return new LoginWebResponse(
                true,
                "Login successful.",
                accessToken,
                jwtService.getAccessTokenExpiration(),
                refreshToken,
                refreshTokenEntity.getExpireAt(),
                request.rememberMe());
    }
}
