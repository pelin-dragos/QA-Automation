package post_create.post_valid_body_returns_201_and_location_header;

import api.BaseApiTest;
import api.config.ApiConfig;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Verifies that POST with a valid body and auth returns 201 Created (or 200) and, when
 * the API supports it, a Location header with a valid URI. Requires BASE_URL,
 * CREATE_ENDPOINT and AUTH_TOKEN. Location is asserted only when present.
 */
@DisplayName("POST valid body returns 201 and Location header")
class PostValidBodyReturns201AndLocationHeaderTest extends BaseApiTest {

    /** Path to the test case specification (relative to project root). Used for traceability. */
    public static final String TEST_CASE_SPEC_PATH =
            "rest-api-tests/post-create/post_valid_body_returns_201_and_location_header/TEST_CASE.md";

    /** HTTP header name for Bearer token. */
    private static final String AUTH_HEADER = "Authorization";
    /** Prefix for the Authorization header value (Bearer &lt;token&gt;). */
    private static final String BEARER_PREFIX = "Bearer ";
    /** Response header that may contain the URL of the created resource. */
    private static final String LOCATION_HEADER = "Location";

    @Test
    @DisplayName("POST with valid body returns 201 and optionally Location header")
    void postValidBody_returns201AndLocationHeader() {
        Assumptions.assumeTrue(ApiConfig.getBaseUrl().isPresent(), "BASE_URL must be set");
        Assumptions.assumeTrue(ApiConfig.getCreateEndpoint().isPresent(), "PROTECTED_ENDPOINT/CREATE_ENDPOINT must be set");
        Assumptions.assumeTrue(ApiConfig.getAuthToken().isPresent(), "AUTH_TOKEN required");

        String path = ApiConfig.getCreateEndpoint().map(p -> p.startsWith("/") ? p : "/" + p).orElseThrow();
        Header authHeader = new Header(AUTH_HEADER, BEARER_PREFIX + ApiConfig.getAuthToken().orElseThrow());
        String email = "post-201-" + System.currentTimeMillis() + "@example.com";
        String body = "{\"name\":\"Post 201 Test\",\"email\":\"" + email + "\",\"gender\":\"male\",\"status\":\"active\"}";

        Response response = given()
                .spec(baseSpec)
                .header(authHeader)
                .body(body)
                .when()
                .post(path)
                .then()
                .statusCode(anyOf(equalTo(201), equalTo(200)))
                .extract()
                .response();

        String location = response.getHeader(LOCATION_HEADER);
        if (location != null && !location.isBlank()) {
            assertTrue(location.startsWith("http") || location.startsWith("/"), "Location should be valid URI");
        }
    }
}
