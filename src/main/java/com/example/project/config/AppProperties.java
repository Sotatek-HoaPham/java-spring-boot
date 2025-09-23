package com.example.project.config;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Map;

@ConfigurationProperties(prefix = "app")
@Component
@Validated
public class AppProperties {

    @NotBlank(message = "Application name is required")
    private String name;

    @NotBlank(message = "Application version is required")
    private String version;

    @Email(message = "Admin email must be valid")
    private String adminEmail;

    @Min(value = 1, message = "Max connections must be at least 1")
    @Max(value = 100, message = "Max connections cannot exceed 100")
    private int maxConnections = 10;

    private boolean debugMode = false;

    private Database database;

    private Security security;

    private List<String> allowedOrigins;

    private Map<String, String> features;

    // Getters and Setters
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

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }

    public Database getDatabase() {
        return database;
    }

    public void setDatabase(Database database) {
        this.database = database;
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

    public Map<String, String> getFeatures() {
        return features;
    }

    public void setFeatures(Map<String, String> features) {
        this.features = features;
    }

    // Nested classes
    public static class Database {
        @NotBlank(message = "Database URL is required")
        private String url;

        @NotBlank(message = "Database username is required")
        private String username;

        @NotBlank(message = "Database password is required")
        private String password;

        private int connectionTimeout = 30000;

        private int maxPoolSize = 10;

        // Getters and Setters
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getConnectionTimeout() {
            return connectionTimeout;
        }

        public void setConnectionTimeout(int connectionTimeout) {
            this.connectionTimeout = connectionTimeout;
        }

        public int getMaxPoolSize() {
            return maxPoolSize;
        }

        public void setMaxPoolSize(int maxPoolSize) {
            this.maxPoolSize = maxPoolSize;
        }
    }

    public static class Security {
        @NotBlank(message = "JWT secret is required")
        private String jwtSecret;

        private int jwtExpirationMs = 86400000; // 24 hours

        private boolean enableCors = true;

        private List<String> corsAllowedMethods;

        // Getters and Setters
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

        public boolean isEnableCors() {
            return enableCors;
        }

        public void setEnableCors(boolean enableCors) {
            this.enableCors = enableCors;
        }

        public List<String> getCorsAllowedMethods() {
            return corsAllowedMethods;
        }

        public void setCorsAllowedMethods(List<String> corsAllowedMethods) {
            this.corsAllowedMethods = corsAllowedMethods;
        }
    }
}
