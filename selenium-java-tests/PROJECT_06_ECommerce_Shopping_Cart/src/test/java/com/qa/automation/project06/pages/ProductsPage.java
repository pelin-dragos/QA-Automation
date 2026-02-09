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
 * Page Object for SauceDemo products/inventory page.
 */
public class ProductsPage {

    private static final By INVENTORY_CONTAINER = By.id("inventory_container");
    private static final By PRODUCT_ITEMS = By.cssSelector(".inventory_item");
    private static final By SHOPPING_CART_BADGE = By.cssSelector(".shopping_cart_badge");
    private static final By SHOPPING_CART_LINK = By.cssSelector(".shopping_cart_link");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    public void waitForPageLoad() {
        wait.until(webDriver -> webDriver.getCurrentUrl() != null && webDriver.getCurrentUrl().contains("inventory"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(PRODUCT_ITEMS));
    }

    public boolean isLoaded() {
        try {
            if (driver.getCurrentUrl() == null || !driver.getCurrentUrl().contains("inventory")) {
                return false;
            }
            List<WebElement> items = driver.findElements(PRODUCT_ITEMS);
            if (!items.isEmpty()) {
                wait.until(ExpectedConditions.visibilityOf(items.get(0)));
                return true;
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(INVENTORY_CONTAINER));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getProductsCount() {
        return driver.findElements(PRODUCT_ITEMS).size();
    }

    public ProductInfo getProductInfo(int index) {
        List<WebElement> products = driver.findElements(PRODUCT_ITEMS);
        if (index < 0 || index >= products.size()) return null;
        WebElement product = products.get(index);
        String name = product.findElement(By.cssSelector(".inventory_item_name")).getText();
        String priceText = product.findElement(By.cssSelector(".inventory_item_price")).getText();
        if (name == null || priceText == null) return null;
        double price = Double.parseDouble(priceText.replace("$", "").trim());
        return new ProductInfo(name.trim(), price);
    }

    public String addProductToCart(int index) {
        List<WebElement> products = driver.findElements(PRODUCT_ITEMS);
        if (index < 0 || index >= products.size()) return null;
        WebElement product = products.get(index);
        String name = product.findElement(By.cssSelector(".inventory_item_name")).getText();
        WebElement addButton = product.findElement(By.cssSelector("button.btn_inventory"));
        String buttonText = addButton.getText();
        if (buttonText != null && buttonText.trim().toLowerCase().contains("remove")) {
            return name != null ? name.trim() : null;
        }
        addButton.click();
        wait.until(webDriver -> {
            WebElement btn = webDriver.findElements(PRODUCT_ITEMS).get(index).findElement(By.cssSelector("button.btn_inventory"));
            return btn.getText() != null && btn.getText().toLowerCase().contains("remove");
        });
        return name != null ? name.trim() : null;
    }

    public List<String> addMultipleProductsToCart(int[] indices) {
        List<String> added = new ArrayList<>();
        for (int index : indices) {
            String name = addProductToCart(index);
            if (name != null) added.add(name);
        }
        return added;
    }

    public int getCartItemsCount() {
        try {
            WebElement badge = driver.findElement(SHOPPING_CART_BADGE);
            if (badge.isDisplayed()) {
                String text = badge.getText();
                return text != null && !text.isEmpty() ? Integer.parseInt(text.trim(), 10) : 0;
            }
        } catch (Exception ignored) {
        }
        return 0;
    }

    public void clickCart() {
        wait.until(ExpectedConditions.elementToBeClickable(SHOPPING_CART_LINK)).click();
        wait.until(webDriver -> webDriver.getCurrentUrl() != null && webDriver.getCurrentUrl().contains("cart"));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public static final class ProductInfo {
        public final String name;
        public final double price;

        public ProductInfo(String name, double price) {
            this.name = name;
            this.price = price;
        }
    }
}
