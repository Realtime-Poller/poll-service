package com.pollservice.poll.dto;

import java.time.Instant;

public class PollResponse {
    public Long id;

    public String title;

    public String description;

    public Instant createdTimestamp;
}
