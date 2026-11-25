package com.smartport.auth.infrastructure.security;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "jwt")
public record JwtProperties(
        @NotBlank @Size(min = 32, message = "jwt.key must be at least 32 characters (HS256)") String key,
        @NotBlank String issuer,
        @NotBlank String audience,
        @Min(1) long accessTokenMinutes,
        @Min(1) long refreshTokenMinutes,
        @DefaultValue("8") @Min(1) long shortSessionHours) {
}
