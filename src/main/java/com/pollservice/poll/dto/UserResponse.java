package com.pollservice.poll.dto;

public class UserResponse {
    public Long id;
    public String email;

    public UserResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
