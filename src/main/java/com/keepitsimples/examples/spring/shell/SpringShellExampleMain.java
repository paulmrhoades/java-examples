package com.keepitsimples.examples.spring.shell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Created by Paul Rhoades on 01/03/2025.
 */
@SpringBootApplication
public class SpringShellExampleMain {

    public static void main(String[] args) {
        final SpringApplication app = new SpringApplication(SpringShellExampleMain.class);
        app.setAdditionalProfiles("shell");
        app.run(args);
    }
}
