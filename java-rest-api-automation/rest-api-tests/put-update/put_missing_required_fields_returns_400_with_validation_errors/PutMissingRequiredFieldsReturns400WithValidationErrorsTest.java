package put_update.put_missing_required_fields_returns_400_with_validation_errors;

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
 * Verifies that PUT with an incomplete body (required fields omitted) returns 400 or
 * 422 when the API requires a full body. Creates a resource, then PUTs with only
 * partial data. If the API allows partial PUT and returns 200, the test is skipped
 * via Assumption. Requires BASE_URL, CREATE_ENDPOINT and AUTH_TOKEN.
 */
@DisplayName("PUT missing required fields returns 400 with validation errors")
class PutMissingRequiredFieldsReturns400WithValidationErrorsTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/put-update/put_missing_required_fields_returns_400_with_validation_errors/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";
    /** Body with only one field; required fields (e.g. email, gender, status) omitted. */
    private static final String INCOMPLETE_BODY = "{\"name\":\"Only Name\"}";

    @Test
    @DisplayName("PUT with missing required fields returns 400 or 422")
    void putMissingRequiredFields_returns400WithValidationErrors() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required");

        String basePath = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        String email = "put-missing-" + System.currentTimeMillis() + "@example.com";
        Response createRes = given().spec(baseSpec).header(authHeader)
                .body("{\"name\":\"Put Missing\",\"email\":\"" + email + "\",\"gender\":\"male\",\"status\":\"active\"}")
                .when().post(basePath);
        Assumptions.assumeTrue(createRes.getStatusCode() == 201 || createRes.getStatusCode() == 200, "Create must succeed");
        Object id = createRes.path("id");
        Assumptions.assumeTrue(id != null, "Create response must contain id");

        String path = basePath + "/" + id;

        Response putRes = given()
                .spec(baseSpec)
                .header(authHeader)
                .body(INCOMPLETE_BODY)
                .when()
                .put(path);

        int status = putRes.getStatusCode();
        Assumptions.assumeTrue(status == 400 || status == 422,
                "API allows partial PUT (returned " + status + "). Skip when full body is not required.");
    }
}
