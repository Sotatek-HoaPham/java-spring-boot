package com.example.auth.domain.model;

import java.util.Objects;
import java.util.UUID;

public class TokenId {
    private final UUID value;

    private TokenId(UUID value) {
        if (value == null) {
            throw new IllegalArgumentException("Token ID cannot be null");
        }
        this.value = value;
    }

    public static TokenId of(UUID value) {
        return new TokenId(value);
    }

    public static TokenId generate() {
        return new TokenId(UUID.randomUUID());
    }

    public UUID getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenId tokenId = (TokenId) o;
        return Objects.equals(value, tokenId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "TokenId{" + value + '}';
    }
}
