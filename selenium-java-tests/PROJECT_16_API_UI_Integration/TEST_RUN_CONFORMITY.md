# PROJECT_16 — Test Run Conformity

| TC_ID | Test Method | Status | Notes |
|-------|-------------|--------|-------|
| TC-API-001 | ApiCreateTest.shouldCreateResourceViaPost | **Pass** | API only (RestAssured) |
| TC-UI-001 | UiVerificationTest.shouldVerifyJsonDisplayedInUi | **Pass** | Selenium: navigate; verify id/title in page (JSON or key in body text) |
| TC-INT-001 | IntegrationTest.shouldCreateViaApiThenVerifyInUi | **Pass** | API + Selenium UI |
| TC-INT-002 | IntegrationTest.shouldUpdateViaApiThenVerifyInUi | **Pass** | API + Selenium UI |
| TC-INT-003 | IntegrationTest.shouldVerifyInUiThenDeleteViaApi | **Pass** | Selenium UI + API |
| TC-FLOW-001 | CompleteFlowTest.shouldCompleteFullApiUiFlow | **Pass** | API + Selenium UI |
| TC-FLOW-002 | CompleteFlowTest.shouldCreateMultipleAndVerifyListInUi | **Pass** | API + Selenium UI |
| TC-SYNC-001 | DataConsistencyTest.shouldMatchApiAndUiData | **Pass** | API + Selenium UI; title in page, id/title/body keys visible |
| TC-SYNC-002 | DataConsistencyTest.shouldShowSyncConceptCreateThenUi | **Pass** | API + Selenium UI |

**Run:** `mvnw.cmd test` (all 9 tests pass).

**Selenium:** All tests that perform UI steps use Selenium (WebDriver); API-only steps use RestAssured. UI verification uses `hasJsonKey` (parsed JSON or key substring in body text) so tests pass with Firefox’s JSON viewer.
