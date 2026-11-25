package com.smartport.auth.infrastructure.persistence.entity;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "IAM_Authentication_Email")
@Getter
@Setter
@NoArgsConstructor
public class EmailEntity {
    @Id
    @Column(name = "EMAIL_ID", nullable = false)
    private UUID emailId;

    @Column(name = "USER_ID", nullable = false)
    private UUID userId;

    @Column(name = "EMAIL", nullable = false, length = 255)
    private String emailAddress;

    @Column(name = "IS_PRIMARY", nullable = false)
    private boolean primary;

    @Column(name = "IS_VERIFIED", nullable = false)
    private boolean verified;

    @Column(name = "IS_DELETED", nullable = false)
    private boolean deleted;

    @Column(name = "DELETED_AT")
    private Instant deletedAt;
}
