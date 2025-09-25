package com.example.auth.application.dto;

import jakarta.validation.constraints.Size;

public class UpdateUserCommand {
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Size(min = 2, max = 100, message = "Full name must be between 2 and 100 characters")
    private String fullName;


    private Long roleId;

    public UpdateUserCommand() {}

    public UpdateUserCommand(String username, String fullName, Long roleId) {
        this.username = username;
        this.fullName = fullName;
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
