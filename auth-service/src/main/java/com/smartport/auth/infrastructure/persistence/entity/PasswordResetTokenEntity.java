package com.smartport.auth.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"IAM_AUTH_PASSWORDRESETTOKEN\"")
@Getter
@Setter
@NoArgsConstructor
public class PasswordResetTokenEntity {
    @Id
    @Column(name = "PASSWORD_RESET_TOKEN_ID", nullable = false)
    private UUID passwordResetTokenId;

    @Column(name = "USER_ID", nullable = false)
    private UUID userId;

    @Column(name = "TOKEN", nullable = false, length = 500)
    private String token;

    @Column(name = "EXPIRE_AT", nullable = false)
    private Instant expireAt;

    @Column(name = "IS_USED", nullable = false)
    private boolean used;

    @Column(name = "CREATED_AT", nullable = false)
    private Instant createdAt;
}
