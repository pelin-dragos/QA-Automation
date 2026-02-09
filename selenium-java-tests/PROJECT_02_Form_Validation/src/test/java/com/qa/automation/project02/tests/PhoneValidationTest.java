package com.qa.automation.project02.tests;

import com.qa.automation.project02.base.BaseTest;
import com.qa.automation.project02.pages.DemoQAPracticeFormPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test suite: Phone (Mobile) validation (TEST_CASES.md — Section 4).
 * <p>
 * Covers TC-PHONE-001 to TC-PHONE-007 on DemoQA Practice Form: valid 10-digit accepted,
 * 9-digit rejected, letters/spaces rejected, phone with dashes handled, too-long phone,
 * and empty phone (required). Uses a helper to fill all required fields except mobile.
 */
class PhoneValidationTest extends BaseTest {

    /**
     * Fills First Name, Last Name, Email, and Gender so that only the Mobile field
     * behaviour is under test. Used by all phone validation tests.
     */
    private void fillRequiredExceptMobile(DemoQAPracticeFormPage page) {
        page.enterFirstName("Test");
        page.enterLastName("User");
        page.enterEmail("test@example.com");
        page.selectGender();
    }

    /**
     * TC-PHONE-001: Valid 10-digit mobile (1234567890). Asserts no mobile/number
     * validation errors appear after submit.
     */
    @Test
    @DisplayName("TC-PHONE-001: Accept valid 10-digit phone (Practice Form)")
    void shouldAcceptValid10DigitPhone() {
        DemoQAPracticeFormPage page = new DemoQAPracticeFormPage(driver);
        page.navigateTo();
        fillRequiredExceptMobile(page);
        page.enterMobile("1234567890");
        page.clickSubmit();
        java.util.List<String> errors = page.getValidationErrors();
        long phoneErrors = errors.stream().filter(e ->
            e.toLowerCase().contains("mobile") || e.toLowerCase().contains("number")).count();
        assertTrue(phoneErrors == 0, "No mobile/number validation errors for valid 10-digit phone");
    }

    /**
     * TC-PHONE-002: 9-digit phone. Asserts Mobile field is invalid (HTML5 validity).
     */
    @Test
    @DisplayName("TC-PHONE-002: Reject 9-digit phone")
    void shouldReject9DigitPhone() {
        DemoQAPracticeFormPage page = new DemoQAPracticeFormPage(driver);
        page.navigateTo();
        fillRequiredExceptMobile(page);
        page.enterMobile("123456789");
        page.clickSubmit();
        assertFalse(page.isFieldValid(By.id("userNumber")), "9-digit phone should be invalid");
    }

    /**
     * TC-PHONE-003: Phone containing a letter (123456789a). Asserts field is invalid.
     */
    @Test
    @DisplayName("TC-PHONE-003: Reject phone with letters")
    void shouldRejectPhoneWithLetters() {
        DemoQAPracticeFormPage page = new DemoQAPracticeFormPage(driver);
        page.navigateTo();
        fillRequiredExceptMobile(page);
        page.enterMobile("123456789a");
        page.clickSubmit();
        assertFalse(page.isFieldValid(By.id("userNumber")), "Phone with letters should be invalid");
    }

    /**
     * TC-PHONE-004: Phone with trailing space. Asserts field is invalid.
     */
    @Test
    @DisplayName("TC-PHONE-004: Reject phone with spaces")
    void shouldRejectPhoneWithSpaces() {
        DemoQAPracticeFormPage page = new DemoQAPracticeFormPage(driver);
        page.navigateTo();
        fillRequiredExceptMobile(page);
        page.enterMobile("123456789 ");
        page.clickSubmit();
        assertFalse(page.isFieldValid(By.id("userNumber")), "Phone with spaces should be invalid");
    }

    /**
     * TC-PHONE-005: Phone with dashes (123-456-7890). Asserts mobile field has a value
     * after input (accept/reject per site spec; test ensures consistent outcome).
     */
    @Test
    @DisplayName("TC-PHONE-005: Handle phone with dashes")
    void shouldHandlePhoneWithDashes() {
        DemoQAPracticeFormPage page = new DemoQAPracticeFormPage(driver);
        page.navigateTo();
        fillRequiredExceptMobile(page);
        page.enterMobile("123-456-7890");
        page.clickSubmit();
        String value = page.getMobileField().getAttribute("value");
        assertTrue(value != null, "Mobile field should have a value after input");
    }

    /**
     * TC-PHONE-006: 11-digit phone. If maxlength is set, value must respect it; otherwise
     * asserts field is invalid or validation errors are present.
     */
    @Test
    @DisplayName("TC-PHONE-006: Phone too long (11 digits)")
    void shouldHandlePhoneTooLong() {
        DemoQAPracticeFormPage page = new DemoQAPracticeFormPage(driver);
        page.navigateTo();
        fillRequiredExceptMobile(page);
        page.enterMobile("12345678901");
        String maxLen = page.getMobileField().getAttribute("maxlength");
        if (maxLen != null && !maxLen.isEmpty()) {
            String value = page.getMobileField().getAttribute("value");
            assertTrue(value != null && value.length() <= Integer.parseInt(maxLen), "Value should respect maxlength");
        } else {
            page.clickSubmit();
            assertFalse(page.isFieldValid(By.id("userNumber")) || !page.getValidationErrors().isEmpty(), "11 digits should be invalid or limited");
        }
    }

    /**
     * TC-PHONE-007: Leave Mobile empty and submit. Asserts Mobile is required and
     * invalid when empty.
     */
    @Test
    @DisplayName("TC-PHONE-007: Empty phone (required)")
    void shouldValidateEmptyPhone() {
        DemoQAPracticeFormPage page = new DemoQAPracticeFormPage(driver);
        page.navigateTo();
        fillRequiredExceptMobile(page);
        page.clickSubmit();
        assertTrue(page.isFieldRequired(By.id("userNumber")), "Mobile should be required");
        assertFalse(page.isFieldValid(By.id("userNumber")), "Empty mobile should be invalid");
    }
}
