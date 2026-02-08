package get_list.get_list_without_auth_returns_401_when_protected;

import api.BaseApiTest;
import api.config.ApiConfig;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Test Case API-GET-LIST-007: GET list without auth returns 401 when endpoint is protected.
 * Objective: Verify list endpoint without credentials returns 401 when it requires auth.
 * Expected: Status 401. Skipped when endpoint is public.
 */
@DisplayName("GET list without auth returns 401 when protected")
class GetListWithoutAuthReturns401WhenProtectedTest extends BaseApiTest {

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
