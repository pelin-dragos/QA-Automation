# Test Run Conformity — PROJECT_04

**Run date:** _（fill after first run）_  
**Browsers:** Firefox, Chrome, Edge  
**Result:** _（e.g. X tests PASSED — 11 scenarios × 3 browsers = 33 executions）_

---

## Conformity with TEST_CASES.md

| TC_ID | Summary | Test Method | Browsers | Result |
|-------|---------|-------------|----------|--------|
| **Cross-Browser Login** |
| TC-CB-LOGIN-001 | Valid credentials | shouldLoginWithValidCredentials | F, C, E | _PASS/FAIL_ |
| TC-CB-LOGIN-002 | standard_user | shouldLoginWithUser_standard_user | F, C, E | _PASS/FAIL_ |
| TC-CB-LOGIN-003 | problem_user | shouldLoginWithUser_problem_user | F, C, E | _PASS/FAIL_ |
| TC-CB-LOGIN-004 | performance_glitch_user | shouldLoginWithUser_performance_glitch_user | F, C, E | _PASS/FAIL_ |
| TC-CB-LOGIN-005 | Invalid credentials error | shouldShowErrorMessageWithInvalidCredentials | F, C, E | _PASS/FAIL_ |
| **Cross-Browser Navigation** |
| TC-CB-NAV-001 | Homepage | shouldNavigateToHomepage | F, C, E | _PASS/FAIL_ |
| TC-CB-NAV-002 | Homepage (path "") | shouldNavigateToPage_homepage | F, C, E | _PASS/FAIL_ |
| TC-CB-NAV-003 | /login | shouldNavigateToPage_login | F, C, E | _PASS/FAIL_ |
| TC-CB-NAV-004 | /checkboxes | shouldNavigateToPage_checkboxes | F, C, E | _PASS/FAIL_ |
| TC-CB-NAV-005 | /dropdown | shouldNavigateToPage_dropdown | F, C, E | _PASS/FAIL_ |
| TC-CB-NAV-006 | Visible elements | shouldHaveVisiblePageElements | F, C, E | _PASS/FAIL_ |

---

## Summary

- **Total scenarios:** 11 (5 login + 6 navigation).
- **Total executions:** 11 × 3 browsers = 33 (when all browsers run).
- Update this file after first run with actual results and date.
