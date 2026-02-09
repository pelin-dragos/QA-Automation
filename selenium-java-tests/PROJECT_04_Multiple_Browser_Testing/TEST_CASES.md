# Test Cases — PROJECT_04: Multiple Browser Testing

**Project:** Multiple Browser Testing (Cross-Browser)  
**Document Version:** 1.0  
**Scope:** Same tests run on Firefox, Chrome, and Edge. Sauce Demo (login) and The Internet (navigation).

---

## 1. Cross-Browser Login (Sauce Demo)

| TC_ID | Summary | Browsers | Steps | Expected Result | Priority |
|-------|---------|----------|-------|-----------------|----------|
| TC-CB-LOGIN-001 | Login with valid credentials | Firefox, Chrome, Edge | 1. Navigate to Sauce Demo. 2. Enter standard_user / secret_sauce. 3. Click Login. | URL contains "inventory"; page loaded. | Critical |
| TC-CB-LOGIN-002 | Login with standard_user | All | Same as above. | Inventory page. | High |
| TC-CB-LOGIN-003 | Login with problem_user | All | Enter problem_user / secret_sauce; Login. | Inventory page. | High |
| TC-CB-LOGIN-004 | Login with performance_glitch_user | All | Enter performance_glitch_user / secret_sauce; Login. | Inventory page. | High |
| TC-CB-LOGIN-005 | Invalid credentials show error | All | Enter invalid_user / invalid_password; Login. | Error message visible; contains "error" or "invalid" or "incorrect" or "epic sadface" or "username and password". | Critical |

---

## 2. Cross-Browser Navigation (The Internet)

| TC_ID | Summary | Browsers | Steps | Expected Result | Priority |
|-------|---------|----------|-------|-----------------|----------|
| TC-CB-NAV-001 | Navigate to homepage | All | 1. Navigate to base URL (empty path). | Page loaded; URL contains the-internet.herokuapp.com. | High |
| TC-CB-NAV-002 | Navigate to homepage (path "") | All | Same. | Page loaded; correct URL. | Medium |
| TC-CB-NAV-003 | Navigate to /login | All | Navigate to path "login". | Page loaded; URL contains "login". | High |
| TC-CB-NAV-004 | Navigate to /checkboxes | All | Navigate to path "checkboxes". | Page loaded; URL contains "checkboxes". | High |
| TC-CB-NAV-005 | Navigate to /dropdown | All | Navigate to path "dropdown". | Page loaded; URL contains "dropdown". | High |
| TC-CB-NAV-006 | Visible page elements | All | Navigate to homepage. Verify body visible and page has links. | Body visible; getAllLinks() non-empty. | High |

---

## Test Data & Notes

- **Sauce Demo base URL:** From env `SAUCEDEMO_BASE_URL` (default https://www.saucedemo.com/).
- **The Internet base URL:** From env `THE_INTERNET_BASE_URL` (default https://the-internet.herokuapp.com/).
- **Browsers:** Firefox, Chrome, Edge — each test runs once per browser via @ParameterizedTest @EnumSource(Browser.class).

---

## Checklist

- [ ] Tests independent; run in any order.
- [ ] Base URLs from config/env; no secrets in code.
- [ ] Page Objects: LoginPage (Sauce Demo), TheInternetPage (The Internet).
- [ ] Explicit waits; no hardcoded sleeps where possible.
- [ ] Each test creates and quits its own WebDriver per browser.
