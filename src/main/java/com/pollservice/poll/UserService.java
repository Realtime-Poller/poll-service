package com.pollservice.poll;

import com.pollservice.api.exception.EmailAlreadyExistsException;
import com.pollservice.poll.dto.CreateUserRequest;
import com.pollservice.poll.dto.UserResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import at.favre.lib.crypto.bcrypt.BCrypt;



@ApplicationScoped
public class UserService {
    @Transactional
    public UserResponse createUser(CreateUserRequest createUserRequest) {
        String normalizedEmail = createUserRequest.email.toLowerCase();

        long count = User.count("email = ?1", normalizedEmail);
        if(count > 0) {
            throw new EmailAlreadyExistsException("Email " + createUserRequest.email + " already exists");
        }

        User user = new User();
        user.setEmail(normalizedEmail);
        user.setPassword(BCrypt.withDefaults().hashToString(12, createUserRequest.password.toCharArray()));
        user.persist();

        return new UserResponse(user.id, user.getEmail());
    }
}
