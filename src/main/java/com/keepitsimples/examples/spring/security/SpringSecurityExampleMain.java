package com.keepitsimples.examples.spring.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Created by Paul Rhoades on 14/02/2025.
 */
@SpringBootApplication
public class SpringSecurityExampleMain {

    public static void main(String[] args) {
        final SpringApplication app = new SpringApplication(SpringSecurityExampleMain.class);
        app.setAdditionalProfiles("security");
        app.run(args);
    }
}


