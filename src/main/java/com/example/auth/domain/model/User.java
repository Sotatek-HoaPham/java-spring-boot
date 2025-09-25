package com.example.auth.domain.model;

import java.util.Objects;

/**
 * Domain Entity
 */
public class User {
    private final UserId id;
    private final Username username;
    private final Email email;
    private final HashedPassword password;
    private final FullName fullName;
    private final Role role;

    private User(UserId id, Username username, Email email, HashedPassword password, FullName fullName, Role role) {
        if (username == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        if (fullName == null) {
            throw new IllegalArgumentException("Full name cannot be null");
        }
        if (role == null) {
            throw new IllegalArgumentException("Role cannot be null");
        }
        
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.role = role;
    }

    public static User create(UserId id, Username username, Email email, HashedPassword password, FullName fullName, Role role) {
        return new User(id, username, email, password, fullName, role);
    }

    public static User createNew(Username username, Email email, HashedPassword password, FullName fullName, Role role) {
        return new User(null, username, email, password, fullName, role);
    }


    public User changeUsername(Username newUsername) {
        return new User(this.id, newUsername, this.email, this.password, this.fullName, this.role);
    }

    public User changeEmail(Email newEmail) {
        return new User(this.id, this.username, newEmail, this.password, this.fullName, this.role);
    }

    public User changeFullName(FullName newFullName) {
        return new User(this.id, this.username, this.email, this.password, newFullName, this.role);
    }

    public User changeRole(Role newRole) {
        return new User(this.id, this.username, this.email, this.password, this.fullName, newRole);
    }

    public UserId getId() {
        return id;
    }

    public Username getUsername() {
        return username;
    }

    public Email getEmail() {
        return email;
    }

    public HashedPassword getPassword() {
        return password;
    }

    public FullName getFullName() {
        return fullName;
    }

    public Role getRole() {
        return role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username=" + username +
                ", fullName=" + fullName +
                ", role=" + role +
                '}';
    }
}
