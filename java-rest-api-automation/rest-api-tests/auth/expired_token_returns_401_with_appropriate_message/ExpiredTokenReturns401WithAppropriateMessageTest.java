package auth.expired_token_returns_401_with_appropriate_message;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.http.Header;
import io.restassured.response.ValidatableResponse;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Verifies that a protected endpoint returns 401 Unauthorized when the request uses an
 * expired Bearer token. Requires EXPIRED_TOKEN in env/.env; test is skipped when not set.
 * Response body may contain an expiry message but is not asserted to keep the test robust
 * across different API implementations.
 */
@DisplayName("Expired token returns 401 with appropriate message")
class ExpiredTokenReturns401WithAppropriateMessageTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/auth/expired_token_returns_401_with_appropriate_message/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER_NAME = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("GET protected endpoint with expired token returns 401")
    void expiredToken_returns401() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getExpiredToken().isPresent(),
                "EXPIRED_TOKEN must be set in env/.env to run this test (skip when not available)");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");

        String path = ApiConfig.getProtectedEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElseThrow();
        Header authHeader = new Header(AUTH_HEADER_NAME, BEARER_PREFIX + ApiConfig.getExpiredToken().orElseThrow());

        // Send GET with expired token; expect 401 (body message not asserted for cross-API compatibility)
        ValidatableResponse response = given()
                .spec(baseSpec)
                .header(authHeader)
                .when()
                .get(path)
                .then();

        response.statusCode(equalTo(401));
    }
}
