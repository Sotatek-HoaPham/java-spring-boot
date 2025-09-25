package com.example.auth.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for User - Infrastructure concern
 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserJpaEntity, Long> {
    Optional<UserJpaEntity> findByUsername(String username);

    @Query("SELECT u FROM UserJpaEntity u JOIN FETCH u.role WHERE u.username = :username")
    Optional<UserJpaEntity> findByUsernameWithRole(@Param("username") String username);

    @Query("SELECT u FROM UserJpaEntity u JOIN FETCH u.role WHERE u.role.name = :roleName")
    List<UserJpaEntity> findUsersWithRoleByRoleName(@Param("roleName") String roleName);
}
