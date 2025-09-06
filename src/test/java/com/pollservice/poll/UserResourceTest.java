package com.pollservice.poll;

import com.pollservice.poll.dto.*;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.smallrye.common.constraint.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

@QuarkusTest
public class UserResourceTest {
    private String existingEmail;

    @Transactional
    @BeforeEach
    public void defaultUser() {
        User.deleteAll();

        User testUser = new User();
        this.existingEmail = "default@existing.com";
        String password = "userpassword";

        testUser.setEmail(this.existingEmail);
        testUser.setPassword(BcryptUtil.bcryptHash(password));
        testUser.persist();
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
        createUserRequest.email = existingEmail;
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
        createUserRequest.email = existingEmail.toUpperCase();
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
        createUserRequest.email = existingEmail;
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

    @Test
    public void testLogin(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.email = existingEmail;
        loginRequest.password = "userpassword";

        LoginResponse loginResponse = given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/users/login")
                .then()
                .statusCode(200)
                .extract().as(LoginResponse.class);

        assertNotNull(loginResponse.token);
    }

    @Test
    public void testLogin_invalidPassword(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.email = existingEmail;
        loginRequest.password = "wrongpassword";

        given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/users/login")
                .then()
                .statusCode(401);
    }

    @Test
    public void testLogin_invalidEmail(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.email = "invalid@email";
        loginRequest.password = "password";

        given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/users/login")
                .then()
                .statusCode(401);
    }

    @Test
    public void testLogin_isCaseInsensitive(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.email = existingEmail.toUpperCase();
        loginRequest.password = "userpassword";

        given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/users/login")
                .then()
                .statusCode(200);
    }

    @Test
    public void testLogin_emailNull(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.email = null;
        loginRequest.password = "password";

        given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/users/login")
                .then()
                .statusCode(400);
    }

    @Test
    public void testLogin_passwordNull(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.email = existingEmail;
        loginRequest.password = null;

        given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/users/login")
                .then()
                .statusCode(400);
    }

    @Test
    public void testLogin_invalidEmailFormat(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.email = "not-an-email";
        loginRequest.password = "password";

        given()
                .contentType(ContentType.JSON)
                .body(loginRequest)
                .when()
                .post("/users/login")
                .then()
                .statusCode(400);
    }
}