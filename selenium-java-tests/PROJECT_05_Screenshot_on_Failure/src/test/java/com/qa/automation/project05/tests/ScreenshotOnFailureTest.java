package com.qa.automation.project05.tests;

import com.qa.automation.project05.base.BaseTest;
import com.qa.automation.project05.pages.LoginPage;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Demonstrates screenshot capture on test failure.
 * One test passes (no screenshot); four tests intentionally fail to trigger screenshots.
 */
public class ScreenshotOnFailureTest extends BaseTest {

    private static final String SCREENSHOT_DEMO_MSG = "INTENTIONAL FAILURE for screenshot demo. Screenshot should be in target/screenshot-on-failure/";

    @Test
    void shouldSuccessfullyLogin_noScreenshot() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        assertTrue(loginPage.isLoaded(), "Login page should be loaded");
        loginPage.login("standard_user", "secret_sauce");
        assertTrue(loginPage.getCurrentUrl().contains("inventory"), "Should be on inventory page");
    }

    @Test
    void shouldFailWithInvalidCredentials_screenshotCaptured() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        assertTrue(loginPage.isLoaded(), "Login page should be loaded");
        loginPage.login("invalid_user", "invalid_password");
        assertTrue(loginPage.getCurrentUrl().contains("inventory"), SCREENSHOT_DEMO_MSG);
    }

    @Test
    void shouldFailWithAssertionError_screenshotCaptured() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        assertFalse(loginPage.isLoaded(), SCREENSHOT_DEMO_MSG);
    }

    @Test
    void shouldFailWithElementNotFound_screenshotCaptured() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("this-element-does-not-exist")));
        } catch (Exception ignored) {
        }
        fail(SCREENSHOT_DEMO_MSG + " (Element not found)");
    }

    @Test
    void shouldFailWithTimeout_screenshotCaptured() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("element-that-will-never-appear")));
        } catch (Exception ignored) {
        }
        fail(SCREENSHOT_DEMO_MSG + " (Timeout)");
    }
}
