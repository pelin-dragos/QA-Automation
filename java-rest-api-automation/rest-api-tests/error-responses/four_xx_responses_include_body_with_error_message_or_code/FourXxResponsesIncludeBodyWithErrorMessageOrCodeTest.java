package error_responses.four_xx_responses_include_body_with_error_message_or_code;

import api.BaseApiTest;
import api.config.ApiConfig;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Verifies that 4xx client-error responses include a body with an error message or code
 * suitable for display or logging. Uses a non-existent resource ID to trigger 404 as a
 * representative 4xx; asserts status in 4xx range and that the body and a message/code
 * field are present (e.g. "message" for GoREST; adapt path if the API uses "error" or "code").
 */
@DisplayName("4xx responses include body with error message or code")
class FourXxResponsesIncludeBodyWithErrorMessageOrCodeTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/error-responses/four_xx_responses_include_body_with_error_message_or_code/TEST_CASE.md";

    /** Non-existent ID used to trigger 404 (a representative 4xx response). */
    private static final long NON_EXISTENT_ID = 999999999L;

    @Test
    @DisplayName("4xx response has body with message or code")
    void fourXxResponse_includesBodyWithMessageOrCode() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");

        String basePath = ApiConfig.getProtectedEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElseThrow();
        String path = basePath + "/" + NON_EXISTENT_ID;

        // GET by non-existent ID to trigger 4xx; expect body with error message or code
        given()
                .spec(baseSpec)
                .when()
                .get(path)
                .then()
                .statusCode(greaterThanOrEqualTo(400))
                .statusCode(lessThan(500))
                .body(notNullValue())
                .body("message", notNullValue());
    }
}
