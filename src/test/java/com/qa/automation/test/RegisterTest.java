package com.qa.automation.test;

import com.qa.automation.service.User;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

public class RegisterTest extends BaseTest {

    private static final String REGISTER_ENDPOINT = "/register";

    @Test
    public void verifySuccessfulUserRegistration() {
        given().
                body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}").
                when().
                post(REGISTER_ENDPOINT).
                then().
                statusCode(HttpStatus.SC_OK).
                body("id", is(4)).
                body("token", is(notNullValue()));
    }

    @Test
    public void verifyUnsuccessfulUserRegistration() {
        given().
                body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\"\n" +
                        "}").
                when().
                post(REGISTER_ENDPOINT).
                then().
                statusCode(HttpStatus.SC_BAD_REQUEST).
                body("error", is("Missing password"));
    }
}
