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
 * Verifies that when the list has no matching items (e.g. filter with no matches), the API
 * returns 200 and an empty collection rather than an error. Uses a filter value that is
 * unlikely to match any resource (e.g. non-existent status). Assumes response root is a
 * JSON array; adapt jsonPath if the API wraps the list.
 */
@DisplayName("GET list when empty returns 200 and empty array")
class GetListWhenEmptyReturns200AndEmptyArrayTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/get-list/get_list_when_empty_returns_200_and_empty_array/TEST_CASE.md";

    /** Filter value chosen so that no resource matches (to trigger empty result). */
    private static final String NON_EXISTENT_STATUS = "NonExistentStatusForEmptyTest";

    @Test
    @DisplayName("GET list with filter yielding no results returns 200 and empty array")
    void getListWhenEmpty_returns200AndEmptyArray() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT (list) must be set");

        String path = ApiConfig.getProtectedEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElseThrow();

        List<?> list = given()
                .spec(baseSpec)
                .queryParam("status", NON_EXISTENT_STATUS)
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
