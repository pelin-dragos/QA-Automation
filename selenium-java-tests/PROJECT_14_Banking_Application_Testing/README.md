# PROJECT_14 — Banking Application Testing

Selenium (Java) tests for a **banking-style application** (e.g. ParaBank): login, dashboard, balance, transfer funds, and statements. Aligned with **[TEST_CASES.md](TEST_CASES.md)**.

## Configuration

- **Credentials and base URL** come only from **environment variables** or system properties (no secrets in code).
- Set `BANKING_USERNAME` and `BANKING_PASSWORD` to run login-dependent tests; otherwise those tests are **skipped** with a clear message.
- Optional: `BANKING_BASE_URL` (default: `https://parabank.parasoft.com/parabank/`).

See **`src/test/resources/.env.example`** for placeholder variable names. Copy to `.env` or export in the shell; do not commit real credentials.

## Run tests

```bash
# With credentials set (e.g. export BANKING_USERNAME=john BANKING_PASSWORD=demo)
./mvnw.cmd test   # Windows
./mvnw test      # Linux/macOS
```

Without credentials, only tests that do not require login (e.g. TC-BANK-LOGIN-002 invalid login) will run; others are skipped.

## Structure

- **config/** — `TestConfig`: base URL, username, password, timeout (from env); `isLoginConfigured()` for skip logic.
- **base/** — `BaseTest`: Firefox, WebDriverManager, per-test driver lifecycle.
- **pages/** — `LoginPage`, `DashboardPage`, `TransferFundsPage`, `AccountActivityPage`.
- **tests/** — `LoginTest`, `DashboardTest`, `StatementsTest`, `TransferFundsTest`, `CompleteFlowTest`.

## Conformity

See **[TEST_RUN_CONFORMITY.md](TEST_RUN_CONFORMITY.md)** for test run results and mapping to test cases (after first run).
