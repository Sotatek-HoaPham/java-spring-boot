package com.example.project.controller;

import com.example.project.application.dto.CreateUserCommand;
import com.example.project.application.dto.UpdateUserCommand;
import com.example.project.application.dto.UserResponse;
import com.example.project.application.service.UserApplicationService;
import com.example.project.dto.response.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
class UserController {

    @Autowired
    private UserApplicationService userApplicationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<UserResponse>>> findAll() {
        List<UserResponse> users = userApplicationService.findAllUsers();
        return ResponseEntity.ok(ApiResponse.success(users));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<UserResponse>> createUser(@Valid @RequestBody CreateUserCommand command) {
        UserResponse createdUser = userApplicationService.createUser(command);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success(createdUser));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> findById(@PathVariable Long id) {
        UserResponse user = userApplicationService.findUserById(id);
        return ResponseEntity.ok(ApiResponse.success(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@PathVariable Long id, @Valid @RequestBody UpdateUserCommand command) {
        UserResponse updatedUser = userApplicationService.updateUser(id, command);
        return ResponseEntity.ok(ApiResponse.success(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable Long id) {
        userApplicationService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.success("User deleted successfully"));
    }
    
    @PostMapping("/verify-password")
    public ResponseEntity<ApiResponse<Boolean>> verifyPassword(
            @RequestParam String username, 
            @RequestParam String password) {
        boolean isValid = userApplicationService.verifyUserPassword(username, password);
        return ResponseEntity.ok(ApiResponse.success(isValid, "Password verification completed"));
    }

}
