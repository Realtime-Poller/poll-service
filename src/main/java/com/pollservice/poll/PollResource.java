package com.pollservice.poll;


import com.pollservice.poll.dto.CreatePollRequest;
import com.pollservice.poll.dto.PollResponse;
import com.pollservice.poll.dto.UpdatePollRequest;
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

    @PATCH
    @Path("/{id}")
    public Response updatePoll(@PathParam("id") Long id, @Valid UpdatePollRequest updatePollRequest) {
        PollResponse pollResponse = pollService.updatePoll(id, updatePollRequest, authenticatedUser);
        return Response.status(Response.Status.OK).entity(pollResponse).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePoll(@PathParam("id") Long id) {
        pollService.deletePoll(id, authenticatedUser);

        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
