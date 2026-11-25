package com.smartport.auth.infrastructure.security;

import com.smartport.auth.application.ports.outbound.JwtService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.UUID;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Service;

@Service
public class JwtServiceImpl implements JwtService {

    private final JwtProperties properties;
    private final SecretKey signingKey;

    public JwtServiceImpl(JwtProperties properties) {
        this.properties = properties;
        this.signingKey = Keys.hmacShaKeyFor(properties.key().getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public String generateAccessToken(UUID userId) {
        return Jwts.builder()
                .subject(userId.toString())
                .id(UUID.randomUUID().toString())
                .issuer(properties.issuer())
                .audience().add(properties.audience()).and()
                .expiration(Date.from(getAccessTokenExpiration()))
                .signWith(signingKey, Jwts.SIG.HS256)
                .compact();
    }

    @Override
    public String generateRefreshToken() {
        return UUID.randomUUID().toString().replace("-", "")
                + UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    public Instant getAccessTokenExpiration() {
        return Instant.now().plus(properties.accessTokenMinutes(), ChronoUnit.MINUTES);
    }

    @Override
    public Instant getRefreshTokenExpiration() {
        return Instant.now().plus(properties.refreshTokenMinutes(), ChronoUnit.MINUTES);
    }

    @Override
    public Instant getRefreshTokenExpiration(boolean rememberMe) {
        if (rememberMe) {
            return getRefreshTokenExpiration();
        }
        // no "remember me" -> short session
        return Instant.now().plus(properties.shortSessionHours(), ChronoUnit.HOURS);
    }
}
