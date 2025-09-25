package com.example.auth.application.service;

import com.example.auth.application.dto.CreateUserCommand;
import com.example.auth.application.dto.UpdateUserCommand;
import com.example.auth.application.dto.UserResponse;
import com.example.auth.application.mapper.UserMapper;
import com.example.auth.domain.exception.ResourceNotFoundException;
import com.example.auth.domain.model.*;
import com.example.auth.domain.repository.RoleRepository;
import com.example.auth.domain.repository.UserRepository;
import com.example.auth.domain.service.PasswordService;
import com.example.auth.domain.service.UserDomainService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserApplicationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordService passwordService;
    private final UserDomainService userDomainService;

    public UserApplicationService(UserRepository userRepository,
                                  RoleRepository roleRepository,
                                  PasswordService passwordService,
                                  UserDomainService userDomainService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordService = passwordService;
        this.userDomainService = userDomainService;
    }

    @Transactional(readOnly = true)
    public List<UserResponse> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UserResponse findUserById(Long id) {
        UserId userId = UserId.of(id);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        return UserMapper.toResponse(user);
    }

    public UserResponse createUser(CreateUserCommand command) {
        // Convert to domain objects
        Username username = Username.of(command.getUsername());
        FullName fullName = FullName.of(command.getFullName());
        RoleId roleId = RoleId.of(command.getRoleId());

        // Business validation
        userDomainService.ensureUsernameIsUnique(username);

        // Find role
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", command.getRoleId()));

        // Hash password
        HashedPassword hashedPassword = passwordService.hashPassword(command.getPassword());

        // Create user
        Email email = Email.of(command.getEmail());
        User user = User.createNew(username, email, hashedPassword, fullName, role);
        User savedUser = userRepository.save(user);

        return UserMapper.toResponse(savedUser);
    }

    public UserResponse updateUser(Long id, UpdateUserCommand command) {
        UserId userId = UserId.of(id);
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        User updatedUser = existingUser;

        // Update username if provided
        if (command.getUsername() != null && !command.getUsername().trim().isEmpty()) {
            Username newUsername = Username.of(command.getUsername());
            if (!newUsername.equals(existingUser.getUsername())) {
                userDomainService.ensureUsernameIsUnique(newUsername);
                updatedUser = updatedUser.changeUsername(newUsername);
            }
        }

        // Update full name if provided
        if (command.getFullName() != null && !command.getFullName().trim().isEmpty()) {
            FullName newFullName = FullName.of(command.getFullName());
            updatedUser = updatedUser.changeFullName(newFullName);
        }


        // Update role if provided
        if (command.getRoleId() != null) {
            RoleId newRoleId = RoleId.of(command.getRoleId());
            Role newRole = roleRepository.findById(newRoleId)
                    .orElseThrow(() -> new ResourceNotFoundException("Role", "id", command.getRoleId()));
            updatedUser = updatedUser.changeRole(newRole);
        }

        User savedUser = userRepository.save(updatedUser);
        return UserMapper.toResponse(savedUser);
    }

    public void deleteUser(Long id) {
        UserId userId = UserId.of(id);
        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        userRepository.deleteById(userId);
    }

    @Transactional(readOnly = true)
    public boolean verifyUserPassword(String username, String password) {
        Username usernameObj = Username.of(username);
        User user = userRepository.findByUsername(usernameObj)
                .orElse(null);

        if (user == null) {
            return false;
        }

        return passwordService.verifyPassword(password, user.getPassword());
    }
}
