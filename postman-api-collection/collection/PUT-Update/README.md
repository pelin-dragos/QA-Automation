# PUT — Full update

Tests for full resource updates. **Run order matters:** Setup creates a user; GoREST allows PUT only on users you create.

| Test folder | Test Case ID |
|-------------|--------------|
| `put_valid_body_existing_id_returns_200/` | API-PUT-001 |
| `put_response_contains_updated_fields_and_values/` | API-PUT-002 |
| `put_nonexistent_id_returns_404/` | API-PUT-003 |
| `put_invalid_id_format_returns_400_or_404/` | API-PUT-004 |
| `put_missing_required_fields_returns_400/` | API-PUT-005 |
| `put_invalid_field_values_returns_400/` | API-PUT-006 |
| `put_without_auth_returns_401/` | API-PUT-007 |
| `put_with_valid_auth_updates_resource/` | API-PUT-008 |

Each subfolder contains TEST_CASE.md. **Endpoint:** `PUT {{base_url}}/users/{{user_id}}`

**GoREST notes:**
- Setup request creates a user before PUT tests (required when run in isolation).
- API-PUT-007: Without auth may return 404. Accept 401 or 404.
