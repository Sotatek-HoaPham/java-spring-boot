package com.example.auth.domain.service;

import com.example.auth.domain.model.HashedPassword;

public interface PasswordService {
    HashedPassword hashPassword(String plainPassword);
    boolean verifyPassword(String plainPassword, HashedPassword hashedPassword);
}
