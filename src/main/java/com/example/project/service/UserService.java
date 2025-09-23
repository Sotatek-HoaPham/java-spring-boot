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

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User createUser(CreateUserDTO userDto) {
        Role role = roleRepository.getRolesById(userDto.getRoleId());
        User user = new User(userDto.getUsername(), userDto.getPassword(), userDto.getFullname(), role);
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    public User updateUser(Long id, User user) {
        user.setId(id);
        return userRepository.save(user);
    }
}
