package com.pollservice.poll;

import com.pollservice.poll.dto.CreatePollRequest;
import com.pollservice.poll.dto.PollResponse;
import com.pollservice.poll.dto.UpdatePollRequest;
import com.pollservice.shared.AuthenticatedUser;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

import java.time.Instant;

@ApplicationScoped
public class PollService {

    @Transactional
    public PollResponse createPoll(CreatePollRequest createPollRequest, AuthenticatedUser authenticatedUser) {
        Poll poll = new Poll();
        poll.setTitle(createPollRequest.title);
        poll.setDescription(createPollRequest.description);

        poll.persist();

        return new PollResponse(
                poll.id,
                poll.getTitle(),
                poll.getDescription(),
                poll.getCreatedTimestamp(),
                poll.getLastUpdatedTimestamp()
        );
    }

    public PollResponse getPoll(long id, AuthenticatedUser authenticatedUser) {
        Poll poll = Poll.findById(id);
        if (poll == null) {
            throw new NotFoundException("Poll not found");
        }

        return new PollResponse(
                poll.id,
                poll.getTitle(),
                poll.getDescription(),
                poll.getCreatedTimestamp(),
                poll.getLastUpdatedTimestamp()
        );
    }

    @Transactional
    public PollResponse updatePoll(long id, UpdatePollRequest updatePollRequest, AuthenticatedUser authenticatedUser) {
        Poll poll = Poll.findById(id);
        if (poll == null) {
            throw new NotFoundException("Poll not found");
        }

        boolean updated = false;

        if (updatePollRequest.title != null) {
            poll.setTitle(updatePollRequest.title);
            updated = true;
        }

        if (updatePollRequest.description != null) {
            poll.setDescription(updatePollRequest.description);
            updated = true;
        }

        if (updated) {
            poll.setLastUpdatedTimestamp(Instant.now()); // Added update timestamp due to timing issues with @PreUpdate flag on hibernate
        }

        return new PollResponse(
                poll.id,
                poll.getTitle(),
                poll.getDescription(),
                poll.getCreatedTimestamp(),
                poll.getLastUpdatedTimestamp()
        );
    }

    @Transactional
    public void deletePoll(long id, AuthenticatedUser authenticatedUser) {
        Poll poll = Poll.findById(id);
        if (poll == null) {
            throw new NotFoundException("Poll not found");
        }

        poll.delete();
    }
}
