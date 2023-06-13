package com.atomic;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RESTAPI {
    public static Response registUser(String uniqueEmail, String password) {
        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";

        // Define the request body as a HashMap
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("firstName", "Nikolay");
        requestBody.put("lastName", "Test");
        requestBody.put("email", uniqueEmail);
        requestBody.put("password", password);

        Response response = given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/users")
                .then()
                .assertThat()
                .statusCode(201) // 201 is the HTTP status code for "Created"
                .body("user.firstName", equalTo("Nikolay")) // Assert that the firstName in the response is "Nikolay"
                .body("user.lastName", equalTo("Test")) // Assert that the lastName in the response is "Test"
                .body("user.email", equalTo(uniqueEmail)) // Assert that the email in the response is the uniqueEmail
                .extract()
                .response();
        return response;
    }
}
