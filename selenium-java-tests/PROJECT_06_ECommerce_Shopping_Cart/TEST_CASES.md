# Test Cases — PROJECT_06 E-Commerce Shopping Cart

**Scope:** Complete e-commerce flow on SauceDemo (Login → Browse → Cart → Checkout → Complete).

---

## Complete Purchase Flow

| TC_ID | Summary | Test Method | Expected |
|-------|---------|-------------|----------|
| TC-FLOW-001 | Full purchase flow E2E | `shouldCompleteFullPurchaseFlow` | Login, add 2 products, checkout, verify total (subtotal+tax), success message "Thank you for your order" |

---

## Cart Management

| TC_ID | Summary | Test Method | Expected |
|-------|---------|-------------|----------|
| TC-CART-001 | Add single product | `shouldAddSingleProductToCart` | One product in cart; cart page shows product name |
| TC-CART-002 | Add multiple products | `shouldAddMultipleProductsToCart` | Three products in cart; names match |
| TC-CART-003 | Remove item from cart | `shouldRemoveItemFromCart` | After remove, count decreases; removed product not in list |
| TC-CART-004 | Cart total calculation | `shouldCalculateCartTotalCorrectly` | Cart total equals sum of product prices |

---

## Checkout Process

| TC_ID | Summary | Test Method | Expected |
|-------|---------|-------------|----------|
| TC-CHK-001 | Validate checkout form (required) | `shouldValidateCheckoutForm` | Continue without filling shows error (first name / required) |
| TC-CHK-002 | Complete checkout form | `shouldCompleteCheckoutForm` | Valid form leads to overview page |
| TC-CHK-003 | Checkout overview calculation | `shouldCalculateCheckoutOverviewCorrectly` | Subtotal and total (subtotal+tax) match expected |
| TC-CHK-004 | Complete checkout | `shouldCompleteCheckout` | Finish leads to complete page with "Thank you for your order" |

---

## Browse and Navigation

| TC_ID | Summary | Test Method | Expected |
|-------|---------|-------------|----------|
| TC-BROWSE-001 | Login and navigate to products | `shouldLoginAndNavigateToProducts` | URL contains inventory; products count > 0 |
| TC-BROWSE-002 | View product details | `shouldViewProductDetails` | Product info (name, price) available; price > 0 |
| TC-BROWSE-003 | Continue shopping from cart | `shouldContinueShoppingFromCart` | From cart, "Continue Shopping" returns to inventory |

---

**Total:** 12 test cases.

**Credentials:** From env (`SAUCEDEMO_USERNAME`, `SAUCEDEMO_PASSWORD`) or defaults for SauceDemo demo site.
