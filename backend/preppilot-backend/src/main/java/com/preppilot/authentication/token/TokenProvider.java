package com.preppilot.authentication.token;

import java.util.Map;

public interface TokenProvider {

    /**
     * Generates an access token.
     */
    String generateAccessToken(String username);

    /**
     * Generates a refresh token.
     */
    String generateRefreshToken(String username);

    /**
     * Generates a token with custom claims.
     */
    String generateToken(
            String username,
            TokenType tokenType,
            Map<String, Object> claims
    );

    /**
     * Extract username from JWT.
     */
    String extractUsername(String token);

    /**
     * Extract a custom claim.
     */
    <T> T extractClaim(
            String token,
            String claimName,
            Class<T> type
    );

    /**
     * Validate token signature and expiry.
     */
    boolean validateToken(String token);

    /**
     * Check whether token has expired.
     */
    boolean isExpired(String token);

}