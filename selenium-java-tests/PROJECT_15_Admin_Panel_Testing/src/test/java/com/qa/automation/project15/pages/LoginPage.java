package com.qa.automation.project15.pages;

import com.qa.automation.project15.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for the admin panel login page (e.g. OrangeHRM).
 * <p>
 * Encapsulates login form locators and actions. Uses explicit waits only.
 * Credentials come from TestConfig or caller; never hardcoded. Do not log passwords.
 */
public class LoginPage {

    /** OrangeHRM 5: inputs by name (or placeholder fallback). */
    private static final By USERNAME_INPUT = By.name("username");
    private static final By PASSWORD_INPUT = By.name("password");
    /** Login submit button (type submit or orangehrm login button). */
    private static final By LOGIN_BUTTON = By.cssSelector("button[type='submit']");

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

    /** Navigates to the login page and waits until the login button is visible. */
    public void navigateTo() {
        driver.get(baseUrl + "index.php/auth/login");
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_BUTTON));
    }

    /** Clears and types the username. Handles null/empty. */
    public void enterUsername(String username) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_INPUT));
        field.clear();
        if (username != null && !username.isEmpty()) {
            field.sendKeys(username);
        }
    }

    /** Clears and types the password. Handles null/empty. Do not log the value. */
    public void enterPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_INPUT));
        field.clear();
        if (password != null && !password.isEmpty()) {
            field.sendKeys(password);
        }
    }

    /** Clicks the Login button. Caller must assert outcome (dashboard or error). */
    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON)).click();
    }

    /**
     * Full login flow: enter username and password, click Login, then wait until
     * the dashboard or main menu is visible (success) or an error appears.
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        wait.until(webDriver -> {
            String url = webDriver.getCurrentUrl();
            if (url != null && (url.contains("dashboard") || url.contains("index.php"))) {
                return true;
            }
            return webDriver.findElements(LOGIN_BUTTON).isEmpty() || !webDriver.findElement(LOGIN_BUTTON).isDisplayed();
        });
    }

    /** Returns true if the login page is loaded (login button visible). */
    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_BUTTON));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
