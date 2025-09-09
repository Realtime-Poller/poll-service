package com.pollservice.poll;

import com.pollservice.poll.dto.CreatePollRequest;
import com.pollservice.poll.dto.PollResponse;
import com.pollservice.poll.dto.UpdatePollRequest;
import com.pollservice.shared.AuthenticatedUser;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.UnauthorizedException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.ForbiddenException;
import jakarta.ws.rs.NotFoundException;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class PollService {

    @Transactional
    public PollResponse createPoll(CreatePollRequest createPollRequest, AuthenticatedUser authenticatedUser) {
        User pollOwner = User.findById(Long.valueOf(authenticatedUser.id()));

        Poll poll = new Poll();
        poll.setTitle(createPollRequest.title);
        poll.setDescription(createPollRequest.description);
        poll.setOwner(pollOwner);

        poll.persist();

        return new PollResponse(
                poll.publicId,
                poll.getTitle(),
                poll.getDescription(),
                poll.getCreatedTimestamp(),
                poll.getLastUpdatedTimestamp()
        );
    }

    public PollResponse getPoll(UUID publicId) {
        Poll poll = Poll.find("publicId", publicId).firstResult();
        if (poll == null) {
            throw new NotFoundException("Poll not found");
        }

        return new PollResponse(
                poll.publicId,
                poll.getTitle(),
                poll.getDescription(),
                poll.getCreatedTimestamp(),
                poll.getLastUpdatedTimestamp()
        );
    }

    @Transactional
    public PollResponse updatePoll(long id, UpdatePollRequest updatePollRequest, AuthenticatedUser authenticatedUser) {
        User suspectedPollOwner = User.findById(Long.valueOf(authenticatedUser.id()));
        if(suspectedPollOwner == null) {
            throw new UnauthorizedException("User not authenticated");
        }

        Poll pollToBeUpdated = Poll.findById(id);
        if (pollToBeUpdated == null) {
            throw new NotFoundException("Poll not found");
        }

        if (!suspectedPollOwner.id.equals(pollToBeUpdated.getOwner().id)) {
            throw new ForbiddenException("You are not allowed to update this poll");
        }

        boolean updated = false;

        if(updatePollRequest.title != null) {
            if(updatePollRequest.title.isBlank()) {
                throw new BadRequestException("Title, if provided, cannot be blank");
            }
            pollToBeUpdated.setTitle(updatePollRequest.title);
            updated = true;
        }

        if (updatePollRequest.description != null) {
            pollToBeUpdated.setDescription(updatePollRequest.description);
            updated = true;
        }

        if (updated) {
            pollToBeUpdated.setLastUpdatedTimestamp(Instant.now()); // Added update timestamp due to timing issues with @PreUpdate flag on hibernate
        }

        return new PollResponse(
                pollToBeUpdated.publicId,
                pollToBeUpdated.getTitle(),
                pollToBeUpdated.getDescription(),
                pollToBeUpdated.getCreatedTimestamp(),
                pollToBeUpdated.getLastUpdatedTimestamp()
        );
    }

    @Transactional
    public void deletePoll(long id, AuthenticatedUser authenticatedUser) {
        User suspectedPollOwner = User.findById(Long.valueOf(authenticatedUser.id()));
        if(suspectedPollOwner == null) {
            throw new UnauthorizedException("User not authenticated");
        }

        Poll pollToBeDeleted = Poll.findById(id);
        if (pollToBeDeleted == null) {
            throw new NotFoundException("Poll not found");
        }

        if (!suspectedPollOwner.id.equals(pollToBeDeleted.getOwner().id)) {
            throw new ForbiddenException("You are not allowed to update this poll");
        }

        pollToBeDeleted.delete();
    }
}
