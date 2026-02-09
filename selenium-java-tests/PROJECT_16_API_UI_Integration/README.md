# PROJECT_16 — API + UI Integration

Selenium (UI) + RestAssured (API) in the same flow: create/read/update/delete via API, verify data in the browser (JSON view). Aligned with **[TEST_CASES.md](TEST_CASES.md)**.

## Technologies

- **Selenium (Java)** for all UI steps: open API URLs in Firefox, wait for load, parse JSON from page body.
- **RestAssured** for API steps: POST, GET, PUT, DELETE to JSONPlaceholder (or configured base URL).
- Base URL from config/env; no hardcoded hosts or secrets.

## Configuration

- Set `API_BASE_URL` optionally (default: `https://jsonplaceholder.typicode.com/`).
- See **`src/test/resources/.env.example`**.

## Run tests

```bash
mvnw.cmd test
```

## Structure

- **config/** — `TestConfig`: API base URL, UI and flow timeouts.
- **base/** — `BaseTest`: Selenium WebDriver (Firefox), WebDriverManager, per-test lifecycle.
- **api/** — `PostsApiClient`: RestAssured calls for /posts.
- **ui/** — `JsonPage`: Selenium navigate to URL, get body text, parse JSON (for UI verification).
- **tests/** — `ApiCreateTest`, `UiVerificationTest`, `IntegrationTest`, `CompleteFlowTest`, `DataConsistencyTest`.

## Demo limitation

**JSONPlaceholder** does not persist POST/PUT/DELETE. Tests use existing ids (e.g. 1) for UI verification where needed; README and test comments document this.

## Conformity

See **[TEST_RUN_CONFORMITY.md](TEST_RUN_CONFORMITY.md)** for mapping of test cases to test methods and run results.
