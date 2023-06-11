package com.atomic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class SauceDemoExercise {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // mandatory to work with Java 11 +
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

    @Test
    public void homePageShouldRender() {
        /*
        * TODO
        * 1. Login to the app without using the UI
        * 2. Assert that https://www.saucedemo.com/inventory.html renders
        *
        * */
    }

    @Test
    public void checkoutShouldWork() {
        /*
         * TODO
         * 1. Login to the app without using the UI
         * 2. Assert that a user can checkout
         *
         * */

        // Don't modify this assertion
        assertDoesNotThrow(this::isCheckoutComplete);
    }
    public int getItemCount() {
        WebElement itemCounter;
        try{
            itemCounter = driver.findElement(By.cssSelector(".shopping_cart_badge"));
            return Integer.parseInt(itemCounter.getText());
        }
        catch (NoSuchElementException e)
        {
            return 0;
        }
    }
    public void isCheckoutComplete() {
        new WebDriverWait(driver, Duration.ofSeconds(6)).until(d -> d.findElement(By.id("checkout_complete_container")));
    }
    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
