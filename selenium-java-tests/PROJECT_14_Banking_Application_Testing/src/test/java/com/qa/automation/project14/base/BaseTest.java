package com.qa.automation.project14.base;

import com.qa.automation.project14.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Base test class for PROJECT_14 (Banking Application Testing).
 * <p>
 * Uses Firefox for all tests. WebDriver is created before each test and quit after each test
 * so tests remain independent. Timeouts are taken from {@link TestConfig}. Tests that require
 * login should check {@link TestConfig#isLoginConfigured()} and skip with a clear message when
 * credentials are not set (no hardcoded credentials).
 */
public abstract class BaseTest {

    /** WebDriver instance for the current test; initialised in setUpDriver, closed in tearDownDriver. */
    protected WebDriver driver;

    /**
     * Initialises Firefox driver with fixed viewport and timeouts before every test.
     * WebDriverManager resolves GeckoDriver automatically.
     */
    @BeforeEach
    void setUpDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    /** Closes the browser and quits the driver after each test. */
    @AfterEach
    void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
