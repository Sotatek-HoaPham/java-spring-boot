package com.example.auth.application.service;

import com.example.auth.application.dto.CreateRoleCommand;
import com.example.auth.application.dto.RoleResponse;
import com.example.auth.application.mapper.RoleMapper;
import com.example.auth.domain.exception.ResourceAlreadyExistsException;
import com.example.auth.domain.exception.ResourceNotFoundException;
import com.example.auth.domain.model.Role;
import com.example.auth.domain.model.RoleId;
import com.example.auth.domain.model.RoleName;
import com.example.auth.domain.repository.RoleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoleApplicationService {

    private final RoleRepository roleRepository;

    public RoleApplicationService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public RoleResponse createRole(CreateRoleCommand command) {
        // Convert to domain objects
        RoleName roleName = RoleName.of(command.getName());
        
        // Check if role name already exists
        if (roleRepository.existsByName(roleName)) {
            throw new ResourceAlreadyExistsException("Role", "name", command.getName());
        }
        
        // Create domain entity
        Role role = Role.createNew(roleName);
        
        // Save
        Role savedRole = roleRepository.save(role);
        
        // Convert to response
        return RoleMapper.toResponse(savedRole);
    }

    @Transactional(readOnly = true)
    public List<RoleResponse> findAllRoles() {
        return roleRepository.findAll()
                .stream()
                .map(RoleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RoleResponse findRoleById(Long id) {
        Role role = roleRepository.findById(RoleId.of(id))
                .orElseThrow(() -> new ResourceNotFoundException("Role", "id", id));
        return RoleMapper.toResponse(role);
    }
}
