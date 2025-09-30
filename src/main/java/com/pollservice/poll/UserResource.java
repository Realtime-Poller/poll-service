package com.pollservice.poll;

import com.pollservice.poll.dto.*;
import io.quarkus.security.identity.SecurityIdentity;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {
    @Inject
    UserService userService;

    @Inject
    SecurityIdentity securityIdentity;

    @POST
    @PermitAll
    @Path("")
    public Response createUser(@Valid CreateUserRequest createUserRequest) {
        UserResponse userResponse = userService.createUser(createUserRequest);
        return Response.status(Response.Status.CREATED).entity(userResponse).build();
    }

    @GET
    @Path("/me")
    @RolesAllowed("user")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMe() {
        Long userId = Long.parseLong(securityIdentity.getPrincipal().getName());

        User user = User.findById(userId);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        UserResponse userResponse = new UserResponse(user.id, user.getEmail());
        return Response.status(Response.Status.OK).entity(userResponse).build();
    }
}
