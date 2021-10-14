package com.example.dashboardapi.configration;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "fileupload")
@Data
public class FileConfiguration {
    private String location;
}

