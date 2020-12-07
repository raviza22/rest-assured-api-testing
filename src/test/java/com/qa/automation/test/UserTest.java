package com.qa.automation.test;

import com.qa.automation.service.User;
import org.apache.http.HttpStatus;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class UserTest extends BaseTest {

    private static final String USERS_ENDPOINT = "/users";

    @Test
    public void verifyUsersListIsDisplayed() {
        int expPageNum = 2;
        given().
                param("page", expPageNum).
                when().
                get(USERS_ENDPOINT).
                then().
                statusCode(HttpStatus.SC_OK).
                body("page", is(expPageNum)).
                body("data", is(notNullValue()));
    }

    @Test
    public void verifySingleUserDataIsDisplayed() {
        given().
                pathParam("userId", 2).
                when().
                get(USERS_ENDPOINT + "/{userId}").
                then().
                statusCode(HttpStatus.SC_OK)
                .body("data.email", containsString("@reqres.in"));
    }

    @Test
    public void verifySingleUserNotFound() {
        given().
                pathParam("userId", 23).
                when().
                get(USERS_ENDPOINT + "/{userId}").
                then().
                statusCode(HttpStatus.SC_NOT_FOUND);
    }

    @Test
    public void verifyNewUserIsCreated() {
        User user = new User("john", "engineer");

        given().
                body(user).
                when().
                post(USERS_ENDPOINT).
                then().
                statusCode(HttpStatus.SC_CREATED).
                body("name", is("john"));
    }
}
