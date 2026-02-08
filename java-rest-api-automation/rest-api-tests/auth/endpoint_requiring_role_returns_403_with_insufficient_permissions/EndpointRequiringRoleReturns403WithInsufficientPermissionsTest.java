package auth.endpoint_requiring_role_returns_403_with_insufficient_permissions;

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
 * Verifies that an endpoint protected by role/scope returns 403 Forbidden when called with
 * a valid but low-privilege token (e.g. user token against an admin-only endpoint).
 * Requires USER_TOKEN and ADMIN_ENDPOINT (or PROTECTED_ENDPOINT) in config; skipped when
 * the API does not enforce role-based access control.
 */
@DisplayName("Endpoint requiring role returns 403 with insufficient permissions")
class EndpointRequiringRoleReturns403WithInsufficientPermissionsTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/auth/endpoint_requiring_role_returns_403_with_insufficient_permissions/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER_NAME = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("GET role-protected endpoint with user token returns 403")
    void endpointRequiringRole_withUserToken_returns403() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getUserToken().isPresent(),
                "USER_TOKEN (low-privilege token) must be set for this test");
        // Prefer admin endpoint; fallback to protected endpoint if admin is not configured
        String roleEndpoint = ApiConfig.getAdminEndpoint()
                .or(() -> ApiConfig.getProtectedEndpoint())
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElse(null);
        Assumptions.assumeTrue(roleEndpoint != null, "ADMIN_ENDPOINT or PROTECTED_ENDPOINT must be set");

        Header authHeader = new Header(AUTH_HEADER_NAME, BEARER_PREFIX + ApiConfig.getUserToken().orElseThrow());

        // Call role-protected endpoint with low-privilege token; expect 403 Forbidden
        ValidatableResponse response = given()
                .spec(baseSpec)
                .header(authHeader)
                .when()
                .get(roleEndpoint)
                .then();

        response.statusCode(equalTo(403));
    }
}
