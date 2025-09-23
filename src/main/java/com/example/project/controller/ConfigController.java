package com.example.project.controller;

import com.example.project.config.AppProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/config")
public class ConfigController {
    
    @Autowired
    private AppProperties appProperties;
    
    @GetMapping("/app-info")
    public ResponseEntity<Map<String, Object>> getAppInfo() {
        Map<String, Object> appInfo = new HashMap<>();
        appInfo.put("name", appProperties.getName());
        appInfo.put("version", appProperties.getVersion());
        appInfo.put("adminEmail", appProperties.getAdminEmail());
        appInfo.put("debugMode", appProperties.isDebugMode());
        appInfo.put("maxConnections", appProperties.getMaxConnections());
        
        return ResponseEntity.ok(appInfo);
    }
    
    @GetMapping("/database-config")
    public ResponseEntity<Map<String, Object>> getDatabaseConfig() {
        AppProperties.Database dbConfig = appProperties.getDatabase();
        Map<String, Object> dbInfo = new HashMap<>();

        dbInfo.put("connectionTimeout", dbConfig.getConnectionTimeout());
        dbInfo.put("maxPoolSize", dbConfig.getMaxPoolSize());
        
        return ResponseEntity.ok(dbInfo);
    }
    
    @GetMapping("/security-config")
    public ResponseEntity<Map<String, Object>> getSecurityConfig() {
        AppProperties.Security securityConfig = appProperties.getSecurity();
        Map<String, Object> securityInfo = new HashMap<>();
        
        securityInfo.put("jwtExpirationMs", securityConfig.getJwtExpirationMs());
        securityInfo.put("enableCors", securityConfig.isEnableCors());
        securityInfo.put("corsAllowedMethods", securityConfig.getCorsAllowedMethods());
        // Don't expose JWT secret in real application
        
        return ResponseEntity.ok(securityInfo);
    }
    
    @GetMapping("/features")
    public ResponseEntity<Map<String, String>> getFeatures() {
        return ResponseEntity.ok(appProperties.getFeatures());
    }
    
    @GetMapping("/allowed-origins")
    public ResponseEntity<java.util.List<String>> getAllowedOrigins() {
        return ResponseEntity.ok(appProperties.getAllowedOrigins());
    }
    
    @GetMapping("/check-feature")
    public ResponseEntity<Map<String, Object>> checkFeature(String featureName) {
        Map<String, Object> result = new HashMap<>();
        boolean isEnabled = "enabled".equals(appProperties.getFeatures().get(featureName));
        result.put("enabled", isEnabled);
        result.put("feature", featureName);
        return ResponseEntity.ok(result);
    }
}
