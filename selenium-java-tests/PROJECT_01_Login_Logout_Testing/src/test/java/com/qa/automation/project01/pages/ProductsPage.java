package com.qa.automation.project01.pages;

import com.qa.automation.project01.config.TestConfig;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * Page Object for the Sauce Demo products (inventory) page shown after successful login.
 * <p>
 * Provides methods to verify that the products page is loaded, to read the page title and URL,
 * to open the hamburger menu and perform logout, and to check visibility of menu and cart.
 * All visibility and clickability checks use explicit waits (no fixed sleeps).
 */
public class ProductsPage {

    // ——— Locators (Sauce Demo inventory page) ———
    private static final By INVENTORY_CONTAINER = By.id("inventory_container");
    private static final By MENU_BUTTON = By.id("react-burger-menu-btn");
    private static final By LOGOUT_LINK = By.id("logout_sidebar_link");
    private static final By SHOPPING_CART_LINK = By.cssSelector(".shopping_cart_link");
    private static final By PAGE_TITLE = By.cssSelector(".title");
    /** Login button on the login page; used to confirm we are back on login after logout. */
    private static final By LOGIN_BUTTON = By.id("login-button");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
    }

    /**
     * Returns true when the products page is loaded: URL contains "inventory" and either
     * the inventory container or the menu button is visible. Used by tests to assert
     * successful login before checking title, menu, or performing logout.
     */
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

    /**
     * Returns the main heading text (e.g. "Products") or null if the element is not found.
     * Used to assert the page title after login (TC-LOGIN-001).
     */
    public String getPageTitle() {
        try {
            WebElement title = driver.findElement(PAGE_TITLE);
            return title != null ? title.getText() : null;
        } catch (Exception e) {
            return null;
        }
    }

    /** Returns the current browser URL so tests can assert redirect (e.g. URL contains "inventory"). */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    /**
     * Opens the hamburger menu (top-left) and waits until the sidebar with the Logout link is visible.
     * Must be called before clicking the Logout link.
     */
    public void openMenu() {
        wait.until(ExpectedConditions.elementToBeClickable(MENU_BUTTON)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGOUT_LINK));
    }

    /**
     * Performs logout: opens the menu, clicks Logout, and waits until the login page is visible
     * (login button present). Waiting for the login button is more reliable than waiting for URL
     * change on some environments.
     *
     * @return a LoginPage instance for the same driver, so tests can continue assertions on the login page
     */
    public LoginPage logout() {
        openMenu();
        wait.until(ExpectedConditions.elementToBeClickable(LOGOUT_LINK)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGIN_BUTTON));
        return new LoginPage(driver);
    }

    /**
     * Returns true if the user is considered logged in: URL contains "inventory" and the page
     * is loaded (inventory or menu visible). Used to assert state before logout (TC-LOGOUT-001).
     */
    public boolean isLoggedIn() {
        return getCurrentUrl().contains("inventory") && isLoaded();
    }

    /** Returns the hamburger menu button element (e.g. for TC-LOGIN-004: assert menu is visible). */
    public WebElement getMenuButton() {
        return driver.findElement(MENU_BUTTON);
    }

    /** Returns the shopping cart link (e.g. for TC-LOGIN-004: assert cart icon is visible). */
    public WebElement getShoppingCartLink() {
        return driver.findElement(SHOPPING_CART_LINK);
    }
}
