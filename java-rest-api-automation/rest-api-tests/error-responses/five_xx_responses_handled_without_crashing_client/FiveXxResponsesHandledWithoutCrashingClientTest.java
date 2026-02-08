package error_responses.five_xx_responses_handled_without_crashing_client;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that when the API returns a 5xx server error, the client handles the response
 * without throwing an unhandled exception: status code is in the 5xx range and the response
 * body can be read (or is empty). Requires ERROR_5XX_ENDPOINT in config; skipped when no
 * 5xx can be triggered (e.g. no fault-injection endpoint).
 */
@DisplayName("5xx responses handled without crashing client")
class FiveXxResponsesHandledWithoutCrashingClientTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/error-responses/five_xx_responses_handled_without_crashing_client/TEST_CASE.md";

    @Test
    @DisplayName("Request to 5xx endpoint returns 5xx and client does not crash")
    void fiveXxResponse_handledWithoutCrashing() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getError5xxEndpoint().isPresent(),
                "ERROR_5XX_ENDPOINT must be set to trigger 5xx (e.g. mock or fault injection). Skip when 5xx cannot be triggered.");

        String path = ApiConfig.getError5xxEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElseThrow();

        // Call endpoint that returns 5xx; client must not throw (e.g. on status or body read)
        Response response = assertDoesNotThrow(() -> given()
                .spec(baseSpec)
                .when()
                .get(path)
                .andReturn());

        int statusCode = response.getStatusCode();
        assertTrue(statusCode >= 500 && statusCode < 600,
                "Expected 5xx, got " + statusCode);
        // Ensure body can be read without throwing (may be empty or error payload)
        assertDoesNotThrow(() -> response.getBody().asString());
    }
}
