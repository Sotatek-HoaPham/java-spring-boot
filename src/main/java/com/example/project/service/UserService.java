package com.example.project.service;

import com.example.project.dto.request.CreateUserDTO;
import com.example.project.entity.Role;
import com.example.project.entity.User;
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
        System.out.printf("id: %d", id);
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(CreateUserDTO userDto) {
        Role role = roleRepository.getRolesById(userDto.getRoleId());
        String hashedPassword = passwordService.hashPassword(userDto.getPassword());
        User user = new User(userDto.getUsername(), hashedPassword, userDto.getFullname(), role);
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }
    
    /**
     * Verify user password
     * @param username user's username
     * @param rawPassword plain text password
     * @return true if password matches
     */
    public boolean verifyUserPassword(String username, String rawPassword) {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            return false;
        }
        return passwordService.verifyPassword(rawPassword, user.getPassword());
    }
}
