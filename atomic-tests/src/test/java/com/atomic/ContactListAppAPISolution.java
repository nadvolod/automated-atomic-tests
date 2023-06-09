package com.atomic;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

public class ContactListAppAPISolution {

    private static String password;
    // Our web app https://thinking-tester-contact-list.herokuapp.com
    private WebDriver driver;
    static String uniqueEmail;


    @BeforeAll
    public static void runOnceBeforeAllTests() {
        uniqueEmail = UUID.randomUUID() + "@email.com";
        password = "Hello123";
    }

    @Test
    public void sanityTest() {
        // 1. Create a user
        // Define the base URL
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
                .extract()
                .response();
        System.out.println("Response:\n" + response.asString());
    }
    @Test
    public void userCanRegister() {
        // Define the base URL
        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";

        // Define the request body as a HashMap
        Map<String, String> requestBody = new HashMap<>();
        requestBody.put("firstName", "Nikolay");
        requestBody.put("lastName", "Test");
        requestBody.put("email", uniqueEmail);
        requestBody.put("password", "Hello123");

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
    }
}
