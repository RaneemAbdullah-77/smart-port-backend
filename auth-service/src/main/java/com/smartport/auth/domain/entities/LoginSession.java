package com.smartport.auth.domain.entities;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LoginSession {
    private UUID sessionId;
    private UUID userId;
    private UUID refreshTokenId;
    private String deviceName;
    private String deviceType;
    private String ipAddress;
    private String userAgent;
    private Instant loginAt;
    private Instant logoutAt;
    private boolean active;
}
