package com.preppilot.common.controller;

import com.preppilot.common.response.ApiResponse;
import com.preppilot.common.response.ResponseBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {

    @GetMapping("/")
    public String home() {
        return "PrepPilot Backend is running!";
    }

    @GetMapping("/health")
    public ApiResponse<String> health(){

        return ResponseBuilder.success(
                "Application is running",
                "UP"
        );
    }
}