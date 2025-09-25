package com.example.auth.infrastructure.service;

import com.example.auth.domain.model.HashedPassword;
import com.example.auth.domain.service.PasswordService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordServiceImpl implements PasswordService {
    
    private final BCryptPasswordEncoder passwordEncoder;

    public PasswordServiceImpl() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public HashedPassword hashPassword(String plainPassword) {
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
        String hashed = passwordEncoder.encode(plainPassword);
        return HashedPassword.of(hashed);
    }

    @Override
    public boolean verifyPassword(String plainPassword, HashedPassword hashedPassword) {
        if (plainPassword == null || plainPassword.trim().isEmpty()) {
            throw new IllegalArgumentException("Plain password cannot be null or empty");
        }
        if (hashedPassword == null) {
            throw new IllegalArgumentException("Hashed password cannot be null");
        }
        return passwordEncoder.matches(plainPassword, hashedPassword.getValue());
    }
}
