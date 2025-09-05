package com.pollservice.poll;

import com.pollservice.poll.dto.CreateUserRequest;
import com.pollservice.poll.dto.UserResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import io.quarkus.elytron.security.common.BcryptUtil;

@ApplicationScoped
public class UserService {
    @Transactional
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        String emailToCheck = createUserRequest.email;

        long count = User.count("email = ?1", emailToCheck);

        if(count > 0) {
            throw new IllegalArgumentException("User with email " + emailToCheck + " already exists");
        }

        User user = new User();
        user.setEmail(createUserRequest.email);
        user.setPassword(BcryptUtil.bcryptHash(createUserRequest.password));

        user.persist();

        return new UserResponse(user.id, user.getEmail());
    }
}
