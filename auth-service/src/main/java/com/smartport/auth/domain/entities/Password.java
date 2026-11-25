package com.smartport.auth.domain.entities;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Password {
    private UUID passwordId;
    private UUID userId;
    private String passwordHash = "";
    private boolean deleted;
    private Instant deletedAt;
    private String passwordAlgorithm = "";
    private boolean active;
}
