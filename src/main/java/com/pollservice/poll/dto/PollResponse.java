package com.pollservice.poll.dto;

import java.time.Instant;
import java.util.UUID;

public class PollResponse {
    public UUID publicId;

    public String title;

    public String description;

    public Instant createdTimestamp;

    public Instant lastUpdatedTimestamp;

    public PollResponse(UUID publicId, String title, String description, Instant createdTimestamp, Instant lastUpdatedTimestamp) {
        this.publicId = publicId;
        this.title = title;
        this.description = description;
        this.createdTimestamp = createdTimestamp;
        this.lastUpdatedTimestamp = lastUpdatedTimestamp;
    }
}
