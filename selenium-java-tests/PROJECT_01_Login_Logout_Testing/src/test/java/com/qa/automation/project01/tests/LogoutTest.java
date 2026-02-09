package com.qa.automation.project01.tests;

import com.qa.automation.project01.base.BaseTest;
import com.qa.automation.project01.config.TestConfig;
import com.qa.automation.project01.pages.LoginPage;
import com.qa.automation.project01.pages.ProductsPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test suite: Logout scenarios (TEST_CASES.md — Section 3).
 * <p>
 * Covers TC-LOGOUT-001 to TC-LOGOUT-003: logout from the products page and redirect to login,
 * session cleared so direct navigation to inventory is blocked, and ability to log in again
 * after logout.
 */
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class LogoutTest extends BaseTest {

    private static final String VALID_USER = "standard_user";
    private static final String VALID_PASSWORD = "secret_sauce";

    /**
     * TC-LOGOUT-001: Full logout flow. Log in, then logout from the products page. Assert that
     * we are redirected to the login page (URL contains saucedemo.com and not inventory), and that
     * username, password, and login button are visible so the user can log in again.
     */
    @Test
    @DisplayName("TC-LOGOUT-001: Logout after successful login")
    void shouldLogoutAndRedirectToLoginPage() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        loginPage.login(VALID_USER, VALID_PASSWORD);

        ProductsPage productsPage = new ProductsPage(driver);
        assertTrue(productsPage.isLoaded(), "Products page should be loaded before logout");
        assertTrue(productsPage.isLoggedIn(), "User should be logged in before logout");

        LoginPage afterLogout = productsPage.logout();

        assertTrue(afterLogout.isLoaded(), "After logout, login page should be loaded");
        String currentUrl = driver.getCurrentUrl();
        assertTrue(currentUrl.contains("saucedemo.com"), "URL should contain saucedemo.com");
        assertTrue(!currentUrl.contains("inventory"), "URL must not contain inventory after logout");
        assertTrue(afterLogout.getUsernameField().isDisplayed(), "Username field should be visible");
        assertTrue(afterLogout.getPasswordField().isDisplayed(), "Password field should be visible");
        assertTrue(afterLogout.getLoginButton().isDisplayed(), "Login button should be visible");
    }

    /**
     * TC-LOGOUT-002: Verifies that after logout, the session is cleared. We log in, logout,
     * then navigate directly to inventory.html. The application should either redirect to login
     * or show the login page so that protected content is not accessible without re-authentication.
     */
    @Test
    @DisplayName("TC-LOGOUT-002: Session cleared after logout (direct access blocked)")
    void shouldBlockDirectAccessToInventoryAfterLogout() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        loginPage.login(VALID_USER, VALID_PASSWORD);

        ProductsPage productsPage = new ProductsPage(driver);
        productsPage.logout();

        String baseUrl = TestConfig.getBaseUrl();
        driver.get(baseUrl + "inventory.html");

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.getTimeoutSeconds()));
        wait.until(webDriver -> {
            String url = webDriver.getCurrentUrl();
            return !url.contains("inventory.html") || new LoginPage(webDriver).isLoaded();
        });

        String currentUrl = driver.getCurrentUrl();
        boolean redirectedOrLoginVisible = !currentUrl.contains("inventory.html") || new LoginPage(driver).isLoaded();
        assertTrue(redirectedOrLoginVisible, "After logout, direct navigation to inventory should redirect to login or show login page");
        assertTrue(new LoginPage(driver).isLoaded(), "Login page should be visible (session cleared)");
    }

    /**
     * TC-LOGOUT-003: Verifies that after logging out, the user can log in again with the same
     * credentials. Second login must succeed and the products page must load (relogin flow works).
     */
    @Test
    @DisplayName("TC-LOGOUT-003: Relogin after logout")
    void shouldAllowReloginAfterLogout() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        loginPage.login(VALID_USER, VALID_PASSWORD);

        ProductsPage productsPage = new ProductsPage(driver);
        LoginPage afterLogout = productsPage.logout();

        afterLogout.login(VALID_USER, VALID_PASSWORD);
        ProductsPage productsPageAgain = new ProductsPage(driver);
        assertTrue(productsPageAgain.isLoaded(), "Second login should succeed and products page should load");
        assertTrue(productsPageAgain.isLoggedIn(), "User should be logged in after relogin");
    }
}
