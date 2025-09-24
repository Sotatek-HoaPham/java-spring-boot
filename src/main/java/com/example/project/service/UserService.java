package com.example.project.service;

import com.example.project.dto.request.CreateUserDTO;
import com.example.project.entity.Role;
import com.example.project.entity.User;
import com.example.project.exception.ResourceAlreadyExistsException;
import com.example.project.exception.ResourceNotFoundException;
import com.example.project.exception.BusinessLogicException;
import com.example.project.repository.RoleRepository;
import com.example.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
    
    @Autowired
    private PasswordService passwordService;

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessLogicException("User ID must be a positive number");
        }
        
        return userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

    public User createUser(CreateUserDTO userDto) {
        // validations
        if (userDto == null) {
            throw new BusinessLogicException("User data cannot be null");
        }

        User existingUser = userRepository.findUserByUsername(userDto.getUsername());
        if (existingUser != null) {
            throw new ResourceAlreadyExistsException("User", "username", userDto.getUsername());
        }

        Role role = roleRepository.getRolesById(userDto.getRoleId());
        if (role == null) {
            throw new ResourceNotFoundException("Role", "id", userDto.getRoleId());
        }

        if (userDto.getPassword() == null || userDto.getPassword().length() < 6) {
            throw new BusinessLogicException("Password must be at least 6 characters long");
        }
        
        String hashedPassword = passwordService.hashPassword(userDto.getPassword());
        User user = new User(userDto.getUsername(), hashedPassword, userDto.getFullname(), role);
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        if (id == null || id <= 0) {
            throw new BusinessLogicException("User ID must be a positive number");
        }

        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("User", "id", id);
        }
        
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User user) {
        if (id == null || id <= 0) {
            throw new BusinessLogicException("User ID must be a positive number");
        }
        
        if (user == null) {
            throw new BusinessLogicException("User data cannot be null");
        }

        User existingUser = userRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("User", "id", id));

        if (user.getUsername() != null && !user.getUsername().trim().isEmpty()) {
            // Check if new username already exists (excluding current user)
            User userWithSameUsername = userRepository.findUserByUsername(user.getUsername());
            if (userWithSameUsername != null && !userWithSameUsername.getId().equals(id)) {
                throw new ResourceAlreadyExistsException("User", "username", user.getUsername());
            }
            existingUser.setUsername(user.getUsername());
        }

        if (user.getPassword() != null && !user.getPassword().trim().isEmpty()) {
            if (user.getPassword().length() < 6) {
                throw new BusinessLogicException("Password must be at least 6 characters long");
            }
            String hashedPassword = passwordService.hashPassword(user.getPassword());
            existingUser.setPassword(hashedPassword);
        }
        
        if (user.getRole() != null) {
            existingUser.setRole(user.getRole());
        }
        
        return userRepository.save(existingUser);
    }
    
    /**
     * Verify user password
     * @param username user's username
     * @param rawPassword plain text password
     * @return true if password matches
     */
    public boolean verifyUserPassword(String username, String rawPassword) {
        if (username == null || username.trim().isEmpty()) {
            throw new BusinessLogicException("Username cannot be null or empty");
        }
        
        if (rawPassword == null || rawPassword.trim().isEmpty()) {
            throw new BusinessLogicException("Password cannot be null or empty");
        }
        
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            return false;
        }
        return passwordService.verifyPassword(rawPassword, user.getPassword());
    }
}
