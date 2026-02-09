package com.qa.automation.project01.tests;

import com.qa.automation.project01.base.BaseTest;
import com.qa.automation.project01.pages.LoginPage;
import com.qa.automation.project01.pages.ProductsPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test suite: Login failure and validation scenarios (TEST_CASES.md — Section 2).
 * <p>
 * Covers TC-LOGIN-005 to TC-LOGIN-011: invalid username, invalid password, empty credentials,
 * wrong credentials, and email-as-username. Every test expects either an error message or
 * HTML5 validation and asserts that the user is not redirected to the inventory page.
 */
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
class LoginFailureTest extends BaseTest {

    /**
     * TC-LOGIN-005: Invalid username with valid password. Expects an error message to be
     * displayed and the user to remain on the login page (URL must not contain "inventory").
     */
    @Test
    @DisplayName("TC-LOGIN-005: Error message with invalid username")
    void shouldShowErrorWithInvalidUsername() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        assertTrue(loginPage.isLoaded(), "Login page should be loaded");

        loginPage.login("invalid_user", "secret_sauce");

        String errorMessage = loginPage.getErrorMessage();
        assertNotNull(errorMessage, "Expected an error message when username is invalid");
        assertTrue(errorMessage.length() > 0, "Error message should not be empty");
        assertFalse(driver.getCurrentUrl().contains("inventory"),
                "User must not be redirected to inventory when login fails");
    }

    /**
     * TC-LOGIN-006: Valid username with wrong password. Expects an error message that
     * mentions password, credentials, or match, and no redirect to inventory.
     */
    @Test
    @DisplayName("TC-LOGIN-006: Error message with invalid password")
    void shouldShowErrorWithInvalidPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        assertTrue(loginPage.isLoaded(), "Login page should be loaded");

        loginPage.login("standard_user", "wrong_password");

        String errorMessage = loginPage.getErrorMessage();
        assertNotNull(errorMessage, "Expected an error message when password is wrong");
        String lower = errorMessage.toLowerCase();
        assertTrue(lower.contains("password") || lower.contains("credentials") || lower.contains("match"),
                "Error message should mention password/credentials/match; got: " + errorMessage);
        assertFalse(driver.getCurrentUrl().contains("inventory"),
                "User must not be redirected to inventory when login fails");
    }

    /**
     * TC-LOGIN-007: Submit with both username and password empty. Expects either HTML5
     * required validation (required attribute on fields) or an error message; user must
     * not reach the inventory page.
     */
    @Test
    @DisplayName("TC-LOGIN-007: Validation with empty credentials")
    void shouldShowValidationWithEmptyCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        assertTrue(loginPage.isLoaded(), "Login page should be loaded");

        loginPage.clickLogin();

        boolean hasValidation = loginPage.getUsernameField().getAttribute("required") != null
                || loginPage.getPasswordField().getAttribute("required") != null
                || loginPage.getErrorMessage() != null;
        assertTrue(hasValidation, "Expected HTML5 required validation or error message when credentials are empty");
        assertFalse(driver.getCurrentUrl().contains("inventory"),
                "User must not reach inventory without entering credentials");
    }

    /**
     * TC-LOGIN-008: Both username and password are wrong. Login must fail; user must not
     * be on the inventory page. If an error element is present, it must have non-empty text.
     */
    @Test
    @DisplayName("TC-LOGIN-008: Fail with both wrong credentials")
    void shouldFailWithBothWrongCredentials() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        loginPage.enterUsername("wrong_user");
        loginPage.enterPassword("wrong_pass");
        loginPage.clickLogin();

        assertFalse(driver.getCurrentUrl().contains("inventory"),
                "Login must fail and user must not be on inventory page");
        String error = loginPage.getErrorMessage();
        if (error != null) {
            assertTrue(error.length() > 0, "If error element is present, it should have text");
        }
    }

    /**
     * TC-LOGIN-009: Password filled, username left empty. Login must fail (no redirect to
     * inventory) and either an error message or HTML5 required validation must be present.
     */
    @Test
    @DisplayName("TC-LOGIN-009: Fail with empty username only")
    void shouldFailWithEmptyUsername() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        assertFalse(driver.getCurrentUrl().contains("inventory"),
                "Login must fail when username is empty");
        boolean hasErrorOrValidation = loginPage.getErrorMessage() != null
                || loginPage.getUsernameField().getAttribute("required") != null;
        assertTrue(hasErrorOrValidation, "Expected error message or required validation");
    }

    /**
     * TC-LOGIN-010: Username filled, password left empty. Login must fail and either an
     * error message or required validation on the password field must be present.
     */
    @Test
    @DisplayName("TC-LOGIN-010: Fail with empty password only")
    void shouldFailWithEmptyPassword() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        loginPage.enterUsername("standard_user");
        loginPage.clickLogin();

        assertFalse(driver.getCurrentUrl().contains("inventory"),
                "Login must fail when password is empty");
        boolean hasErrorOrValidation = loginPage.getErrorMessage() != null
                || loginPage.getPasswordField().getAttribute("required") != null;
        assertTrue(hasErrorOrValidation, "Expected error message or required validation");
    }

    /**
     * TC-LOGIN-011: Email format used as username (test@test.com). Login must fail and
     * the products page must not be loaded—user has no access to inventory.
     */
    @Test
    @DisplayName("TC-LOGIN-011: Fail with email as username (invalid format)")
    void shouldFailWithEmailAsUsername() {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.navigateTo();
        loginPage.login("test@test.com", "password123");

        assertFalse(driver.getCurrentUrl().contains("inventory"),
                "Login must fail when using email as username; no access to products");
        ProductsPage productsPage = new ProductsPage(driver);
        assertFalse(productsPage.isLoaded(), "Products page must not be loaded");
    }
}
