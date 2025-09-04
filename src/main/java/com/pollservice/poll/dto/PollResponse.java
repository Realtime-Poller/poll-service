package com.pollservice.poll.dto;

import java.time.Instant;

public class PollResponse {
    public Long id;

    public String title;

    public String description;

    public Instant createdTimestamp;

    public Instant lastUpdatedTimestamp;

    public PollResponse(Long id, String title, String description, Instant createdTimestamp, Instant lastUpdatedTimestamp) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.createdTimestamp = createdTimestamp;
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }
}
