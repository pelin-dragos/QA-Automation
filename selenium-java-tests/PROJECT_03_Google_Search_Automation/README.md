# Project 03: Google Search Automation

## 🎯 Objective

Automate **Google Search** with Selenium (Java): search with different queries, verify results (relevance, title, link), test **autocomplete** suggestions, and handle **cookie popups** (accept/reject). Tests are resilient to reCAPTCHA (when Google detects automation). Aligned with **[TEST_CASES.md](TEST_CASES.md)**.

## 📋 Requirements

- ✅ Search with simple, multiple, long, and special-character queries
- ✅ Empty-query behaviour
- ✅ Verify search results: URL, relevance, title and link structure
- ✅ Autocomplete suggestions for partial queries
- ✅ Cookie popup: accept and reject (multiple languages)
- ✅ Search after cookie handling
- ✅ reCAPTCHA / "sorry" page handled without failing tests
- ✅ Base URL from config/env; no secrets in code

## 🛠️ Technologies

- **Selenium WebDriver** — Browser automation
- **Java 17** — Language
- **Maven** — Build (Maven Wrapper or parent POM)
- **JUnit 5** — Test framework (including **junit-jupiter-params** for parameterized tests)
- **WebDriverManager** — Driver management
- **Firefox** — Default browser

## 📁 Project Structure

```
PROJECT_03_Google_Search_Automation/
├── pom.xml                          # Maven dependencies
├── README.md                        # This file
├── TEST_CASES.md                    # Test case specifications
├── TEST_RUN_CONFORMITY.md           # TC-to-method mapping and run status
│
└── src/test/
    ├── java/.../project03/
    │   ├── base/
    │   │   └── BaseTest.java        # WebDriver lifecycle, Firefox
    │   ├── config/
    │   │   └── TestConfig.java     # GOOGLE_BASE_URL from env
    │   ├── pages/
    │   │   ├── GoogleSearchPage.java # Search, cookies, results, autocomplete
    │   │   └── SearchResult.java     # title, link, snippet
    │   └── tests/
    │       ├── SearchQueriesTest.java   # Simple, multiple, long, special, empty
    │       ├── SearchResultsTest.java  # Relevance, keywords, title/link
    │       ├── AutocompleteTest.java   # Suggestions, multiple queries, relevance
    │       └── CookiesTest.java        # Accept, reject, search after
    │
    └── resources/
        └── .env.example             # GOOGLE_BASE_URL (optional)
```

## ✨ Features

### 1. Search Queries

- **Simple** — One query, submit, assert search URL or reCAPTCHA
- **Parameterized** — Multiple queries (e.g. Selenium, pytest, web development, etc.)
- **Long query** — Full sentence
- **Special characters** — e.g. "Python 3.11", "C++", "price $100"
- **Empty query** — Submit empty; assert URL/page behaviour

### 2. Search Results

- **First N results** — Get up to N results; assert search URL
- **Keywords in results** — Parameterized query + keywords; verify at least some results contain keywords
- **Title and link** — Each result has non-empty title and http(s) link

### 3. Autocomplete

- **Suggestions** — Type partial query; get list of suggestions (may be empty)
- **Multiple queries** — Parameterized (selenium, pytest, web, python, test)
- **Relevance** — Count suggestions containing query string (informational)

### 4. Cookies

- **Accept** — Click Accept all / Acceptă tot / Accept (multi-language)
- **Reject** — Click Reject all / Respinge tot / Reject
- **Search after** — Accept cookies then perform search; assert search or sorry URL

### 5. reCAPTCHA Handling

- If Google shows "sorry" or reCAPTCHA, tests do not fail; they assert expected behaviour (e.g. URL or "automation detected").

## 📝 Deliverables

- ✅ GoogleSearchPage: navigateTo, acceptCookies, rejectCookies, enterSearchQuery, submitSearch, search(), getAutocompleteSuggestions, getSearchResults, verifyResultContainsKeywords, verifyResultsRelevant, isLoaded
- ✅ Four test classes with parameterized tests where applicable
- ✅ TEST_CASES.md and TEST_RUN_CONFORMITY.md

## ✅ Evaluation Criteria

- ✅ All scenarios from TEST_CASES.md covered
- ✅ No credentials in code; base URL from env
- ✅ Tests independent; runnable in any order
- ✅ reCAPTCHA / automation detection handled gracefully

## 🚀 Quick Start

### 1. Prerequisites

- **Java 17+**, **Firefox** installed

### 2. Run All Tests (from repo root)

```bash
mvn test -pl selenium-java-tests/PROJECT_03_Google_Search_Automation
```

### 3. Run from Project Directory

If you have Maven Wrapper in this project (or copy from PROJECT_01):

```bash
cd selenium-java-tests/PROJECT_03_Google_Search_Automation
.\mvnw.cmd test
```

### 4. Run a Single Test Class

```bash
mvn test -pl selenium-java-tests/PROJECT_03_Google_Search_Automation -Dtest=SearchQueriesTest
mvn test -pl selenium-java-tests/PROJECT_03_Google_Search_Automation -Dtest=CookiesTest
```

### 5. Optional: Custom Base URL

```bash
set GOOGLE_BASE_URL=https://www.google.com
mvn test -pl selenium-java-tests/PROJECT_03_Google_Search_Automation
```

## 📚 Documentation

- **[TEST_CASES.md](TEST_CASES.md)** — Full list of test cases (Search, Results, Autocomplete, Cookies)
- **[TEST_RUN_CONFORMITY.md](TEST_RUN_CONFORMITY.md)** — Mapping of test cases to test methods; update after run

## ⚠️ Notes

- **Google** may show reCAPTCHA or "unusual traffic" for automated requests; tests are written to accept this and not fail.
- **Cookies** popup text varies by region/language; multiple button texts are tried (Accept all, Acceptă tot, Reject all, etc.).
- **Educational use:** Respect Google’s Terms of Service; avoid high-frequency or abusive runs.

## 📊 Implementation Status

| Feature        | Status        | Notes                          |
|----------------|---------------|--------------------------------|
| Search queries | ✅ Implemented | Simple, parameterized, long, special, empty |
| Search results | ✅ Implemented | Relevance, keywords, title/link |
| Autocomplete   | ✅ Implemented | Suggestions, parameterized, relevance |
| Cookies        | ✅ Implemented | Accept, reject, search after   |
| reCAPTCHA      | ✅ Handled     | No failure when detected       |
| Env config     | ✅ Implemented | GOOGLE_BASE_URL                |

## 💡 Tips

1. If tests fail with "element not found", Google may have changed the page structure; review locators in `GoogleSearchPage.java`.
2. To reduce rate limiting, run fewer tests in a row or increase delays (use sparingly).
3. Cookie popup may not appear in all regions or after first acceptance; tests handle absence gracefully.

---

**Aligned with [TEST_CASES.md](TEST_CASES.md) and [TEST_RUN_CONFORMITY.md](TEST_RUN_CONFORMITY.md).**
