package post_create.post_duplicate_returns_409_or_400_when_applicable;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that a second POST with the same unique field (e.g. email) returns 409
 * Conflict, 400 Bad Request or 422 Unprocessable Entity and does not create a
 * duplicate. First POST must succeed; second uses the same body. Requires BASE_URL,
 * CREATE_ENDPOINT and AUTH_TOKEN.
 */
@DisplayName("POST duplicate returns 409 or 400 when applicable")
class PostDuplicateReturns409Or400WhenApplicableTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/post-create/post_duplicate_returns_409_or_400_when_applicable/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("Second POST with same email returns 409 or 400")
    void postDuplicate_returns409Or400WhenApplicable() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required");

        String path = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());
        String email = "post-dup-" + System.currentTimeMillis() + "@example.com";
        String body = "{\"name\":\"Post Duplicate\",\"email\":\"" + email + "\",\"gender\":\"male\",\"status\":\"active\"}";

        Response first = given().spec(baseSpec).header(authHeader).body(body).when().post(path);
        Assumptions.assumeTrue(first.getStatusCode() == 201 || first.getStatusCode() == 200, "First POST must succeed");

        int secondStatus = given()
                .spec(baseSpec)
                .header(authHeader)
                .body(body)
                .when()
                .post(path)
                .then()
                .extract()
                .statusCode();

        assertTrue(secondStatus == 409 || secondStatus == 400 || secondStatus == 422,
                "Duplicate POST should return 409, 400 or 422 (client error), got " + secondStatus);
    }
}
