# POST — Create

Tests for creating resources. POST-001 and POST-002 set `user_id` for downstream folders (PUT, PATCH, DELETE, GET-Single).

| Test folder | Test Case ID |
|-------------|--------------|
| `post_valid_body_returns_201_and_location_header/` | API-POST-001 |
| `post_valid_body_returns_created_resource_in_response/` | API-POST-002 |
| `post_missing_required_field_returns_400/` | API-POST-003 |
| `post_invalid_field_format_returns_400/` | API-POST-004 |
| `post_empty_body_returns_400_or_415/` | API-POST-005 |
| `post_wrong_content_type_returns_415_or_400/` | API-POST-006 |
| `post_without_auth_returns_401/` | API-POST-007 |
| `post_with_valid_auth_creates_resource/` | API-POST-008 |
| `post_duplicate_returns_409_or_400/` | API-POST-009 |

Each subfolder contains TEST_CASE.md. **Endpoint:** `POST {{base_url}}/users`

**GoREST notes:**
- Use unique email per request (e.g. `post-{{$timestamp}}@example.com` in Postman).
- GoREST returns 422 for validation errors (missing field, invalid format, duplicate).
