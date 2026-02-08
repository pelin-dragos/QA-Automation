# Headers & Content-Type

Tests for request/response headers.

| Test folder | Test Case ID |
|-------------|--------------|
| `request_accept_application_json_receives_json_response/` | API-HEADERS-001 |
| `response_includes_content_type_application_json/` | API-HEADERS-002 |
| `response_does_not_expose_sensitive_headers/` | API-HEADERS-003 |

Each subfolder contains TEST_CASE.md. **Endpoint:** `GET {{base_url}}/users?per_page=1` (public, no auth).

**GoREST notes:** All tests use public GET /users. No X-Powered-By header expected.
