package com.example.auth.domain.model;

import java.util.Objects;

public class RoleName {
    private final String value;

    private RoleName(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Role name cannot be null or empty");
        }
        if (value.length() > 50) {
            throw new IllegalArgumentException("Role name must be at most 50 characters");
        }
        this.value = value.trim();
    }

    public static RoleName of(String value) {
        return new RoleName(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoleName roleName = (RoleName) o;
        return Objects.equals(value, roleName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "RoleName{" + value + '}';
    }
}
