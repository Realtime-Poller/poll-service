package com.pollservice.poll.dto;

import jakarta.validation.constraints.Size;

public class UpdatePollRequest {
    @Size(max = 200)
    public String title;

    @Size(max = 5000)
    public String description;
}
