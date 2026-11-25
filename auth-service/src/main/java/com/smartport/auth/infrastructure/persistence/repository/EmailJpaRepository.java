package com.smartport.auth.infrastructure.persistence.repository;

import com.smartport.auth.infrastructure.persistence.entity.EmailEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailJpaRepository extends JpaRepository<EmailEntity, UUID> {

    Optional<EmailEntity> findFirstByEmailAddressAndDeletedFalse(String emailAddress);
}
