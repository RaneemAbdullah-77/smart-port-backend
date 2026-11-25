package com.smartport.auth.infrastructure.persistence.repository;

import com.smartport.auth.infrastructure.persistence.entity.LoginSessionEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginSessionJpaRepository extends JpaRepository<LoginSessionEntity, UUID> {
}
