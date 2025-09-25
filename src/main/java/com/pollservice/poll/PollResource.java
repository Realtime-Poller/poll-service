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
import org.keycloak.authorization.client.AuthorizationDeniedException;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.idm.authorization.AuthorizationRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Path("/polls")
public class PollResource {
    @Inject
    PollService pollService;

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    AuthzClient authzClient;

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
    @Path("/{publicId}")
    public Response updatePoll(@PathParam("publicId") UUID publicId, @Valid UpdatePollRequest updatePollRequest) {
        String realUserId = securityIdentity.getPrincipal().getName();
        AuthenticatedUser realUser = new AuthenticatedUser(realUserId);

        PollResponse pollResponse = pollService.updatePoll(publicId, updatePollRequest, realUser);
        return Response.status(Response.Status.OK).entity(pollResponse).build();
    }

    @DELETE
    @Path("/{publicId}")
    public Response deletePoll(@PathParam("publicId") UUID publicId) {
        Poll pollToBeDeleted = pollService.findPollByPublicId(publicId);

        try {
            AuthorizationRequest request = new AuthorizationRequest();

            request.addPermission("Poll", "poll:delete");

            Map<String, List<String>> claims = new HashMap<>();
            claims.put("owner", List.of(pollToBeDeleted.getOwner().id.toString()));
            request.setClaims(claims);

            authzClient.authorization().authorize(request);

            String realUserId = securityIdentity.getPrincipal().getName();
            AuthenticatedUser realUser = new AuthenticatedUser(realUserId);
            pollService.deletePoll(publicId, realUser);

            return Response.status(Response.Status.NO_CONTENT).build();

        } catch (AuthorizationDeniedException e) {
            throw new ForbiddenException("You are not allowed to delete this poll");
        }
    }
}
