# GET — List

Tests for listing resources (collection endpoint).

| Test folder | Test Case ID |
|-------------|--------------|
| `get_list_returns_200/` | API-GET-LIST-001 |
| `get_list_returns_valid_json_array/` | API-GET-LIST-002 |
| `get_list_valid_pagination_returns_correct_page_and_size/` | API-GET-LIST-003 |
| `get_list_invalid_pagination_returns_400_or_422/` | API-GET-LIST-004 |
| `get_list_when_empty_returns_200_and_empty_array/` | API-GET-LIST-005 |
| `get_list_respects_query_filters_returns_matching_results/` | API-GET-LIST-006 |
| `get_list_without_auth_returns_401_when_protected/` | API-GET-LIST-007 |
| `get_list_with_valid_auth_returns_200_and_data/` | API-GET-LIST-008 |

Each subfolder contains TEST_CASE.md. **Endpoint:** `GET {{base_url}}/users`

**GoREST notes:**
- API-GET-LIST-004: Invalid pagination returns 200 (permissive). Accept 200/400/422.
- API-GET-LIST-007: GET list is public; POST /users used to test 401.
