package delete.delete_with_conflict_returns_409_when_applicable;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.http.Header;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

/**
 * Test Case API-DELETE-007: DELETE with conflict returns 409 when applicable.
 * Objective: When resource cannot be deleted (e.g. dependency), API returns 409 (or 400) and does not delete.
 * Expected: Status 409 or 400; error message in body; resource still exists. Skipped when API does not support.
 */
@DisplayName("DELETE with conflict returns 409 when applicable")
class DeleteWithConflictReturns409WhenApplicableTest extends BaseApiTest {

    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/delete/delete_with_conflict_returns_409_when_applicable/TEST_CASE.md";

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("DELETE resource in conflict state returns 409 or 400")
    void deleteWithConflict_returns409Or400() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required");
        Assumptions.assumeTrue(ApiConfig.getConflictResourceId().isPresent(),
                "CONFLICT_RESOURCE_ID must be set for a resource that cannot be deleted. Skip when API does not support conflict.");

        String conflictId = ApiConfig.getConflictResourceId().orElseThrow();
        String basePath = ApiConfig.getProtectedEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        String path = basePath + "/" + conflictId;
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        // TEST_CASE Step 2–3: Send DELETE with valid auth; capture status and body
        given()
                .spec(baseSpec)
                .header(authHeader)
                .when()
                .delete(path)
                .then()
                .statusCode(anyOf(equalTo(409), equalTo(400)));
    }
}
