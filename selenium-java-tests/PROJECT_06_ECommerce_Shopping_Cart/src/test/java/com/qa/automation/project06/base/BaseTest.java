package com.qa.automation.project06.base;

import com.qa.automation.project06.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Base test for PROJECT_06 (E-Commerce Shopping Cart). Uses Firefox and WebDriverManager.
 */
public abstract class BaseTest {

    protected WebDriver driver;

    @BeforeEach
    void setUpDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
}
