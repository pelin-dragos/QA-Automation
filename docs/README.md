# Portfolio site

This folder contains the portfolio site for the QA-Automation project. It is meant to be served via **GitHub Pages** from this repository.

## Structure (modular)

```
docs/
├── index.html              # Home: hero, project cards, test types, CTA
├── css/
│   ├── common.css          # Variables, reset, header, footer, buttons, tables, code
│   ├── home.css            # Homepage: hero, cards, test list, repo CTA
│   └── project.css         # Project detail pages: hero, blocks, test categories, run block
├── projects/
│   ├── java-rest-api.html  # Java REST API: test categories, structure, how to run
│   ├── postman.html        # Postman collection: purpose, structure, how to run
│   └── playwright.html    # Playwright: projects list, structure, how to run
└── README.md
```

- **index.html** uses `css/common.css` + `css/home.css`.
- Each project page uses `css/common.css` + `css/project.css`.
- Add new project pages in `projects/` and link them from the home cards.

## Deploy on GitHub Pages

1. In the **QA-Automation** repository on GitHub: **Settings** → **Pages**.
2. Under **Build and deployment**:
   - **Source**: “Deploy from a branch”.
   - **Branch**: `main` (or your default branch).
   - **Folder**: `/docs`.
3. Save. After 1–2 minutes the site will be available at:
   - `https://<USERNAME>.github.io/QA-Automation/`

## Customization

- **GitHub username**: in `index.html` and in each `projects/*.html`, replace the username in repo links if needed.
- **Content**: edit the relevant HTML and CSS files. Keep common styles in `css/common.css`, page-specific in `home.css` or `project.css`.
- **New project page**: add e.g. `projects/my-project.html`, same header/footer/breadcrumb pattern, link from home card.

## Run locally

From the `docs/` folder:

```bash
npx serve .
# or
python -m http.server 8000
```

Then open `http://localhost:8000` (or the port shown). Use a local server so that paths like `css/common.css` and `projects/java-rest-api.html` resolve correctly.
