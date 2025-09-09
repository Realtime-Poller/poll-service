package com.pollservice.poll;


import com.pollservice.poll.dto.CreatePollRequest;
import com.pollservice.poll.dto.PollResponse;
import com.pollservice.poll.dto.UpdatePollRequest;
import com.pollservice.shared.AuthenticatedUser;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;

import java.util.UUID;

@Path("/polls")
public class PollResource {
    @Inject
    PollService pollService;

    @Inject
    SecurityIdentity securityIdentity;

    //fake AuthenticatedUser
    AuthenticatedUser authenticatedUser = new AuthenticatedUser("123456");

    @POST
    @Path("")
    @RolesAllowed("user")
    public Response createPoll(@Valid CreatePollRequest createPollRequest) {
        String realUserId = securityIdentity.getPrincipal().getName();

        AuthenticatedUser realUser = new AuthenticatedUser(realUserId);

        PollResponse pollResponse = pollService.createPoll(createPollRequest, realUser);
        return Response.status(Response.Status.CREATED).entity(pollResponse).build();
    }

    @GET
    @Path("/{publicId}")
    public Response getPoll(@PathParam("publicId") UUID publicId) {
        PollResponse pollResponse = pollService.getPoll(publicId);
        return Response.status(Response.Status.OK).entity(pollResponse).build();
    }

    @PATCH
    @Path("/{id}")
    public Response updatePoll(@PathParam("id") Long id, @Valid UpdatePollRequest updatePollRequest) {
        String realUserId = securityIdentity.getPrincipal().getName();
        AuthenticatedUser realUser = new AuthenticatedUser(realUserId);

        PollResponse pollResponse = pollService.updatePoll(id, updatePollRequest, realUser);
        return Response.status(Response.Status.OK).entity(pollResponse).build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletePoll(@PathParam("id") Long id) {
        String realUserId = securityIdentity.getPrincipal().getName();
        AuthenticatedUser realUser = new AuthenticatedUser(realUserId);

        pollService.deletePoll(id, realUser);
        return Response.status(Response.Status.NO_CONTENT).build();
    }
}
