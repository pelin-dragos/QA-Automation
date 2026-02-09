package com.qa.automation.project14.pages;

import com.qa.automation.project14.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for the banking application login page (e.g. ParaBank).
 * <p>
 * Encapsulates locators and actions for the login form. Uses explicit waits only.
 * Credentials are never hardcoded; callers pass username/password (typically from
 * TestConfig). Do not log passwords or full credentials in reports.
 */
public class LoginPage {

    /** ParaBank login form: username and password inputs by name, submit by value. */
    private static final By USERNAME_INPUT = By.name("username");
    private static final By PASSWORD_INPUT = By.name("password");
    private static final By LOGIN_BUTTON = By.xpath("//input[@value='Log In']");
    /** Error message shown when login fails. ParaBank may use .error or text in #rightPanel. */
    private static final By ERROR_MESSAGE = By.className("error");
    /** Fallback: right panel often contains error text when login fails. */
    private static final By RIGHT_PANEL = By.id("rightPanel");

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

    /** Navigates to the login page (index) and waits until the login button is visible. */
    public void navigateTo() {
        driver.get(baseUrl + "index.htm");
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_BUTTON));
    }

    /** Clears and types the username. Handles null/empty for negative tests. */
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

    /** Clicks the Log In button. Callers must assert outcome (dashboard or error). */
    public void clickLogin() {
        wait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON)).click();
    }

    /**
     * Full login flow: enter username and password, click Log In, then wait until either
     * the overview/dashboard is visible (success) or we remain on login (failure: URL still index or error visible).
     */
    public void login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        wait.until(webDriver -> {
            String url = webDriver.getCurrentUrl();
            if (url != null && url.contains("overview")) {
                return true;
            }
            if (webDriver.findElements(ERROR_MESSAGE).stream().anyMatch(WebElement::isDisplayed)) {
                return true;
            }
            if (url != null && url.contains("index")) {
                return webDriver.findElements(USERNAME_INPUT).stream().anyMatch(WebElement::isDisplayed);
            }
            return false;
        });
    }

    /**
     * Returns the visible error message text if login failed; otherwise null.
     * Tries .error first, then #rightPanel text if it contains error-like wording. Do not log full text.
     */
    public String getErrorMessage() {
        try {
            WebElement error = driver.findElement(ERROR_MESSAGE);
            if (error.isDisplayed()) {
                String text = error.getText();
                return (text != null && !text.isBlank()) ? text.trim() : null;
            }
        } catch (Exception ignored) {
            // No .error element; try right panel.
        }
        try {
            WebElement panel = driver.findElement(RIGHT_PANEL);
            if (panel.isDisplayed()) {
                String text = panel.getText();
                if (text != null && !text.isBlank()) {
                    String lower = text.toLowerCase();
                    if (lower.contains("error") || lower.contains("invalid") || lower.contains("incorrect") || lower.contains("wrong")) {
                        return text.trim();
                    }
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    /** Returns true if the login page is loaded (login button visible). Used to confirm we are on login (e.g. after logout). */
    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_BUTTON));
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
