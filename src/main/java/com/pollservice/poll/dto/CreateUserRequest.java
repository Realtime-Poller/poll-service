package com.pollservice.poll.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateUserRequest {
    @NotBlank(message = "Email may not be blank.")
    @Email(message = "Email is not valid.")
    @Size(max = 254)
    public String email;

    @NotBlank(message = "Password may not be blank.")
    @Size(min = 8, max = 254, message = "Password must be between 8 and 254 characters.")
    public String password;
}
