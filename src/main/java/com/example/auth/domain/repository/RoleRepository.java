package com.example.auth.domain.repository;

import com.example.auth.domain.model.Role;
import com.example.auth.domain.model.RoleId;
import com.example.auth.domain.model.RoleName;

import java.util.List;
import java.util.Optional;

public interface RoleRepository {
    Role save(Role role);
    Optional<Role> findById(RoleId id);
    Optional<Role> findByName(RoleName name);
    List<Role> findAll();
    void deleteById(RoleId id);
    boolean existsById(RoleId id);
    boolean existsByName(RoleName name);
}
