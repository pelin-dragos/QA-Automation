package get_list.get_list_when_empty_returns_200_and_empty_array;

import api.BaseApiTest;
import api.config.ApiConfig;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Test Case API-GET-LIST-005: GET list when empty returns 200 and empty array.
 * Objective: Verify when list is empty (e.g. filter with no matches), API returns 200 and empty collection.
 * Expected: Status 200; body is valid empty array or collection.
 */
@DisplayName("GET list when empty returns 200 and empty array")
class GetListWhenEmptyReturns200AndEmptyArrayTest extends BaseApiTest {

    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/get-list/get_list_when_empty_returns_200_and_empty_array/TEST_CASE.md";

    @Test
    @DisplayName("GET list with filter yielding no results returns 200 and empty array")
    void getListWhenEmpty_returns200AndEmptyArray() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT (list) must be set");

        String path = ApiConfig.getProtectedEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElseThrow();

        // Use filter that likely returns no results (e.g. status value that does not exist)
        List<?> list = given()
                .spec(baseSpec)
                .queryParam("status", "NonExistentStatusForEmptyTest")
                .when()
                .get(path)
                .then()
                .statusCode(equalTo(200))
                .extract()
                .body()
                .jsonPath()
                .getList("$");

        assertNotNull(list);
        assertTrue(list.isEmpty(), "Response must be empty array when filter matches no items");
    }
}
