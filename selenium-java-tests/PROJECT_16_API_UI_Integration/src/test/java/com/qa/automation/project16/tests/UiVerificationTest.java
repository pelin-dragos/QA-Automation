package com.qa.automation.project16.tests;

import com.qa.automation.project16.base.BaseTest;
import com.qa.automation.project16.ui.JsonPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test suite: UI Verification — JSON displayed in browser (TEST_CASES.md — Section 2).
 * <p>
 * TC-UI-001: Navigate to GET /posts/1 in browser (JSON view), wait for load, parse JSON; assert valid and contains id, title.
 */
class UiVerificationTest extends BaseTest {

    /**
     * TC-UI-001: Navigate to /posts/1 in browser, wait for page load, parse JSON from page.
     * Asserts JSON is valid and contains keys "id" and "title".
     */
    @Test
    @DisplayName("TC-UI-001: Verify JSON displayed in UI")
    void shouldVerifyJsonDisplayedInUi() {
        JsonPage jsonPage = new JsonPage(driver);
        jsonPage.navigateTo("posts/1");

        assertTrue(jsonPage.hasJsonKey("id"), "JSON valid and contains key id");
        assertTrue(jsonPage.hasJsonKey("title"), "JSON valid and contains key title");
        assertNotNull(jsonPage.getBodyAsJsonPath(), "JSON from page must be parseable");
    }
}
