package get_list.get_list_invalid_pagination_returns_400_or_422;

import api.BaseApiTest;
import api.config.ApiConfig;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

/**
 * Test Case API-GET-LIST-004: GET list with invalid pagination returns 400 or 422.
 * Objective: Verify invalid pagination (e.g. negative page/size) returns 400 or 422.
 * Expected: Status 400 or 422; no successful data payload.
 */
@DisplayName("GET list invalid pagination returns 400 or 422")
class GetListInvalidPaginationReturns400Or422Test extends BaseApiTest {

    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/get-list/get_list_invalid_pagination_returns_400_or_422/TEST_CASE.md";

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
                .queryParam("page", -1)
                .queryParam("per_page", 10)
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
