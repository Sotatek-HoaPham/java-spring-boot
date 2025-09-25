package com.example.auth.domain.model;

import java.util.Objects;

public class UserId {
    private final Long value;

    private UserId(Long value) {
        if (value == null || value <= 0) {
            throw new IllegalArgumentException("User ID must be a positive number");
        }
        this.value = value;
    }

    public static UserId of(Long value) {
        return new UserId(value);
    }

    public Long getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return Objects.equals(value, userId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "UserId{" + value + '}';
    }
}
