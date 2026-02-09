package com.qa.automation.project04.base;

import com.qa.automation.project04.config.TestConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Base for PROJECT_04 cross-browser tests.
 * Provides createDriver(Browser) for parameterized tests; each test is responsible for quitting the driver.
 */
public abstract class BaseTest {

    /**
     * Creates a new WebDriver for the given browser. Caller must quit the driver when done.
     */
    protected static WebDriver createDriver(Browser browser) {
        WebDriver driver;
        switch (browser) {
            case CHROME:
                WebDriverManager.chromedriver().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--window-size=1920,1080");
                driver = new ChromeDriver(chromeOptions);
                break;
            case EDGE:
                WebDriverManager.edgedriver().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.addArguments("--window-size=1920,1080");
                driver = new EdgeDriver(edgeOptions);
                break;
            case FIREFOX:
            default:
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--width=1920", "--height=1080");
                driver = new FirefoxDriver(firefoxOptions);
                break;
        }
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
        return driver;
    }
}
