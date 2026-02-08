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
 * Test Case API-DELETE-001: DELETE with valid existing ID returns 204 or 200.
 * Objective: Verify DELETE with valid existing resource ID returns 204 (or 200) and resource is removed.
 * Expected: Status 204 or 200; resource deleted (optional GET same ID → 404).
 */
@DisplayName("DELETE with valid existing ID returns 204 or 200")
class DeleteValidExistingIdReturns204Or200Test extends BaseApiTest {

    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/delete/delete_valid_existing_id_returns_204_or_200/TEST_CASE.md";

    private static final String AUTH_HEADER = "Authorization";
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("DELETE existing resource returns 204 or 200")
    void deleteValidExistingId_returns204Or200() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "CREATE_ENDPOINT or PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required to create and delete resource");

        String basePath = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        // TEST_CASE Step 1: Create resource; obtain ID
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

        // TEST_CASE Step 2–3: Send DELETE; capture status
        given()
                .spec(baseSpec)
                .header(authHeader)
                .when()
                .delete(path)
                .then()
                .statusCode(anyOf(equalTo(204), equalTo(200)));

        // TEST_CASE Step 4 (optional): GET same ID → 404
        given().spec(baseSpec).header(authHeader).when().get(path).then().statusCode(equalTo(404));
    }
}
