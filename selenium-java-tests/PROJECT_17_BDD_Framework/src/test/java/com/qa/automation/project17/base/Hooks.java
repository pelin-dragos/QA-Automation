package com.qa.automation.project17.base;

import com.qa.automation.project17.config.TestConfig;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Cucumber hooks: create and quit WebDriver per scenario.
 * Each scenario gets a fresh browser so tests are independent.
 */
public class Hooks {

    @Before
    public void setUp() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920");
        options.addArguments("--height=1080");
        WebDriver driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
        DriverHolder.set(driver);
    }

    @After
    public void tearDown() {
        WebDriver driver = DriverHolder.get();
        if (driver != null) {
            driver.quit();
        }
        DriverHolder.clear();
    }
}
