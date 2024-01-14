package com.project.boot.manager;

import com.project.boot.common.log.annotation.EnableLogAspect;
import com.project.boot.manager.config.UserAuthProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableLogAspect
@EnableScheduling
@SpringBootApplication
@EnableConfigurationProperties(value = {UserAuthProperties.class})
public class ManagerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManagerApplication.class,args);
    }
}
