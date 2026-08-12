package com.preppilot.authentication.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/me")
    public Map<String, Object> getCurrentUser(
            Authentication authentication) {

        return Map.of(
                "email",
                authentication.getName(),

                "authorities",
                authentication.getAuthorities()
        );
    }
}