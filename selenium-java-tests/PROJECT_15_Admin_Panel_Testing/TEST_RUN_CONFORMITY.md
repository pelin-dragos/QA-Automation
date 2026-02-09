# PROJECT_15 — Test Run Conformity

| TC_ID | Test Method | Status | Notes |
|-------|-------------|--------|-------|
| TC-ADMIN-LOGIN-001 | LoginTest.shouldLoginAsAdmin | — | Requires ADMIN_USERNAME + ADMIN_PASSWORD |
| TC-ADMIN-C-001 | CreateUserTest.shouldCreateNewUser | — | Requires credentials |
| TC-ADMIN-R-001 | ReadUsersTest.shouldViewUsersList | — | Requires credentials |
| TC-ADMIN-R-002 | ReadUsersTest.shouldSearchUserByUsername | — | Requires credentials |
| TC-ADMIN-R-003 | ReadUsersTest.shouldGetUserInformation | — | Requires credentials |
| TC-ADMIN-U-001 | UpdateUserTest.shouldEditUser | — | Requires credentials |
| TC-ADMIN-D-001 | DeleteUserTest.shouldDeleteUser | — | Requires credentials |
| TC-ADMIN-S-001 | SearchFilterTest.shouldSearchByUsername | — | Requires credentials |
| TC-ADMIN-S-002 | SearchFilterTest.shouldResetSearch | — | Requires credentials |
| TC-ADMIN-P-001 | PaginationTest.shouldHavePaginationWhenApplicable | — | Requires credentials |
| TC-ADMIN-B-001 | BulkOperationsTest.shouldBulkDeleteUsers | — | Requires credentials |
| TC-ADMIN-FLOW-001 | CompleteCrudFlowTest.shouldCompleteFullCrudFlow | — | Requires credentials |

**Run:** `mvnw.cmd test` with `ADMIN_USERNAME` and `ADMIN_PASSWORD` set. Update Status and Notes after execution.
