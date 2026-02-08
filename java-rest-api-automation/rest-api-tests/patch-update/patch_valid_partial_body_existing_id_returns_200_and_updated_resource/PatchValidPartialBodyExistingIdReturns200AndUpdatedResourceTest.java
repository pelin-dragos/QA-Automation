package patch_update.patch_valid_partial_body_existing_id_returns_200_and_updated_resource;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Verifies that PATCH with a valid partial body and an existing resource ID returns 200
 * and that the patched field is updated (e.g. status). Creates a resource via POST,
 * then PATCHes one field and asserts GET returns the new value. Requires BASE_URL,
 * CREATE_ENDPOINT and AUTH_TOKEN.
 */
@DisplayName("PATCH valid partial body existing ID returns 200 and updated resource")
class PatchValidPartialBodyExistingIdReturns200AndUpdatedResourceTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/patch-update/patch_valid_partial_body_existing_id_returns_200_and_updated_resource/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";
    /** Partial update body (single field). */
    private static final String PARTIAL_BODY = "{\"status\":\"inactive\"}";

    @Test
    @DisplayName("PATCH with valid partial body returns 200 and updated resource")
    void patchValidPartialBodyExistingId_returns200AndUpdatedResource() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required");

        String basePath = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        String email = "patch-valid-" + System.currentTimeMillis() + "@example.com";
        Response createRes = given().spec(baseSpec).header(authHeader)
                .body("{\"name\":\"Patch Test\",\"email\":\"" + email + "\",\"gender\":\"male\",\"status\":\"active\"}")
                .when().post(basePath);
        Assumptions.assumeTrue(createRes.getStatusCode() == 201, "Create must succeed");
        Object id = createRes.path("id");
        Assumptions.assumeTrue(id != null, "Create response must contain id");

        String path = basePath + "/" + id;

        given()
                .spec(baseSpec)
                .header(authHeader)
                .body(PARTIAL_BODY)
                .when()
                .patch(path)
                .then()
                .statusCode(equalTo(200));

        Map<String, Object> updated = given().spec(baseSpec).header(authHeader).when().get(path).then().extract().body().jsonPath().getMap("$");
        assertNotNull(updated);
        assertEquals("inactive", updated.get("status"), "Patched field must reflect new value");
    }
}
