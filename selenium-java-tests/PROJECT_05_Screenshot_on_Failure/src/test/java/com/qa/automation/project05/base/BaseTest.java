package com.qa.automation.project05.base;

import com.qa.automation.project05.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Base for PROJECT_05. Provides WebDriver and registers ScreenshotOnFailureWatcher.
 */
public abstract class BaseTest {

    protected WebDriver driver;

    @RegisterExtension
    ScreenshotOnFailureWatcher screenshotWatcher = new ScreenshotOnFailureWatcher(() -> driver);

    @BeforeEach
    void setUpDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--width=1920", "--height=1080");
        driver = new FirefoxDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    // Driver is quit by ScreenshotOnFailureWatcher (after screenshot on failure, or on success/abort)
}
