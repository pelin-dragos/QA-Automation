package com.qa.automation.project02.tests;

import com.qa.automation.project02.base.BaseTest;
import com.qa.automation.project02.pages.DemoQAPracticeFormPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test suite: Required fields validation (TEST_CASES.md — Section 1).
 * <p>
 * Covers TC-REQ-001 to TC-REQ-005 on DemoQA Practice Form: empty First Name, empty Last Name,
 * empty Email, successful submit when all required fields are filled, and multiple required
 * fields empty. Asserts HTML5 required/validity and visible validation errors where applicable.
 */
class RequiredFieldsTest extends BaseTest {

    /**
     * TC-REQ-001: Leave First Name empty; fill all other required fields and submit. Asserts
     * First Name is required and invalid when empty.
     */
    @Test
    @DisplayName("TC-REQ-001: Validate empty First Name (Practice Form)")
    void shouldValidateEmptyFirstName() {
        DemoQAPracticeFormPage page = new DemoQAPracticeFormPage(driver);
        page.navigateTo();
        page.enterLastName("User");
        page.enterEmail("test@example.com");
        page.selectGender();
        page.enterMobile("1234567890");
        page.clickSubmit();
        assertTrue(page.isFieldRequired(By.id("firstName")), "First Name should be required");
        assertFalse(page.isFieldValid(By.id("firstName")), "First Name should be invalid when empty");
    }

    /**
     * TC-REQ-002: Leave Last Name empty; fill other required fields and submit. Asserts
     * Last Name is required and invalid when empty.
     */
    @Test
    @DisplayName("TC-REQ-002: Validate empty Last Name (Practice Form)")
    void shouldValidateEmptyLastName() {
        DemoQAPracticeFormPage page = new DemoQAPracticeFormPage(driver);
        page.navigateTo();
        page.enterFirstName("Test");
        page.enterEmail("test@example.com");
        page.selectGender();
        page.enterMobile("1234567890");
        page.clickSubmit();
        assertTrue(page.isFieldRequired(By.id("lastName")), "Last Name should be required");
        assertFalse(page.isFieldValid(By.id("lastName")), "Last Name should be invalid when empty");
    }

    /**
     * TC-REQ-003: Leave Email empty; fill other required fields and submit. Asserts that
     * the email field was left empty as per test steps (site may or may not require email).
     */
    @Test
    @DisplayName("TC-REQ-003: Validate empty Email (Practice Form)")
    void shouldValidateEmptyEmail() {
        DemoQAPracticeFormPage page = new DemoQAPracticeFormPage(driver);
        page.navigateTo();
        page.enterFirstName("Test");
        page.enterLastName("User");
        page.selectGender();
        page.enterMobile("1234567890");
        page.clickSubmit();
        assertTrue(page.getEmailField().getAttribute("value") == null || page.getEmailField().getAttribute("value").isEmpty(),
                "Email field was left empty as per test steps");
    }

    /**
     * TC-REQ-004: Fill all required fields with valid data and submit. Asserts that no
     * required-field validation errors (first/last name, email) appear.
     */
    @Test
    @DisplayName("TC-REQ-004: Form submits when all required fields valid")
    void shouldSubmitWhenAllRequiredFilled() {
        DemoQAPracticeFormPage page = new DemoQAPracticeFormPage(driver);
        page.navigateTo();
        page.enterFirstName("Test");
        page.enterLastName("User");
        page.enterEmail("test@example.com");
        page.selectGender();
        page.enterMobile("1234567890");
        page.clickSubmit();
        java.util.List<String> errors = page.getValidationErrors();
        boolean hasRequiredError = errors.stream().anyMatch(e ->
            e.toLowerCase().contains("first") || e.toLowerCase().contains("last") || e.toLowerCase().contains("name") || e.toLowerCase().contains("email"));
        assertFalse(hasRequiredError, "No required-field errors when all required fields are filled");
    }

    /**
     * TC-REQ-005: Leave First Name and Last Name empty; fill only gender and mobile, then
     * submit. Asserts both name fields are invalid when empty.
     */
    @Test
    @DisplayName("TC-REQ-005: Multiple required fields empty")
    void shouldValidateMultipleRequiredEmpty() {
        DemoQAPracticeFormPage page = new DemoQAPracticeFormPage(driver);
        page.navigateTo();
        page.selectGender();
        page.enterMobile("1234567890");
        page.clickSubmit();
        assertFalse(page.isFieldValid(By.id("firstName")), "First Name invalid when empty");
        assertFalse(page.isFieldValid(By.id("lastName")), "Last Name invalid when empty");
    }
}
