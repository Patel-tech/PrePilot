package com.preppilot.authentication.token;


import com.preppilot.authentication.config.JwtProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Date;
import java.util.Map;

@Component
public class JwtTokenProvider1 implements TokenProvider1 {

    private final JwtProperties jwtProperties;

    public JwtTokenProvider1(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;

    }

    private SecretKey getSigningKey() {

        return Keys.hmacShaKeyFor(
                jwtProperties
                        .getSecret()
                        .getBytes(StandardCharsets.UTF_8)
        );

    }
    @Override
    public String generateAccessToken(String username) {

        return generateToken(
                username,
                TokenType.ACCESS,
                Collections.emptyMap()
        );

    }
    @Override
    public String generateRefreshToken(String username) {

        return generateToken(
                username,
                TokenType.REFRESH,
                Collections.emptyMap()
        );

    }

    @Override
    public String generateToken(
            String username,
            TokenType tokenType,
            Map<String, Object> claims
    ) {

        Date now = new Date();

        long expiry = tokenType == TokenType.ACCESS
                ? jwtProperties.getAccessTokenExpiration()
                : jwtProperties.getRefreshTokenExpiration();

        return Jwts.builder()

                .claims(claims)

                .subject(username)

                .issuer(jwtProperties.getIssuer())

                .issuedAt(now)

                .expiration(new Date(now.getTime() + expiry))

                .signWith(getSigningKey())

                .compact();

    }

    @Override
    public String extractUsername(String token) {

        return parseClaims(token).getSubject();

    }
    @Override
    public <T> T extractClaim(
            String token,
            String claimName,
            Class<T> type
    ) {

        Object value = parseClaims(token).get(claimName);

        return type.cast(value);

    }

    @Override
    public boolean validateToken(String token) {

        try {

            parseClaims(token);

            return !isExpired(token);

        } catch (JwtException | IllegalArgumentException ex) {

            return false;

        }

    }
    @Override
    public boolean isExpired(String token) {

        return parseClaims(token)

                .getExpiration()

                .before(new Date());

    }

    private Claims parseClaims(String token) {

        return Jwts.parser()

                .verifyWith(getSigningKey())

                .build()

                .parseSignedClaims(token)

                .getPayload();

    }

}