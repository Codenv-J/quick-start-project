package com.project.boot.manager.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;


import java.util.List;

@Data
@ConfigurationProperties(prefix = "project.auth")
public class UserAuthProperties {
    private List<String> noAuthUrls ;
}
