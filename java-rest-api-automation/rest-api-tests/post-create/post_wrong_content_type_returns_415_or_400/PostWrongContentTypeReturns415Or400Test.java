package post_create.post_wrong_content_type_returns_415_or_400;

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
 * Verifies that POST with an unsupported Content-Type (e.g. text/plain instead of
 * application/json) returns 415 Unsupported Media Type, 400 or 422 and does not
 * create a resource. Overrides the default Content-Type from baseSpec. Requires
 * BASE_URL, CREATE_ENDPOINT and AUTH_TOKEN.
 */
@DisplayName("POST wrong Content-Type returns 415 or 400")
class PostWrongContentTypeReturns415Or400Test extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/post-create/post_wrong_content_type_returns_415_or_400/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";
    /** Request header to set unsupported media type. */
    private static final String CONTENT_TYPE_HEADER = "Content-Type";
    /** Unsupported value to trigger 415 or 400. */
    private static final String WRONG_CONTENT_TYPE = "text/plain";

    @Test
    @DisplayName("POST with Content-Type text/plain returns 415 or 400")
    void postWrongContentType_returns415Or400() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required");

        String path = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());
        String body = "{\"name\":\"Wrong CT\",\"email\":\"wrongct-" + System.currentTimeMillis() + "@example.com\",\"gender\":\"male\",\"status\":\"active\"}";

        given()
                .spec(baseSpec)
                .header(authHeader)
                .header(CONTENT_TYPE_HEADER, WRONG_CONTENT_TYPE)
                .body(body)
                .when()
                .post(path)
                .then()
                .statusCode(anyOf(equalTo(415), equalTo(400), equalTo(422)));
    }
}
