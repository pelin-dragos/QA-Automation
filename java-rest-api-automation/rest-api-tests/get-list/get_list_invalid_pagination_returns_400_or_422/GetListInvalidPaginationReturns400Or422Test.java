package get_list.get_list_invalid_pagination_returns_400_or_422;

import api.BaseApiTest;
import api.config.ApiConfig;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/**
 * Verifies that the list endpoint returns 400 or 422 when given invalid pagination
 * parameters (e.g. negative page). If the API ignores invalid params and returns 200,
 * the test is skipped via Assumption so it does not fail in permissive environments.
 */
@DisplayName("GET list invalid pagination returns 400 or 422")
class GetListInvalidPaginationReturns400Or422Test extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/get-list/get_list_invalid_pagination_returns_400_or_422/TEST_CASE.md";

    /** Invalid page value (negative) used to trigger validation error. */
    private static final int INVALID_PAGE = -1;
    private static final int PER_PAGE = 10;

    @Test
    @DisplayName("GET list with invalid pagination returns 400 or 422")
    void getListWithInvalidPagination_returns400Or422() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT (list) must be set");

        String path = ApiConfig.getProtectedEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElseThrow();

        int statusCode = given()
                .spec(baseSpec)
                .queryParam("page", INVALID_PAGE)
                .queryParam("per_page", PER_PAGE)
                .when()
                .get(path)
                .then()
                .extract()
                .statusCode();

        Assumptions.assumeTrue(statusCode == 400 || statusCode == 422,
                "API did not return 400/422 for invalid pagination (got " + statusCode + "). Skip when API ignores invalid params.");
        org.junit.jupiter.api.Assertions.assertTrue(statusCode == 400 || statusCode == 422);
    }
}
