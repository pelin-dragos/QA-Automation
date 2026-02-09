# PROJECT_01 — Login & Logout Testing

Automated login/logout for **Sauce Demo** with Selenium (Java): login success (standard_user, problem_user, performance_glitch_user), login failure (invalid/empty), logout and session verification.

## Test cases

See **[TEST_CASES.md](TEST_CASES.md)** for the full list (TC-ID, steps, expected result, priority).

## Implementation summary

- **Config:** `TestConfig` reads base URL from env/`SAUCEDEMO_BASE_URL` (default: https://www.saucedemo.com/). No credentials in code.
- **Page Objects:** `LoginPage`, `ProductsPage` with explicit waits (WebDriverWait); no `Thread.sleep`.
- **Tests:** `LoginSuccessTest` (TC-LOGIN-001–004), `LoginFailureTest` (TC-LOGIN-005–011), `LogoutTest` (TC-LOGOUT-001–003). Each test is independent and can run in any order.
- **Driver:** Firefox via WebDriverManager (avoids Chrome password-manager popup); no manual driver path required.

## Running the tests

**Prerequisites:** Java 17+ (set `JAVA_HOME` to your JDK). Maven is not required on PATH — the project uses **Maven Wrapper** (`mvnw.cmd` on Windows), same as `java-rest-api-automation`.

From the project folder:

```bash
cd PROJECT_01_Login_Logout_Testing
mvnw.cmd test
```

On first run, the wrapper will download Maven 3.9.6 once into `.mvn/wrapper/maven/`.

To run a single test class:

```bash
mvnw.cmd test -Dtest=LoginSuccessTest
mvnw.cmd test -Dtest=LoginFailureTest
mvnw.cmd test -Dtest=LogoutTest
```

If you have Maven on PATH you can use `mvn test` instead of `mvnw.cmd test`.

**Browser:** All tests run in **Firefox** to avoid the Chrome/Google "Change your password" (data breach) popup.

Optional: set `SAUCEDEMO_BASE_URL` if the app is hosted elsewhere. See `src/test/resources/.env.example` for placeholders.
