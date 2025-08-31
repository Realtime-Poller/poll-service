package com.pollservice.poll;


import com.pollservice.poll.dto.CreatePollRequest;
import com.pollservice.poll.dto.PollResponse;
import com.pollservice.shared.AuthenticatedUser;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/polls")
public class PollResource {
    @Inject
    PollService pollService;

    //fake AuthenticatedUser
    AuthenticatedUser authenticatedUser = new AuthenticatedUser("123456");

    @POST
    @Path("")
    public Response createPoll(CreatePollRequest createPollRequest) {
        PollResponse pollResponse = pollService.createPoll(createPollRequest, authenticatedUser);
        return Response.status(Response.Status.CREATED).entity(pollResponse).build();
    }
}
