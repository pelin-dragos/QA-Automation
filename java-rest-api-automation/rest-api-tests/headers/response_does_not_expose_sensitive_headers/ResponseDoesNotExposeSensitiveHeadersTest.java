package headers.response_does_not_expose_sensitive_headers;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that the API response does not expose security-sensitive headers (e.g.
 * X-Powered-By that leaks framework info). Asserts each header in the list is absent
 * or empty. Only X-Powered-By is checked by default; Server is often set by CDN/proxy
 * and may be acceptable per policy. Extend SENSITIVE_HEADER_NAMES if needed.
 */
@DisplayName("Response does not expose sensitive headers")
class ResponseDoesNotExposeSensitiveHeadersTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/headers/response_does_not_expose_sensitive_headers/TEST_CASE.md";

    /** Headers that must be absent or empty (e.g. X-Powered-By to avoid framework leak). */
    private static final List<String> SENSITIVE_HEADER_NAMES = Arrays.asList(
            "X-Powered-By"
    );

    @Test
    @DisplayName("Response does not include sensitive headers")
    void response_doesNotExposeSensitiveHeaders() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");

        String path = ApiConfig.getProtectedEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElseThrow();

        Response response = given()
                .spec(baseSpec)
                .when()
                .get(path)
                .then()
                .statusCode(equalTo(200))
                .extract()
                .response();

        for (String headerName : SENSITIVE_HEADER_NAMES) {
            String value = response.getHeader(headerName);
            assertTrue(value == null || value.isBlank(),
                    "Sensitive header '" + headerName + "' should be absent or empty, got: " + value);
        }
    }
}
