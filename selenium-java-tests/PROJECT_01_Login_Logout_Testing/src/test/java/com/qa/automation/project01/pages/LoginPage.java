package com.qa.automation.project01.pages;

import com.qa.automation.project01.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for the Sauce Demo login page.
 * <p>
 * Encapsulates all locators and actions for the login form so tests stay readable and
 * maintainable. Uses explicit waits (WebDriverWait) only—no Thread.sleep—so tests are
 * stable and fast. Selectors match Sauce Demo's DOM (IDs and data-test attributes).
 */
public class LoginPage {

    // ——— Locators (Sauce Demo login form) ———
    private static final By USERNAME_INPUT = By.id("user-name");
    private static final By PASSWORD_INPUT = By.id("password");
    private static final By LOGIN_BUTTON = By.id("login-button");
    /** Error banner shown when credentials are wrong or validation fails. */
    private static final By ERROR_MESSAGE = By.cssSelector("h3[data-test='error']");

    private final WebDriver driver;
    private final String baseUrl;
    private final WebDriverWait wait;

    /** Creates a LoginPage using the base URL from TestConfig (env or default). */
    public LoginPage(WebDriver driver) {
        this(driver, TestConfig.getBaseUrl());
    }

    /** Creates a LoginPage with an explicit base URL (e.g. for different environments). */
    public LoginPage(WebDriver driver, String baseUrl) {
        this.driver = driver;
        this.baseUrl = baseUrl.endsWith("/") ? baseUrl : baseUrl + "/";
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    /**
     * Opens the login page and waits until the login button is visible.
     * Ensures the page is ready before tests enter credentials or click Login.
     */
    public void navigateTo() {
        driver.get(baseUrl);
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_BUTTON));
    }

    /**
     * Clears the username field and types the given value.
     * Handles null/empty so callers can simulate "leave empty" for validation tests.
     */
    public void enterUsername(String username) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_INPUT));
        field.clear();
        if (username != null && !username.isEmpty()) {
            field.sendKeys(username);
        }
    }

    /**
     * Clears the password field and types the given value.
     * Handles null/empty for validation tests (e.g. empty password).
     */
    public void enterPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_INPUT));
        field.clear();
        if (password != null && !password.isEmpty()) {
            field.sendKeys(password);
        }
    }

    /**
     * Clicks the Login button. Does not wait for navigation or error message.
     * Callers must assert outcome (e.g. ProductsPage.isLoaded() or getErrorMessage()).
     */
    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON)).click();
    }

    /**
     * Full login flow: fill username and password, click Login, then wait until either
     * the URL contains "inventory" (success) or the error message is visible (failure).
     * Tests must still assert the expected outcome (success vs failure).
     */
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

    /**
     * Returns the visible error message text if the login failed and the error element is shown;
     * otherwise null. Used by tests to assert that invalid login shows an error.
     * Message content is not logged to avoid leaking sensitive data in reports.
     */
    public String getErrorMessage() {
        try {
            WebElement error = driver.findElement(ERROR_MESSAGE);
            if (error.isDisplayed()) {
                String text = error.getText();
                return (text != null && !text.isBlank()) ? text.trim() : null;
            }
        } catch (Exception ignored) {
            // No error element or not visible — login may have succeeded or page not updated yet.
        }
        return null;
    }

    /**
     * Returns true if the login page is considered loaded (login button visible).
     * Used to confirm we are on the login screen (e.g. after logout or when asserting no redirect).
     */
    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_BUTTON));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /** Exposes the username input for tests that need to check required/validation (e.g. TC-LOGIN-007). */
    public WebElement getUsernameField() {
        return driver.findElement(USERNAME_INPUT);
    }

    /** Exposes the password input for validation checks (e.g. required attribute). */
    public WebElement getPasswordField() {
        return driver.findElement(PASSWORD_INPUT);
    }

    /** Exposes the login button (e.g. to assert it is visible after logout). */
    public WebElement getLoginButton() {
        return driver.findElement(LOGIN_BUTTON);
    }
}
