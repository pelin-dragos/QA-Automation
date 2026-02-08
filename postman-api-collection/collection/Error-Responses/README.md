# Error responses

Tests for error handling and response behaviour.

| Test folder | Test Case ID |
|-------------|--------------|
| `four_xx_responses_include_body_with_error_message_or_code/` | API-ERROR-001 |
| `five_xx_responses_handled_without_crashing_client/` | API-ERROR-002 |
| `response_time_for_success_within_configured_timeout/` | API-ERROR-003 |

Each subfolder contains TEST_CASE.md.

**Notes:**
- API-ERROR-001: GET /users/999999999 triggers 404; GoREST returns `{"message":"Resource not found"}`.
- API-ERROR-002: 5xx cannot be triggered on GoREST; test uses fallback (accepts 200–599).
- API-ERROR-003: Timeout 5000 ms; can be flaky on slow networks.
