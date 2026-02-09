# PROJECT_15 — Admin Panel Testing

Selenium (Java) tests for an **admin panel** (e.g. OrangeHRM): admin login, **Users CRUD**, search, reset, pagination, bulk operations. Aligned with **[TEST_CASES.md](TEST_CASES.md)**.

## Configuration

- **Base URL and admin credentials** from **environment variables** or system properties only (no secrets in code).
- Set `ADMIN_USERNAME` and `ADMIN_PASSWORD` to run tests; otherwise tests are **skipped** with a clear message.
- Optional: `ADMIN_BASE_URL` (default: OrangeHRM demo).

See **`src/test/resources/.env.example`**. OrangeHRM demo defaults: `Admin` / `admin123`.

## Run tests

```bash
# Set credentials (e.g. OrangeHRM demo: Admin / admin123)
set ADMIN_USERNAME=Admin
set ADMIN_PASSWORD=admin123
mvnw.cmd test
```

## Structure

- **config/** — `TestConfig`: base URL, username, password, timeout; `isLoginConfigured()`.
- **base/** — `BaseTest`: Firefox, WebDriverManager, per-test driver lifecycle.
- **pages/** — `LoginPage`, `UsersManagementPage` (Admin → User Management → Users).
- **util/** — `TestDataHelper`: unique usernames for test independence.
- **tests/** — `LoginTest`, `CreateUserTest`, `ReadUsersTest`, `UpdateUserTest`, `DeleteUserTest`, `SearchFilterTest`, `PaginationTest`, `BulkOperationsTest`, `CompleteCrudFlowTest`.

## Demo limitations

OrangeHRM demo may reset data periodically; create/delete may have restrictions. Documented in test comments and [TEST_RUN_CONFORMITY.md](TEST_RUN_CONFORMITY.md) where applicable.
