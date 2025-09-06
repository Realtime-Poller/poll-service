package com.pollservice.poll;

import com.pollservice.api.exception.EmailAlreadyExistsException;
import com.pollservice.api.exception.InvalidCredentialsException;
import com.pollservice.poll.dto.CreateUserRequest;
import com.pollservice.poll.dto.LoginRequest;
import com.pollservice.poll.dto.LoginResponse;
import com.pollservice.poll.dto.UserResponse;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import io.quarkus.elytron.security.common.BcryptUtil;

import java.util.Arrays;
import java.util.HashSet;

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
        user.setPassword(BcryptUtil.bcryptHash(createUserRequest.password));

        user.persist();

        return new UserResponse(user.id, user.getEmail());
    }

    public LoginResponse login(LoginRequest loginRequest) {
        String normalizedEmail = loginRequest.email.toLowerCase();
        User user = User.find("email", normalizedEmail).firstResult();

        if (user == null) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        if (!BcryptUtil.matches(loginRequest.password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid credentials");
        }

        long tokenDurationInSeconds = 3600L;
        String token = Jwt.issuer("https://poll-service-konrad.com")
                .subject(user.id.toString())
                .upn(user.getEmail())
                .groups(new HashSet<>(Arrays.asList("user")))
                .expiresIn(tokenDurationInSeconds)
                .sign();

        return new LoginResponse(
            token, "Bearer", tokenDurationInSeconds, user.getEmail()
        );
    }
}
