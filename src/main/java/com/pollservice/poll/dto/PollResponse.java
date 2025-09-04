package com.pollservice.poll.dto;

import java.time.Instant;

public class PollResponse {
    public Long id;

    public String title;

    public String description;

    public Instant createdTimestamp;

    public PollResponse(Long id, String title, String description, Instant createdTimestamp) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdTimestamp = createdTimestamp;
    }
}
