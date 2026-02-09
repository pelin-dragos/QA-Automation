# Project 04: Multiple Browser Testing

## 🎯 Objective

Run the **same tests** on **multiple browsers** (Firefox, Chrome, Edge) to ensure cross-browser compatibility. Covers **Sauce Demo** login and **The Internet** (herokuapp) navigation. Each test is parameterized with `@EnumSource(Browser.class)` so it runs once per browser. Aligned with **[TEST_CASES.md](TEST_CASES.md)**.

## 📋 Requirements

- ✅ Configuration for at least 3 browsers (Firefox, Chrome, Edge)
- ✅ Same test suite runs on all browsers (no duplicated test code)
- ✅ Cross-browser login (valid and invalid credentials)
- ✅ Cross-browser navigation (homepage, login, checkboxes, dropdown)
- ✅ Page Object Model; base URLs from config/env
- ✅ No secrets in code

## 🛠️ Technologies

- **Selenium WebDriver** — Browser automation
- **Java 17** — Language
- **Maven** — Build (Maven Wrapper included)
- **JUnit 5** — Test framework + **junit-jupiter-params** (parameterized by browser)
- **WebDriverManager** — Driver management for Firefox, Chrome, Edge

## 📁 Project Structure

```
PROJECT_04_Multiple_Browser_Testing/
├── pom.xml
├── mvnw.cmd
├── README.md
├── TEST_CASES.md
├── TEST_RUN_CONFORMITY.md
│
└── src/test/
    ├── java/.../project04/
    │   ├── base/
    │   │   ├── BaseTest.java      # createDriver(Browser)
    │   │   └── Browser.java       # FIREFOX, CHROME, EDGE
    │   ├── config/
    │   │   └── TestConfig.java    # Sauce Demo & The Internet base URLs
    │   ├── pages/
    │   │   ├── LoginPage.java     # Sauce Demo login
    │   │   └── TheInternetPage.java # The Internet navigation
    │   └── tests/
    │       ├── CrossBrowserLoginTest.java      # 5 scenarios × 3 browsers
    │       └── CrossBrowserNavigationTest.java # 6 scenarios × 3 browsers
    │
    └── resources/
        └── .env.example
```

## ✨ Features

### 1. Cross-Browser Login (Sauce Demo)

- Valid credentials (standard_user) on all browsers
- Parameterized: standard_user, problem_user, performance_glitch_user
- Invalid credentials: error message with expected keywords on all browsers

### 2. Cross-Browser Navigation (The Internet)

- Navigate to homepage (empty path)
- Navigate to /login, /checkboxes, /dropdown
- Verify page loaded and URL contains path
- Verify body visible and page has links

### 3. Browser Enum

- **Browser.FIREFOX**, **Browser.CHROME**, **Browser.EDGE** — WebDriverManager sets up the correct driver; `BaseTest.createDriver(Browser)` returns a new WebDriver per call. Each test quits the driver in a `finally` block.

## 📝 Deliverables

- ✅ Same tests run on Firefox, Chrome, Edge (parameterized)
- ✅ LoginPage and TheInternetPage; TestConfig for both base URLs
- ✅ TEST_CASES.md and TEST_RUN_CONFORMITY.md

## ✅ Evaluation Criteria

- ✅ Minimum 3 browsers supported
- ✅ No test code duplicated per browser
- ✅ All scenarios from TEST_CASES.md covered

## 🚀 Quick Start

### 1. Prerequisites

- **Java 17+**
- **Firefox** installed (for Firefox runs)
- **Chrome** installed (for Chrome runs)
- **Edge** installed (for Edge runs)  
  WebDriverManager downloads the matching driver binaries on first run.

### 2. Run All Tests (all 3 browsers)

```bash
cd selenium-java-tests/PROJECT_04_Multiple_Browser_Testing
.\mvnw.cmd test
```

This runs 11 parameterized scenarios × 3 browsers = **33 test executions**.

### 3. Run a Single Test Class

```bash
.\mvnw.cmd test -Dtest=CrossBrowserLoginTest
.\mvnw.cmd test -Dtest=CrossBrowserNavigationTest
```

### 4. From Repo Root

```bash
mvn test -pl selenium-java-tests/PROJECT_04_Multiple_Browser_Testing
```

### 5. Optional: Override Base URLs

```bash
set SAUCEDEMO_BASE_URL=https://www.saucedemo.com/
set THE_INTERNET_BASE_URL=https://the-internet.herokuapp.com/
.\mvnw.cmd test
```

## 📚 Documentation

- **[TEST_CASES.md](TEST_CASES.md)** — All scenarios (login + navigation) and browsers
- **[TEST_RUN_CONFORMITY.md](TEST_RUN_CONFORMITY.md)** — TC-to-method mapping; update after run

## 📊 Implementation Status

| Feature | Status | Notes |
|---------|--------|-------|
| Firefox | ✅ Implemented | WebDriverManager.firefoxdriver() |
| Chrome | ✅ Implemented | WebDriverManager.chromedriver() |
| Edge | ✅ Implemented | WebDriverManager.edgedriver() |
| Login tests | ✅ Implemented | 5 scenarios × 3 browsers |
| Navigation tests | ✅ Implemented | 6 scenarios × 3 browsers |
| Env config | ✅ Implemented | Two base URLs |

## 💡 Tips

1. First run may download ChromeDriver/EdgeDriver/GeckoDriver via WebDriverManager.
2. If a test fails on one browser only, check browser-specific behaviour (e.g. alerts, focus).
3. To run only one browser, you could add a JUnit 5 tag or a custom source that filters to a single `Browser` (e.g. `@ParameterizedTest` with `@MethodSource("firefoxOnly")`); by default all three run.

---

**Aligned with [TEST_CASES.md](TEST_CASES.md) and [TEST_RUN_CONFORMITY.md](TEST_RUN_CONFORMITY.md).**
