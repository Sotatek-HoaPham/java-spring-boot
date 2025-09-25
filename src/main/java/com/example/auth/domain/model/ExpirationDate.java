package com.example.auth.domain.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class ExpirationDate {
    private final LocalDateTime value;

    private ExpirationDate(LocalDateTime value) {
        if (value == null) {
            throw new IllegalArgumentException("Expiration date cannot be null");
        }
        this.value = value;
    }

    public static ExpirationDate of(LocalDateTime value) {
        return new ExpirationDate(value);
    }

    public static ExpirationDate now() {
        return new ExpirationDate(LocalDateTime.now());
    }

    public static ExpirationDate fromNow(long hours) {
        return new ExpirationDate(LocalDateTime.now().plusHours(hours));
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(value);
    }

    public boolean isValid() {
        return !isExpired();
    }

    public LocalDateTime getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExpirationDate that = (ExpirationDate) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "ExpirationDate{" + value + '}';
    }
}
