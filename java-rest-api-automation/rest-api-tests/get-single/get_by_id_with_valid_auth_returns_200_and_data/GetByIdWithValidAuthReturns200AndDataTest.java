package get_single.get_by_id_with_valid_auth_returns_200_and_data;

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
import static org.hamcrest.Matchers.notNullValue;

/**
 * Verifies that GET by resource ID with valid Bearer authentication returns 200 and a
 * body containing the resource (at least an "id" field). Obtains a valid ID from the list
 * endpoint using the same token. Requires BASE_URL, PROTECTED_ENDPOINT and AUTH_TOKEN.
 */
@DisplayName("GET by ID with valid auth returns 200 and data")
class GetByIdWithValidAuthReturns200AndDataTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/get-single/get_by_id_with_valid_auth_returns_200_and_data/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("GET resource by ID with valid auth returns 200 and data")
    void getByIdWithValidAuth_returns200AndData() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN must be set");

        String basePath = ApiConfig.getProtectedEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        // Obtain a valid resource ID from the list (with auth if list is protected)
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
        Assumptions.assumeTrue(list != null && !list.isEmpty(), "List must have at least one resource");
        Object id = list.get(0).get("id");
        Assumptions.assumeTrue(id != null, "First resource must have id");

        String path = basePath + "/" + id;

        given()
                .spec(baseSpec)
                .header(authHeader)
                .when()
                .get(path)
                .then()
                .statusCode(equalTo(200))
                .body(notNullValue())
                .body("id", notNullValue());
    }
}
