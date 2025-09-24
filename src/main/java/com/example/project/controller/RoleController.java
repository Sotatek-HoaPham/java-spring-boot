package com.example.project.controller;

import com.example.project.application.dto.RoleResponse;
import com.example.project.application.service.RoleApplicationService;
import com.example.project.dto.response.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
class RoleController {

    @Autowired
    private RoleApplicationService roleApplicationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<RoleResponse>>> findAll() {
        List<RoleResponse> roles = roleApplicationService.findAllRoles();
        return ResponseEntity.ok(ApiResponse.success(roles));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<RoleResponse>> findRoleById(@PathVariable Long id) {
        RoleResponse role = roleApplicationService.findRoleById(id);
        return ResponseEntity.ok(ApiResponse.success(role));
    }
}
