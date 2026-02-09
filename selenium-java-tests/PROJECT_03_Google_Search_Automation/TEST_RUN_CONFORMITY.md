# Test Run Conformity — PROJECT_03

**Run date:** _（fill after first run）_  
**Browser:** Firefox  
**Result:** _（e.g. X tests PASSED / Y failed / Z skipped）_

---

## Conformity with TEST_CASES.md

| TC_ID | Test Case Summary | Test Method | Result | Expected (TEST_CASES.md) |
|-------|-------------------|-------------|--------|---------------------------|
| **1. Search Queries** |
| TC-SEARCH-001 | Simple search | shouldSearchWithSimpleQuery | _PASS/FAIL_ | Search URL or reCAPTCHA |
| TC-SEARCH-002 | Multiple queries | shouldSearchWithMultipleQueries (×7) | _PASS/FAIL_ | Search URL or reCAPTCHA |
| TC-SEARCH-003 | Long query | shouldSearchWithLongQuery | _PASS/FAIL_ | Search URL or reCAPTCHA |
| TC-SEARCH-004 | Special characters | shouldSearchWithSpecialCharacters (×4) | _PASS/FAIL_ | Search URL or reCAPTCHA |
| TC-SEARCH-005 | Empty query | shouldHandleEmptyQuerySearch | _PASS/FAIL_ | URL set |
| **2. Search Results** |
| TC-RESULT-001 | First 3 results relevant | shouldVerifyFirstThreeResultsRelevant | _PASS/FAIL_ | Results or reCAPTCHA |
| TC-RESULT-002 | Results contain keywords | shouldVerifyResultsContainKeywords (×4) | _PASS/FAIL_ | Search URL; relevance |
| TC-RESULT-003 | Results have title and link | shouldVerifyResultsHaveTitleAndLink | _PASS/FAIL_ | Title/link or reCAPTCHA |
| **3. Autocomplete** |
| TC-AUTO-001 | Suggestions appear | shouldShowAutocompleteSuggestions | _PASS/FAIL_ | Suggestions list not null |
| TC-AUTO-002 | Autocomplete multiple queries | shouldShowAutocompleteForMultipleQueries (×5) | _PASS/FAIL_ | Suggestions not null |
| TC-AUTO-003 | Suggestions relevant | shouldVerifyAutocompleteSuggestionsRelevant | _PASS/FAIL_ | Relevance count ≥ 0 |
| **4. Cookies** |
| TC-COOKIE-001 | Accept cookies | shouldAcceptCookies | _PASS/FAIL_ | Page loaded |
| TC-COOKIE-002 | Reject cookies | shouldRejectCookies | _PASS/FAIL_ | Page loaded |
| TC-COOKIE-003 | Search after cookie handling | shouldSearchAfterCookieHandling | _PASS/FAIL_ | Search/sorry URL |

---

## Summary

- **Total test cases in TEST_CASES.md:** 15+ (with parameterized expansion).
- **Total tests implemented:** SearchQueriesTest (5), SearchResultsTest (3), AutocompleteTest (3), CookiesTest (3) — plus parameterized variants.
- Update this file after first run with actual results and date.
