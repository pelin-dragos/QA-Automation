package get_list.get_list_returns_200;

import api.BaseApiTest;
import api.config.ApiConfig;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Verifies that a GET request to the list (collection) endpoint returns HTTP 200 OK.
 * Does not send auth; applicable when the list endpoint is public or when the API
 * returns 200 before enforcing auth. Requires BASE_URL and PROTECTED_ENDPOINT.
 */
@DisplayName("GET list returns 200")
class GetListReturns200Test extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/get-list/get_list_returns_200/TEST_CASE.md";

    @Test
    @DisplayName("GET list endpoint returns 200")
    void getList_returns200() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT (list) must be set");

        String path = ApiConfig.getProtectedEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElseThrow();

        given()
                .spec(baseSpec)
                .when()
                .get(path)
                .then()
                .statusCode(equalTo(200));
    }
}
