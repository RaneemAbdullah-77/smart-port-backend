package com.smartport.auth.domain.entities;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PasswordResetToken {
    private UUID passwordResetTokenId;
    private UUID userId;
    private String token = "";
    private Instant expireAt;
    private boolean used;
    private Instant createdAt;
}
