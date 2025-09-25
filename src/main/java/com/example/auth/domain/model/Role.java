package com.example.auth.domain.model;

import java.util.Objects;

/**
 * Domain Entity
 */
public class Role {
    private final RoleId id;
    private final RoleName name;

    private Role(RoleId id, RoleName name) {
        if (name == null) {
            throw new IllegalArgumentException("Role name cannot be null");
        }
        this.id = id;
        this.name = name;
    }

    public static Role create(RoleId id, RoleName name) {
        return new Role(id, name);
    }

    public static Role createNew(RoleName name) {
        return new Role(null, name);
    }

    public RoleId getId() {
        return id;
    }

    public RoleName getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return Objects.equals(id, role.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name=" + name +
                '}';
    }
}
