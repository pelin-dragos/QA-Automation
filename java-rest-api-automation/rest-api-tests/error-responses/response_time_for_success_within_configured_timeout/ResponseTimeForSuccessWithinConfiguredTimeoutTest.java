package error_responses.response_time_for_success_within_configured_timeout;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that a successful request to the configured success endpoint completes within
 * a maximum response time. Uses RESPONSE_TIMEOUT_MS from config if set; otherwise falls
 * back to a default (10 seconds). Asserts status 200 and that elapsed time does not
 * exceed the threshold.
 */
@DisplayName("Response time for success within configured timeout")
class ResponseTimeForSuccessWithinConfiguredTimeoutTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/error-responses/response_time_for_success_within_configured_timeout/TEST_CASE.md";

    /** Default max response time in ms when RESPONSE_TIMEOUT_MS is not set. */
    private static final long DEFAULT_TIMEOUT_MS = 10000L;

    @Test
    @DisplayName("Success endpoint responds within configured timeout")
    void successResponse_withinConfiguredTimeout() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set (success endpoint)");

        long timeoutMs = ApiConfig.getResponseTimeoutMs().orElse(DEFAULT_TIMEOUT_MS);
        String path = ApiConfig.getProtectedEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElseThrow();

        // GET success endpoint and capture response (including elapsed time)
        Response response = given()
                .spec(baseSpec)
                .when()
                .get(path)
                .then()
                .statusCode(equalTo(200))
                .extract()
                .response();

        long responseTimeMs = response.getTime();
        assertTrue(responseTimeMs <= timeoutMs,
                "Response time " + responseTimeMs + " ms exceeded threshold " + timeoutMs + " ms");
    }
}
