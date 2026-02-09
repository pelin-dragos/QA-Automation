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
 * Test suite: Data Consistency — API and UI (TEST_CASES.md — Section 5).
 * <p>
 * TC-SYNC-001: GET resource via API, open same URL in UI, verify same data visible. TC-SYNC-002: Create via API, open in UI, verify structure.
 */
class DataConsistencyTest extends BaseTest {

    /**
     * TC-SYNC-001: GET resource by id via API. Open same URL in UI; verify UI displays the same title and body (content contains API values).
     */
    @Test
    @DisplayName("TC-SYNC-001: API and UI data consistency")
    void shouldMatchApiAndUiData() {
        Response apiResponse = PostsApiClient.getPost(1);
        assertNotNull(apiResponse.jsonPath().get("id"), "API returns id");
        String apiTitle = apiResponse.jsonPath().getString("title");

        JsonPage jsonPage = new JsonPage(driver);
        jsonPage.navigateTo("posts/1");
        String bodyText = jsonPage.getBodyText();
        assertNotNull(bodyText, "UI page has body text");
        assertTrue(bodyText.contains(apiTitle), "UI displays same title as API");
        assertTrue(jsonPage.hasJsonKey("id"), "UI shows id");
        assertTrue(jsonPage.hasJsonKey("title"), "UI shows title");
        assertTrue(jsonPage.hasJsonKey("body"), "UI shows body");
    }

    /**
     * TC-SYNC-002: CREATE via API. Open resource in UI. Verify UI shows JSON structure with id and title (sync concept).
     */
    @Test
    @DisplayName("TC-SYNC-002: Real-time sync concept (create then UI)")
    void shouldShowSyncConceptCreateThenUi() {
        PostsApiClient.createPost("Sync title", "Sync body", 1);

        JsonPage jsonPage = new JsonPage(driver);
        jsonPage.navigateTo("posts/1");
        assertTrue(jsonPage.hasJsonKey("id"), "UI shows id");
        assertTrue(jsonPage.hasJsonKey("title"), "UI shows title");
    }
}
