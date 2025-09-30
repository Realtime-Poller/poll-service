package com.pollservice.poll;

import com.pollservice.poll.dto.CreatePollRequest;
import com.pollservice.poll.dto.PollResponse;
import com.pollservice.poll.dto.UpdatePollRequest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;

import java.time.Instant;
import java.util.UUID;

@ApplicationScoped
public class PollService {

    @Transactional
    public PollResponse createPoll(CreatePollRequest createPollRequest, String authenticatedUserId) {
        User pollOwner = User.findById(Long.valueOf(authenticatedUserId));

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
    public PollResponse updatePoll(UUID publicId, UpdatePollRequest updatePollRequest) {
        Poll pollToBeUpdated = Poll.find("publicId", publicId).firstResult();
        if (pollToBeUpdated == null) {
            throw new NotFoundException("Poll not found");
        }

        boolean updated = false;

        if (updatePollRequest.title != null) {
            if (updatePollRequest.title.isBlank()) {
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
            pollToBeUpdated.setLastUpdatedTimestamp(Instant.now());
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
    public void deletePoll(UUID publicId) {
        Poll pollToBeDeleted = findPollByPublicId(publicId);
        pollToBeDeleted.delete();
    }

    public Poll findPollByPublicId(UUID publicId) {
        Poll poll = Poll.find("publicId", publicId).firstResult();
        if (poll == null) {
            throw new NotFoundException("Poll not found");
        }
        return poll;
    }
}
