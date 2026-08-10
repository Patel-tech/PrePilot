package com.preppilot.authentication.jwt;

import org.springframework.security.core.Authentication;

public interface TokenProvider {

    String generateToken(
            Authentication authentication);

}