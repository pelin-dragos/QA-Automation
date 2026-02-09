package com.qa.automation.project17.pages;

import com.qa.automation.project17.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Page Object for Sauce Demo cart page.
 */
public class CartPage {

    private static final By CART_LIST = By.cssSelector(".cart_list");
    private static final By CART_ITEM = By.cssSelector(".cart_item");
    private static final By REMOVE_BUTTON = By.cssSelector("button[data-test^='remove']");
    private static final By CHECKOUT_BUTTON = By.id("checkout");
    private static final By CONTINUE_SHOPPING_BUTTON = By.id("continue-shopping");
    private static final By ITEM_PRICE = By.cssSelector(".inventory_item_price");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.urlContains("cart"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(CART_LIST));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public int getCartItemCount() {
        return driver.findElements(CART_ITEM).size();
    }

    public void removeFirstItem() {
        List<WebElement> items = driver.findElements(CART_ITEM);
        if (!items.isEmpty()) {
            WebElement remove = items.get(0).findElement(REMOVE_BUTTON);
            wait.until(ExpectedConditions.elementToBeClickable(remove)).click();
        }
    }

    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(CHECKOUT_BUTTON)).click();
    }

    public ProductsPage clickContinueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(CONTINUE_SHOPPING_BUTTON)).click();
        wait.until(ExpectedConditions.urlContains("inventory"));
        return new ProductsPage(driver);
    }

    /** Sum of item prices in cart (e.g. 29.99 + 9.99). Parses $X.XX format. */
    public double getSubtotalFromItems() {
        List<WebElement> prices = driver.findElements(ITEM_PRICE);
        Pattern p = Pattern.compile("\\$?([\\d.]+)");
        double sum = 0;
        for (WebElement el : prices) {
            String text = el.getText();
            Matcher m = p.matcher(text);
            if (m.find()) {
                sum += Double.parseDouble(m.group(1));
            }
        }
        return sum;
    }

    /** True if checkout button is visible and enabled. */
    public boolean isCheckoutEnabled() {
        try {
            WebElement btn = driver.findElement(CHECKOUT_BUTTON);
            return btn.isDisplayed() && btn.isEnabled();
        } catch (Exception e) {
            return false;
        }
    }
}
