package com.keepitsimples.examples.authorization.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by Paul Rhoades on 14/02/2025.
 */
@RestController
public class HtmlController {

    private static final String HTML_DIR = "src/main/resources/authorization/";

    private final Log logger = LogFactory.getLog(HtmlController.class);

    @GetMapping("/")
    public String home_page(Authentication authentication) {
        logger.info("'/' called");
        if (authentication != null && authentication.isAuthenticated()) {
            return read("home.html");
        } else {
            return read("index.html");
        }
    }

    @GetMapping(value = "/authorities")
    public String authorities(Authentication authentication) {
        return authentication.getName() + authentication.getAuthorities();
    }

    @GetMapping("/login")
    public String login_page() {
        logger.info("'/login' called");
        return read("login.html");
    }

    @GetMapping("/denied")
    public String denied_page() {
        logger.info("'/denied' called");
        return "*** DENIED ***";
    }

    @GetMapping("/unprotected")
    public String unprotected_page() {
        logger.info("'/unprotected' called");
        return "*** THIS IS UNPROTECTED ***";
    }

    @GetMapping("/protected")
    public String protected_page() {
        logger.info("/protected' called");
        return "*** THIS IS PROTECTED ***";
    }

    // @RolesAllowed("ADMIN")
    @GetMapping("/admin")
    public String admin_page() {
        logger.info("/admin' called");
        return "*** ADMIN ***";
    }

    private final String read(String filename) {
        try {
            final Path path = Paths.get(HTML_DIR + filename);
            return Files.readString(path);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        return "";
    }
}
