
package com.smartport.auth.infrastructure.persistence.repository;

import com.smartport.auth.infrastructure.persistence.entity.EmployeeEntity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeJpaRepository
        extends JpaRepository<EmployeeEntity, UUID> {
}
