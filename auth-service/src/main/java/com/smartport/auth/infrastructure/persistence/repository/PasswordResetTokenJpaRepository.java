package com.smartport.auth.infrastructure.persistence.repository;

import com.smartport.auth.infrastructure.persistence.entity.PasswordResetTokenEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PasswordResetTokenJpaRepository
        extends JpaRepository<PasswordResetTokenEntity, UUID> {

    Optional<PasswordResetTokenEntity> findFirstByTokenAndUsedFalse(String token);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("update PasswordResetTokenEntity t set t.used = true "
            + "where t.userId = :userId and t.used = false")
    int markAllUsedByUserId(@Param("userId") UUID userId);
}
