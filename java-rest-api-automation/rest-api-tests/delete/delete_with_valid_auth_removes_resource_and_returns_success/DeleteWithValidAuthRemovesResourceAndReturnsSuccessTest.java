package delete.delete_with_valid_auth_removes_resource_and_returns_success;

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
 * Verifies that DELETE with valid Bearer authentication removes the resource and returns
 * 204 No Content or 200 OK, and that a subsequent GET for the same ID returns 404.
 * Creates a resource via POST first; requires BASE_URL, CREATE_ENDPOINT and AUTH_TOKEN.
 */
@DisplayName("DELETE with valid auth removes resource and returns success")
class DeleteWithValidAuthRemovesResourceAndReturnsSuccessTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/delete/delete_with_valid_auth_removes_resource_and_returns_success/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("DELETE with valid auth returns 204 or 200 and resource is removed")
    void deleteWithValidAuth_removesResourceAndReturnsSuccess() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "CREATE_ENDPOINT or PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required");

        String basePath = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        // Create a resource so we have an ID to delete with valid auth
        String email = "delete-auth-" + System.currentTimeMillis() + "@example.com";
        Response createResponse = given()
                .spec(baseSpec)
                .header(authHeader)
                .body("{\"name\":\"Delete Auth Test\",\"email\":\"" + email + "\",\"gender\":\"male\",\"status\":\"active\"}")
                .when()
                .post(basePath);
        Assumptions.assumeTrue(createResponse.getStatusCode() == 201, "Create must succeed");
        Object id = createResponse.path("id");
        Assumptions.assumeTrue(id != null, "Create response must contain id");

        String path = basePath + "/" + id;

        // DELETE with valid auth; expect 204 or 200
        given()
                .spec(baseSpec)
                .header(authHeader)
                .when()
                .delete(path)
                .then()
                .statusCode(anyOf(equalTo(204), equalTo(200)));

        // Confirm resource is gone: GET same ID must return 404
        given().spec(baseSpec).header(authHeader).when().get(path).then().statusCode(equalTo(404));
    }
}
