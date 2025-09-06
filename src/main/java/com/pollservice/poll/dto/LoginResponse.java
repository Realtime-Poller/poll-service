package com.pollservice.poll.dto;

public class LoginResponse {
    public String token;

    public String tokenType;

    public long expiresIn;

    public String email;

    public LoginResponse(String token, String tokenType, long expiresIn, String email) {
        this.token = token;
        this.tokenType = tokenType;
        this.expiresIn = expiresIn;
        this.email = email;
    }
}
