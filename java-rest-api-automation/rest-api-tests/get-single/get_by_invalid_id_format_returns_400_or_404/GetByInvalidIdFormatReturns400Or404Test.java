package get_single.get_by_invalid_id_format_returns_400_or_404;

import api.BaseApiTest;
import api.config.ApiConfig;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

/**
 * Verifies that GET by resource ID with an invalid ID format in the path (e.g. non-numeric
 * when the API expects a number) returns 400 Bad Request or 404 Not Found. Uses a constant
 * invalid ID string; request is sent without auth.
 */
@DisplayName("GET by invalid ID format returns 400 or 404")
class GetByInvalidIdFormatReturns400Or404Test extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/get-single/get_by_invalid_id_format_returns_400_or_404/TEST_CASE.md";

    /** Malformed ID (e.g. non-numeric) to trigger format validation or route mismatch. */
    private static final String INVALID_ID = "abc";

    @Test
    @DisplayName("GET resource by invalid ID format returns 400 or 404")
    void getByInvalidIdFormat_returns400Or404() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");

        String basePath = ApiConfig.getProtectedEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        String path = basePath + "/" + INVALID_ID;

        given()
                .spec(baseSpec)
                .when()
                .get(path)
                .then()
                .statusCode(anyOf(equalTo(400), equalTo(404)));
    }
}
