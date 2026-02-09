# Test Cases — PROJECT_03: Google Search Automation

**Project:** Google Search Automation  
**Document Version:** 1.0  
**Scope:** Web UI automation with Selenium (Java). Page Object Model; search queries, results verification, autocomplete, cookie handling.

---

## 1. Search Queries

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-SEARCH-001 | Simple search | Browser open; Google base URL from config. | 1. Navigate to Google.<br>2. Accept cookies if popup shown.<br>3. Enter query "Python programming".<br>4. Submit search (Enter). | URL contains /search or q=; results page loads; or reCAPTCHA (automation detected). | Critical | Functional |
| TC-SEARCH-002 | Multiple queries (parameterized) | Same. | 1. Navigate; accept cookies.<br>2. For each query (Selenium automation, pytest, web development, etc.): enter query, submit. | For each: search results URL or reCAPTCHA. | High | Functional |
| TC-SEARCH-003 | Long query | Same. | 1. Enter long query "How to learn Python programming from scratch for beginners".<br>2. Submit. | Search results URL or reCAPTCHA. | Medium | Functional |
| TC-SEARCH-004 | Special characters (parameterized) | Same. | 1. For each: "Python 3.11 features", "C++ programming", "test@example.com search", "price $100": enter and submit. | Search results URL or reCAPTCHA. | Medium | Functional |
| TC-SEARCH-005 | Empty query | Same. | 1. Accept cookies.<br>2. Leave search box empty; submit. | Page does not navigate to /search or URL remains valid. | Low | Negative |

---

## 2. Search Results

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-RESULT-001 | First 3 results relevant | Search performed. | 1. Search "Selenium automation testing".<br>2. Get first 3 results.<br>3. Optionally verify relevance to query. | Results page; if results returned, count ≥ 0; or reCAPTCHA. | High | Functional |
| TC-RESULT-002 | Results contain keywords (parameterized) | Same. | 1. Search for query (e.g. Python programming, web development).<br>2. Get results; check title/snippet contain keywords. | Search URL; if results, at least some contain keywords; or reCAPTCHA. | High | Functional |
| TC-RESULT-003 | Results have title and link | Same. | 1. Search "web development".<br>2. For each result: verify title non-empty, link non-empty, link starts with http. | Each result has title and http(s) link; or no results (reCAPTCHA). | High | Functional |

---

## 3. Autocomplete

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-AUTO-001 | Autocomplete suggestions appear | On Google home. | 1. Navigate; accept cookies.<br>2. Enter "python".<br>3. Get autocomplete suggestions (max 5). | Suggestions list returned (may be empty if not shown). | High | Functional |
| TC-AUTO-002 | Autocomplete for multiple queries (parameterized) | Same. | 1. For each: selenium, pytest, web, python, test — enter query, get suggestions. | Suggestions list not null. | Medium | Functional |
| TC-AUTO-003 | Suggestions relevant to query | Same. | 1. Enter "python"; get suggestions.<br>2. Check how many contain "python". | Count ≥ 0; relevance is informational. | Low | Functional |

---

## 4. Cookies Handling

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-COOKIE-001 | Accept cookies | On Google home. | 1. Navigate to Google.<br>2. Call accept cookies (Accept all / Acceptă tot / etc.). | Page still loaded and functional; search box visible. | High | Functional |
| TC-COOKIE-002 | Reject cookies | Same. | 1. Navigate to Google.<br>2. Call reject cookies. | Page still loaded and functional. | High | Functional |
| TC-COOKIE-003 | Search after cookie handling | Same. | 1. Navigate; accept cookies.<br>2. Perform search "Selenium testing". | Search results URL or reCAPTCHA; no crash. | High | Functional |

---

## Test Data & Notes

- **Base URL:** From config/env (`GOOGLE_BASE_URL`, default https://www.google.com).
- **reCAPTCHA:** Google may show "unusual traffic" or reCAPTCHA; tests treat this as acceptable outcome and assert accordingly (no failure for automation detection).
- **Cookies:** Accept/Reject buttons may vary by region/language; multiple button texts supported.

---

## Checklist (pre-implementation)

- [ ] Tests independent; can run in any order.
- [ ] Base URL from config/env; `.env.example` with placeholders only.
- [ ] Page Object: GoogleSearchPage (search box, cookies, results, autocomplete).
- [ ] Explicit waits where possible; minimal Thread.sleep for autocomplete/cookies.
- [ ] Assertion messages descriptive.
- [ ] No sensitive data in logs or reports.
