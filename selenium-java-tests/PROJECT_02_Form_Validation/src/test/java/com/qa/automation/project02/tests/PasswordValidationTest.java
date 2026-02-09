package com.qa.automation.project02.tests;

import com.qa.automation.project02.base.BaseTest;
import com.qa.automation.project02.pages.TheInternetLoginPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test suite: Password validation (TEST_CASES.md — Section 3).
 * <p>
 * Covers TC-PWD-001 to TC-PWD-004 on The Internet Form Authentication: valid password
 * accepted, too-short password rejected, empty password shows error, and parameterized
 * different password formats. Uses the public test user tomsmith; asserts flash message outcome.
 */
class PasswordValidationTest extends BaseTest {

    /** Valid username for The Internet login (public test account). */
    private static final String VALID_USER = "tomsmith";

    /**
     * TC-PWD-001: Login with valid username and correct password. Asserts no error flash
     * (invalid/incorrect/wrong) is shown—login succeeds.
     */
    @Test
    @DisplayName("TC-PWD-001: Accept valid password (The Internet login)")
    void shouldAcceptValidPassword() {
        TheInternetLoginPage page = new TheInternetLoginPage(driver);
        page.navigateTo();
        page.enterUsername(VALID_USER);
        page.enterPassword("SuperSecretPassword!");
        page.clickLogin();
        assertFalse(page.isFlashError(), "Login with valid password should succeed (no invalid/incorrect error)");
    }

    /**
     * TC-PWD-002: Login with valid username and too-short password (123). Asserts flash
     * message indicates error (invalid/incorrect/wrong).
     */
    @Test
    @DisplayName("TC-PWD-002: Reject too short password")
    void shouldRejectTooShortPassword() {
        TheInternetLoginPage page = new TheInternetLoginPage(driver);
        page.navigateTo();
        page.enterUsername(VALID_USER);
        page.enterPassword("123");
        page.clickLogin();
        assertTrue(page.isFlashError(), "Short password should show invalid/incorrect/wrong error");
    }

    /**
     * TC-PWD-003: Login with valid username and empty password. Asserts flash error is
     * shown and login fails.
     */
    @Test
    @DisplayName("TC-PWD-003: Validate empty password")
    void shouldValidateEmptyPassword() {
        TheInternetLoginPage page = new TheInternetLoginPage(driver);
        page.navigateTo();
        page.enterUsername(VALID_USER);
        page.clickLogin();
        assertTrue(page.isFlashError(), "Empty password should show error; login must fail");
    }

    /**
     * TC-PWD-004: Parameterized test with different password formats. Asserts that a flash
     * message is present after each login attempt (success or error)—no unhandled failure.
     */
    @ParameterizedTest
    @ValueSource(strings = {"SuperSecretPassword!@#$%", "12345678", "Password123", "PASSWORD123!"})
    @DisplayName("TC-PWD-004: Handle different password formats")
    void shouldHandleDifferentFormats(String password) {
        TheInternetLoginPage page = new TheInternetLoginPage(driver);
        page.navigateTo();
        page.enterUsername(VALID_USER);
        page.enterPassword(password);
        page.clickLogin();
        String msg = page.getFlashMessage();
        assertTrue(msg != null && !msg.isEmpty(), "Flash message should be present after login attempt");
    }
}
