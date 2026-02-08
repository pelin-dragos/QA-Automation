# DELETE

Tests for deleting resources. **Run order matters:** Setup requests create users; 001/004 use first, 006/007 use second.

| Test folder | Test Case ID |
|-------------|--------------|
| `delete_valid_existing_id_returns_204_or_200/` | API-DELETE-001 |
| `delete_nonexistent_id_returns_404/` | API-DELETE-002 |
| `delete_invalid_id_format_returns_400_or_404/` | API-DELETE-003 |
| `get_after_delete_returns_404_for_same_id/` | API-DELETE-004 |
| `delete_without_auth_returns_401/` | API-DELETE-005 |
| `delete_with_valid_auth_removes_resource/` | API-DELETE-006 |
| `delete_with_conflict_returns_409/` | API-DELETE-007 |

Each subfolder contains TEST_CASE.md. **Endpoint:** `DELETE {{base_url}}/users/{id}`

**Postman:** Collection includes Setup requests that create users before DELETE tests. API-DELETE-005 uses fixed ID 1.
