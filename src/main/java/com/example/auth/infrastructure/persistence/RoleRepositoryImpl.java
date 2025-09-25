package com.example.auth.infrastructure.persistence;

import com.example.auth.domain.model.Role;
import com.example.auth.domain.model.RoleId;
import com.example.auth.domain.model.RoleName;
import com.example.auth.domain.repository.RoleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class RoleRepositoryImpl implements RoleRepository {
    
    private final RoleJpaRepository roleJpaRepository;

    public RoleRepositoryImpl(RoleJpaRepository roleJpaRepository) {
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public Role save(Role role) {
        RoleJpaEntity jpaEntity = toJpaEntity(role);
        RoleJpaEntity savedEntity = roleJpaRepository.save(jpaEntity);
        return toDomainEntity(savedEntity);
    }

    @Override
    public Optional<Role> findById(RoleId id) {
        return roleJpaRepository.findById(id.getValue())
            .map(this::toDomainEntity);
    }

    @Override
    public Optional<Role> findByName(RoleName name) {
        return roleJpaRepository.findByName(name.getValue())
            .map(this::toDomainEntity);
    }

    @Override
    public List<Role> findAll() {
        return roleJpaRepository.findAll()
            .stream()
            .map(this::toDomainEntity)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(RoleId id) {
        roleJpaRepository.deleteById(id.getValue());
    }

    @Override
    public boolean existsById(RoleId id) {
        return roleJpaRepository.existsById(id.getValue());
    }

    @Override
    public boolean existsByName(RoleName name) {
        return roleJpaRepository.existsByName(name.getValue());
    }

    private RoleJpaEntity toJpaEntity(Role role) {
        return new RoleJpaEntity(
            role.getId() != null ? role.getId().getValue() : null,
            role.getName().getValue()
        );
    }

    private Role toDomainEntity(RoleJpaEntity jpaEntity) {
        return Role.create(
            RoleId.of(jpaEntity.getId()),
            RoleName.of(jpaEntity.getName())
        );
    }
}
