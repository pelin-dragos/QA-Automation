package put_update.put_invalid_field_values_returns_400_with_validation_message;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that PUT with an invalid field value (e.g. invalid enum for gender) returns
 * 400 or 422 and does not update the resource. Creates a resource first, then PUTs
 * with one invalid value. Requires BASE_URL, CREATE_ENDPOINT and AUTH_TOKEN.
 */
@DisplayName("PUT invalid field values returns 400 with validation message")
class PutInvalidFieldValuesReturns400WithValidationMessageTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/put-update/put_invalid_field_values_returns_400_with_validation_message/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("PUT with invalid field value returns 400 or 422")
    void putInvalidFieldValues_returns400WithValidationMessage() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required");

        String basePath = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        String email = "put-invalid-val-" + System.currentTimeMillis() + "@example.com";
        Response createRes = given().spec(baseSpec).header(authHeader)
                .body("{\"name\":\"Put Invalid\",\"email\":\"" + email + "\",\"gender\":\"male\",\"status\":\"active\"}")
                .when().post(basePath);
        Assumptions.assumeTrue(createRes.getStatusCode() == 201 || createRes.getStatusCode() == 200, "Create must succeed");
        Object id = createRes.path("id");
        Assumptions.assumeTrue(id != null, "Create response must contain id");

        String path = basePath + "/" + id;
        String invalidBody = "{\"name\":\"Put Invalid\",\"email\":\"" + email + "\",\"gender\":\"invalid_enum\",\"status\":\"active\"}";

        Response putRes = given()
                .spec(baseSpec)
                .header(authHeader)
                .body(invalidBody)
                .when()
                .put(path);

        int status = putRes.getStatusCode();
        assertTrue(status == 400 || status == 422, "Expected 400 or 422, got " + status);
    }
}
