package com.example.auth.domain.model;

import java.util.Objects;

public class TokenValue {
    private final String value;

    private TokenValue(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Token value cannot be null or empty");
        }
        this.value = value;
    }

    public static TokenValue of(String value) {
        return new TokenValue(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenValue that = (TokenValue) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "TokenValue{***}";
    }
}
