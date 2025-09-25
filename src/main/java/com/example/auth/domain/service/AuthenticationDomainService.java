package com.example.auth.domain.service;

import com.example.auth.domain.exception.BusinessLogicException;
import com.example.auth.domain.model.User;
import com.example.auth.domain.model.Username;
import com.example.auth.domain.model.HashedPassword;
public class AuthenticationDomainService {
    private final PasswordService passwordService;

    public AuthenticationDomainService(PasswordService passwordService) {
        this.passwordService = passwordService;
    }

    /**
     * Business rule: Authenticate user with username and password
     */
    public boolean authenticateUser(User user, String plainPassword) {
        if (user == null) {
            return false;
        }
        
        return passwordService.verifyPassword(plainPassword, user.getPassword());
    }

    /**
     * Business rule: Validate password strength
     */
    public void validatePasswordStrength(String password) {
        if (password == null || password.trim().isEmpty()) {
            throw new BusinessLogicException("Password cannot be null or empty");
        }
        
        if (password.length() < 8) {
            throw new BusinessLogicException("Password must be at least 8 characters long");
        }
        
        if (!password.matches(".*[A-Z].*")) {
            throw new BusinessLogicException("Password must contain at least one uppercase letter");
        }
        
        if (!password.matches(".*[0-9].*")) {
            throw new BusinessLogicException("Password must contain at least one number");
        }
        
        if (!password.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw new BusinessLogicException("Password must contain at least one special character");
        }
    }

}
