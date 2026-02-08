package delete.get_after_delete_returns_404_for_same_id;

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

/**
 * Verifies the delete semantics: after a successful DELETE (204 or 200), a GET request
 * for the same resource ID returns 404 Not Found, confirming the resource no longer exists.
 * Creates a resource via POST, deletes it, then asserts GET returns 404.
 */
@DisplayName("GET after DELETE for same ID returns 404")
class GetAfterDeleteReturns404ForSameIdTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/delete/get_after_delete_returns_404_for_same_id/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("GET same ID after DELETE returns 404")
    void getAfterDelete_returns404ForSameId() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "CREATE_ENDPOINT or PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required");

        String basePath = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        // Create a resource and remember its ID
        String email = "get-after-del-" + System.currentTimeMillis() + "@example.com";
        Response createResponse = given()
                .spec(baseSpec)
                .header(authHeader)
                .body("{\"name\":\"Get After Delete Test\",\"email\":\"" + email + "\",\"gender\":\"male\",\"status\":\"active\"}")
                .when()
                .post(basePath);
        Assumptions.assumeTrue(createResponse.getStatusCode() == 201, "Create must succeed");
        Object id = createResponse.path("id");
        Assumptions.assumeTrue(id != null, "Create response must contain id");

        String path = basePath + "/" + id;

        // DELETE the resource; expect 204 or 200
        given()
                .spec(baseSpec)
                .header(authHeader)
                .when()
                .delete(path)
                .then()
                .statusCode(anyOf(equalTo(204), equalTo(200)));

        // Same ID must now return 404 (resource no longer exists)
        given()
                .spec(baseSpec)
                .header(authHeader)
                .when()
                .get(path)
                .then()
                .statusCode(equalTo(404));
    }
}
