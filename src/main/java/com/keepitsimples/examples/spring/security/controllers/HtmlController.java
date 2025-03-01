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

    private static final String RESOURCE_DIR = "src/main/resources/authorization/";

    private final Log logger = LogFactory.getLog(HtmlController.class);

    @GetMapping("/")
    public String home_page(Authentication authentication) {
        logger.info("'/' called");
        return html("page_template.html", "Home Page", "This is the 'Home Page'", authentication);
    }

    @GetMapping("/denied")
    public String denied_page(Authentication authentication) {
        logger.info("'/denied' called");
        return html("page_template.html", "Access Denies", "You were DENIED access to this page.", authentication);
    }

    @GetMapping("/unprotected")
    public String unprotected_page(Authentication authentication) {
        logger.info("'/unprotected' called");
        return html("page_template.html", "Unprotected Page", "You can access this page because it is NOT PROTECTED.", authentication);
    }

    @GetMapping("/protected")
    public String protected_page(Authentication authentication) {
        logger.info("'/protected' called");
        return html("page_template.html", "Protected Page", "You are ALLOWED to see this page because you are logged in.", authentication);
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/admin")
    public String admin_page(Authentication authentication) {
        logger.info("/admin' called");
        return html("page_template.html", "Admin Page", "You are ALLOWED to see this page as you are logged in as a User with [Role=ADMIN]", authentication);
    }

    @GetMapping("/login")
    public String login_page(Authentication authentication) {
        logger.info("'/login' called");
        return html("page_template.html", "Login Page","Please login...", authentication);
    }

    private String html(String html_file, String title, String message, Authentication authentication) {
        String html = "*** OOPS SOMETHING WENT WRONG ***";
        try {
            final Path html_path = Paths.get(RESOURCE_DIR + html_file);
            html = Files.readString(html_path);
            html = html.replace("#TITLE#", title);
            html = html.replace("#NAME#", name(authentication));
            html = html.replace("#MESSAGE#", message);

            final Path css_path = Paths.get( RESOURCE_DIR + "styles.css");
            final String css = Files.readString(css_path);
            html = html.replace("#STYLES#", css);
        } catch (Exception ioe) {
            ioe.printStackTrace();
        }
        return html;
    }

    private boolean is_authenticated(Authentication authentication) {
        return authentication != null && authentication.isAuthenticated();
    }

    private String name(Authentication authentication) {
        return is_authenticated(authentication) ? "'" + authentication.getName() + "' " + authentication.getAuthorities() : "'Anonymous'" ;
    }

}
