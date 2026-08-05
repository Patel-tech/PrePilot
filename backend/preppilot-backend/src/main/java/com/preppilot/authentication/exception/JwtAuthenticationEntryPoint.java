package com.preppilot.authentication.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Component
public class JwtAuthenticationEntryPoint
        implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        response.setContentType("application/json");

        Map<String, Object> body = Map.of(

                "timestamp", LocalDateTime.now(),

                "status", 401,

                "error", "Unauthorized",

                "message", authException.getMessage(),

                "path", request.getRequestURI()

        );

        new ObjectMapper()

                .writeValue(response.getOutputStream(), body);

    }

}