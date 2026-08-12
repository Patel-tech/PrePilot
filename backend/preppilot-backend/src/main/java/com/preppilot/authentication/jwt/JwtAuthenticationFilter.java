package com.preppilot.authentication.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            TokenProvider tokenProvider,
            UserDetailsService userDetailsService) {

        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String requestUri = request.getRequestURI();

        log.debug("JWT Filter processing request: {}", requestUri);

        String token = extractToken(request);

        if (token == null) {

            log.debug("No JWT token found for request: {}", requestUri);

        } else {

            log.debug("JWT token found for request: {}", requestUri);

            try {

                if (tokenProvider.validateToken(token)) {

                    String username =
                            tokenProvider.getUsernameFromToken(token);

                    log.debug(
                            "JWT validated successfully. Username: {}",
                            username
                    );

                    UserDetails userDetails =
                            userDetailsService
                                    .loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails,
                                    null,
                                    userDetails.getAuthorities()
                            );

                    SecurityContextHolder
                            .getContext()
                            .setAuthentication(authentication);

                    log.debug(
                            "Authentication set successfully for user: {}",
                            username
                    );

                } else {

                    log.warn("JWT validation failed for request: {}",
                            requestUri);
                }

            } catch (Exception exception) {

                log.error(
                        "Exception while processing JWT for request: {}",
                        requestUri,
                        exception
                );

                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {

        String header =
                request.getHeader("Authorization");

        if (header != null
                && header.startsWith("Bearer ")) {

            return header.substring(7).trim();
        }

        return null;
    }
}