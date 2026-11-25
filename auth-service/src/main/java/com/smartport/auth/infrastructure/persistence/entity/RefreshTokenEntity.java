package com.smartport.auth.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"IAM_AUTH_REFRESHTOKEN\"")
@Getter
@Setter
@NoArgsConstructor
public class RefreshTokenEntity {
    @Id
    @Column(name = "REFRESH_TOKEN_ID", nullable = false)
    private UUID refreshTokenId;

    @Column(name = "USER_ID", nullable = false)
    private UUID userId;

    @Column(name = "TOKEN", nullable = false, length = 500)
    private String token;

    @Column(name = "EXPIRE_AT", nullable = false)
    private Instant expireAt;

    @Column(name = "IS_REVOKED", nullable = false)
    private boolean revoked;

    @Column(name = "REVOKED_AT")
    private Instant revokedAt;
}
