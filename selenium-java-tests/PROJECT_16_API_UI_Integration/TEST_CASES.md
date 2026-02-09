# Test Cases — PROJECT_16: API + UI Integration

**Project:** API and UI in same flow (e.g. JSONPlaceholder + browser)  
**Document Version:** 1.0  
**Last Updated:** 2025-02-08  
**Scope:** Create/read/update/delete via API; verify data in UI; consistency and full flow.

---

## 1. API Setup (Create)

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-API-001 | Create resource via POST API | API base URL from config. | 1. POST to /posts with body: title, body, userId.<br>2. Assert response status 201 (or 200).<br>3. Assert response has id and title matches request. | Resource created; response contains id and given title. | Critical | API |

---

## 2. UI Verification (JSON)

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-UI-001 | Verify JSON displayed in UI | Browser; API base URL. | 1. Navigate to GET /posts/1 in browser (JSON view).<br>2. Wait for page load.<br>3. Parse JSON from page. | JSON valid; contains keys "id" and "title". | High | UI |

---

## 3. API Setup + UI Verify (Integration)

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-INT-001 | Create via API then verify in UI | API client + browser. | 1. POST create resource (title, body, userId).<br>2. Assert creation (id, title).<br>3. Navigate in UI to resource URL (e.g. /posts/1 if using existing for demo).<br>4. Verify JSON in UI has id and title. | API creation success; UI shows valid JSON with expected structure. | Critical | Integration |
| TC-INT-002 | Update via API then verify in UI | Same. | 1. PUT/PATCH resource (e.g. id 1) with new title/body.<br>2. Assert update response.<br>3. Open same resource in UI; verify JSON. | Update response correct; UI shows valid JSON (note: JSONPlaceholder may not persist). | High | Integration |
| TC-INT-003 | Verify in UI then delete via API | Same. | 1. Open resource in UI; verify JSON valid and has id.<br>2. DELETE resource via API.<br>3. (If API persists delete) verify resource gone. | UI pre-delete valid; DELETE call succeeds; for demo API, document limitation. | Medium | Integration |

---

## 4. Complete API–UI Flow

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-FLOW-001 | Full flow: CREATE → READ → UI → UPDATE → UI → DELETE | API client + browser. | 1. CREATE via API; assert id, title.<br>2. READ same resource via API; assert.<br>3. Open resource in UI; assert JSON has id, title.<br>4. UPDATE via API; assert.<br>5. Reload UI; assert JSON structure.<br>6. DELETE via API (cleanup). | All steps pass; data consistency between API and UI where API persists. | Critical | Integration |
| TC-FLOW-002 | Create multiple resources and verify list in UI | Same. | 1. Create 3 resources via API.<br>2. GET all /posts via API.<br>3. Navigate to /posts in UI.<br>4. Verify UI shows array; optionally check created ids in list. | List endpoint returns data; UI shows valid JSON array. | High | Integration |

---

## 5. Data Consistency

| TC_ID | Summary | Preconditions | Steps | Expected Result | Priority | Type |
|-------|---------|---------------|-------|-----------------|----------|------|
| TC-SYNC-001 | API and UI data consistency | API + browser. | 1. GET resource by id via API (e.g. /posts/1).<br>2. Open same resource URL in UI; get JSON from page.<br>3. Compare id, title, body. | API and UI values match for id, title, body. | Critical | Integration |
| TC-SYNC-002 | Real-time sync concept (create then UI) | Same. | 1. CREATE via API.<br>2. Immediately open resource in UI (or existing id for demo).<br>3. Verify UI shows valid JSON with id and title. | Demonstrates sync concept; UI shows expected structure. | Medium | Integration |

---

## Notes

- **JSONPlaceholder:** Does not persist POST/PUT/DELETE; use existing ids (e.g. 1) for UI verification where needed; document in README.
- **API client:** Use RestAssured or Java HTTP client; base URL from config.
- **UI:** Page object to navigate, wait for load, parse JSON from page body.

---

## Checklist (pre-implementation)

- [ ] Base URL from config/env.
- [ ] No secrets in code; no hardcoded hosts.
- [ ] API and UI steps in same test where required; clear assertions per step.
- [ ] Timeout for full flow tests (e.g. 60s) configurable.
