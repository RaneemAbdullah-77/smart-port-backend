package com.smartport.auth.infrastructure.persistence.repository;

import com.smartport.auth.infrastructure.persistence.entity.RefreshTokenEntity;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshTokenEntity, UUID> {

    /** Token must be un-revoked and its owner must still have a non-deleted e-mail. */
    @Query("""
            select r from RefreshTokenEntity r
            where r.token = :token
              and r.revoked = false
              and exists (select 1 from EmailEntity e
                          where e.userId = r.userId and e.deleted = false)
            """)
    Optional<RefreshTokenEntity> findActiveByToken(@Param("token") String token);
}
