package com.pollservice.poll.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreatePollRequest {
    @NotBlank(message = "Title may not be blank.")
    @Size(max = 200)
    public String title;

    @Size(max = 5000)
    public String description;
}
