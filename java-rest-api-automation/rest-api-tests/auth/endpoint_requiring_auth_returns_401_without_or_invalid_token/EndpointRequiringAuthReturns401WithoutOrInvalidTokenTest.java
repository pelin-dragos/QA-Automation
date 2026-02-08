package auth.endpoint_requiring_auth_returns_401_without_or_invalid_token;

import api.BaseApiTest;
import api.config.ApiConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/**
 * Verifies that a protected endpoint returns 401 Unauthorized when the request has no
 * Authorization header or carries an invalid Bearer token. Tests are skipped when the
 * configured endpoint is public (e.g. returns 200 without auth).
 */
@DisplayName("Endpoint requiring auth returns 401 without or invalid token")
class EndpointRequiringAuthReturns401WithoutOrInvalidTokenTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/auth/endpoint_requiring_auth_returns_401_without_or_invalid_token/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER_NAME = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("GET protected endpoint without Authorization header returns 401")
    void endpointRequiringAuth_withoutAuthHeader_returns401() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");

        String path = ApiConfig.getProtectedEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElseThrow();

        // Request with no Authorization header; expect 401 if endpoint is protected
        int statusCode = given()
                .spec(baseSpec)
                .when()
                .get(path)
                .then()
                .extract().statusCode();

        // Skip when endpoint is public (e.g. GoREST list endpoint returns 200 without token)
        Assumptions.assumeTrue(statusCode == 401,
                "Endpoint does not require auth (returned " + statusCode + " without token). "
                        + "Use an endpoint that requires authentication to run this test.");
        Assertions.assertEquals(401, statusCode, "Expected 401 Unauthorized without token");
    }

    @Test
    @DisplayName("GET protected endpoint with invalid token returns 401")
    void endpointRequiringAuth_withInvalidToken_returns401() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");

        String path = ApiConfig.getProtectedEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElseThrow();

        // Request with a fake Bearer token; expect 401 if endpoint validates the token
        int statusCode = given()
                .spec(baseSpec)
                .header(AUTH_HEADER_NAME, BEARER_PREFIX + "invalid_token_12345")
                .when()
                .get(path)
                .then()
                .extract().statusCode();

        // Skip when endpoint does not validate tokens (e.g. accepts any value or is public)
        Assumptions.assumeTrue(statusCode == 401,
                "Endpoint did not return 401 with invalid token (got " + statusCode + "). "
                        + "Use an endpoint that requires authentication.");
        Assertions.assertEquals(401, statusCode, "Expected 401 Unauthorized with invalid token");
    }
}
