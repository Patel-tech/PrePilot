package com.preppilot.authentication.dto;

public class LoginResponse {

    private String accessToken;

    private String tokenType;
    private String refreshToken;
    private String email;

    public LoginResponse(
            String accessToken,
            String refreshToken,
            String tokenType,
            String email) {

        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.email = email;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getEmail() {
        return email;
    }
    public String getRefreshToken() {
        return refreshToken;
    }
}