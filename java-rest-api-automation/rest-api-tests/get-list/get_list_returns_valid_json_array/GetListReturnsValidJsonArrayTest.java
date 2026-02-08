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
 * Test Case API-GET-LIST-002: GET list returns valid JSON array.
 * Objective: Verify list endpoint returns valid JSON that can be parsed (array or object with collection).
 * Expected: Body is valid JSON; root is array or response contains collection array.
 */
@DisplayName("GET list returns valid JSON array")
class GetListReturnsValidJsonArrayTest extends BaseApiTest {

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
