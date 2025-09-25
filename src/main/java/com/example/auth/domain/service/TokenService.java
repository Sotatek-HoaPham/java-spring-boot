package com.example.auth.domain.service;

import com.example.auth.domain.model.TokenValue;
import com.example.auth.domain.model.UserId;

public interface TokenService {
    TokenValue generateToken(UserId userId);
    boolean validateToken(TokenValue tokenValue);
}
