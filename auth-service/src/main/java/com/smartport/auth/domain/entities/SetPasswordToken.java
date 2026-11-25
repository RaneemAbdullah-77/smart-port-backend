package com.smartport.auth.domain.entities;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SetPasswordToken {
    private UUID setPasswordTokenId;
    private UUID userId;
    private String token = "";
    private Instant createdAt;
    private Instant expireAt;
    private boolean used;
}
