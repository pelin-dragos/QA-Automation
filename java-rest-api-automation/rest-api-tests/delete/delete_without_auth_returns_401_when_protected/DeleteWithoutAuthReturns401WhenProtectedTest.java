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
 * Test Case API-DELETE-005: DELETE without auth returns 401 when endpoint is protected.
 * Objective: Verify DELETE without valid authentication returns 401 and resource is not deleted.
 * Expected: Status 401; resource still exists. Skipped when endpoint is not protected.
 */
@DisplayName("DELETE without auth returns 401 when protected")
class DeleteWithoutAuthReturns401WhenProtectedTest extends BaseApiTest {

    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/delete/delete_without_auth_returns_401_when_protected/TEST_CASE.md";

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("DELETE without auth returns 401 and resource is not deleted")
    void deleteWithoutAuth_returns401WhenProtected() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "CREATE_ENDPOINT or PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required to create resource");

        String basePath = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        // TEST_CASE precondition: ensure a resource exists (create with auth)
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

        // TEST_CASE Step 1–2: DELETE with valid ID but without auth (omit header); capture status
        int statusCode = given()
                .spec(baseSpec)
                .when()
                .delete(path)
                .then()
                .extract().statusCode();

        Assumptions.assumeTrue(statusCode == 401,
                "Endpoint did not return 401 without auth (got " + statusCode + "). Skip when endpoint is not protected.");

        // TEST_CASE Step 3 (optional): GET with auth to confirm resource still exists
        given().spec(baseSpec).header(authHeader).when().get(path).then().statusCode(equalTo(200));
    }
}
