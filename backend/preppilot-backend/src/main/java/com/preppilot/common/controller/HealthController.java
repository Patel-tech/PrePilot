package com.preppilot.common.controller;

import com.preppilot.common.response.ApiResponse;
import com.preppilot.common.response.ResponseBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthController {
    private static final Logger log =
            LoggerFactory.getLogger(HealthController.class);

    @GetMapping("/")
    public String home() {
        return "PrepPilot Backend is running!";
    }

    @GetMapping("/health")
    public ApiResponse<String> health(){
        log.info("Health API Called");

        return ResponseBuilder.success(
                "Application is running",
                "UP"
        );
    }
}