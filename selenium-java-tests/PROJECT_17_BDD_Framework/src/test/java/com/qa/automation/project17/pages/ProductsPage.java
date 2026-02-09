package com.qa.automation.project17.pages;

import com.qa.automation.project17.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

/**
 * Page Object for Sauce Demo products (inventory) page.
 * Add to cart, sort, menu, logout, cart icon.
 */
public class ProductsPage {

    private static final By INVENTORY_CONTAINER = By.id("inventory_container");
    private static final By MENU_BUTTON = By.id("react-burger-menu-btn");
    private static final By MENU_CLOSE_BUTTON = By.id("react-burger-cross-btn");
    private static final By LOGOUT_LINK = By.id("logout_sidebar_link");
    private static final By CART_LINK = By.cssSelector(".shopping_cart_link");
    private static final By CART_BADGE = By.cssSelector(".shopping_cart_badge");
    private static final By SORT_DROPDOWN = By.cssSelector(".product_sort_container");
    private static final By INVENTORY_ITEMS = By.cssSelector(".inventory_item");
    private static final By ADD_TO_CART_BUTTON = By.cssSelector("button[data-test^='add-to-cart']");
    private static final By REMOVE_BUTTON = By.cssSelector("button[data-test^='remove']");
    private static final By LOGIN_BUTTON = By.id("login-button");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    public boolean isLoaded() {
        try {
            wait.until(ExpectedConditions.urlContains("inventory"));
            wait.until(ExpectedConditions.or(
                    ExpectedConditions.visibilityOfElementLocated(INVENTORY_CONTAINER),
                    ExpectedConditions.visibilityOfElementLocated(MENU_BUTTON)
            ));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public void openMenu() {
        try {
            if (driver.findElements(MENU_CLOSE_BUTTON).stream().anyMatch(WebElement::isDisplayed)) {
                wait.until(ExpectedConditions.elementToBeClickable(MENU_CLOSE_BUTTON)).click();
                wait.until(ExpectedConditions.invisibilityOfElementLocated(LOGOUT_LINK));
            }
        } catch (Exception ignored) {
        }
        wait.until(ExpectedConditions.elementToBeClickable(MENU_BUTTON)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGOUT_LINK));
    }

    public LoginPage logout() {
        openMenu();
        wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_LINK)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_BUTTON));
        return new LoginPage(driver);
    }

    public void clickCartIcon() {
        wait.until(ExpectedConditions.elementToBeClickable(CART_LINK)).click();
    }

    /** Add to cart the first product (index 0). */
    public void addFirstProductToCart() {
        List<WebElement> items = driver.findElements(INVENTORY_ITEMS);
        if (!items.isEmpty()) {
            WebElement first = items.get(0);
            WebElement addBtn = first.findElement(ADD_TO_CART_BUTTON);
            wait.until(ExpectedConditions.elementToBeClickable(addBtn)).click();
        }
    }

    /** Add to cart the product at 0-based index (0 = first, 1 = second). */
    public void addProductToCartByIndex(int index) {
        List<WebElement> items = driver.findElements(INVENTORY_ITEMS);
        if (index >= 0 && index < items.size()) {
            WebElement addBtn = items.get(index).findElement(ADD_TO_CART_BUTTON);
            wait.until(ExpectedConditions.elementToBeClickable(addBtn)).click();
        }
    }

    public int getCartBadgeCount() {
        try {
            WebElement badge = driver.findElement(CART_BADGE);
            return Integer.parseInt(badge.getText().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    public void selectSort(String visibleText) {
        WebElement dropdown = wait.until(ExpectedConditions.visibilityOfElementLocated(SORT_DROPDOWN));
        new Select(dropdown).selectByVisibleText(visibleText);
    }

    /** Visible text options: "Name (A to Z)", "Name (Z to A)", "Price (low to high)", "Price (high to low)". */
    public void sortBy(String option) {
        selectSort(option);
    }

    public int getProductCount() {
        return driver.findElements(INVENTORY_ITEMS).size();
    }

    /** First product name on the page (after sort). */
    public String getFirstProductName() {
        List<WebElement> items = driver.findElements(INVENTORY_ITEMS);
        if (items.isEmpty()) return null;
        try {
            return items.get(0).findElement(By.cssSelector(".inventory_item_name")).getText();
        } catch (Exception e) {
            return null;
        }
    }

    public String getFirstProductPrice() {
        List<WebElement> items = driver.findElements(INVENTORY_ITEMS);
        if (items.isEmpty()) return null;
        try {
            return items.get(0).findElement(By.cssSelector(".inventory_item_price")).getText();
        } catch (Exception e) {
            return null;
        }
    }

    public String getLastProductName() {
        List<WebElement> items = driver.findElements(INVENTORY_ITEMS);
        if (items.isEmpty()) return null;
        try {
            return items.get(items.size() - 1).findElement(By.cssSelector(".inventory_item_name")).getText();
        } catch (Exception e) {
            return null;
        }
    }

    public String getLastProductPrice() {
        List<WebElement> items = driver.findElements(INVENTORY_ITEMS);
        if (items.isEmpty()) return null;
        try {
            return items.get(items.size() - 1).findElement(By.cssSelector(".inventory_item_price")).getText();
        } catch (Exception e) {
            return null;
        }
    }

    public boolean isLoggedIn() {
        return getCurrentUrl().contains("inventory") && isLoaded();
    }
}
