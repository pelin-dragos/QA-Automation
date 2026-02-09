# Test Cases — PROJECT_14: Banking Application Testing

**Project:** Banking Application (e.g. ParaBank)  
**Document Version:** 1.0  
**Last Updated:** 2025-02-08  
**Scope:** Login, dashboard, balance, transfer, statements; credentials from env only.

---

## 1. Login

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-BANK-LOGIN-001 | Successful login with valid credentials | Base URL and credentials from env. | 1. Navigate to login page.<br>2. Enter username and password from env.<br>3. Click Login. | Login succeeds; isLoggedIn() true; dashboard/welcome visible. | Critical | Functional |
| TC-BANK-LOGIN-002 | Login fails with invalid credentials | On login page. | 1. Enter invalid_user / invalid_password.<br>2. Click Login. | Error message displayed; user not logged in. | High | Negative |

---

## 2. Dashboard

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-BANK-DASH-001 | Navigate to dashboard after login | User credentials in env. | 1. Login with env credentials.<br>2. Wait for dashboard load. | Dashboard loaded; isLoggedIn true; welcome message present. | Critical | Functional |
| TC-BANK-DASH-002 | View Accounts Overview | User logged in. | 1. From dashboard, click Accounts Overview. | Accounts Overview page loads; no error. | High | Functional |
| TC-BANK-DASH-003 | Get account numbers | User logged in. | 1. From dashboard, get list of account numbers. | At least one account number returned. | High | Functional |
| TC-BANK-DASH-004 | Get account balance | User logged in. | 1. From dashboard, get account balance. | Balance value returned (may be 0); not null. | Critical | Functional |
| TC-BANK-DASH-005 | Logout from dashboard | User logged in. | 1. Click Logout. | Logout succeeds; redirected to login or home. | High | Functional |

---

## 3. Balance & Statements

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-BANK-STMT-001 | View account statements | User logged in; at least one account. | 1. Login; navigate to account/statements.<br>2. Verify statements/transactions visible or list present. | Statements or transaction list available; no crash. | Critical | Functional |
| TC-BANK-STMT-002 | Verify account balance displayed | User logged in. | 1. Get balance from dashboard.<br>2. Verify balance is displayed correctly. | Balance not null; format consistent. | High | Functional |

---

## 4. Transfer Funds

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-BANK-XFER-001 | Transfer funds between two accounts | User logged in; at least 2 accounts; sufficient balance. | 1. Navigate to Transfer Funds.<br>2. Select from account, to account, amount (e.g. 10.00).<br>3. Submit transfer. | Success message or transfer confirmation; or clear error (e.g. insufficient funds). | Critical | Functional |
| TC-BANK-XFER-002 | Transfer with invalid amount (negative/zero) | User logged in; 2 accounts. | 1. Navigate to Transfer Funds.<br>2. Enter negative or zero amount.<br>3. Submit. | Error message or validation; transfer not completed. | High | Negative |

---

## 5. Complete Flow

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-BANK-FLOW-001 | Full banking flow | Credentials in env. | 1. Login.<br>2. Verify dashboard loaded.<br>3. Get balance (not null).<br>4. Get account numbers (count > 0).<br>5. Navigate to Transfer Funds (link works). | All steps pass; transfer page loads if link clicked. | High | Integration |

---

## Security & Config

- **Credentials:** Only from environment variables or config; never in code. Use `.env.example` with placeholder names.
- **Base URL:** From config/env.
- **Sensitive data:** Do not log passwords or full account numbers; mask in reports.

---

## Checklist (pre-implementation)

- [ ] Skip tests gracefully when credentials not set (e.g. test.skip with message).
- [ ] Page objects: LoginPage, DashboardPage, TransferPage, StatementsPage (as needed).
- [ ] Explicit waits for dashboard and post-transfer state.
- [ ] Assertions with clear messages (e.g. "Expected at least one account after login").
