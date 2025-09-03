package com.pollservice.poll;

import com.pollservice.poll.dto.CreatePollRequest;
import com.pollservice.poll.dto.PollResponse;
import com.pollservice.shared.AuthenticatedUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.net.Authenticator;

@ApplicationScoped
public class PollService {

    @Transactional
    public PollResponse createPoll(CreatePollRequest createPollRequest, AuthenticatedUser authenticatedUser) {
        Poll poll = new Poll();
        poll.setTitle(createPollRequest.title);
        poll.setDescription(createPollRequest.description);
        poll.setDate(java.time.Instant.now());

        poll.persist();

        PollResponse pollResponse = new PollResponse();
        pollResponse.id = poll.id;
        pollResponse.title = poll.getTitle();
        pollResponse.description = poll.getDescription();
        pollResponse.createdTimestamp = poll.getDate();

        return pollResponse;
    }
}
