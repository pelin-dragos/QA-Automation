package delete.delete_invalid_id_format_returns_400_or_404;

import api.BaseApiTest;
import api.config.ApiConfig;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

/**
 * Test Case API-DELETE-003: DELETE with invalid ID format returns 400 or 404.
 * Objective: Verify DELETE with invalid ID format (e.g. non-numeric) returns 400 or 404; no resource deleted.
 * Expected: Status 400 or 404; no 500.
 */
@DisplayName("DELETE with invalid ID format returns 400 or 404")
class DeleteInvalidIdFormatReturns400Or404Test extends BaseApiTest {

    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/delete/delete_invalid_id_format_returns_400_or_404/TEST_CASE.md";

    private static final String INVALID_ID = "abc";

    @Test
    @DisplayName("DELETE with invalid ID format returns 400 or 404")
    void deleteWithInvalidIdFormat_returns400Or404() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");

        String basePath = ApiConfig.getProtectedEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElseThrow();
        String path = basePath + "/" + INVALID_ID;

        // TEST_CASE Step 1–2: Send DELETE with invalid ID; capture status
        given()
                .spec(baseSpec)
                .when()
                .delete(path)
                .then()
                .statusCode(anyOf(equalTo(400), equalTo(404)));
    }
}
