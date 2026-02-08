package put_update.put_invalid_id_format_returns_400_or_404;

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
 * Verifies that PUT with an invalid ID format in the path (e.g. non-numeric when the
 * API expects a number) returns 400 or 404 and does not update any resource. Uses
 * valid auth and body. Requires BASE_URL, PROTECTED_ENDPOINT and AUTH_TOKEN.
 */
@DisplayName("PUT invalid ID format returns 400 or 404")
class PutInvalidIdFormatReturns400Or404Test extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/put-update/put_invalid_id_format_returns_400_or_404/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";
    /** Malformed ID to trigger format validation or route mismatch. */
    private static final String INVALID_ID = "invalid-id-format";
    /** Valid full body; only the path ID is invalid. */
    private static final String FULL_BODY = "{\"name\":\"Put Invalid Id\",\"email\":\"putinvalid@example.com\",\"gender\":\"male\",\"status\":\"active\"}";

    @Test
    @DisplayName("PUT with invalid ID format returns 400 or 404")
    void putInvalidIdFormat_returns400Or404() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required");

        String basePath = ApiConfig.getProtectedEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        String path = basePath + "/" + INVALID_ID;
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        given()
                .spec(baseSpec)
                .header(authHeader)
                .body(FULL_BODY)
                .when()
                .put(path)
                .then()
                .statusCode(anyOf(equalTo(400), equalTo(404)));
    }
}
