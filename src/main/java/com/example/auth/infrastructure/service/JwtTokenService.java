package com.example.auth.infrastructure.service;

import com.example.auth.domain.model.TokenValue;
import com.example.auth.domain.model.UserId;
import com.example.auth.domain.service.TokenService;
import com.example.auth.infrastructure.AppProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JwtTokenService implements TokenService {

    @Autowired
    private AppProperties appProperties;

    private SecretKey getSigningKey() {
        String secret = appProperties.getSecurity().getJwtSecret();
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    @Override
    public TokenValue generateToken(UserId userId) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", userId.getValue());
        claims.put("type", "access");

        long expirationMs = appProperties.getSecurity().getJwtExpirationMs();
        Date expirationDate = new Date(System.currentTimeMillis() + expirationMs);

        String token = Jwts.builder()
                .claims(claims)
                .subject(userId.getValue().toString())
                .issuedAt(new Date())
                .expiration(expirationDate)
                .signWith(getSigningKey())
                .compact();

        return TokenValue.of(token);
    }

    @Override
    public boolean validateToken(TokenValue tokenValue) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(tokenValue.getValue())
                    .getPayload();

            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }


    public UserId getUserIdFromToken(TokenValue tokenValue) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(getSigningKey())
                    .build()
                    .parseSignedClaims(tokenValue.getValue())
                    .getPayload();

            Long userId = Long.valueOf(claims.getSubject());
            return UserId.of(userId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid token");
        }
    }

}
