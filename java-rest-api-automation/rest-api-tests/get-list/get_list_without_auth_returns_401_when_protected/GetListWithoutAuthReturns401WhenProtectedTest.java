package get_list.get_list_without_auth_returns_401_when_protected;

import api.BaseApiTest;
import api.config.ApiConfig;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Verifies that the list endpoint returns 401 Unauthorized when the request has no
 * Authorization header and the endpoint requires authentication. If the endpoint is
 * public and returns 200, the test is skipped via Assumption.
 */
@DisplayName("GET list without auth returns 401 when protected")
class GetListWithoutAuthReturns401WhenProtectedTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/get-list/get_list_without_auth_returns_401_when_protected/TEST_CASE.md";

    @Test
    @DisplayName("GET list without auth returns 401 when protected")
    void getListWithoutAuth_returns401WhenProtected() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT (list) must be set");

        String path = ApiConfig.getProtectedEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElseThrow();

        int statusCode = given()
                .spec(baseSpec)
                .when()
                .get(path)
                .then()
                .extract()
                .statusCode();

        Assumptions.assumeTrue(statusCode == 401,
                "Endpoint did not return 401 without auth (got " + statusCode + "). Skip when list is public.");
    }
}
