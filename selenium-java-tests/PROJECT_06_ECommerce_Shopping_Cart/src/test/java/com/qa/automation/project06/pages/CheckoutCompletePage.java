package com.qa.automation.project06.pages;

import com.qa.automation.project06.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for SauceDemo checkout complete page.
 */
public class CheckoutCompletePage {

    private static final By COMPLETE_HEADER = By.cssSelector(".complete-header");
    private static final By COMPLETE_TEXT = By.cssSelector(".complete-text");
    private static final By BACK_HOME_BUTTON = By.id("back-to-products");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public CheckoutCompletePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(COMPLETE_HEADER));
    }

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(COMPLETE_HEADER));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getSuccessMessage() {
        try {
            String text = driver.findElement(COMPLETE_HEADER).getText();
            return text != null ? text.trim() : "";
        } catch (Exception e) {
            return "";
        }
    }

    public void clickBackHome() {
        wait.until(ExpectedConditions.elementToBeClickable(BACK_HOME_BUTTON)).click();
        wait.until(webDriver -> webDriver.getCurrentUrl() != null && webDriver.getCurrentUrl().contains("inventory"));
    }
}
