# SQL Tests

Tests that verify data in the database — validation after API operations, setup/cleanup, or integrity checks.

## Objective

- Demonstrate **SQL proficiency** for QA (Senior QA Engineer JD requirement)
- Data verification after POST/PUT/PATCH/DELETE
- Test data setup before execution
- Cleanup after tests (isolation, independence)

## Structure (to be implemented)

```
sql-tests/
├── scripts/                    # Reusable SQL scripts
│   ├── setup/                  # Setup files (INSERT)
│   ├── cleanup/                # Cleanup files (DELETE)
│   └── assertions/             # Verification queries
├── java/                       # (Optional) Java tests with JDBC
│   └── DbValidationTest.java
├── docs/
│   └── Schema.md               # DB schema, relevant tables
├── .env.example                # DB connection (user, host — no passwords)
└── README.md
```

## Test types

1. **Setup**: INSERT test data before execution
2. **Assertion**: SELECT for validation after API operation
3. **Cleanup**: DELETE/TRUNCATE for clean state

## Requirements

- **Do not hardcode passwords** — use `.env` or environment variables
- **Test DB only** — never on production
- **Parameterization** — avoid direct concatenation in SQL (SQL injection prevention)

## Java integration

Can use the same API from `java-rest-api-automation` + JDBC for DB verification after REST requests.
