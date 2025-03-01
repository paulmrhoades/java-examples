package com.keepitsimples.examples.spring.security.controllers;

import jakarta.annotation.security.RolesAllowed;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
        return html("template.html", "This is the 'Home Page'", authentication);
    }

    @GetMapping("/denied")
    public String denied_page(Authentication authentication) {
        logger.info("'/denied' called");
        return html("template.html", "You were DENIED access to the page you just attempted", authentication);
    }

    @GetMapping("/unprotected")
    public String unprotected_page(Authentication authentication) {
        logger.info("'/unprotected' called");
        return html("template.html", "You can access this page because it is NOT PROTECTED.", authentication);
    }

    @GetMapping("/protected")
    public String protected_page(Authentication authentication) {
        logger.info("'/protected' called");
        return html("template.html", "You need to be AUTHENTICATED to see this page, so you must have logged in as 'user' or 'admin' to see it.", authentication);
    }

    @RolesAllowed("ADMINISTRATOR")
    @GetMapping("/admin")
    public String admin_page(Authentication authentication) {
        logger.info("/admin' called");
        return html("template.html", "You need to be AUTHENTICATED with 'ROLE = ADMINISTRATOR' to see this page, so you must have logged in as 'admin' to see it.", authentication);
    }

    @GetMapping("/login")
    public String login_page(Authentication authentication) {
        logger.info("'/login' called");
        return html("login_page.html", "", authentication);
    }

    private String html(String html_file, String message, Authentication authentication) {
        String html = "*** OOPS SOMETHING WENT WRONG ***";
        try {
            final Path path = Paths.get(HTML_DIR + html_file);
            html = Files.readString(path);
            html = html.replace("#NAME#", name(authentication));
            html = html.replace("#MESSAGE#", message);
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        return html;
    }

    private boolean is_authenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }

    private String name(Authentication authentication) {
        return is_authenticated(authentication) ? authentication.getName() : "Anonymous" ;
    }

}
