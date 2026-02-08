package get_list.get_list_with_valid_auth_returns_200_and_data;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.http.Header;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Verifies that the list endpoint, when protected, returns 200 and a non-empty or valid
 * body when the request includes valid Bearer authentication. Requires BASE_URL,
 * PROTECTED_ENDPOINT and AUTH_TOKEN. Asserts status 200 and that the response body is present.
 */
@DisplayName("GET list with valid auth returns 200 and data")
class GetListWithValidAuthReturns200AndDataTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/get-list/get_list_with_valid_auth_returns_200_and_data/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";

    @Test
    @DisplayName("GET list with valid token returns 200 and collection")
    void getListWithValidAuth_returns200AndData() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT (list) must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN must be set for protected list");

        String path = ApiConfig.getProtectedEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());

        given()
                .spec(baseSpec)
                .header(authHeader)
                .when()
                .get(path)
                .then()
                .statusCode(equalTo(200))
                .body(notNullValue());
    }
}
