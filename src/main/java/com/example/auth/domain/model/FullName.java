package com.example.auth.domain.model;

import java.util.Objects;

public class FullName {
    private final String value;

    private FullName(String value) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException("Full name cannot be null or empty");
        }
        if (value.length() < 2 || value.length() > 100) {
            throw new IllegalArgumentException("Full name must be between 2 and 100 characters");
        }
        this.value = value.trim();
    }

    public static FullName of(String value) {
        return new FullName(value);
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FullName fullName = (FullName) o;
        return Objects.equals(value, fullName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "FullName{" + value + '}';
    }
}
