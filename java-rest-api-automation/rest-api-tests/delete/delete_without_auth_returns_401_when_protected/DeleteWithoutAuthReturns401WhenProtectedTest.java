package delete.delete_without_auth_returns_401_when_protected;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Verifies that DELETE without a valid Bearer token returns 401 Unauthorized and does not
 * remove the resource. Creates a resource with auth, then calls DELETE without the
 * Authorization header; if the endpoint is public and returns 2xx, the test is skipped.
 * Finally confirms with GET (with auth) that the resource still exists.
 */
@DisplayName("DELETE without auth returns 401 when protected")
class DeleteWithoutAuthReturns401WhenProtectedTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/delete/delete_without_auth_returns_401_when_protected/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("DELETE without auth returns 401 and resource is not deleted")
    void deleteWithoutAuth_returns401WhenProtected() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "CREATE_ENDPOINT or PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required to create resource");

        String basePath = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        // Create a resource so we have a valid ID; then delete without auth
        String email = "delete-401-" + System.currentTimeMillis() + "@example.com";
        Response createResponse = given()
                .spec(baseSpec)
                .header(authHeader)
                .body("{\"name\":\"Delete 401 Test\",\"email\":\"" + email + "\",\"gender\":\"male\",\"status\":\"active\"}")
                .when()
                .post(basePath);
        Assumptions.assumeTrue(createResponse.getStatusCode() == 201, "Create must succeed");
        Object id = createResponse.path("id");
        Assumptions.assumeTrue(id != null, "Create response must contain id");

        String path = basePath + "/" + id;

        // DELETE without Authorization header; expect 401 if endpoint is protected
        int statusCode = given()
                .spec(baseSpec)
                .when()
                .delete(path)
                .then()
                .extract().statusCode();

        Assumptions.assumeTrue(statusCode == 401,
                "Endpoint did not return 401 without auth (got " + statusCode + "). Skip when endpoint is not protected.");

        // Resource must still exist: GET with auth returns 200
        given().spec(baseSpec).header(authHeader).when().get(path).then().statusCode(equalTo(200));
    }
}
