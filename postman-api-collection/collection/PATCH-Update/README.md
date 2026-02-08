# PATCH — Partial update

Tests for partial resource updates. **Run order matters:** Setup creates a user; GoREST allows PATCH only on users you create.

| Test folder | Test Case ID |
|-------------|--------------|
| `patch_valid_partial_body_returns_200/` | API-PATCH-001 |
| `patch_updates_only_sent_fields_others_unchanged/` | API-PATCH-002 |
| `patch_nonexistent_id_returns_404/` | API-PATCH-003 |
| `patch_invalid_field_value_returns_400/` | API-PATCH-004 |
| `patch_empty_body_returns_400_or_200/` | API-PATCH-005 |
| `patch_without_auth_returns_401/` | API-PATCH-006 |
| `patch_with_valid_auth_updates_resource/` | API-PATCH-007 |

Each subfolder contains TEST_CASE.md. **Endpoint:** `PATCH {{base_url}}/users/{{user_id}}`

**GoREST notes:**
- Setup request creates a user before PATCH tests (required when run in isolation).
- API-PATCH-006: Without auth may return 404. Accept 401 or 404.
