package com.example.auth.infrastructure;

import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@ConfigurationProperties(prefix = "app")
@Component
@Validated
public class AppProperties {

    @NotBlank(message = "Application name is required")
    private String name;

    @NotBlank(message = "Application version is required")
    private String version;

    private Security security;

    private List<String> allowedOrigins;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Security getSecurity() {
        return security;
    }

    public void setSecurity(Security security) {
        this.security = security;
    }

    public List<String> getAllowedOrigins() {
        return allowedOrigins;
    }

    public void setAllowedOrigins(List<String> allowedOrigins) {
        this.allowedOrigins = allowedOrigins;
    }


    public static class Security {
        @NotBlank(message = "JWT secret is required")
        private String jwtSecret;

        private int jwtExpirationMs = 86400000; // 24 hours

        private List<String> corsAllowedMethods;

        public String getJwtSecret() {
            return jwtSecret;
        }

        public void setJwtSecret(String jwtSecret) {
            this.jwtSecret = jwtSecret;
        }

        public int getJwtExpirationMs() {
            return jwtExpirationMs;
        }

        public void setJwtExpirationMs(int jwtExpirationMs) {
            this.jwtExpirationMs = jwtExpirationMs;
        }

        public List<String> getCorsAllowedMethods() {
            return corsAllowedMethods;
        }

        public void setCorsAllowedMethods(List<String> corsAllowedMethods) {
            this.corsAllowedMethods = corsAllowedMethods;
        }
    }
}
