package com.javamentor.qa.platform.webapp.configs;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.javamentor.qa.platform")
@EntityScan("com.javamentor.qa.platform.models.entity")
public class PlatformApplication {

    public static void main(String[] args){
        SpringApplication.run(PlatformApplication.class, args);

    }

}
