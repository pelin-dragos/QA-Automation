# Test Cases — PROJECT_17: BDD Framework

**Project:** BDD (Cucumber/Gherkin) with Selenium Java  
**Document Version:** 1.0  
**Last Updated:** 2025-02-08  
**Scope:** Login, Logout, Shopping Cart, Checkout, Navigation, Product Sorting (Sauce Demo). Scenarios map to Gherkin features.

---

## 1. Login (Feature: login.feature)

| TC_ID | Scenario / Summary | Preconditions | Steps (Gherkin-style) | Expected Result | Priority | Type |
|-------|--------------------|---------------|------------------------|-----------------|----------|------|
| TC-BDD-LOGIN-001 | Successful login with valid credentials | On login page. | Given I am on the login page.<br>When I enter username "standard_user" and password "secret_sauce" and click login.<br>Then I should be logged in and see the products page. | Products page visible; URL contains inventory. | Critical | Functional |
| TC-BDD-LOGIN-002 | Failed login with invalid credentials | On login page. | Given I am on the login page.<br>When I enter username "invalid_user" and password "invalid_password" and click login.<br>Then I should see an error message and not be logged in. | Error message; stay on login. | High | Negative |
| TC-BDD-LOGIN-003 | Login with empty fields | On login page. | Given I am on the login page.<br>When I click login without entering credentials.<br>Then I should see an error and not be logged in. | Error message; not logged in. | High | Negative |
| TC-BDD-LOGIN-004 | Login with different user types (Outline) | On login page. | Examples: standard_user → products page; locked_out_user → error; problem_user → products page. | Outcomes match expected_result column. | High | Functional/Negative |

---

## 2. Logout (Feature: logout.feature)

| TC_ID | Scenario / Summary | Preconditions | Steps (Gherkin-style) | Expected Result | Priority | Type |
|-------|--------------------|---------------|------------------------|-----------------|----------|------|
| TC-BDD-LOGOUT-001 | Successful logout | User logged in. | Given I am logged in.<br>When I click logout.<br>Then I should be logged out and redirected to login; URL contains "index.html". | Login page; URL as expected. | Critical | Functional |
| TC-BDD-LOGOUT-002 | Restricted access after logout | User has logged out. | Given I have logged out.<br>When I try to access the products page directly.<br>Then I should be redirected to login. | Redirect to login; no inventory access. | High | Security |
| TC-BDD-LOGOUT-003 | Logout from menu | On products page. | Given I am on the products page.<br>When I open the menu and click "Logout".<br>Then I should be logged out. | Logout success; login page. | High | Functional |

---

## 3. Shopping Cart (Feature: shopping_cart.feature)

| TC_ID | Scenario / Summary | Preconditions | Steps (Gherkin-style) | Expected Result | Priority | Type |
|-------|--------------------|---------------|------------------------|-----------------|----------|------|
| TC-BDD-CART-001 | Add product to cart | Logged in; on products page. | Given I am on the products page.<br>When I click Add to cart for first product.<br>Then the product is in the cart and cart badge shows "1". | Cart count 1; product in cart. | Critical | Functional |
| TC-BDD-CART-002 | Add multiple products | Same. | Add first and second product to cart. | Cart badge "2"; both in cart. | High | Functional |
| TC-BDD-CART-003 | Remove product from cart | One product in cart. | Go to cart; click Remove. | Cart empty; badge updated. | High | Functional |
| TC-BDD-CART-004 | Verify total price in cart | Products in cart. | Go to cart page. | Total price calculated correctly. | High | Functional |
| TC-BDD-CART-005 | Empty cart state | On cart page; cart empty. | When cart is empty.<br>Then empty message shown; checkout disabled (if applicable). | Message visible; checkout not enabled. | Medium | Functional |

---

## 4. Checkout (Feature: checkout.feature)

| TC_ID | Scenario / Summary | Preconditions | Steps (Gherkin-style) | Expected Result | Priority | Type |
|-------|--------------------|---------------|------------------------|-----------------|----------|------|
| TC-BDD-CHK-001 | Complete checkout process | Logged in; one product in cart. | From cart: Checkout → fill First Name, Last Name, Postal Code → Continue → overview → Finish. | Success message "Thank you for your order"; order completed. | Critical | Functional |
| TC-BDD-CHK-002 | Checkout form validation – empty fields | On checkout page. | Click Continue without filling form. | Error e.g. "First Name is required". | High | Negative |
| TC-BDD-CHK-003 | Checkout form validation – missing first name | On checkout page. | Fill last name and postal code only; Continue. | Error message at checkout. | High | Negative |
| TC-BDD-CHK-004 | Correct total price on overview | Cart with known total (e.g. $50). | Reach checkout overview. | Subtotal, tax, final total correct. | High | Functional |

---

## 5. Navigation (Feature: navigation.feature)

| TC_ID | Scenario / Summary | Preconditions | Steps (Gherkin-style) | Expected Result | Priority | Type |
|-------|--------------------|---------------|------------------------|-----------------|----------|------|
| TC-BDD-NAV-001 | Navigate to products page | Logged in. | When I navigate to the products page.<br>Then I see the product list and at least one product. | Product list visible. | High | Functional |
| TC-BDD-NAV-002 | Navigate to shopping cart | Logged in. | When I click the cart icon.<br>Then I am on the cart page; URL contains "cart". | Cart page; URL correct. | High | Functional |
| TC-BDD-NAV-003 | Navigate back from cart | On cart page. | When I click "Continue Shopping".<br>Then I return to the products page. | Products page. | Medium | Functional |
| TC-BDD-NAV-004 | Menu navigation | Logged in. | When I navigate through the application.<br>Then all menu links are accessible. | No broken links; pages load. | Medium | Functional |

---

## 6. Product Sorting (Feature: product_sorting.feature)

| TC_ID | Scenario / Summary | Preconditions | Steps (Gherkin-style) | Expected Result | Priority | Type |
|-------|--------------------|---------------|------------------------|-----------------|----------|------|
| TC-BDD-SORT-001 | Sort by name A–Z | Logged in; on products page. | When I select "Name (A to Z)".<br>Then products sorted A–Z; first product earlier in alphabet. | Order correct. | High | Functional |
| TC-BDD-SORT-002 | Sort by name Z–A | Same. | Select "Name (Z to A)". | Products sorted Z–A. | High | Functional |
| TC-BDD-SORT-003 | Sort by price low to high | Same. | Select "Price (low to high)". | Ascending price; first has lowest price. | High | Functional |
| TC-BDD-SORT-004 | Sort by price high to low | Same. | Select "Price (high to low)". | Descending price; first has highest price. | High | Functional |
| TC-BDD-SORT-005 | Sort with different options (Outline) | Same. | Examples: Name (A to Z), (Z to A), Price low-high, high-low. | Products sorted according to option. | High | Functional |

---

## Implementation Notes

- **Feature files:** Under `src/test/resources/features/` (e.g. login.feature, logout.feature, shopping_cart.feature, checkout.feature, navigation.feature, product_sorting.feature).
- **Step definitions:** Java classes in `src/test/java`; use Selenium Page Objects for actions and assertions.
- **Background:** Use for common "Given I am logged in" where applicable.
- **Test data:** Credentials and base URL from config; no secrets in feature text (use placeholders or config in steps).

---

## Checklist (pre-implementation)

- [ ] Cucumber JUnit Platform engine; feature glue configured.
- [ ] Steps reuse Page Objects; no duplicate Selenium logic in steps.
- [ ] Tags (e.g. @smoke) for filtering; CI can run by tag.
- [ ] Scenarios independent; Background or Before creates clean state.
