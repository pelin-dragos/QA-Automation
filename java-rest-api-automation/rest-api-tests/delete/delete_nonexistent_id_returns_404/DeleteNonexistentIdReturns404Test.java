package delete.delete_nonexistent_id_returns_404;

import api.BaseApiTest;
import api.config.ApiConfig;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

/**
 * Test Case API-DELETE-002: DELETE with non-existent ID returns 404.
 * Objective: Verify DELETE with non-existent resource ID returns 404 (or 204 if API is idempotent).
 * Expected: Status 404 (or 204 per contract); no 500.
 */
@DisplayName("DELETE with non-existent ID returns 404")
class DeleteNonexistentIdReturns404Test extends BaseApiTest {

    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/delete/delete_nonexistent_id_returns_404/TEST_CASE.md";

    private static final long NON_EXISTENT_ID = 999999999L;

    @Test
    @DisplayName("DELETE with non-existent ID returns 404 or 204")
    void deleteWithNonexistentId_returns404Or204() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");

        String basePath = ApiConfig.getProtectedEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElseThrow();
        String path = basePath + "/" + NON_EXISTENT_ID;

        // TEST_CASE Step 2–3: Send DELETE with non-existent ID; capture status
        given()
                .spec(baseSpec)
                .when()
                .delete(path)
                .then()
                .statusCode(anyOf(equalTo(404), equalTo(204)));
    }
}
