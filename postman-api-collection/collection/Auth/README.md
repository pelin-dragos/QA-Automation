# Auth

Authentication tests for the Postman collection.

| Test folder | Test Case ID | Description |
|-------------|--------------|-------------|
| `valid_token_returns_200_or_201/` | API-AUTH-002 | Valid token returns 200/201 |
| `no_token_returns_401/` | API-AUTH-001 | No token returns 401 |
| `invalid_token_returns_401/` | API-AUTH-001b | Invalid token returns 401 |
| `expired_token_returns_401/` | API-AUTH-004 | Expired token returns 401 |

Each subfolder contains a professional TEST_CASE.md. See [docs/TEST_LIST.md](../docs/TEST_LIST.md) for full list.
