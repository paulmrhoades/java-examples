package com.keepitsimples.examples.spring.jpa;

import com.keepitsimples.examples.spring.jpa.model.MyUser;
import com.keepitsimples.examples.spring.jpa.model.MyUserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * Created by Paul Rhoades on 01/03/2025.
 */
@SpringBootApplication
public class SpringJpaExampleMain {

    private static final Log logger = LogFactory.getLog(SpringJpaExampleMain.class);

    public static void main(String[] args) {
        final SpringApplication app = new SpringApplication(SpringJpaExampleMain.class);
        app.setAdditionalProfiles("jpa");
        app.run(args);
    }

    @Bean
    public CommandLineRunner run(MyUserRepository repository) {
        return new CommandLineRunner() {
            @Override
            public void run(String... args) throws Exception {

                final MyUser joe = new MyUser("joe", "blogs");
                logger.info("before save -> " +  joe);
                repository.save(joe);
                logger.info("after save -> " +  joe);

                final List<MyUser> blogs = repository.findByLastName("blogs");
                logger.info("find('blogs') -> " + blogs);

            }
        };
    }
}
