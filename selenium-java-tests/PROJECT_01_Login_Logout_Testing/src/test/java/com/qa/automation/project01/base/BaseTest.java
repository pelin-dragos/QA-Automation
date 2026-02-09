package com.qa.automation.project01.base;

import com.qa.automation.project01.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Base class for all PROJECT_01 Selenium tests.
 * <p>
 * Responsibilities:
 * <ul>
 *   <li>Create and configure a Firefox WebDriver before each test (no shared browser state).</li>
 *   <li>Use WebDriverManager so GeckoDriver is resolved automatically (no manual driver path).</li>
 *   <li>Set timeouts: short implicit wait (2s) and configurable page-load timeout for stability.</li>
 *   <li>Quit the driver after each test so tests remain independent and do not affect each other.</li>
 * </ul>
 * Firefox is used instead of Chrome to avoid the "Change your password" (data breach) popup
 * that can block the login form during automation.
 */
public abstract class BaseTest {

    /** Shared WebDriver instance for the current test; initialised in setUpDriver, closed in tearDownDriver. */
    protected WebDriver driver;

    /**
     * Runs before each test method. Initialises Firefox, sets timeouts, and assigns the driver.
     * Each test gets a fresh browser session so execution order does not matter.
     */
    @BeforeEach
    void setUpDriver() {
        // Resolve and set up GeckoDriver (downloads if missing); no manual PATH or driver path needed.
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        driver = new FirefoxDriver(options);

        // Short implicit wait as fallback; tests should rely on explicit waits in page objects.
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    /**
     * Runs after each test method. Closes the browser and releases resources so the next test
     * starts with a clean driver.
     */
    @AfterEach
    void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
