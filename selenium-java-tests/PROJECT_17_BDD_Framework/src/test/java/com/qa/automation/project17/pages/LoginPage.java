package com.qa.automation.project17.pages;

import com.qa.automation.project17.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for Sauce Demo login page.
 * Used by BDD step definitions for login feature.
 */
public class LoginPage {

    private static final By USERNAME_INPUT = By.id("user-name");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    private static final By ERROR_MESSAGE = By.cssSelector("h3[data-test='error']");

    private final WebDriver driver;
    private final String baseUrl;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this(driver, TestConfig.getBaseUrl());
    }

    public LoginPage(WebDriver driver, String baseUrl) {
        this.driver = driver;
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    public void navigateTo() {
        driver.get(baseUrl);
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_BUTTON));
    }

    public void enterUsername(String username) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_INPUT));
        field.clear();
        if (username != null && !username.isEmpty()) {
            field.sendKeys(username);
        }
    }

    public void enterPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_INPUT));
        field.clear();
        if (password != null && !password.isEmpty()) {
            field.sendKeys(password);
        }
    }

    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON)).click();
    }

    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        wait.until(webDriver -> {
            String url = webDriver.getCurrentUrl();
            if (url != null && url.contains("inventory")) return true;
            return webDriver.findElements(ERROR_MESSAGE).stream().anyMatch(WebElement::isDisplayed);
        });
    }

    public String getErrorMessage() {
        try {
            WebElement error = driver.findElement(ERROR_MESSAGE);
            if (error.isDisplayed()) {
                String text = error.getText();
                return (text != null && !text.isBlank()) ? text.trim() : null;
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_BUTTON));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
