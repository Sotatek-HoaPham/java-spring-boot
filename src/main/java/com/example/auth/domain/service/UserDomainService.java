package com.example.auth.domain.service;

import com.example.auth.domain.exception.ResourceAlreadyExistsException;
import com.example.auth.domain.model.User;
import com.example.auth.domain.model.Username;
import com.example.auth.domain.repository.UserRepository;
public class UserDomainService {
    private final UserRepository userRepository;

    public UserDomainService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Business rule: Username must be unique
     */
    public void ensureUsernameIsUnique(Username username) {
        if (userRepository.existsByUsername(username)) {
            throw new ResourceAlreadyExistsException("User", "username", username.getValue());
        }
    }

    /**
     * Business rule: User must exist before operations
     */
    public User ensureUserExists(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        return user;
    }
}
