package post_create.post_empty_body_returns_400_or_415_when_required;

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
 * Verifies that POST with an empty body {} returns 400, 415 or 422 when the API
 * requires a non-empty body; no resource is created. Some APIs return 422 for
 * validation. Requires BASE_URL, CREATE_ENDPOINT and AUTH_TOKEN.
 */
@DisplayName("POST empty body returns 400 or 415 when required")
class PostEmptyBodyReturns400Or415WhenRequiredTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/post-create/post_empty_body_returns_400_or_415_when_required/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";
    /** Empty JSON object; API should reject when body is required. */
    private static final String EMPTY_BODY = "{}";

    @Test
    @DisplayName("POST with empty body returns 400 or 415")
    void postEmptyBody_returns400Or415WhenRequired() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required");

        String path = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        given()
                .spec(baseSpec)
                .header(authHeader)
                .body(EMPTY_BODY)
                .when()
                .post(path)
                .then()
                .statusCode(anyOf(equalTo(400), equalTo(415), equalTo(422)));
    }
}
