# Test Cases — PROJECT_15: Admin Panel Testing

**Project:** Admin Panel (e.g. OrangeHRM)  
**Document Version:** 1.0  
**Last Updated:** 2025-02-08  
**Scope:** Admin login, Users CRUD, search, pagination, bulk operations. Test data from config/fixtures.

---

## 1. Login

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-ADMIN-LOGIN-001 | Successful login as admin | Base URL and admin credentials from config (no hardcode). | 1. Navigate to login page.<br>2. Enter admin username and password.<br>3. Click Login. | Login succeeds; isLoggedIn true; dashboard accessible. | Critical | Functional |

---

## 2. Create (CRUD)

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-ADMIN-C-001 | Create a new user | Logged in as admin. | 1. Navigate to Admin → Users (or User Management).<br>2. Click Add User.<br>3. Fill role (e.g. ESS), employee, username, password, status (Enabled) from test data.<br>4. Save. | User created; search by new username finds user; or form submits without error (demo limitations noted). | Critical | Functional |

---

## 3. Read (CRUD)

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-ADMIN-R-001 | View users list | Logged in as admin. | 1. Navigate to User Management.<br>2. Wait for list load. | Users table/list displayed; getUsersCount() > 0. | Critical | Functional |
| TC-ADMIN-R-002 | Search for a user by username | Logged in as admin. | 1. Navigate to User Management.<br>2. Search for "Admin" (or known user).<br>3. Verify results. | Search executes; Admin (or target user) present in results. | High | Functional |
| TC-ADMIN-R-003 | Get user information | Logged in as admin. | 1. Search for a user (e.g. Admin).<br>2. Get user info (username, role, etc.). | User info returned (e.g. username not null); or page loaded for demo. | High | Functional |

---

## 4. Update (CRUD)

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-ADMIN-U-001 | Edit a user | Logged in as admin; optionally create user first. | 1. Navigate to User Management.<br>2. Create user if needed.<br>3. Search and open user; edit (e.g. role/status).<br>4. Save. | Edit flow completes; page loaded; changes reflected if demo allows. | Critical | Functional |

---

## 5. Delete (CRUD)

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-ADMIN-D-001 | Delete a user | Logged in as admin; test user created. | 1. Create user with test data.<br>2. Search user; delete.<br>3. Confirm deletion.<br>4. Search again. | User deleted (not in list) or delete flow executed without crash; demo limitations documented. | Critical | Functional |

---

## 6. Search & Filtering

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-ADMIN-S-001 | Search by username | Logged in as admin. | 1. Open User Management.<br>2. Enter username "Admin"; search. | Results contain Admin or list updated. | High | Functional |
| TC-ADMIN-S-002 | Reset search | Logged in as admin. | 1. Perform a search.<br>2. Click Reset/Clear.<br>3. Verify full list or default state. | Full list or default view restored; no exception. | Medium | Functional |

---

## 7. Pagination

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-ADMIN-P-001 | Pagination exists when applicable | Logged in as admin; multiple users. | 1. Open User Management.<br>2. Check for pagination controls. | hasPagination() returns true/false consistently; no crash. | Medium | Functional |

---

## 8. Bulk Operations

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-ADMIN-B-001 | Bulk delete users | Logged in as admin; 2 test users created. | 1. Create 2 users (unique test data).<br>2. Select both; bulk delete (or delete one by one).<br>3. Verify removal. | Users removed or flow executed; page stable. | High | Functional |

---

## 9. Complete CRUD Flow

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-ADMIN-FLOW-001 | Full CRUD flow | Logged in as admin. | 1. CREATE: Add user with generated test data.<br>2. READ: Search and verify user; get user info.<br>3. UPDATE: Edit user (if supported).<br>4. DELETE: Delete user.<br>5. Verify user no longer in list. | Flow completes; create/read/delete verified where demo allows. | Critical | Integration |

---

## Test Data

- **Admin credentials:** From config/env; not in code.
- **Test user data:** Generated (e.g. unique username, password) via TestDataManager or equivalent; no production data.
- **Base URL:** From config (e.g. OrangeHRM demo URL).

---

## Checklist (pre-implementation)

- [ ] Page objects: LoginPage, AdminDashboardPage, UsersManagementPage.
- [ ] Reusable test data generator; same data not shared across tests (independence).
- [ ] Timeouts configurable; explicit waits for table and modals.
- [ ] Demo limitations (e.g. OrangeHRM create/delete) documented in README or test comments.
