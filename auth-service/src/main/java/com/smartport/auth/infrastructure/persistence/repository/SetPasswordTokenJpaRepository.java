package com.smartport.auth.infrastructure.persistence.repository;

import com.smartport.auth.infrastructure.persistence.entity.SetPasswordTokenEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SetPasswordTokenJpaRepository
        extends JpaRepository<SetPasswordTokenEntity, UUID> {

    Optional<SetPasswordTokenEntity> findFirstByTokenAndUsedFalse(String token);
}
