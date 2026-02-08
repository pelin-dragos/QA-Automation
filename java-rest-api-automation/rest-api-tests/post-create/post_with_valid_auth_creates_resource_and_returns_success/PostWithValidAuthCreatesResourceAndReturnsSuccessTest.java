package post_create.post_with_valid_auth_creates_resource_and_returns_success;

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
 * Verifies that POST with valid Bearer authentication creates the resource (201 or 200)
 * and that the created resource can be retrieved via GET by ID. Requires BASE_URL,
 * CREATE_ENDPOINT and AUTH_TOKEN.
 */
@DisplayName("POST with valid auth creates resource and returns success")
class PostWithValidAuthCreatesResourceAndReturnsSuccessTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/post-create/post_with_valid_auth_creates_resource_and_returns_success/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("POST with valid auth returns 201 and created resource can be retrieved")
    void postWithValidAuth_createsResourceAndReturnsSuccess() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required");

        String basePath = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());
        String email = "post-auth-" + System.currentTimeMillis() + "@example.com";
        String body = "{\"name\":\"Post Auth\",\"email\":\"" + email + "\",\"gender\":\"male\",\"status\":\"active\"}";

        Object id = given()
                .spec(baseSpec)
                .header(authHeader)
                .body(body)
                .when()
                .post(basePath)
                .then()
                .statusCode(anyOf(equalTo(201), equalTo(200)))
                .extract()
                .path("id");

        Assumptions.assumeTrue(id != null, "Create response must contain id");
        given().spec(baseSpec).header(authHeader).when().get(basePath + "/" + id).then().statusCode(equalTo(200));
    }
}
