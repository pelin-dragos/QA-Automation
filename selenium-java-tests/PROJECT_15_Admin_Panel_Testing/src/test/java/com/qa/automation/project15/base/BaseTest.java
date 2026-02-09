package com.qa.automation.project15.base;

import com.qa.automation.project15.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Base test class for PROJECT_15 (Admin Panel Testing).
 * <p>
 * Uses Firefox; WebDriver is created before each test and quit after each test for independence.
 * Timeouts from {@link TestConfig}. Login-dependent tests should check {@link TestConfig#isLoginConfigured()}
 * and skip with a clear message when credentials are not set.
 */
public abstract class BaseTest {

    /** WebDriver for the current test; initialised in setUpDriver, closed in tearDownDriver. */
    protected WebDriver driver;

    /** Initialises Firefox driver before every test; WebDriverManager resolves GeckoDriver. */
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
