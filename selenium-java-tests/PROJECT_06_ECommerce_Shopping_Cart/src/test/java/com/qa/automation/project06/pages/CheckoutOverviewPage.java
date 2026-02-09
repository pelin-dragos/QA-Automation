package com.qa.automation.project06.pages;

import com.qa.automation.project06.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Page Object for SauceDemo checkout step two (overview).
 */
public class CheckoutOverviewPage {

    private static final By FINISH_BUTTON = By.id("finish");
    private static final By CANCEL_BUTTON = By.id("cancel");
    private static final By SUMMARY_SUBTOTAL = By.cssSelector(".summary_subtotal_label");
    private static final By SUMMARY_TAX = By.cssSelector(".summary_tax_label");
    private static final By SUMMARY_TOTAL = By.cssSelector(".summary_total_label");
    private static final Pattern DOLLAR_PATTERN = Pattern.compile("\\$([\\d.]+)");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public CheckoutOverviewPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    public void waitForPageLoad() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(FINISH_BUTTON));
    }

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(FINISH_BUTTON));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public double getSubtotal() {
        return parseDollarFromElement(SUMMARY_SUBTOTAL);
    }

    public double getTax() {
        return parseDollarFromElement(SUMMARY_TAX);
    }

    public double getTotal() {
        return parseDollarFromElement(SUMMARY_TOTAL);
    }

    public double calculateExpectedTotal() {
        return getSubtotal() + getTax();
    }

    public void clickFinish() {
        wait.until(ExpectedConditions.elementToBeClickable(FINISH_BUTTON)).click();
        wait.until(webDriver -> webDriver.getCurrentUrl() != null && webDriver.getCurrentUrl().contains("checkout-complete"));
    }

    public void clickCancel() {
        wait.until(ExpectedConditions.elementToBeClickable(CANCEL_BUTTON)).click();
    }

    private double parseDollarFromElement(By locator) {
        try {
            WebElement el = driver.findElement(locator);
            String text = el.getText();
            if (text != null) {
                Matcher m = DOLLAR_PATTERN.matcher(text);
                if (m.find()) return Double.parseDouble(m.group(1));
            }
        } catch (Exception ignored) {
        }
        return 0.0;
    }
}
