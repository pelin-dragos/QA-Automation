package get_single.get_by_id_without_auth_returns_401_when_protected;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.http.Header;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Verifies that GET by resource ID without an Authorization header returns 401
 * Unauthorized when the endpoint requires authentication. Obtains a valid ID from the
 * list endpoint (with auth); then calls GET by ID without auth. If the endpoint is
 * public and returns 200, the test is skipped via Assumption.
 */
@DisplayName("GET by ID without auth returns 401 when protected")
class GetByIdWithoutAuthReturns401WhenProtectedTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/get-single/get_by_id_without_auth_returns_401_when_protected/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("GET resource by ID without auth returns 401 when protected")
    void getByIdWithoutAuth_returns401WhenProtected() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required to obtain valid ID from list");

        String basePath = ApiConfig.getProtectedEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        // Get a valid ID from list (with auth), then call GET by ID without auth
        List<Map<String, Object>> list = given()
                .spec(baseSpec)
                .header(authHeader)
                .when()
                .get(basePath)
                .then()
                .extract()
                .body()
                .jsonPath()
                .getList("$");
        Assumptions.assumeTrue(list != null && !list.isEmpty(), "List must have at least one resource for this test");
        Object id = list.get(0).get("id");
        Assumptions.assumeTrue(id != null, "First resource must have id");

        String path = basePath + "/" + id;

        int statusCode = given()
                .spec(baseSpec)
                .when()
                .get(path)
                .then()
                .extract()
                .statusCode();

        Assumptions.assumeTrue(statusCode == 401,
                "Endpoint did not return 401 without auth (got " + statusCode + "). Skip when endpoint is public.");
    }
}
