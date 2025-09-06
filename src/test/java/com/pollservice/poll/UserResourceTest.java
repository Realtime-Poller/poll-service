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
    private User user = new User();

    @Transactional
    @BeforeEach
    public void defaultUser() {
        user.setEmail("default@existing.com");
        user.setPassword("userpassword");
        user.persist();
    }

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

    @Test
    @Transactional
    public void testCreateUser_passwordNull() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.email = "testemail@example.com";
        createUserRequest.password = null;

        given()
                .contentType(ContentType.JSON)
                .body(createUserRequest)
                .when()
                .post("/users")
                .then()
                .statusCode(400);
    }

    @Test
    @Transactional
    public void testCreateUser_emailNull() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.email = null;
        createUserRequest.password = "testpassword";

        given()
                .contentType(ContentType.JSON)
                .body(createUserRequest)
                .when()
                .post("/users")
                .then()
                .statusCode(400);
    }

    @Test
    @Transactional
    public void testCreateUser_emailInvalid() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.email = "testemail";
        createUserRequest.password = "password";

        given()
                .contentType(ContentType.JSON)
                .body(createUserRequest)
                .when()
                .post("/users")
                .then()
                .statusCode(400);
    }

    @Test
    @Transactional
    public void testCreateUser_emailAlreadyInUse() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.email = user.getEmail();
        createUserRequest.password = "testpassword";

        given()
                .contentType(ContentType.JSON)
                .body(createUserRequest)
                .when()
                .post("/users")
                .then()
                .statusCode(409);
    }

    @Test
    @Transactional
    public void testCreateUser_existingEmailCaseSensitive() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.email = user.getEmail().toUpperCase();
        createUserRequest.password = "password";

        given()
                .contentType(ContentType.JSON)
                .body(createUserRequest)
                .when()
                .post("/users")
                .then()
                .statusCode(409);
    }

    @Test
    @Transactional
    public void testCreateUser_passwordTooShort() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.email = user.getEmail();
        createUserRequest.password = "1234567";

        given()
                .contentType(ContentType.JSON)
                .body(createUserRequest)
                .when()
                .post("/users")
                .then()
                .statusCode(400);
    }

    @Test
    @Transactional
    public void testCreateUser_passwordTooLong() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.email = "testemail@example.com";
        createUserRequest.password = "xmfpajbbsyvhwfholqdrxmzeorsekeqqduxayqaoypzfsgygojnafxvdrmijecodhsleaslldalsemjwzjiogvzoibxpovmtaxnpvhejuoljcrvageynuodblbsnyhpayrrbtwfunhggnbhvirmvuyozinrwispubsbmztgjbguegqotwduwshfqyuxenjihzewaxqkjdmngvokcylrfdmypakmghftmmoledvqrefmbomycpljelcvgvytbtfn";

        given()
                .contentType(ContentType.JSON)
                .body(createUserRequest)
                .when()
                .post("/users")
                .then()
                .statusCode(400);
    }

    @Test
    @Transactional
    public void testCreateUser_passwordNoLetters() {
        CreateUserRequest createUserRequest = new CreateUserRequest();
        createUserRequest.email = "testemail@example.com";
        createUserRequest.password = "";

        given()
                .contentType(ContentType.JSON)
                .body(createUserRequest)
                .when()
                .post("/users")
                .then()
                .statusCode(400);
    }
}