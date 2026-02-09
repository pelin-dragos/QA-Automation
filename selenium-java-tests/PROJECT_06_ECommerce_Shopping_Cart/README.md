# PROJECT_06 — E-Commerce Shopping Cart Flow

## Objective

Complete e-commerce test suite on **SauceDemo**: Login → Browse products → Add to cart → Checkout (form, overview, complete). Same scope as the Playwright PROJECT_06, implemented in Selenium Java with Page Object Model.

## Requirements

- Java 17+
- Maven 3.6+ (or Maven Wrapper in project)
- Firefox (WebDriverManager manages driver)
- Network access to https://www.saucedemo.com/

## Technologies

- **Selenium 4** — browser automation
- **JUnit 5** — tests and nested groups
- **WebDriverManager** — driver management
- **Page Object Pattern** — LoginPage, ProductsPage, CartPage, CheckoutPage, CheckoutOverviewPage, CheckoutCompletePage

## Project Structure

```
PROJECT_06_ECommerce_Shopping_Cart/
├── pom.xml
├── README.md
├── TEST_CASES.md
├── TEST_RUN_CONFORMITY.md
├── .env.example
├── src/test/java/com/qa/automation/project06/
│   ├── config/
│   │   └── TestConfig.java
│   ├── base/
│   │   └── BaseTest.java
│   ├── pages/
│   │   ├── LoginPage.java
│   │   ├── ProductsPage.java
│   │   ├── CartPage.java
│   │   ├── CheckoutPage.java
│   │   ├── CheckoutOverviewPage.java
│   │   └── CheckoutCompletePage.java
│   └── tests/
│       └── ShoppingCartFlowTest.java
└── .mvn/ (Maven Wrapper)
```

## Features

- **Complete purchase flow:** one E2E test (login, add 2 products, cart, checkout form, overview, finish, success message).
- **Cart management:** add one, add multiple, remove item, verify cart total.
- **Checkout process:** form validation (required fields), complete form, overview calculation, complete order.
- **Browse and navigation:** login to products, view product info, continue shopping from cart.
- **Configuration:** base URL and credentials from env (no secrets in code). See `.env.example`.

## Deliverables

- 12 tests in 4 nested groups (Complete Purchase Flow, Cart Management, Checkout Process, Browse and Navigation).
- 6 page objects; explicit waits; no hardcoded credentials in source.
- TEST_CASES.md and TEST_RUN_CONFORMITY.md for traceability.

## Quick Start

```bash
cd selenium-java-tests/PROJECT_06_ECommerce_Shopping_Cart
# Optional: set SAUCEDEMO_BASE_URL, SAUCEDEMO_USERNAME, SAUCEDEMO_PASSWORD
.\mvnw.cmd test   # Windows
./mvnw test       # Linux/macOS
```

## Tested Site

- **SauceDemo:** https://www.saucedemo.com/  
- Credentials: from env or default demo values (see TestConfig).

## Evaluation Criteria

- All 12 tests pass against SauceDemo.
- Page objects used; no duplicated locators in tests.
- Config from env; TEST_CASES.md and TEST_RUN_CONFORMITY.md aligned with run.
