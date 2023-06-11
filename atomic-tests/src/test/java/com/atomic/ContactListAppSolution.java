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

public class ContactListAppSolution {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // mandatory to work with Java 11 +
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

    @Test
    public void userCanLogin() {
        // 1. Create a user

    }

    @Test
    public void checkoutShouldWork() {
        String username = "standard_user";
        List<String> products = new ArrayList<>();
        products.add("0");
        String desiredFinalPath = "https://www.saucedemo.com/checkout-step-two.html";
        String userCookies = "document.cookie='session-username=" + username + "';";
        String productStorage = !products.isEmpty() ? "localStorage.setItem('cart-contents', '[" + String.join(",", products) + "]');" : "";

        // Go to the domain
        driver.get("https://www.saucedemo.com/");
        // Clear the cookies and storage
        driver.manage().deleteAllCookies();
        ((JavascriptExecutor) driver).executeScript("localStorage.clear();");
        // Set the new cookies and storage
        ((JavascriptExecutor) driver).executeScript(userCookies + " " + productStorage);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Now go to the page
        driver.get(desiredFinalPath);
        // throws a nice exception
        new WebDriverWait(driver, Duration.ofSeconds(6)).until(d -> d.findElement(By.id("checkout_summary_container")));
        Assertions.assertEquals(1, getItemCount(), "Should have 1 item in the cart");

        /*
         * The actual test
         * */
        WebElement finishButton = new WebDriverWait(driver, Duration.ofSeconds(6)).until(d -> d.findElement(By.cssSelector("[data-test='finish']")));
        finishButton.click();

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
