# Postman Environments

Environment variables for Postman. **Do not include real values** (API keys, passwords) in committed files.

## Structure (to be implemented)

```
environments/
├── demo.postman_environment.json    # Demo environment (placeholders)
├── .env.example                     # Required variables (no real values)
└── README.md
```

## Recommended variables

| Variable     | Description            | Example placeholder   |
|--------------|------------------------|-----------------------|
| `base_url`   | API base URL           | `https://api.example.com` |
| `auth_token` | JWT/OAuth token        | `{{token}}` (set from pre-request) |
| `api_key`    | API key (if applicable)| `your_api_key_here`   |

## Security

- Real values: set in local Postman or via environment variables
- `.env.example`: variable names only, no secrets
- `.gitignore`: exclude files that may contain credentials
