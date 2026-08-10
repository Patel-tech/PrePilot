package com.preppilot.authentication.filter;

import com.preppilot.authentication.security.CustomUserDetailsService;
import com.preppilot.authentication.token.TokenProvider1;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger LOGGER =
            LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    private final TokenProvider1 tokenProvider;

    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(
            TokenProvider1 tokenProvider,
            CustomUserDetailsService userDetailsService) {

        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain)
            throws ServletException, IOException {

        String authorizationHeader =
                request.getHeader("Authorization");

        if (authorizationHeader == null
                || !authorizationHeader.startsWith("Bearer ")) {

            filterChain.doFilter(request, response);
            return;
        }

        String token = authorizationHeader.substring(7);

        if (!tokenProvider.validateToken(token)) {

            LOGGER.warn("Invalid JWT token received.");

            filterChain.doFilter(request, response);

            return;
        }

        String username =
                tokenProvider.extractUsername(token);

        if (username != null &&
                SecurityContextHolder.getContext()
                        .getAuthentication() == null) {

            UserDetails userDetails =
                    userDetailsService
                            .loadUserByUsername(username);

            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(

                            userDetails,

                            null,

                            userDetails.getAuthorities());

            authentication.setDetails(

                    new WebAuthenticationDetailsSource()

                            .buildDetails(request));

            SecurityContextHolder

                    .getContext()

                    .setAuthentication(authentication);

        }

        filterChain.doFilter(request, response);

    }

}