package com.pollservice.poll;

import com.pollservice.poll.dto.CreateUserRequest;
import com.pollservice.poll.dto.PollResponse;
import com.pollservice.poll.dto.UserResponse;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static io.restassured.RestAssured.given;
import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class UserResourceTest {
    @Test
    @Transactional
    public void testCreateUser() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.email = "testemail@example.com";
        createUserRequest.password = "testpassword";

        UserResponse userResponse =
                given()
                        .contentType(ContentType.JSON)
                        .body(createUserRequest)
                        .when()
                        .post("/users")
                        .then()
                        .statusCode(201)
                        .extract().as(UserResponse.class);

        assertEquals("testemail@example.com", userResponse.email);
        assertNotNull(userResponse.id);
    }
}