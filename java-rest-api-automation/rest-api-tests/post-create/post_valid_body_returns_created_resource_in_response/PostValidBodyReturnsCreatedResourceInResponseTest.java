package post_create.post_valid_body_returns_created_resource_in_response;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.http.Header;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Verifies that POST with a valid body returns 201 or 200 and the response body contains
 * the created resource: id plus key fields (e.g. name, email) matching or derived from
 * the request. Requires BASE_URL, CREATE_ENDPOINT and AUTH_TOKEN.
 */
@DisplayName("POST valid body returns created resource in response")
class PostValidBodyReturnsCreatedResourceInResponseTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/post-create/post_valid_body_returns_created_resource_in_response/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("POST with valid body returns response with id and key fields")
    void postValidBody_returnsCreatedResourceInResponse() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required");

        String path = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());
        String name = "Post Created Resource";
        String email = "post-created-" + System.currentTimeMillis() + "@example.com";
        String body = "{\"name\":\"" + name + "\",\"email\":\"" + email + "\",\"gender\":\"male\",\"status\":\"active\"}";

        JsonPath jsonPath = given()
                .spec(baseSpec)
                .header(authHeader)
                .body(body)
                .when()
                .post(path)
                .then()
                .statusCode(anyOf(equalTo(201), equalTo(200)))
                .extract()
                .body()
                .jsonPath();

        assertNotNull(jsonPath.get("id"), "Response must contain id");
        assertEquals(name, jsonPath.get("name"));
        assertEquals(email, jsonPath.get("email"));
    }
}
