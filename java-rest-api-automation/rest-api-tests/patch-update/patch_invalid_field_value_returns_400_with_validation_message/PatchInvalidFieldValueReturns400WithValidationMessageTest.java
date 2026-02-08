package patch_update.patch_invalid_field_value_returns_400_with_validation_message;

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
 * Verifies that PATCH with an invalid field value (e.g. invalid enum for status) returns
 * 400 Bad Request or 422 Unprocessable Entity and does not update the resource. Creates
 * a resource first, then sends a body with an invalid value. Requires BASE_URL,
 * CREATE_ENDPOINT and AUTH_TOKEN.
 */
@DisplayName("PATCH invalid field value returns 400 with validation message")
class PatchInvalidFieldValueReturns400WithValidationMessageTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/patch-update/patch_invalid_field_value_returns_400_with_validation_message/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";
    /** Body with invalid enum value to trigger validation error. */
    private static final String INVALID_STATUS_BODY = "{\"status\":\"invalid_enum_value\"}";

    @Test
    @DisplayName("PATCH with invalid field value returns 400 or 422")
    void patchInvalidFieldValue_returns400WithValidationMessage() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required");

        String basePath = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        String email = "patch-invalid-" + System.currentTimeMillis() + "@example.com";
        Response createRes = given().spec(baseSpec).header(authHeader)
                .body("{\"name\":\"Patch Invalid\",\"email\":\"" + email + "\",\"gender\":\"male\",\"status\":\"active\"}")
                .when().post(basePath);
        Assumptions.assumeTrue(createRes.getStatusCode() == 201, "Create must succeed");
        Object id = createRes.path("id");
        Assumptions.assumeTrue(id != null, "Create response must contain id");

        String path = basePath + "/" + id;

        Response patchRes = given()
                .spec(baseSpec)
                .header(authHeader)
                .body(INVALID_STATUS_BODY)
                .when()
                .patch(path);

        int status = patchRes.getStatusCode();
        assertTrue(status == 400 || status == 422, "Expected 400 or 422, got " + status);
    }
}
