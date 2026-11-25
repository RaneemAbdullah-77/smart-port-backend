package com.smartport.auth.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "IAM_Authentication_Password")
@Getter
@Setter
@NoArgsConstructor
public class PasswordEntity {
    @Id
    @Column(name = "PASSWORD_ID", nullable = false)
    private UUID passwordId;

    @Column(name = "USER_ID", nullable = false)
    private UUID userId;

    @Column(name = "PASSWORD_HASH", nullable = false, length = 255)
    private String passwordHash;

    @Column(name = "PASSWORD_ALGORITHM", nullable = false, length = 50)
    private String passwordAlgorithm;

    @Column(name = "IS_ACTIVE", nullable = false)
    private boolean active;

    @Column(name = "IS_DELETED", nullable = false)
    private boolean deleted;

    @Column(name = "DELETED_AT")
    private Instant deletedAt;
}
