package com.atomic;

import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ContactListAppUIExercise {
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
        // TODO make this test pass, the user should be able to login

        // 1. But how can we login without a registered user??? (see RESTAPI.java)

        //2. Navigate to our URL

        // 3. Wait for email input then enter uniqueEmail

        // 4. enter password and click submit

        // Do NOT modify the code below!!
        WebElement logout = new WebDriverWait(driver, Duration.ofSeconds(6))
                .until(ExpectedConditions.elementToBeClickable(By.cssSelector("#logout")));
        assertTrue(logout.isDisplayed(), "Logout button should be displayed");
    }

    @Test
    void addContactAtomic() {
        // TODO the user should be able to Add a Contact to our app without logging in to the UI

        // 1. How will we create a new user without using the UI???

        //2. Go to our app URL

        //3. clean current browser state. Use this code below, it will help you
        driver.manage().deleteAllCookies();
        ((JavascriptExecutor) driver).executeScript("localStorage.clear();");

        //4.  Set the new cookies in the browser

        // Now we have access to the private pages of the app without using the UI!
        //5. Do NOT change the code below!! It must pass after you set cookies
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
