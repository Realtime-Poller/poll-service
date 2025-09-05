package com.pollservice.poll.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserRequest {
    @NotBlank(message = "Email may not be blank.")
    @Size(max = 254)
    public String email;

    @Size(min = 8, max = 254, message = "Password must be between 8 and 254 characters.")
    public String password;
}
