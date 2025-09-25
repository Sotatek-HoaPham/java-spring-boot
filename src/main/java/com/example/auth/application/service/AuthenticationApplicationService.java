package com.example.auth.application.service;

import com.example.auth.application.dto.LoginCommand;
import com.example.auth.application.dto.LoginResponse;
import com.example.auth.application.dto.UserResponse;
import com.example.auth.application.mapper.UserMapper;
import com.example.auth.domain.exception.ResourceNotFoundException;
import com.example.auth.domain.model.*;
import com.example.auth.domain.repository.UserRepository;
import com.example.auth.domain.service.AuthenticationDomainService;
import com.example.auth.domain.service.TokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Service
@Transactional
public class AuthenticationApplicationService {

    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final AuthenticationDomainService authenticationDomainService;

    public AuthenticationApplicationService(UserRepository userRepository,
                                            TokenService tokenService,
                                            AuthenticationDomainService authenticationDomainService) {
        this.userRepository = userRepository;
        this.tokenService = tokenService;
        this.authenticationDomainService = authenticationDomainService;
    }

    public LoginResponse login(LoginCommand command) {
        // Convert to domain objects
        Username username = Username.of(command.getUsername());

        // Find user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "username", command.getUsername()));

        // Authenticate user
        boolean isAuthenticated = authenticationDomainService.authenticateUser(user, command.getPassword());
        if (!isAuthenticated) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        // Generate token
        TokenValue tokenValue = tokenService.generateToken(user.getId());
        ExpirationDate expiresAt = ExpirationDate.fromNow(24); // 24 hours

        // Calculate expiration in seconds
        long expiresIn = ChronoUnit.SECONDS.between(LocalDateTime.now(), expiresAt.getValue());

        // Convert to response
        UserResponse userResponse = UserMapper.toResponse(user);

        return new LoginResponse(
                tokenValue.getValue(),
                "Bearer",
                expiresIn,
                userResponse
        );
    }


    public void logoutFromContext() {
        SecurityContextHolder.clearContext();
    }

    @Transactional(readOnly = true)
    public UserResponse getCurrentUserFromContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalArgumentException("User not authenticated");
        }

        // Get user ID from authentication principal (set by JWT filter)
        Long userId = Long.valueOf(authentication.getName());
        UserId userIdObj = UserId.of(userId);

        User user = userRepository.findById(userIdObj)
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

        return UserMapper.toResponse(user);
    }
}
