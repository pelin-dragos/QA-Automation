package delete.delete_valid_existing_id_returns_204_or_200;

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
 * Verifies that DELETE with a valid existing resource ID returns 204 No Content or 200 OK
 * and that the resource is actually removed (subsequent GET for the same ID returns 404).
 * Creates a resource via POST first; requires BASE_URL, CREATE_ENDPOINT and AUTH_TOKEN.
 */
@DisplayName("DELETE with valid existing ID returns 204 or 200")
class DeleteValidExistingIdReturns204Or200Test extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/delete/delete_valid_existing_id_returns_204_or_200/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("DELETE existing resource returns 204 or 200")
    void deleteValidExistingId_returns204Or200() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "CREATE_ENDPOINT or PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required to create and delete resource");

        String basePath = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        // Create a resource so we have a valid ID to delete
        String email = "delete-test-" + System.currentTimeMillis() + "@example.com";
        Response createResponse = given()
                .spec(baseSpec)
                .header(authHeader)
                .body("{\"name\":\"Delete Test User\",\"email\":\"" + email + "\",\"gender\":\"male\",\"status\":\"active\"}")
                .when()
                .post(basePath);
        Assumptions.assumeTrue(createResponse.getStatusCode() == 201, "Create must succeed to run delete test");
        Object id = createResponse.path("id");
        Assumptions.assumeTrue(id != null, "Create response must contain id");

        String path = basePath + "/" + id;

        // DELETE by ID; expect 204 or 200
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
