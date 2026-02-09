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
 * Test suite: Complete API–UI Flow (TEST_CASES.md — Section 4).
 * <p>
 * TC-FLOW-001: Full flow CREATE → READ → UI → UPDATE → UI → DELETE. TC-FLOW-002: Create multiple, GET all, verify list in UI.
 */
class CompleteFlowTest extends BaseTest {

    /**
     * TC-FLOW-001: CREATE via API; assert id, title. READ same resource via API; assert. Open resource in UI; assert JSON has id, title.
     * UPDATE via API; assert. Reload UI; assert JSON structure. DELETE via API (cleanup). All steps pass; demo may not persist.
     */
    @Test
    @DisplayName("TC-FLOW-001: Full flow CREATE → READ → UI → UPDATE → UI → DELETE")
    void shouldCompleteFullApiUiFlow() {
        Response createRes = PostsApiClient.createPost("Flow title", "Flow body", 1);
        assertTrue(createRes.getStatusCode() == 201 || createRes.getStatusCode() == 200, "CREATE success");
        assertNotNull(createRes.jsonPath().get("id"), "Response has id");
        assertNotNull(createRes.jsonPath().get("title"), "Response has title");

        Response readRes = PostsApiClient.getPost(1);
        assertTrue(readRes.getStatusCode() == 200, "READ success");

        JsonPage jsonPage = new JsonPage(driver);
        jsonPage.navigateTo("posts/1");
        assertTrue(jsonPage.hasJsonKey("id") && jsonPage.hasJsonKey("title"), "UI shows id and title");

        Response updateRes = PostsApiClient.updatePost(1, "Updated flow title", "Updated body", 1);
        assertTrue(updateRes.getStatusCode() >= 200 && updateRes.getStatusCode() < 300, "UPDATE success");

        jsonPage.navigateTo("posts/1");
        assertNotNull(jsonPage.getBodyAsJsonPath(), "UI shows valid JSON structure after update");

        Response deleteRes = PostsApiClient.deletePost(1);
        assertTrue(deleteRes.getStatusCode() >= 200 && deleteRes.getStatusCode() < 300, "DELETE cleanup succeeds");
    }

    /**
     * TC-FLOW-002: Create 3 resources via API. GET all /posts via API. Navigate to /posts in UI. Verify UI shows array; optionally check structure.
     */
    @Test
    @DisplayName("TC-FLOW-002: Create multiple resources and verify list in UI")
    void shouldCreateMultipleAndVerifyListInUi() {
        PostsApiClient.createPost("One", "Body one", 1);
        PostsApiClient.createPost("Two", "Body two", 1);
        PostsApiClient.createPost("Three", "Body three", 1);

        Response listRes = PostsApiClient.getAllPosts();
        assertTrue(listRes.getStatusCode() == 200, "GET all posts success");
        assertNotNull(listRes.jsonPath().getList("$"), "List endpoint returns data");

        JsonPage jsonPage = new JsonPage(driver);
        jsonPage.navigateTo("posts");
        assertNotNull(jsonPage.getBodyAsJsonPath(), "UI shows valid JSON array");
    }
}
