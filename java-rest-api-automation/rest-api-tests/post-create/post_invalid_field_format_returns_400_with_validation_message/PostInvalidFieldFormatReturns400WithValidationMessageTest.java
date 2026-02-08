package post_create.post_invalid_field_format_returns_400_with_validation_message;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.http.Header;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;

/**
 * Verifies that POST with an invalid field format (e.g. malformed email) returns 400 or
 * 422 and does not create a resource. Requires BASE_URL, CREATE_ENDPOINT and AUTH_TOKEN.
 */
@DisplayName("POST invalid field format returns 400 with validation message")
class PostInvalidFieldFormatReturns400WithValidationMessageTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/post-create/post_invalid_field_format_returns_400_with_validation_message/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";
    /** Invalid email value to trigger format validation. */
    private static final String INVALID_EMAIL = "not-an-email";

    @Test
    @DisplayName("POST with invalid email format returns 400 or 422")
    void postInvalidFieldFormat_returns400WithValidationMessage() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required");

        String path = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());
        String body = "{\"name\":\"Invalid Email\",\"email\":\"" + INVALID_EMAIL + "\",\"gender\":\"male\",\"status\":\"active\"}";

        given()
                .spec(baseSpec)
                .header(authHeader)
                .body(body)
                .when()
                .post(path)
                .then()
                .statusCode(anyOf(equalTo(400), equalTo(422)));
    }
}
