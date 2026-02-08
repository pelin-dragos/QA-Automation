package get_list.get_list_returns_valid_json_array;

import api.BaseApiTest;
import api.config.ApiConfig;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Verifies that the list endpoint returns a response body that is valid JSON and can be
 * parsed as a list (root array or extractable collection). Asserts 200 and that the
 * extracted root is a non-null list. Assumes API returns a root-level array; adapt
 * jsonPath if the API wraps the list in an object (e.g. "data").
 */
@DisplayName("GET list returns valid JSON array")
class GetListReturnsValidJsonArrayTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/get-list/get_list_returns_valid_json_array/TEST_CASE.md";

    @Test
    @DisplayName("GET list returns parseable JSON array")
    void getList_returnsValidJsonArray() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT (list) must be set");

        String path = ApiConfig.getProtectedEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElseThrow();

        List<?> list = given()
                .spec(baseSpec)
                .when()
                .get(path)
                .then()
                .statusCode(equalTo(200))
                .extract()
                .body()
                .jsonPath()
                .getList("$");

        org.junit.jupiter.api.Assertions.assertNotNull(list, "Response body must be a JSON array");
        org.junit.jupiter.api.Assertions.assertTrue(list instanceof List, "Root must be array or extractable as list");
    }
}
