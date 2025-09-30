package com.pollservice.poll;


import com.pollservice.poll.dto.CreatePollRequest;
import com.pollservice.poll.dto.PollResponse;
import com.pollservice.poll.dto.UpdatePollRequest;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.keycloak.authorization.client.AuthorizationDeniedException;
import org.keycloak.authorization.client.AuthzClient;
import org.keycloak.representations.idm.authorization.AuthorizationRequest;
import org.eclipse.microprofile.jwt.JsonWebToken;

import java.security.Principal;
import java.util.*;

import org.keycloak.representations.idm.authorization.PermissionRequest;
import org.keycloak.representations.idm.authorization.PermissionResponse;

@Path("/polls")
public class PollResource {
    @Inject
    PollService pollService;

    @Inject
    SecurityIdentity securityIdentity;

    @Inject
    AuthzClient authzClient;

    @POST
    @Path("")
    public Response createPoll(@Valid CreatePollRequest createPollRequest) {
        String realUserId = securityIdentity.getPrincipal().getName();
        PollResponse pollResponse = pollService.createPoll(createPollRequest, realUserId);
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
        PollResponse pollResponse = pollService.updatePoll(publicId, updatePollRequest);
        return Response.status(Response.Status.OK).entity(pollResponse).build();
    }

    @DELETE
    @Path("/{publicId}")
    public Response deletePoll(@PathParam("publicId") UUID publicId) {
        Poll pollToBeDeleted = pollService.findPollByPublicId(publicId);

        try {
            Principal principal = securityIdentity.getPrincipal();

            if (!(principal instanceof JsonWebToken)) {
                throw new ForbiddenException("Invalid token type.");
            }
            JsonWebToken jwt = (JsonWebToken) principal;
            String accessToken = jwt.getRawToken();

            if (authzClient == null) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("AuthzClient not configured").build();
            }

            PermissionRequest permissionRequest = new PermissionRequest();
            permissionRequest.setResourceId("Poll");
            permissionRequest.setScopes(Set.of("poll:delete"));

            Map<String, List<String>> claims = new HashMap<>();
            claims.put("owner", List.of(pollToBeDeleted.getOwner().id.toString()));
            permissionRequest.setClaims(claims);

            PermissionResponse response = authzClient.protection().permission().create(permissionRequest);

            String ticket = response.getTicket();

            AuthorizationRequest authzRequest = new AuthorizationRequest(ticket);

            authzClient.authorization(accessToken).authorize(authzRequest);

            pollService.deletePoll(publicId);
            return Response.status(Response.Status.NO_CONTENT).build();

        } catch (AuthorizationDeniedException e) {
            throw new ForbiddenException("You are not authorized to delete this poll.");
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build();
        }
    }
}
