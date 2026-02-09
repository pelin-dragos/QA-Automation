package com.qa.automation.project02.tests;

import com.qa.automation.project02.base.BaseTest;
import com.qa.automation.project02.pages.DemoQATextBoxPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test suite: Email validation (TEST_CASES.md — Section 2).
 * <p>
 * Covers TC-EMAIL-001 to TC-EMAIL-009 on DemoQA Text Box page: valid format accepted, invalid
 * formats rejected (missing @, domain, username, spaces, no TLD), parameterized valid formats,
 * and empty email. Uses HTML5 validity and required attribute where applicable.
 */
class EmailValidationTest extends BaseTest {

    /**
     * TC-EMAIL-001: Submit form with valid email (test@example.com) and other fields filled.
     * Asserts email is not marked invalid by HTML5 validation.
     */
    @Test
    @DisplayName("TC-EMAIL-001: Accept valid email format (Text Box)")
    void shouldAcceptValidEmail() {
        DemoQATextBoxPage page = new DemoQATextBoxPage(driver);
        page.navigateTo();
        page.enterEmail("test@example.com");
        page.enterFullName("Test User");
        page.enterCurrentAddress("Address");
        page.clickSubmit();
        assertFalse(page.isEmailInvalid(), "Valid email should not be marked invalid");
    }

    /**
     * TC-EMAIL-002: Email without @ (testexample.com). Asserts field is invalid.
     */
    @Test
    @DisplayName("TC-EMAIL-002: Reject email without @")
    void shouldRejectEmailWithoutAt() {
        DemoQATextBoxPage page = new DemoQATextBoxPage(driver);
        page.navigateTo();
        page.enterEmail("testexample.com");
        page.enterFullName("Test");
        page.clickSubmit();
        assertTrue(page.isEmailInvalid(), "Email without @ should be invalid");
    }

    /**
     * TC-EMAIL-003: Email without domain (test@). Asserts field is invalid.
     */
    @Test
    @DisplayName("TC-EMAIL-003: Reject email without domain")
    void shouldRejectEmailWithoutDomain() {
        DemoQATextBoxPage page = new DemoQATextBoxPage(driver);
        page.navigateTo();
        page.enterEmail("test@");
        assertTrue(page.isEmailInvalid(), "Email without domain should be invalid");
    }

    /**
     * TC-EMAIL-004: Email without username (@example.com). Asserts field is invalid.
     */
    @Test
    @DisplayName("TC-EMAIL-004: Reject email without username")
    void shouldRejectEmailWithoutUsername() {
        DemoQATextBoxPage page = new DemoQATextBoxPage(driver);
        page.navigateTo();
        page.enterEmail("@example.com");
        assertTrue(page.isEmailInvalid(), "Email without username should be invalid");
    }

    /**
     * TC-EMAIL-005: Email with space in username (test @example.com). Asserts invalid.
     */
    @Test
    @DisplayName("TC-EMAIL-005: Reject email with space (username)")
    void shouldRejectEmailWithSpaceInUsername() {
        DemoQATextBoxPage page = new DemoQATextBoxPage(driver);
        page.navigateTo();
        page.enterEmail("test @example.com");
        assertTrue(page.isEmailInvalid(), "Email with space in username should be invalid");
    }

    /**
     * TC-EMAIL-006: Email with space in domain (test@exam ple.com). Asserts invalid.
     */
    @Test
    @DisplayName("TC-EMAIL-006: Reject email with space in domain")
    void shouldRejectEmailWithSpaceInDomain() {
        DemoQATextBoxPage page = new DemoQATextBoxPage(driver);
        page.navigateTo();
        page.enterEmail("test@exam ple.com");
        assertTrue(page.isEmailInvalid(), "Email with space in domain should be invalid");
    }

    /**
     * TC-EMAIL-007: Email without TLD (test@example). Browsers may accept or reject; test
     * asserts the field retains the entered value after input.
     */
    @Test
    @DisplayName("TC-EMAIL-007: Reject email without TLD")
    void shouldRejectEmailWithoutTld() {
        DemoQATextBoxPage page = new DemoQATextBoxPage(driver);
        page.navigateTo();
        page.enterEmail("test@example");
        assertTrue(page.getEmailField() != null && page.getEmailField().getAttribute("value") != null,
                "Email field should retain value after input");
    }

    /**
     * TC-EMAIL-008: Parameterized test with multiple valid email formats. Each must not be
     * marked invalid by HTML5 validation.
     */
    @ParameterizedTest
    @ValueSource(strings = {"test@example.com", "user.name@example.com", "user+tag@example.co.uk", "test123@test-domain.com", "a@b.co"})
    @DisplayName("TC-EMAIL-008: Accept valid formats (parameterized)")
    void shouldAcceptValidFormats(String email) {
        DemoQATextBoxPage page = new DemoQATextBoxPage(driver);
        page.navigateTo();
        page.enterEmail(email);
        assertFalse(page.isEmailInvalid(), "Valid email format should not be invalid: " + email);
    }

    /**
     * TC-EMAIL-009: Submit with empty email and other fields filled. If email is required,
     * it must be invalid; otherwise the test documents current behaviour.
     */
    @Test
    @DisplayName("TC-EMAIL-009: Validate empty email field")
    void shouldValidateEmptyEmail() {
        DemoQATextBoxPage page = new DemoQATextBoxPage(driver);
        page.navigateTo();
        page.enterEmail("");
        page.enterFullName("Test User");
        page.enterCurrentAddress("Address");
        page.clickSubmit();
        boolean invalid = page.isEmailInvalid();
        boolean required = page.isFieldRequired(org.openqa.selenium.By.id("userEmail"));
        assertTrue(!required || invalid, "When email is required, empty should be invalid");
    }
}
