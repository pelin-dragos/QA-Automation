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
 * Verifies that DELETE with a non-existent resource ID returns 404 Not Found (or 204 No Content
 * if the API treats delete-by-missing-id as idempotent). No auth header so the test applies
 * to endpoints that allow unauthenticated delete or that return 404 before auth checks.
 */
@DisplayName("DELETE with non-existent ID returns 404")
class DeleteNonexistentIdReturns404Test extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/delete/delete_nonexistent_id_returns_404/TEST_CASE.md";

    /** ID that is assumed not to exist in the target environment (avoids accidental deletion). */
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

        // DELETE by non-existent ID; expect 404 or 204 (idempotent delete)
        given()
                .spec(baseSpec)
                .when()
                .delete(path)
                .then()
                .statusCode(anyOf(equalTo(404), equalTo(204)));
    }
}
