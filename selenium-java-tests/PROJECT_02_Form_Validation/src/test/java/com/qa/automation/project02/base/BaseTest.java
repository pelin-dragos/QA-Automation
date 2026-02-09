package com.qa.automation.project02.base;

import com.qa.automation.project02.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Base test class for PROJECT_02 (Form Validation).
 * <p>
 * All tests use Firefox. WebDriver is created before each test and quit after each test so
 * tests remain independent. Timeouts are taken from {@link TestConfig}; page load and a short
 * implicit wait are configured here.
 */
public abstract class BaseTest {

    /** WebDriver instance shared by the test; initialized in {@link #setUpDriver()}. */
    protected WebDriver driver;

    /** Initializes Firefox driver with fixed viewport and timeouts; called before every test. */
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
        if (driver != null) driver.quit();
    }
}
