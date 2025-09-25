package com.example.auth.infrastructure.config;

import com.example.auth.domain.service.AuthenticationDomainService;
import com.example.auth.domain.service.PasswordService;
import com.example.auth.domain.service.UserDomainService;
import com.example.auth.domain.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public AuthenticationDomainService authenticationDomainService(PasswordService passwordService) {
        return new AuthenticationDomainService(passwordService);
    }

    @Bean
    public UserDomainService userDomainService(UserRepository userRepository) {
        return new UserDomainService(userRepository);
    }
}
