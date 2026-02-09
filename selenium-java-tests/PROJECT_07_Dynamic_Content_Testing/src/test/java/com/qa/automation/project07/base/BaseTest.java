package com.qa.automation.project07.base;

import com.qa.automation.project07.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Base test for PROJECT_07 (Dynamic Content). Uses Firefox and WebDriverManager.
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
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().window().maximize();
    }

    @AfterEach
    void tearDownDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    protected long getPageHeight() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object h = js.executeScript("return document.body.scrollHeight;");
        return h != null ? ((Number) h).longValue() : 0;
    }

    protected Object executeScript(String script) {
        return ((JavascriptExecutor) driver).executeScript(script);
    }
}
