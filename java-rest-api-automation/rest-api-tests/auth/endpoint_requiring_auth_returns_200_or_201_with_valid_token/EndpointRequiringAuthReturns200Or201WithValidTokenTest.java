package auth.endpoint_requiring_auth_returns_200_or_201_with_valid_token;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.http.Header;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Verifies that a protected endpoint accepts a valid Bearer token and returns a success response
 * with a non-empty body. Skips when BASE_URL, AUTH_TOKEN or protected endpoint are not configured.
 */
@DisplayName("Endpoint requiring auth returns 200 or 201 with valid token")
class EndpointRequiringAuthReturns200Or201WithValidTokenTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/auth/endpoint_requiring_auth_returns_200_or_201_with_valid_token/TEST_CASE.md";

    /** HTTP header name for authorization (Bearer token). */
    private static final String AUTH_HEADER_NAME = "Authorization";
    /** Prefix required by most APIs for Bearer token in the Authorization header. */
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("GET protected endpoint with valid token returns success and data")
    void endpointRequiringAuth_withValidToken_returns200Or201AndData() {
        // Precondition: skip if required config (base URL, token, protected path) is missing
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(),
                "BASE_URL must be set (e.g. from .env or environment)");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(),
                "AUTH_TOKEN must be set for this test");

        String path = ApiConfig.getProtectedEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElse("/api/protected/resource");
        Header authHeader = new Header(AUTH_HEADER_NAME, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        // Send GET to protected endpoint with valid Bearer token
        ValidatableResponse response = given()
                .spec(baseSpec)
                .header(authHeader)
                .when()
                .get(path)
                .then();

        // Expect 200 or 201 and a non-empty response body (resource/list data)
        response.statusCode(anyOf(equalTo(200), equalTo(201)));
        response.body(notNullValue());
    }
}
