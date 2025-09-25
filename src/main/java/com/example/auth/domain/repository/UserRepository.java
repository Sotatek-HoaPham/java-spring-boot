package com.example.auth.domain.repository;

import com.example.auth.domain.model.User;
import com.example.auth.domain.model.UserId;
import com.example.auth.domain.model.Username;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findById(UserId id);
    Optional<User> findByUsername(Username username);
    List<User> findAll();
    void deleteById(UserId id);
    boolean existsById(UserId id);
    boolean existsByUsername(Username username);
}
