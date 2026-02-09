package com.qa.automation.project02.pages;

import com.qa.automation.project02.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for The Internet Form Authentication login page.
 * <p>
 * Used for password validation tests. Locators: #username, #password, button[type=submit], #flash.
 * Credentials are test-only (tomsmith / SuperSecretPassword!); no production data.
 */
public class TheInternetLoginPage {

    private static final By USERNAME = By.id("username");
    private static final By PASSWORD = By.id("password");
    private static final By LOGIN_BUTTON = By.cssSelector("button[type='submit']");
    private static final By FLASH = By.id("flash");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public TheInternetLoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    /** Navigates to the login URL and waits until the login button is visible. */
    public void navigateTo() {
        driver.get(TestConfig.getTheInternetLoginUrl());
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_BUTTON));
    }

    /** Clears and fills the username field; no-op if string is null or empty. */
    public void enterUsername(String s) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME));
        el.clear();
        if (s != null && !s.isEmpty()) el.sendKeys(s);
    }

    /** Clears and fills the password field; no-op if string is null or empty. */
    public void enterPassword(String s) {
        WebElement el = driver.findElement(PASSWORD);
        el.clear();
        if (s != null && !s.isEmpty()) el.sendKeys(s);
    }

    /** Clicks the login button after ensuring it is clickable. */
    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON)).click();
    }

    /** Returns the flash message text if visible and non-empty; otherwise null. */
    public String getFlashMessage() {
        try {
            WebElement el = driver.findElement(FLASH);
            if (el.isDisplayed()) {
                String t = el.getText();
                return (t != null && !t.trim().isEmpty()) ? t.trim() : null;
            }
        } catch (Exception ignored) {}
        return null;
    }

    /** Returns true if the flash message indicates an error (invalid, incorrect, wrong, error). */
    public boolean isFlashError() {
        String msg = getFlashMessage();
        if (msg == null) return false;
        String lower = msg.toLowerCase();
        return lower.contains("invalid") || lower.contains("incorrect") || lower.contains("wrong") || lower.contains("error");
    }
}
