package com.smartport.auth.domain.entities;

import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Email {
    private UUID emailId;
    private UUID userId;
    private String emailAddress = "";
    private boolean primary;
    private boolean deleted;
    private Instant deletedAt;
    private boolean verified;
}
