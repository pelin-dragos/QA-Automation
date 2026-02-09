package com.qa.automation.project16.tests;

import com.qa.automation.project16.api.PostsApiClient;
import com.qa.automation.project16.base.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test suite: API Setup / Create (TEST_CASES.md — Section 1).
 * <p>
 * TC-API-001: Create resource via POST; assert status 201 (or 200); response has id and title matches request.
 */
class ApiCreateTest extends BaseTest {

    /**
     * TC-API-001: POST to /posts with title, body, userId. Asserts response status 201 or 200;
     * response contains id and title matches request. JSONPlaceholder may return 201 without persisting.
     */
    @Test
    @DisplayName("TC-API-001: Create resource via POST API")
    void shouldCreateResourceViaPost() {
        String title = "Test title";
        String body = "Test body";
        int userId = 1;

        Response response = PostsApiClient.createPost(title, body, userId);

        int status = response.getStatusCode();
        assertTrue(status == 201 || status == 200, "Expected status 201 or 200; got " + status);

        assertNotNull(response.jsonPath().get("id"), "Response must contain id");
        assertEquals(title, response.jsonPath().getString("title"), "Title in response must match request");
    }
}
