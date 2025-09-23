package com.example.project.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(AppProperties.class)
public class ConfigurationPropertiesConfig {
    // This class enables ConfigurationProperties processing
    // and makes AppProperties available as a Spring bean
}
