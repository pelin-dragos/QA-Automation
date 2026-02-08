package get_list.get_list_valid_pagination_returns_correct_page_and_size;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that the list endpoint accepts valid pagination parameters (page, per_page) and
 * returns 200 with a collection whose size does not exceed the requested per_page. Assumes
 * response root is a JSON array; adapt jsonPath if the API wraps the list (e.g. in "data").
 */
@DisplayName("GET list valid pagination returns correct page and size")
class GetListValidPaginationReturnsCorrectPageAndSizeTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/get-list/get_list_valid_pagination_returns_correct_page_and_size/TEST_CASE.md";

    /** Page number for the request (first page). */
    private static final int PAGE = 1;
    /** Requested page size; response must contain at most this many items. */
    private static final int PER_PAGE = 10;

    @Test
    @DisplayName("GET list with page and per_page returns 200 and at most per_page items")
    void getListWithValidPagination_returnsCorrectPageAndSize() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT (list) must be set");

        String path = ApiConfig.getProtectedEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElseThrow();

        Response response = given()
                .spec(baseSpec)
                .queryParam("page", PAGE)
                .queryParam("per_page", PER_PAGE)
                .when()
                .get(path)
                .then()
                .statusCode(equalTo(200))
                .extract()
                .response();

        List<?> list = response.jsonPath().getList("$");
        assertNotNull(list, "Response must be JSON array");
        assertTrue(list.size() <= PER_PAGE,
                "Returned size " + list.size() + " must be <= requested per_page " + PER_PAGE);
    }
}
