package com.atomic;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.*;

import io.restassured.RestAssured.*;
import io.restassured.matcher.RestAssuredMatchers.*;
import org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ContactListAppSolution {

    // Our web app https://thinking-tester-contact-list.herokuapp.com
    private WebDriver driver;

//    @BeforeEach
//    public void setUp() {
//        ChromeOptions options = new ChromeOptions();
//        // mandatory to work with Java 11 +
//        options.addArguments("--remote-allow-origins=*");
//        driver = new ChromeDriver(options);
//    }


    @Test
    void userCanRegister() {

    }

    @Test
    public void sanityTest() {
        // 1. Create a user
        String uniqueEmail = UUID.randomUUID() + "@email.com";
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
                .extract()
                .response();
        System.out.println("Response:\n" + response.asString());
    }
    @Test
    public void registerUser() {
        // 1. Create a user
        String uniqueEmail = UUID.randomUUID() + "@email.com";
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
////                .body("lastName", equalTo("Test"))
//                .body("email", equalTo(uniqueEmail))
//                .extract()
//                .response();
    }
}
