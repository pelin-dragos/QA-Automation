package com.qa.automation.project01.tests;

import com.qa.automation.project01.base.BaseTest;
import com.qa.automation.project01.pages.LoginPage;
import com.qa.automation.project01.pages.ProductsPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test suite: Login success scenarios (TEST_CASES.md — Section 1).
 * <p>
 * Covers TC-LOGIN-001 to TC-LOGIN-004: valid credentials for standard_user, problem_user,
 * and performance_glitch_user, plus verification that menu and cart are visible after login.
 * Password is the standard Sauce Demo value; no production or hardcoded secrets.
 */
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class LoginSuccessTest extends BaseTest {

    /** Standard password for all valid Sauce Demo users (public demo data). */
    private static final String VALID_PASSWORD = "secret_sauce";

    /**
     * TC-LOGIN-001: Verifies that logging in with valid standard_user credentials
     * leads to the products page, correct URL, and page title "Products".
     */
    @Test
    @DisplayName("TC-LOGIN-001: Login with valid credentials (standard_user)")
    void shouldLoginWithStandardUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        assertTrue(loginPage.isLoaded(), "Login page should be loaded before submitting credentials");

        loginPage.login("standard_user", VALID_PASSWORD);

        ProductsPage productsPage = new ProductsPage(driver);
        assertTrue(productsPage.isLoaded(), "Products page should load after valid login");
        assertTrue(productsPage.getCurrentUrl().contains("inventory"),
                "Expected URL to contain 'inventory' after successful login");

        String pageTitle = productsPage.getPageTitle();
        assertNotNull(pageTitle, "Page title should be present");
        assertTrue("Products".equalsIgnoreCase(pageTitle.trim()),
                "Expected page title 'Products'; got: " + pageTitle);
    }

    /**
     * TC-LOGIN-002: Verifies that problem_user (valid credentials) can log in and
     * reaches the inventory page. Same expected outcome as standard_user for URL and load.
     */
    @Test
    @DisplayName("TC-LOGIN-002: Login with valid credentials (problem_user)")
    void shouldLoginWithProblemUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        assertTrue(loginPage.isLoaded(), "Login page should be loaded");

        loginPage.login("problem_user", VALID_PASSWORD);

        ProductsPage productsPage = new ProductsPage(driver);
        assertTrue(productsPage.isLoaded(), "Products page should load for problem_user");
        assertTrue(productsPage.getCurrentUrl().contains("inventory"),
                "Expected URL to contain 'inventory' after successful login");
    }

    /**
     * TC-LOGIN-003: Verifies that performance_glitch_user (valid credentials) can log in
     * and reaches the inventory page. May have slower response; explicit waits handle it.
     */
    @Test
    @DisplayName("TC-LOGIN-003: Login with valid credentials (performance_glitch_user)")
    void shouldLoginWithPerformanceGlitchUser() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        assertTrue(loginPage.isLoaded(), "Login page should be loaded");

        loginPage.login("performance_glitch_user", VALID_PASSWORD);

        ProductsPage productsPage = new ProductsPage(driver);
        assertTrue(productsPage.isLoaded(), "Products page should load for performance_glitch_user");
        assertTrue(productsPage.getCurrentUrl().contains("inventory"),
                "Expected URL to contain 'inventory' after successful login");
    }

    /**
     * TC-LOGIN-004: After successful login as standard_user, verifies that the menu button
     * and shopping cart icon are visible. Ensures key UI elements are present for further actions.
     */
    @Test
    @DisplayName("TC-LOGIN-004: Verify UI elements after successful login")
    void shouldShowMenuAndCartAfterLogin() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        loginPage.login("standard_user", VALID_PASSWORD);

        ProductsPage productsPage = new ProductsPage(driver);
        assertTrue(productsPage.isLoaded(), "Products page should be loaded");

        assertTrue(productsPage.getMenuButton().isDisplayed(),
                "Menu button should be visible after successful login");
        assertTrue(productsPage.getShoppingCartLink().isDisplayed(),
                "Shopping cart icon should be visible after successful login");
    }
}
