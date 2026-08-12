package com.preppilot.authentication.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtTokenProvider implements TokenProvider {

    private final SecretKey key;
    private final long accessTokenExpiration;
    private final String issuer;

    public JwtTokenProvider(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.access-token-expiration}") long accessTokenExpiration,
            @Value("${jwt.issuer}") String issuer) {
        this.key = Keys.hmacShaKeyFor(
                secret.getBytes(StandardCharsets.UTF_8)
        );

        this.accessTokenExpiration = accessTokenExpiration;
        this.issuer = issuer;
    }

    @Override
    public String generateToken(Authentication authentication) {

        Date now = new Date();

        Date expiry = new Date(
                now.getTime() + accessTokenExpiration
        );

        return Jwts.builder()
                .subject(authentication.getName())
                .issuer(issuer)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)
                .compact();
    }

    @Override
    public String getUsernameFromToken(String token) {

        Claims claims = Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    @Override
    public boolean validateToken(String token) {

        try {

            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);

            return true;

        } catch (Exception exception) {

            return false;
        }
    }
}