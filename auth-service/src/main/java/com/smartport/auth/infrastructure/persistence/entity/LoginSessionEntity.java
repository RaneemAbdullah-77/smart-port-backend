package com.smartport.auth.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "IAM_Auth_LoginSession")
@Getter
@Setter
@NoArgsConstructor
public class LoginSessionEntity {
    @Id
    @Column(name = "SESSION_ID", nullable = false)
    private UUID sessionId;

    @Column(name = "USER_ID", nullable = false)
    private UUID userId;

    @Column(name = "REFRESH_TOKEN_ID")
    private UUID refreshTokenId;

    @Column(name = "DEVICE_NAME", length = 100)
    private String deviceName;

    @Column(name = "DEVICE_TYPE", length = 50)
    private String deviceType;

    @Column(name = "IP_ADDRESS", length = 45)
    private String ipAddress;

    @Column(name = "USER_AGENT", length = 255)
    private String userAgent;

    @Column(name = "LOGIN_AT", nullable = false)
    private Instant loginAt;

    @Column(name = "LOGOUT_AT")
    private Instant logoutAt;

    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean active;
}
