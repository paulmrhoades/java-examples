package com.keepitsimples.examples.spring;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

/**
 * Created by Paul Rhoades on 27/02/2025.
 */
@SpringBootApplication
public class SpringProcessExampleMain {

    public static void main(String... args) {
        final SpringApplicationBuilder app = new SpringApplicationBuilder(SpringProcessExampleMain.class).web(WebApplicationType.NONE);
        app.run(args);
    }

    @Bean
    public CommandLineRunner hello() {
        return (args) -> {
            System.out.println("hello World");
        };
    }
}
