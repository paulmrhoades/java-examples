package com.keepitsimples.examples.spring.shell;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

/**
 * Created by Paul Rhoades on 01/03/2025.
 */
@ShellComponent
public class ShellCommands {

    @ShellMethod("try me")
    public String hello() {
        return "Hello";
    }

}
