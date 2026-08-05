package com.preppilot.authentication.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Component
public class JwtAccessDeniedHandler
        implements AccessDeniedHandler {

    @Override
    public void handle(
            HttpServletRequest request,
            HttpServletResponse response,
            AccessDeniedException exception)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        response.setContentType("application/json");

        Map<String, Object> body = Map.of(

                "timestamp", LocalDateTime.now(),

                "status", 403,

                "error", "Forbidden",

                "message", exception.getMessage(),

                "path", request.getRequestURI()

        );

        new ObjectMapper()

                .writeValue(response.getOutputStream(), body);

    }

}