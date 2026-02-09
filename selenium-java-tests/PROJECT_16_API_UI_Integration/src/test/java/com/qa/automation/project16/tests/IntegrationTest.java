package com.qa.automation.project16.tests;

import com.qa.automation.project16.api.PostsApiClient;
import com.qa.automation.project16.base.BaseTest;
import com.qa.automation.project16.ui.JsonPage;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test suite: API Setup + UI Verify — Integration (TEST_CASES.md — Section 3).
 * <p>
 * TC-INT-001: Create via API then verify in UI. TC-INT-002: Update via API then verify in UI.
 * TC-INT-003: Verify in UI then delete via API. JSONPlaceholder does not persist; use existing id for UI where needed.
 */
class IntegrationTest extends BaseTest {

    /**
     * TC-INT-001: POST create resource, assert creation (id, title). Navigate in UI to resource URL
     * (e.g. /posts/1 for demo). Verify JSON in UI has id and title.
     */
    @Test
    @DisplayName("TC-INT-001: Create via API then verify in UI")
    void shouldCreateViaApiThenVerifyInUi() {
        Response createResponse = PostsApiClient.createPost("Integration title", "Integration body", 1);
        assertTrue(createResponse.getStatusCode() == 201 || createResponse.getStatusCode() == 200, "API creation success");
        assertNotNull(createResponse.jsonPath().get("id"), "Response has id");
        assertNotNull(createResponse.jsonPath().get("title"), "Response has title");

        JsonPage jsonPage = new JsonPage(driver);
        jsonPage.navigateTo("posts/1");
        assertTrue(jsonPage.hasJsonKey("id") && jsonPage.hasJsonKey("title"), "UI shows valid JSON with id and title");
    }

    /**
     * TC-INT-002: PUT/PATCH resource (e.g. id 1) with new title/body. Assert update response. Open same resource in UI; verify JSON.
     */
    @Test
    @DisplayName("TC-INT-002: Update via API then verify in UI")
    void shouldUpdateViaApiThenVerifyInUi() {
        Response updateResponse = PostsApiClient.updatePost(1, "Updated title", "Updated body", 1);
        assertTrue(updateResponse.getStatusCode() >= 200 && updateResponse.getStatusCode() < 300, "Update response success");

        JsonPage jsonPage = new JsonPage(driver);
        jsonPage.navigateTo("posts/1");
        assertNotNull(jsonPage.getBodyAsJsonPath(), "UI shows valid JSON (JSONPlaceholder may not persist update)");
    }

    /**
     * TC-INT-003: Open resource in UI; verify JSON valid and has id. DELETE via API. Assert DELETE succeeds (demo limitation documented).
     */
    @Test
    @DisplayName("TC-INT-003: Verify in UI then delete via API")
    void shouldVerifyInUiThenDeleteViaApi() {
        JsonPage jsonPage = new JsonPage(driver);
        jsonPage.navigateTo("posts/1");
        assertTrue(jsonPage.hasJsonKey("id"), "UI pre-delete: JSON valid and has id");

        Response deleteResponse = PostsApiClient.deletePost(1);
        assertTrue(deleteResponse.getStatusCode() >= 200 && deleteResponse.getStatusCode() < 300, "DELETE call succeeds (demo API may not persist)");
    }
}
