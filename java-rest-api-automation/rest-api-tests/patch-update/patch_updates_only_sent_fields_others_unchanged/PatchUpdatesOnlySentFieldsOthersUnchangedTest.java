package patch_update.patch_updates_only_sent_fields_others_unchanged;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Verifies that PATCH updates only the fields sent in the body; other fields (e.g. name,
 * email) remain unchanged. Creates a resource, PATCHes only status, then GETs and
 * asserts status is updated and name/email are unchanged. Requires BASE_URL,
 * CREATE_ENDPOINT and AUTH_TOKEN.
 */
@DisplayName("PATCH updates only sent fields, others unchanged")
class PatchUpdatesOnlySentFieldsOthersUnchangedTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/patch-update/patch_updates_only_sent_fields_others_unchanged/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("PATCH only status leaves name and email unchanged")
    void patch_updatesOnlySentFieldsOthersUnchanged() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required");

        String basePath = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        String name = "Patch Only Fields";
        String email = "patch-only-" + System.currentTimeMillis() + "@example.com";
        Response createRes = given().spec(baseSpec).header(authHeader)
                .body("{\"name\":\"" + name + "\",\"email\":\"" + email + "\",\"gender\":\"male\",\"status\":\"active\"}")
                .when().post(basePath);
        Assumptions.assumeTrue(createRes.getStatusCode() == 201, "Create must succeed");
        Object id = createRes.path("id");
        Assumptions.assumeTrue(id != null, "Create response must contain id");

        String path = basePath + "/" + id;

        given()
                .spec(baseSpec)
                .header(authHeader)
                .body("{\"status\":\"inactive\"}")
                .when()
                .patch(path)
                .then()
                .statusCode(equalTo(200));

        Map<String, Object> after = given().spec(baseSpec).header(authHeader).when().get(path).then().extract().body().jsonPath().getMap("$");
        assertNotNull(after);
        assertEquals("inactive", after.get("status"));
        assertEquals(name, after.get("name"));
        assertEquals(email, after.get("email"));
    }
}
