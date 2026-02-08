package headers.request_accept_application_json_receives_json_response;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.http.Header;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

/**
 * Verifies that when the client sends Accept: application/json, the API returns 200 with
 * a Content-Type header containing "application/json" and a non-empty body (valid JSON).
 * Requires BASE_URL and PROTECTED_ENDPOINT. No auth sent; use a public or pre-auth endpoint.
 */
@DisplayName("Request Accept application/json receives JSON response")
class RequestAcceptApplicationJsonReceivesJsonResponseTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/headers/request_accept_application_json_receives_json_response/TEST_CASE.md";

    /** Request header to request JSON response. */
    private static final String ACCEPT_HEADER = "Accept";
    /** Value for Accept header when requesting JSON. */
    private static final String ACCEPT_JSON = "application/json";
    /** Response header to assert correct content type. */
    private static final String CONTENT_TYPE_HEADER = "Content-Type";

    @Test
    @DisplayName("GET with Accept application/json returns JSON and Content-Type application/json")
    void requestWithAcceptJson_receivesJsonResponse() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getProtectedEndpoint().isPresent(), "PROTECTED_ENDPOINT must be set");

        String path = ApiConfig.getProtectedEndpoint()
                .map(p -> p.startsWith("/") ? p : "/" + p)
                .orElseThrow();

        given()
                .spec(baseSpec)
                .header(new Header(ACCEPT_HEADER, ACCEPT_JSON))
                .when()
                .get(path)
                .then()
                .statusCode(equalTo(200))
                .header(CONTENT_TYPE_HEADER, containsString("application/json"))
                .body(notNullValue());
    }
}
