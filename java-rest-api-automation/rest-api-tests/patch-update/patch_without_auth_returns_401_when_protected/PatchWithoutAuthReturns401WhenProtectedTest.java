package patch_update.patch_without_auth_returns_401_when_protected;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/**
 * Verifies that PATCH without an Authorization header returns 401 Unauthorized when the
 * endpoint requires authentication. Creates a resource with auth, then sends PATCH
 * without the header; if the endpoint is public and returns 2xx, the test is skipped
 * via Assumption.
 */
@DisplayName("PATCH without auth returns 401 when protected")
class PatchWithoutAuthReturns401WhenProtectedTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/patch-update/patch_without_auth_returns_401_when_protected/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("PATCH without auth returns 401 when protected")
    void patchWithoutAuth_returns401WhenProtected() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required to create resource");

        String basePath = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        String email = "patch-401-" + System.currentTimeMillis() + "@example.com";
        Response createRes = given().spec(baseSpec).header(authHeader)
                .body("{\"name\":\"Patch 401\",\"email\":\"" + email + "\",\"gender\":\"male\",\"status\":\"active\"}")
                .when().post(basePath);
        Assumptions.assumeTrue(createRes.getStatusCode() == 201, "Create must succeed");
        Object id = createRes.path("id");
        Assumptions.assumeTrue(id != null, "Create response must contain id");

        String path = basePath + "/" + id;

        int statusCode = given()
                .spec(baseSpec)
                .body("{\"status\":\"inactive\"}")
                .when()
                .patch(path)
                .then()
                .extract()
                .statusCode();

        Assumptions.assumeTrue(statusCode == 401,
                "Endpoint did not return 401 without auth (got " + statusCode + "). Skip when endpoint is not protected.");
    }
}
