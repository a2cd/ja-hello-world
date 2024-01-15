package org.a2cd.boot.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author a2cd
 * @since 2024-01-04
 */

@RestController
public class IndexController {

    @GetMapping
    public String hello() {
        return "Welcome to java spring boot 3.0!";
    }
}
