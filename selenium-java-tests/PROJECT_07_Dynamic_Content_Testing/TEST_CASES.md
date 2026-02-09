# Test Cases — PROJECT_07 Dynamic Content Testing

**Scope:** The Internet (herokuapp): AJAX loading, infinite scroll, dynamic content, wait strategies, lazy loading.

---

## AJAX Content

| TC_ID | Summary | Test Method | Expected |
|-------|---------|-------------|----------|
| TC-AJAX-001 | Load AJAX content | `shouldLoadAjaxContent` | Click Start, wait for finish message; text contains "Hello World" or "Loaded" |
| TC-AJAX-002 | AJAX with custom wait | `shouldHandleAjaxLoadingWithCustomWait` | Loading hidden, finish message visible and non-empty |

---

## Infinite Scroll

| TC_ID | Summary | Test Method | Expected |
|-------|---------|-------------|----------|
| TC-SCROLL-001 | Scroll infinitely | `shouldScrollInfinitely` | Page height increases; scroll count > 0 |
| TC-SCROLL-002 | Scroll and verify expansion | `shouldScrollAndVerifyPageExpanded` | Height after scroll >= initial height |

---

## Dynamic Content

| TC_ID | Summary | Test Method | Expected |
|-------|---------|-------------|----------|
| TC-DYN-001 | Refresh dynamic content | `shouldRefreshDynamicContent` | After refresh, at least one content block differs from initial |
| TC-DYN-002 | Verify content count | `shouldVerifyDynamicContentCount` | At least 2 content elements on page |

---

## Wait Strategies

| TC_ID | Summary | Test Method | Expected |
|-------|---------|-------------|----------|
| TC-WAIT-001 | Wait for element visible | `shouldWaitForElementVisible` | First content row is displayed |
| TC-WAIT-002 | Wait for element count | `shouldWaitForElementCount` | At least 2 content elements |
| TC-WAIT-003 | Wait for page load | `shouldWaitForPageLoad` | document.readyState is "complete" |
| TC-WAIT-004 | Wait for text in element | `shouldWaitForTextInElement` | First row has text; element contains that text |

---

## Lazy Loading

| TC_ID | Summary | Test Method | Expected |
|-------|---------|-------------|----------|
| TC-LAZY-001 | Verify image loading | `shouldVerifyImageLoading` | At least one image; each has src attribute |

---

**Total:** 11 test cases.

**Base URL:** THE_INTERNET_BASE_URL from env (default https://the-internet.herokuapp.com/).
