package com.pollservice.poll;


import com.pollservice.poll.dto.CreatePollRequest;
import com.pollservice.poll.dto.PollResponse;
import com.pollservice.shared.AuthenticatedUser;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

@Path("/polls")
public class PollResource {
    @Inject
    PollService pollService;

    //fake AuthenticatedUser
    AuthenticatedUser authenticatedUser = new AuthenticatedUser("123456");

    @POST
    @Path("")
    public Response createPoll(@Valid CreatePollRequest createPollRequest) {
        PollResponse pollResponse = pollService.createPoll(createPollRequest, authenticatedUser);
        return Response.status(Response.Status.CREATED).entity(pollResponse).build();
    }

    @GET
    @Path("/{id}")
    public Response getPoll(@PathParam("id") Long id) {
        PollResponse pollResponse = pollService.getPoll(id, authenticatedUser);
        return Response.status(Response.Status.OK).entity(pollResponse).build();
    }
}
