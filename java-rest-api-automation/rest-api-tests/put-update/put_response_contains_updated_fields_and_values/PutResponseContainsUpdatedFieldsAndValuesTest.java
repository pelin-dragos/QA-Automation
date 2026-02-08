package put_update.put_response_contains_updated_fields_and_values;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Verifies that after a successful PUT, the response body (when present) or a subsequent
 * GET contains the updated field values (e.g. name, status). Creates a resource,
 * PUTs with new values, then asserts on PUT response body or GET. Requires BASE_URL,
 * CREATE_ENDPOINT and AUTH_TOKEN.
 */
@DisplayName("PUT response contains updated fields and values")
class PutResponseContainsUpdatedFieldsAndValuesTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/put-update/put_response_contains_updated_fields_and_values/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("PUT response or GET contains updated field values")
    void putResponse_containsUpdatedFieldsAndValues() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required");

        String basePath = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        String email = "put-fields-" + System.currentTimeMillis() + "@example.com";
        Response createRes = given().spec(baseSpec).header(authHeader)
                .body("{\"name\":\"Original\",\"email\":\"" + email + "\",\"gender\":\"male\",\"status\":\"active\"}")
                .when().post(basePath);
        Assumptions.assumeTrue(createRes.getStatusCode() == 201 || createRes.getStatusCode() == 200, "Create must succeed");
        Object id = createRes.path("id");
        Assumptions.assumeTrue(id != null, "Create response must contain id");

        String path = basePath + "/" + id;
        String updatedName = "Updated Title";
        String updateBody = "{\"name\":\"" + updatedName + "\",\"email\":\"" + email + "\",\"gender\":\"female\",\"status\":\"inactive\"}";

        Response putRes = given()
                .spec(baseSpec)
                .header(authHeader)
                .body(updateBody)
                .when()
                .put(path)
                .then()
                .statusCode(anyOf(equalTo(200), equalTo(204)))
                .extract()
                .response();

        if (putRes.getStatusCode() == 200 && putRes.getBody().asString() != null && !putRes.getBody().asString().isBlank()) {
            Map<String, Object> body = putRes.jsonPath().getMap("$");
            assertNotNull(body);
            assertEquals(updatedName, body.get("name"), "Response must contain updated name");
            assertEquals("inactive", body.get("status"), "Response must contain updated status");
        } else {
            Map<String, Object> getBody = given().spec(baseSpec).header(authHeader).when().get(path).then().extract().body().jsonPath().getMap("$");
            assertNotNull(getBody);
            assertEquals(updatedName, getBody.get("name"), "GET must return updated name");
            assertEquals("inactive", getBody.get("status"), "GET must return updated status");
        }
    }
}
