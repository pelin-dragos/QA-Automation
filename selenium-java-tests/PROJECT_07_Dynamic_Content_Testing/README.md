# PROJECT_07 — Dynamic Content Testing

## Objective

Test suite for dynamic content on **The Internet** (herokuapp): AJAX loading, infinite scroll, dynamic content that changes on refresh, explicit wait strategies, and lazy-loaded images. Same scope as Playwright PROJECT_07, implemented in Selenium Java with explicit waits (no fixed sleeps except where necessary for scroll/load).

## Requirements

- Java 17+
- Maven 3.6+ (or Maven Wrapper in project)
- Firefox (WebDriverManager)
- Network access to https://the-internet.herokuapp.com/

## Technologies

- **Selenium 4** — browser automation
- **JUnit 5** — tests and nested groups
- **WebDriverManager** — driver management
- **WebDriverWait** — explicit waits (no blind Thread.sleep for test logic)
- **Page Object** — DynamicContentPage

## Project Structure

```
PROJECT_07_Dynamic_Content_Testing/
├── pom.xml
├── README.md
├── TEST_CASES.md
├── TEST_RUN_CONFORMITY.md
├── .env.example
├── src/test/java/com/qa/automation/project07/
│   ├── config/
│   │   └── TestConfig.java
│   ├── base/
│   │   └── BaseTest.java
│   ├── pages/
│   │   └── DynamicContentPage.java
│   └── tests/
│       └── DynamicContentTest.java
└── .mvn/ (Maven Wrapper)
```

## Features

- **AJAX:** Navigate to Dynamic Loading → Example 1; click Start; wait for loading to disappear and finish message (Hello World / Loaded).
- **Infinite Scroll:** Navigate to Infinite Scroll; scroll to bottom incrementally; verify page height increases.
- **Dynamic Content:** Navigate to Dynamic Content; capture initial content; refresh; verify at least one block changes; verify element count.
- **Wait Strategies:** Wait for element visible, element count, page load (readyState), text in element.
- **Lazy Loading:** Dynamic Content page; wait for content; verify images present and have src.

## Deliverables

- 11 tests in 5 nested groups (AJAX, Infinite Scroll, Dynamic Content, Wait Strategies, Lazy Loading).
- One page object; explicit waits; THE_INTERNET_BASE_URL from env.
- TEST_CASES.md and TEST_RUN_CONFORMITY.md for traceability.

## Quick Start

```bash
cd selenium-java-tests/PROJECT_07_Dynamic_Content_Testing
# Optional: set THE_INTERNET_BASE_URL
.\mvnw.cmd test   # Windows
./mvnw test       # Linux/macOS
```

## Tested Site

- **The Internet:** https://the-internet.herokuapp.com/
  - Dynamic Content, Dynamic Loading (Example 1), Infinite Scroll

## Evaluation Criteria

- All 11 tests pass against The Internet.
- No fixed Thread.sleep for assertion logic (only minimal pause for scroll/content load where needed).
- Config from env; TEST_CASES.md and TEST_RUN_CONFORMITY.md aligned with run.
