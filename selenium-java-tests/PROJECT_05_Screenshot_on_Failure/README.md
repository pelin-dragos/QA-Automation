# Project 05: Screenshot on Failure

## 🎯 Objective

Capture a **screenshot automatically when a test fails**, for easier debugging. Uses **Sauce Demo** login: one test passes (no screenshot), four tests **intentionally fail** to demonstrate that a PNG is saved to `target/screenshot-on-failure/`. Aligned with **[TEST_CASES.md](TEST_CASES.md)**.

## 📋 Requirements

- ✅ Automatic screenshot on test failure (no manual capture in test code)
- ✅ Screenshots saved with test class name, method name, and timestamp
- ✅ Output directory configurable (default: target/screenshot-on-failure)
- ✅ One passing test (no screenshot); four failing tests to demonstrate behaviour
- ✅ Base URL from config/env; no secrets in code

## 🛠️ Technologies

- **Selenium WebDriver** — Browser automation + TakesScreenshot
- **Java 17** — Language
- **Maven** — Build (Maven Wrapper included)
- **JUnit 5** — Test framework + **TestWatcher** extension for screenshot on failure
- **WebDriverManager** — Driver management
- **Firefox** — Default browser

## 📁 Project Structure

```
PROJECT_05_Screenshot_on_Failure/
├── pom.xml
├── mvnw.cmd
├── README.md
├── TEST_CASES.md
├── TEST_RUN_CONFORMITY.md
│
└── src/test/
    ├── java/.../project05/
    │   ├── base/
    │   │   ├── BaseTest.java                 # WebDriver + @RegisterExtension watcher
    │   │   └── ScreenshotOnFailureWatcher.java # TestWatcher: screenshot on testFailed
    │   ├── config/
    │   │   └── TestConfig.java               # SAUCEDEMO_BASE_URL, SCREENSHOT_OUTPUT_DIR
    │   ├── pages/
    │   │   └── LoginPage.java                # Sauce Demo login
    │   └── tests/
    │       └── ScreenshotOnFailureTest.java # 1 pass + 4 intentional failures
    │
    └── resources/
        └── .env.example
```

## ✨ Features

### 1. ScreenshotOnFailureWatcher

- JUnit 5 **TestWatcher**; implements **testFailed(ExtensionContext, Throwable)**.
- Gets WebDriver from a **Supplier** (so BaseTest can pass `() -> driver`).
- Casts driver to **TakesScreenshot**, calls **getScreenshotAs(OutputType.BYTES)**, writes to a file.
- File name: `ClassName_methodName_yyyyMMdd-HHmmss.png`.
- Directory: **TestConfig.getScreenshotOutputDir()** (default `target/screenshot-on-failure`).

### 2. Tests

- **shouldSuccessfullyLogin_noScreenshot** — Passes; no screenshot.
- **shouldFailWithInvalidCredentials_screenshotCaptured** — Invalid login then assert inventory URL → fails → screenshot.
- **shouldFailWithAssertionError_screenshotCaptured** — Assert page not loaded → fails → screenshot.
- **shouldFailWithElementNotFound_screenshotCaptured** — Wait for non-existent element, then fail → screenshot.
- **shouldFailWithTimeout_screenshotCaptured** — Wait for element that never appears, then fail → screenshot.

## 📝 Deliverables

- ✅ ScreenshotOnFailureWatcher (reusable for other projects if desired)
- ✅ BaseTest with @RegisterExtension
- ✅ LoginPage + ScreenshotOnFailureTest (5 tests)
- ✅ TEST_CASES.md and TEST_RUN_CONFORMITY.md

## ✅ Evaluation Criteria

- ✅ Screenshot taken only on failure
- ✅ File name includes test identity and timestamp
- ✅ Output directory configurable via env

## 🚀 Quick Start

### 1. Prerequisites

- **Java 17+**, **Firefox** installed

### 2. Run All Tests (4 will fail and produce screenshots)

```bash
cd selenium-java-tests/PROJECT_05_Screenshot_on_Failure
.\mvnw.cmd test
```

After the run, check **target/screenshot-on-failure/** for PNG files (one per failed test).

### 3. Run Only the Passing Test

```bash
.\mvnw.cmd test -Dtest=ScreenshotOnFailureTest#shouldSuccessfullyLogin_noScreenshot
```

### 4. Optional: Custom Screenshot Directory

```bash
set SCREENSHOT_OUTPUT_DIR=test-results/screenshots
.\mvnw.cmd test
```

## 📚 Documentation

- **[TEST_CASES.md](TEST_CASES.md)** — All scenarios (1 pass, 4 failure demos)
- **[TEST_RUN_CONFORMITY.md](TEST_RUN_CONFORMITY.md)** — Mapping and expected result

## 📊 Implementation Status

| Feature | Status | Notes |
|---------|--------|-------|
| Screenshot on failure | ✅ Implemented | TestWatcher + TakesScreenshot |
| Naming (class_method_timestamp) | ✅ Implemented | PNG in output dir |
| Configurable output dir | ✅ Implemented | SCREENSHOT_OUTPUT_DIR |
| Passing test (no screenshot) | ✅ Implemented | shouldSuccessfullyLogin_noScreenshot |
| Failure demos | ✅ Implemented | 4 tests |

## 💡 Tips

1. To see screenshots, run the full suite; the four intentional failures will each produce one PNG.
2. Add **target/screenshot-on-failure/** to **.gitignore** if you don’t want to commit screenshots.
3. For CI, set **SCREENSHOT_OUTPUT_DIR** to an artifact path so failed-test screenshots are published.

---

**Aligned with [TEST_CASES.md](TEST_CASES.md) and [TEST_RUN_CONFORMITY.md](TEST_RUN_CONFORMITY.md).**
