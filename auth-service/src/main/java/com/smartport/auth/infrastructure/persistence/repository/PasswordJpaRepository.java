package com.smartport.auth.infrastructure.persistence.repository;

import com.smartport.auth.infrastructure.persistence.entity.PasswordEntity;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordJpaRepository extends JpaRepository<PasswordEntity, UUID> {

    Optional<PasswordEntity> findFirstByUserIdAndActiveTrueAndDeletedFalse(UUID userId);

    List<PasswordEntity> findByUserIdAndActiveTrue(UUID userId);

    boolean existsByUserIdAndActiveTrue(UUID userId);
}
