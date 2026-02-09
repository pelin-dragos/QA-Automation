package com.qa.automation.project06.pages;

import com.qa.automation.project06.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Page Object for SauceDemo shopping cart page.
 */
public class CartPage {

    private static final By CART_ITEMS = By.cssSelector(".cart_item");
    private static final By CONTINUE_SHOPPING = By.id("continue-shopping");
    private static final By CHECKOUT_BUTTON = By.id("checkout");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    public void waitForPageLoad() {
        wait.until(webDriver -> webDriver.getCurrentUrl() != null && webDriver.getCurrentUrl().contains("cart"));
        wait.until(ExpectedConditions.or(
                ExpectedConditions.visibilityOfElementLocated(CHECKOUT_BUTTON),
                ExpectedConditions.visibilityOfElementLocated(CONTINUE_SHOPPING)
        ));
    }

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(CHECKOUT_BUTTON),
                    ExpectedConditions.visibilityOfElementLocated(CONTINUE_SHOPPING)
            ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getCartItemsCount() {
        return driver.findElements(CART_ITEMS).size();
    }

    public List<String> getAllCartItemsNames() {
        List<WebElement> items = driver.findElements(CART_ITEMS);
        List<String> names = new ArrayList<>();
        for (WebElement item : items) {
            String name = item.findElement(By.cssSelector(".inventory_item_name")).getText();
            if (name != null) names.add(name.trim());
        }
        return names;
    }

    public List<Double> getAllCartItemsPrices() {
        List<WebElement> items = driver.findElements(CART_ITEMS);
        List<Double> prices = new ArrayList<>();
        for (WebElement item : items) {
            String priceText = item.findElement(By.cssSelector(".inventory_item_price")).getText();
            if (priceText != null) {
                prices.add(Double.parseDouble(priceText.replace("$", "").trim()));
            }
        }
        return prices;
    }

    public double calculateTotalPrice() {
        return getAllCartItemsPrices().stream().mapToDouble(Double::doubleValue).sum();
    }

    public String removeItemFromCart(int index) {
        List<WebElement> items = driver.findElements(CART_ITEMS);
        if (index < 0 || index >= items.size()) return null;
        WebElement item = items.get(index);
        String name = item.findElement(By.cssSelector(".inventory_item_name")).getText();
        WebElement removeButton = item.findElement(By.cssSelector("button.cart_button"));
        removeButton.click();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return name != null ? name.trim() : null;
    }

    public void clickContinueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(CONTINUE_SHOPPING)).click();
        wait.until(webDriver -> webDriver.getCurrentUrl() != null && webDriver.getCurrentUrl().contains("inventory"));
    }

    public void clickCheckout() {
        wait.until(ExpectedConditions.elementToBeClickable(CHECKOUT_BUTTON)).click();
        wait.until(webDriver -> webDriver.getCurrentUrl() != null && webDriver.getCurrentUrl().contains("checkout-step-one"));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
