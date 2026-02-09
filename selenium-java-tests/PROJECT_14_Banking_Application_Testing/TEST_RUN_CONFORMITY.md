# PROJECT_14 — Test Run Conformity

**Last run:** With `BANKING_USERNAME=john`, `BANKING_PASSWORD=demo` (ParaBank). **Tests: 12 run, 0 failures, 0 errors, 1 skipped.**

| TC_ID | Test Method | Status | Notes |
|-------|-------------|--------|-------|
| TC-BANK-LOGIN-001 | LoginTest.shouldLoginWithValidCredentials | Pass | Requires BANKING_USERNAME + BANKING_PASSWORD |
| TC-BANK-LOGIN-002 | LoginTest.shouldFailLoginWithInvalidCredentials | Skip | ParaBank public accepts any credentials; test skipped (N/A) |
| TC-BANK-DASH-001 | DashboardTest.shouldLoadDashboardAfterLogin | Pass | Requires credentials |
| TC-BANK-DASH-002 | DashboardTest.shouldViewAccountsOverview | Pass | Requires credentials |
| TC-BANK-DASH-003 | DashboardTest.shouldGetAccountNumbers | Pass | Requires credentials |
| TC-BANK-DASH-004 | DashboardTest.shouldGetAccountBalance | Pass | Requires credentials |
| TC-BANK-DASH-005 | DashboardTest.shouldLogoutFromDashboard | Pass | Requires credentials |
| TC-BANK-STMT-001 | StatementsTest.shouldViewAccountStatements | Pass | Requires credentials |
| TC-BANK-STMT-002 | StatementsTest.shouldVerifyBalanceDisplayed | Pass | Requires credentials |
| TC-BANK-XFER-001 | TransferFundsTest.shouldTransferFundsBetweenAccounts | Pass | Requires credentials; ≥2 accounts |
| TC-BANK-XFER-002 | TransferFundsTest.shouldRejectInvalidTransferAmount | Pass | Requires credentials |
| TC-BANK-FLOW-001 | CompleteFlowTest.shouldCompleteFullBankingFlow | Pass | Requires credentials |

**Run:** `mvnw.cmd test` (or `./mvnw test`) with `BANKING_USERNAME` and `BANKING_PASSWORD` set for full coverage.
