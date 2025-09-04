package com.pollservice.poll;

import com.pollservice.poll.dto.CreatePollRequest;
import com.pollservice.poll.dto.PollResponse;
import com.pollservice.shared.AuthenticatedUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.NotFoundException;

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
                poll.getCreatedTimestamp()
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
                poll.getCreatedTimestamp()
        );
    }
}
