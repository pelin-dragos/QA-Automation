package get_single.get_by_nonexistent_id_returns_404;

import api.BaseApiTest;
import api.config.ApiConfig;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

/**
 * Verifies that GET by resource ID with a non-existent ID returns 404 Not Found. Uses a
 * constant high ID that is assumed not to exist in the target environment. Request is sent
 * without auth; if the endpoint requires auth it may return 401 before 404.
 */
@DisplayName("GET by non-existent ID returns 404")
class GetByNonexistentIdReturns404Test extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/get-single/get_by_nonexistent_id_returns_404/TEST_CASE.md";

    /** ID assumed not to exist so that the API returns 404. */
    private static final long NON_EXISTENT_ID = 999999999L;

    @Test
    @DisplayName("GET resource by non-existent ID returns 404")
    void getByNonexistentId_returns404() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");

        String basePath = ApiConfig.getProtectedEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        String path = basePath + "/" + NON_EXISTENT_ID;

        given()
                .spec(baseSpec)
                .when()
                .get(path)
                .then()
                .statusCode(equalTo(404));
    }
}
