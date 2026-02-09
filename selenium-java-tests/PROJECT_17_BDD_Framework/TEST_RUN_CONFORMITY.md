# PROJECT_17 — Test Run Conformity

| TC_ID | Feature / Scenario | Status | Notes |
|-------|--------------------|--------|-------|
| TC-BDD-LOGIN-001 | login.feature — Successful login | **Pass** | Products page visible; URL contains inventory |
| TC-BDD-LOGIN-002 | login.feature — Failed login invalid credentials | **Pass** | Error message; stay on login |
| TC-BDD-LOGIN-003 | login.feature — Login with empty fields | **Pass** | Error; not logged in |
| TC-BDD-LOGIN-004 | login.feature — Outline (standard_user, locked_out_user, problem_user) | **Pass** | 3 rows; outcomes match |
| TC-BDD-LOGOUT-001 | logout.feature — Successful logout | **Pass** | Login page; URL login |
| TC-BDD-LOGOUT-002 | logout.feature — Restricted access after logout | **Pass** | Redirect to login |
| TC-BDD-LOGOUT-003 | logout.feature — Logout from menu | **Pass** | Menu open + Logout |
| TC-BDD-CART-001 | shopping_cart.feature — Add product to cart | **Pass** | Badge shows 1 |
| TC-BDD-CART-002 | shopping_cart.feature — Add multiple products | **Pass** | Badge shows 2 |
| TC-BDD-CART-003 | shopping_cart.feature — Remove product from cart | **Pass** | Cart empty or badge updated |
| TC-BDD-CART-004 | shopping_cart.feature — Verify total price in cart | **Pass** | Subtotal from items |
| TC-BDD-CART-005 | shopping_cart.feature — Empty cart state | **Pass** | Cart page; checkout state |
| TC-BDD-CHK-001 | checkout.feature — Complete checkout process | **Pass** | Thank you message |
| TC-BDD-CHK-002 | checkout.feature — Checkout form validation empty | **Pass** | Error visible |
| TC-BDD-CHK-003 | checkout.feature — Missing first name | **Pass** | Checkout error |
| TC-BDD-CHK-004 | checkout.feature — Correct total on overview | **Pass** | Subtotal and total |
| TC-BDD-NAV-001 | navigation.feature — Navigate to products page | **Pass** | Product list visible |
| TC-BDD-NAV-002 | navigation.feature — Navigate to cart | **Pass** | URL contains cart |
| TC-BDD-NAV-003 | navigation.feature — Continue Shopping from cart | **Pass** | Return to products |
| TC-BDD-NAV-004 | navigation.feature — Menu navigation | **Pass** | Menu visible |
| TC-BDD-SORT-001 | product_sorting.feature — Sort Name A–Z | **Pass** | First earlier than last |
| TC-BDD-SORT-002 | product_sorting.feature — Sort Name Z–A | **Pass** | First later than last |
| TC-BDD-SORT-003 | product_sorting.feature — Sort price low to high | **Pass** | First has lowest price |
| TC-BDD-SORT-004 | product_sorting.feature — Sort price high to low | **Pass** | First has highest price |
| TC-BDD-SORT-005 | product_sorting.feature — Outline (4 sort options) | **Pass** | 4 rows; list visible |

**Run:** `.\mvnw.cmd test` — 30 scenarios passed.

**Selenium:** All UI steps use Selenium (WebDriver) via Page Objects; no secrets in feature text; tags `@smoke`, `@login`, `@logout`, `@cart`, `@checkout`, `@navigation`, `@sorting` for filtering.
