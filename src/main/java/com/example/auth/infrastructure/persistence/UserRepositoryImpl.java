package com.example.auth.infrastructure.persistence;

import com.example.auth.domain.model.*;
import com.example.auth.domain.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Infrastructure implementation of User Repository
 */
@Repository
public class UserRepositoryImpl implements UserRepository {
    
    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;

    public UserRepositoryImpl(UserJpaRepository userJpaRepository, RoleJpaRepository roleJpaRepository) {
        this.userJpaRepository = userJpaRepository;
        this.roleJpaRepository = roleJpaRepository;
    }

    @Override
    public User save(User user) {
        UserJpaEntity jpaEntity = toJpaEntity(user);
        UserJpaEntity savedEntity = userJpaRepository.save(jpaEntity);
        return toDomainEntity(savedEntity);
    }

    @Override
    public Optional<User> findById(UserId id) {
        return userJpaRepository.findById(id.getValue())
            .map(this::toDomainEntity);
    }

    @Override
    public Optional<User> findByUsername(Username username) {
        return userJpaRepository.findByUsername(username.getValue())
            .map(this::toDomainEntity);
    }

    @Override
    public List<User> findAll() {
        return userJpaRepository.findAll()
            .stream()
            .map(this::toDomainEntity)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UserId id) {
        userJpaRepository.deleteById(id.getValue());
    }

    @Override
    public boolean existsById(UserId id) {
        return userJpaRepository.existsById(id.getValue());
    }

    @Override
    public boolean existsByUsername(Username username) {
        return userJpaRepository.findByUsername(username.getValue()).isPresent();
    }

    private UserJpaEntity toJpaEntity(User user) {
        RoleJpaEntity roleJpaEntity = null;
        if (user.getRole() != null) {
            roleJpaEntity = roleJpaRepository.findById(user.getRole().getId().getValue())
                .orElseThrow(() -> new RuntimeException("Role not found"));
        }

        return new UserJpaEntity(
            user.getId() != null ? user.getId().getValue() : null,
            user.getUsername().getValue(),
            user.getEmail().getValue(),
            user.getPassword().getValue(),
            user.getFullName().getValue(),
            roleJpaEntity
        );
    }

    private User toDomainEntity(UserJpaEntity jpaEntity) {
        Role domainRole = null;
        if (jpaEntity.getRole() != null) {
            domainRole = Role.create(
                RoleId.of(jpaEntity.getRole().getId()),
                RoleName.of(jpaEntity.getRole().getName())
            );
        }

        return User.create(
            UserId.of(jpaEntity.getId()),
            Username.of(jpaEntity.getUsername()),
            Email.of(jpaEntity.getEmail()),
            HashedPassword.of(jpaEntity.getPassword()),
            FullName.of(jpaEntity.getFullname()),
            domainRole
        );
    }
}
