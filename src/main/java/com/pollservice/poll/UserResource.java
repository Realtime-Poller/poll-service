package com.pollservice.poll;

import com.pollservice.poll.dto.CreatePollRequest;
import com.pollservice.poll.dto.CreateUserRequest;
import com.pollservice.poll.dto.PollResponse;
import com.pollservice.poll.dto.UserResponse;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;

@Path("/users")
public class UserResource {
    @Inject
    UserService userService;

    @POST
    @Path("")
    public Response createUser(@Valid CreateUserRequest createUserRequest) {
        UserResponse userResponse = userService.createUser(createUserRequest);
        return Response.status(Response.Status.CREATED).entity(userResponse).build();
    }
}
