package com.atomic;

import io.restassured.internal.RestAssuredResponseImpl;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContactListAppUISolution {
    private static String password;
    // Our web app https://thinking-tester-contact-list.herokuapp.com
    private WebDriver driver;
    static String uniqueEmail;


    @BeforeAll
    public static void runOnceBeforeAllTests() {
        uniqueEmail = UUID.randomUUID() + "@email.com";
        password = "Hello123";
    }

    @BeforeEach
    void beforeEachTest() {
        ChromeOptions options = new ChromeOptions();
        // mandatory to work with Java 11 +
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

    @Test
    void userCanLogin() {
        RESTAPI.registUser(uniqueEmail, password);

        driver.navigate().to("https://thinking-tester-contact-list.herokuapp.com/");

        // First wait should be explicit to allow for extra rendering time
        WebElement emailInput = new WebDriverWait(driver, Duration.ofSeconds(6))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#email")));
        emailInput.sendKeys(uniqueEmail);

        // Next locators can be implicit
        driver.findElement(By.cssSelector("#password")).sendKeys(password);
        driver.findElement(By.cssSelector("#submit")).click();

        WebElement logout = new WebDriverWait(driver, Duration.ofSeconds(6))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#logout")));
        assertTrue(logout.isDisplayed(), "Logout button should be displayed");
    }

    @Test
    void addContactAtomic() {
        Response response = RESTAPI.registUser(uniqueEmail, password);

        driver.navigate().to("https://thinking-tester-contact-list.herokuapp.com/");

        // clean current browser state
        driver.manage().deleteAllCookies();
        ((JavascriptExecutor) driver).executeScript("localStorage.clear();");
        // Set the new cookies
        String userCookies = "document.cookie='token=" + response.cookies().get("token") + "';";
        ((JavascriptExecutor) driver).executeScript(userCookies);
        // refresh to reload the session
        driver.navigate().refresh();

        // Now we have access to the private pages of the app without using the UI!
        driver.navigate().to("https://thinking-tester-contact-list.herokuapp.com/contactList");
        WebElement logout = new WebDriverWait(driver, Duration.ofSeconds(6))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#logout")));
        assertTrue(logout.isDisplayed(), "Logout button should be displayed");

        // The UI test can now start here. Do anything that you want in the UI
        // Like adding a contact
    }

    @Test
    void unregisteredUserCantLogin() {
        driver.navigate().to("https://thinking-tester-contact-list.herokuapp.com/");

        // First wait should be explicit to allow for extra rendering time
        WebElement emailInput = new WebDriverWait(driver, Duration.ofSeconds(6))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#email")));
        emailInput.sendKeys(uniqueEmail);

        // Next locators can be implicit
        driver.findElement(By.cssSelector("#password")).sendKeys(password);
        driver.findElement(By.cssSelector("#submit")).click();

        // It's smart to wait for our element before asserting
        WebElement errorMessage = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#error")));

        assertEquals("Incorrect username or password", errorMessage.getText(), "Should display error message for unregistered user");
    }

    @AfterEach
    public void tearDown() {
        if(driver != null)
            driver.quit();
    }
}
