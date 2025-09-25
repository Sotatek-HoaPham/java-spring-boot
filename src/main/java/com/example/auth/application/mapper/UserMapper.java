package com.example.auth.application.mapper;

import com.example.auth.application.dto.RoleResponse;
import com.example.auth.application.dto.UserResponse;
import com.example.auth.domain.model.Role;
import com.example.auth.domain.model.User;

public class UserMapper {
    
    public static UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }
        
        RoleResponse roleResponse = user.getRole() != null 
            ? new RoleResponse(user.getRole().getId().getValue(), user.getRole().getName().getValue())
            : null;
            
        return new UserResponse(
            user.getId().getValue(),
            user.getUsername().getValue(),
            user.getFullName().getValue(),
            roleResponse
        );
    }
    
    public static RoleResponse toRoleResponse(Role role) {
        return new RoleResponse(
            role.getId().getValue(),
            role.getName().getValue()
        );
    }
}
