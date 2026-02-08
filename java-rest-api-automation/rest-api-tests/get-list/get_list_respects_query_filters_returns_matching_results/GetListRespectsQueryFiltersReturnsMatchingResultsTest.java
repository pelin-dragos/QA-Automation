package get_list.get_list_respects_query_filters_returns_matching_results;

import api.BaseApiTest;
import api.config.ApiConfig;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test Case API-GET-LIST-006: GET list respects query filters and returns matching results.
 * Objective: Verify filtered request returns only items matching the filter (e.g. status=active).
 * Expected: Status 200; every item satisfies the filter.
 */
@DisplayName("GET list respects query filters and returns matching results")
class GetListRespectsQueryFiltersReturnsMatchingResultsTest extends BaseApiTest {

    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/get-list/get_list_respects_query_filters_returns_matching_results/TEST_CASE.md";

    private static final String STATUS_FILTER = "active";

    @Test
    @DisplayName("GET list with filter returns only matching items")
    void getListWithFilter_returnsMatchingResults() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT (list) must be set");

        String path = ApiConfig.getProtectedEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElseThrow();

        List<Map<String, Object>> list = given()
                .spec(baseSpec)
                .queryParam("status", STATUS_FILTER)
                .when()
                .get(path)
                .then()
                .statusCode(equalTo(200))
                .extract()
                .body()
                .jsonPath()
                .getList("$");

        assertNotNull(list);
        for (Map<String, Object> item : list) {
            Object status = item != null ? item.get("status") : null;
            assertTrue(STATUS_FILTER.equals(status),
                    "Each item must have status=" + STATUS_FILTER + ", got: " + status);
        }
    }
}
