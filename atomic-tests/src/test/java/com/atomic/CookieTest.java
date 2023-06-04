package com.atomic;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CookieTest {
    private WebDriver driver;

    @BeforeEach
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        // mandatory to work with Java 11 +
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
    }

    @Test
    public void cookieLogin() throws InterruptedException {
        String username = "standard_user";
        List<String> products = new ArrayList<>();
        String desiredFinalPath = "https://www.saucedemo.com/inventory.html";
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
        // only for demo
        Thread.sleep(5000);

        // throws a nice exception
        new WebDriverWait(driver, Duration.ofSeconds(6)).until(d -> d.findElement(By.id("inventory_container")));
    }

    @Test
    public void seedingData() {
        String username = "standard_user";
        List<String> products = new ArrayList<>();
        products.add("0");
        String desiredFinalPath = "https://www.saucedemo.com/inventory.html";
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
        new WebDriverWait(driver, Duration.ofSeconds(6)).until(d -> d.findElement(By.id("inventory_container")));

        Assertions.assertEquals(1, getItemCount());
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
    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
