package patch_update.patch_empty_body_returns_400_or_200_per_contract;

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
 * Verifies that PATCH with an empty body {} returns either 400 (validation: nothing to
 * update) or 200 (no-op accepted per contract), and never 500. Creates a resource first,
 * then PATCHes with {}. Requires BASE_URL, CREATE_ENDPOINT and AUTH_TOKEN.
 */
@DisplayName("PATCH empty body returns 400 or 200 per contract")
class PatchEmptyBodyReturns400Or200PerContractTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/patch-update/patch_empty_body_returns_400_or_200_per_contract/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";
    /** Empty JSON object; API may return 400 or 200. */
    private static final String EMPTY_BODY = "{}";

    @Test
    @DisplayName("PATCH with empty body returns 400 or 200")
    void patchEmptyBody_returns400Or200PerContract() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required");

        String basePath = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        String email = "patch-empty-" + System.currentTimeMillis() + "@example.com";
        Response createRes = given().spec(baseSpec).header(authHeader)
                .body("{\"name\":\"Patch Empty\",\"email\":\"" + email + "\",\"gender\":\"male\",\"status\":\"active\"}")
                .when().post(basePath);
        Assumptions.assumeTrue(createRes.getStatusCode() == 201, "Create must succeed");
        Object id = createRes.path("id");
        Assumptions.assumeTrue(id != null, "Create response must contain id");

        String path = basePath + "/" + id;

        int status = given()
                .spec(baseSpec)
                .header(authHeader)
                .body(EMPTY_BODY)
                .when()
                .patch(path)
                .then()
                .extract()
                .statusCode();

        assertTrue(status == 400 || status == 200, "Expected 400 or 200 for empty PATCH body, got " + status);
    }
}
