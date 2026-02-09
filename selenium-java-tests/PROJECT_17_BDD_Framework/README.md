# PROJECT_17 — BDD Framework

**Cucumber / Gherkin** with Selenium Java: features for **login**, **logout**, **shopping cart**, **checkout**, **navigation**, **product sorting** (Sauce Demo).

## Test cases

See **[TEST_CASES.md](TEST_CASES.md)** for scenarios mapped to feature files and step definitions.

## Structure

- **Config:** `src/test/java/.../config/TestConfig.java` — base URL and timeouts from env.
- **Base:** `src/test/java/.../base/DriverHolder.java`, `Hooks.java` — WebDriver lifecycle per scenario.
- **Pages:** `src/test/java/.../pages/` — LoginPage, ProductsPage, CartPage, CheckoutPage (Selenium Page Objects).
- **Features:** `src/test/resources/features/` — login, logout, shopping_cart, checkout, navigation, product_sorting.
- **Steps:** `src/test/java/.../steps/` — LoginSteps, LogoutSteps, CartSteps, CheckoutSteps, NavigationSteps, ProductSortingSteps.

Credentials and base URL come from environment or `.env`; use **[.env.example](.env.example)** as template (no secrets in code).

## Run

```bash
# All scenarios
.\mvnw.cmd test

# Smoke only
.\mvnw.cmd test -Dcucumber.filter.tags="@smoke"

# Filter by tag (e.g. login, cart, checkout)
.\mvnw.cmd test -Dcucumber.filter.tags="@login"
```

## Conformity

See **[TEST_RUN_CONFORMITY.md](TEST_RUN_CONFORMITY.md)** for TC-to-scenario mapping and run status.
