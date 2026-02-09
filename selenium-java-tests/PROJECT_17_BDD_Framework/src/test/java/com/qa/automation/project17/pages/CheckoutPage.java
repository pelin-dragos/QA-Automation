package com.qa.automation.project17.pages;

import com.qa.automation.project17.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Page Object for Sauce Demo checkout (info + overview + complete).
 */
public class CheckoutPage {

    private static final By FIRST_NAME = By.id("first-name");
    private static final By LAST_NAME = By.id("last-name");
    private static final By POSTAL_CODE = By.id("postal-code");
    private static final By CONTINUE_BUTTON = By.id("continue");
    private static final By ERROR_MESSAGE = By.cssSelector("h3[data-test='error']");
    private static final By FINISH_BUTTON = By.id("finish");
    private static final By COMPLETE_HEADER = By.cssSelector(".complete-header");
    private static final By SUBTOTAL = By.cssSelector(".summary_subtotal_label");
    private static final By TAX = By.cssSelector(".summary_tax_label");
    private static final By TOTAL = By.cssSelector(".summary_total_label");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    public void fillInfo(String firstName, String lastName, String postalCode) {
        if (firstName != null && !firstName.isEmpty()) {
            WebElement fn = wait.until(ExpectedConditions.visibilityOfElementLocated(FIRST_NAME));
            fn.clear();
            fn.sendKeys(firstName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            WebElement ln = driver.findElement(LAST_NAME);
            ln.clear();
            ln.sendKeys(lastName);
        }
        if (postalCode != null && !postalCode.isEmpty()) {
            WebElement pc = driver.findElement(POSTAL_CODE);
            pc.clear();
            pc.sendKeys(postalCode);
        }
    }

    public void clickContinue() {
        wait.until(ExpectedConditions.elementToBeClickable(CONTINUE_BUTTON)).click();
    }

    public String getCheckoutError() {
        try {
            WebElement err = driver.findElement(ERROR_MESSAGE);
            if (err.isDisplayed()) return err.getText().trim();
        } catch (Exception ignored) {
        }
        return null;
    }

    public void clickFinish() {
        wait.until(ExpectedConditions.elementToBeClickable(FINISH_BUTTON)).click();
    }

    public String getCompleteMessage() {
        try {
            WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(COMPLETE_HEADER));
            return el.getText();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isThankYouVisible() {
        String msg = getCompleteMessage();
        return msg != null && msg.toLowerCase().contains("thank you");
    }

    /** Parse total from summary (e.g. "Total: $58.29"). */
    public Double getSummaryTotal() {
        try {
            String text = driver.findElement(TOTAL).getText();
            Matcher m = Pattern.compile("\\$?([\\d.]+)").matcher(text);
            return m.find() ? Double.parseDouble(m.group(1)) : null;
        } catch (Exception e) {
            return null;
        }
    }

    public Double getSummarySubtotal() {
        try {
            String text = driver.findElement(SUBTOTAL).getText();
            Matcher m = Pattern.compile("\\$?([\\d.]+)").matcher(text);
            return m.find() ? Double.parseDouble(m.group(1)) : null;
        } catch (Exception e) {
            return null;
        }
    }
}
