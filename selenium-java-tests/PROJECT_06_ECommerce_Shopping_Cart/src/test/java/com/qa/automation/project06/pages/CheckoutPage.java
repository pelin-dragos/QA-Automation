package com.qa.automation.project06.pages;

import com.qa.automation.project06.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for SauceDemo checkout step one (information form).
 */
public class CheckoutPage {

    private static final By FIRST_NAME = By.id("first-name");
    private static final By LAST_NAME = By.id("last-name");
    private static final By POSTAL_CODE = By.id("postal-code");
    private static final By CONTINUE_BUTTON = By.id("continue");
    private static final By CANCEL_BUTTON = By.id("cancel");
    private static final By ERROR_MESSAGE = By.cssSelector("[data-test='error']");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME));
    }

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void enterFirstName(String firstName) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME));
        field.clear();
        if (firstName != null) field.sendKeys(firstName);
    }

    public void enterLastName(String lastName) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(LAST_NAME));
        field.clear();
        if (lastName != null) field.sendKeys(lastName);
    }

    public void enterPostalCode(String postalCode) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(POSTAL_CODE));
        field.clear();
        if (postalCode != null) field.sendKeys(postalCode);
    }

    public void fillCheckoutForm(String firstName, String lastName, String postalCode) {
        enterFirstName(firstName);
        enterLastName(lastName);
        enterPostalCode(postalCode);
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(CONTINUE_BUTTON)).click();
        try {
            wait.until(webDriver -> webDriver.getCurrentUrl() != null && webDriver.getCurrentUrl().contains("checkout-step-two"));
        } catch (Exception ignored) {
        }
    }

    public void clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(CANCEL_BUTTON)).click();
    }

    public String getErrorMessage() {
        try {
            WebElement error = driver.findElement(ERROR_MESSAGE);
            if (error.isDisplayed()) {
                String text = error.getText();
                return (text != null && !text.isBlank()) ? text.trim() : null;
            }
        } catch (Exception ignored) {
        }
        return null;
    }
}
