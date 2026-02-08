# GET — Single resource

Tests for fetching a single resource by ID. **Run order matters:** Setup fetches a valid user_id.

| Test folder | Test Case ID |
|-------------|--------------|
| `get_by_valid_id_returns_200_and_correct_body/` | API-GET-SINGLE-001 |
| `get_by_valid_id_returns_expected_fields_and_types/` | API-GET-SINGLE-002 |
| `get_by_nonexistent_id_returns_404/` | API-GET-SINGLE-003 |
| `get_by_invalid_id_format_returns_400_or_404/` | API-GET-SINGLE-004 |
| `get_without_auth_returns_401_when_protected/` | API-GET-SINGLE-005 |
| `get_with_valid_auth_returns_200_and_data/` | API-GET-SINGLE-006 |

Each subfolder contains TEST_CASE.md. **Endpoint:** `GET {{base_url}}/users/{{user_id}}`

**GoREST notes:**
- Setup request fetches user_id from GET /users (required when running folder in isolation).
- API-GET-SINGLE-005: GET by ID is public; POST /users used to test 401.
