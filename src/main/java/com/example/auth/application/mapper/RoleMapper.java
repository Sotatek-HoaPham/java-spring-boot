package com.example.auth.application.mapper;

import com.example.auth.application.dto.RoleResponse;
import com.example.auth.domain.model.Role;

public class RoleMapper {
    
    public static RoleResponse toResponse(Role role) {
        if (role == null) {
            return null;
        }
        
        return new RoleResponse(
            role.getId().getValue(),
            role.getName().getValue()
        );
    }
}
