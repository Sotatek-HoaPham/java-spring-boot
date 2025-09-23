package com.example.project.dto.request;

import jakarta.validation.constraints.NotBlank;

public class CreateUserDTO {
    @NotBlank
    private String username;

    @NotBlank
    private String fullname;

    @NotBlank
    private String password;

    @NotBlank
    private Long roleId;

    public CreateUserDTO () {
    }

    public CreateUserDTO(String username, String fullname, String password, Long roleId) {
        this.username = username;
        this.fullname = fullname;
        this.password = password;
        this.roleId = roleId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
