package com.example.project.controller;

import com.example.project.entity.Role;
import com.example.project.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    @GetMapping
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @GetMapping("/{id}")
    public Role findRoleById(@PathVariable Long id) {
        return roleService.findRoleById(id);
    }

   @DeleteMapping("/{id}")
    public void deleteRoleById(@PathVariable Long id) {
        roleService.deleteRoleById(id);
   }
}
