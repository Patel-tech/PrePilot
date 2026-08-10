package com.preppilot.authentication.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider
        implements TokenProvider {

    private static final String SECRET =
            "preppilot-secret-key-preppilot-secret-key-123456";

    private static final long EXPIRATION =
            86400000;

    private final SecretKey key =
            Keys.hmacShaKeyFor(
                    SECRET.getBytes(
                            StandardCharsets.UTF_8));

    @Override
    public String generateToken(
            Authentication authentication) {

        Date now = new Date();

        Date expiry =
                new Date(now.getTime() + EXPIRATION);

        return Jwts.builder()
                .subject(authentication.getName())
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)
                .compact();
    }
}