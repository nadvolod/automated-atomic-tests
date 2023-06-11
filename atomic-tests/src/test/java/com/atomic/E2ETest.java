package com.atomic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class E2ETest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // mandatory to work with Java 11 +
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }
    @Test
    public void shouldAddItemToCart() {
        // Open the SauceDemo website
        driver.get("https://www.saucedemo.com");

        // Find the username and password input fields and enter the credentials
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));

        usernameField.sendKeys("standard_user");
        passwordField.sendKeys("secret_sauce");

        // Find and click on the login button
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.click();

        // Find the Add to Cart button for the desired item and click on it
        WebElement addToCartButton = driver.findElement(By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']"));
        addToCartButton.click();

        // Verify that the item is added to the cart
        WebElement cartBadge = driver.findElement(By.className("shopping_cart_badge"));
        String cartCount = cartBadge.getText();

        // Use assertions to verify the result
        Assertions.assertEquals(1, getItemsInCart(), "Item count in the cart is not as expected!");
    }
    public int getItemsInCart() {
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
    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
