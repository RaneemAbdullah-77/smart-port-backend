package com.smartport.auth.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "IAM_Authentication_SetPasswordToken")
@Getter
@Setter
@NoArgsConstructor
public class SetPasswordTokenEntity {
    @Id
    @Column(name = "SET_PASSWORD_TOKEN_ID", nullable = false)
    private UUID setPasswordTokenId;

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
