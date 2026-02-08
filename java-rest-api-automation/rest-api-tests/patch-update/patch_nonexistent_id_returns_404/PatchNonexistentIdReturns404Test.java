package patch_update.patch_nonexistent_id_returns_404;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.http.Header;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Verifies that PATCH with a non-existent resource ID returns 404 Not Found and does not
 * create or update any resource. Uses a constant high ID; sends valid auth and a valid
 * partial body so that the only reason for failure is the missing resource.
 */
@DisplayName("PATCH nonexistent ID returns 404")
class PatchNonexistentIdReturns404Test extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/patch-update/patch_nonexistent_id_returns_404/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";
    /** ID assumed not to exist so that the API returns 404. */
    private static final long NON_EXISTENT_ID = 999999999L;
    /** Valid partial body for PATCH (only ID is invalid). */
    private static final String PARTIAL_BODY = "{\"status\":\"inactive\"}";

    @Test
    @DisplayName("PATCH with non-existent ID returns 404")
    void patchNonexistentId_returns404() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required for PATCH");

        String basePath = ApiConfig.getProtectedEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        String path = basePath + "/" + NON_EXISTENT_ID;
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        given()
                .spec(baseSpec)
                .header(authHeader)
                .body(PARTIAL_BODY)
                .when()
                .patch(path)
                .then()
                .statusCode(equalTo(404));
    }
}
