package com.qa.automation.project16.base;

import com.qa.automation.project16.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Base test class for PROJECT_16 (API + UI Integration).
 * <p>
 * Provides a Selenium WebDriver (Firefox) for UI steps: navigate to API URLs in the browser
 * and parse JSON from the page. API steps use RestAssured in the test classes with the same
 * base URL from {@link TestConfig}. Each test gets a fresh driver; quit after each test.
 */
public abstract class BaseTest {

    /** WebDriver for UI verification steps; initialised in setUpDriver, closed in tearDownDriver. */
    protected WebDriver driver;

    /** Initialises Firefox driver before every test; used when test performs UI steps. */
    @BeforeEach
    void setUpDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestConfig.getUiTimeoutSeconds()));
    }

    /** Closes the browser and quits the driver after each test. */
    @AfterEach
    void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
