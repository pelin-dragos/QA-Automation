# Selenium Java Tests — High Priority Projects

Proiecte de automatizare UI cu **Selenium** și **Java** (Maven, JUnit 5), aliniate la JD Senior QA Engineer. Fiecare modul este independent și are documentație de **Test Cases** profesională.

## Structură

```
selenium-java-tests/
├── pom.xml                                    # Parent POM (modules, dependencyManagement)
├── PROJECT_01_Login_Logout_Testing/            # Login success/failure, logout
├── PROJECT_02_Form_Validation/                # Email, password, phone, required fields
├── PROJECT_14_Banking_Application_Testing/    # Banking: login, balance, transfer
├── PROJECT_15_Admin_Panel_Testing/            # Admin CRUD, search, pagination
├── PROJECT_16_API_UI_Integration/             # API + UI în același flux
└── PROJECT_17_BDD_Framework/                    # Cucumber/Gherkin, Sauce Demo
```

## Conținut per proiect

- **pom.xml** — dependențe (Selenium, JUnit; unde e cazul RestAssured, Cucumber)
- **mvnw.cmd** — Maven Wrapper (nu e nevoie de Maven instalat global)
- **TEST_CASES.md** — cazuri de test în format profesional
- **README.md** — descriere și cum rulezi
- **.gitignore** (la root) — exclude `target/`, `.mvn/wrapper/maven/`, `.env`

**Implementate:** PROJECT_01 (Login & Logout), PROJECT_02 (Form Validation), PROJECT_14 (Banking), PROJECT_15 (Admin Panel), PROJECT_16 (API + UI Integration). PROJECT_17 pregătit.

**Selenium:** Toate testele din PROJECT_01, 02, 14, 15 și 16 folosesc **Selenium** (WebDriver, Firefox) pentru pașii de UI. PROJECT_16 folosește în plus **RestAssured** pentru pașii de API; verificarea în browser (JSON) se face tot cu Selenium.

## Cerințe

- Java 17+
- Firefox (testele rulează în Firefox)
- Setează `JAVA_HOME`; Maven se descarcă la prima rulare prin `mvnw.cmd`

## Comenzi

```bash
# PROJECT_01
cd PROJECT_01_Login_Logout_Testing && mvnw.cmd test

# PROJECT_02
cd PROJECT_02_Form_Validation && mvnw.cmd test

# PROJECT_14 (setează BANKING_USERNAME, BANKING_PASSWORD)
cd PROJECT_14_Banking_Application_Testing && mvnw.cmd test

# PROJECT_15 (setează ADMIN_USERNAME, ADMIN_PASSWORD)
cd PROJECT_15_Admin_Panel_Testing && mvnw.cmd test

# PROJECT_16 (API + UI; opțional API_BASE_URL)
cd PROJECT_16_API_UI_Integration && mvnw.cmd test

# Sau de la root (cu Maven instalat)
mvn test -pl PROJECT_01_Login_Logout_Testing
mvn test -pl PROJECT_02_Form_Validation
```

## Reguli (din .cursor/rules)

- Teste independente; fără secrete în cod; aserții clare; așteptări explicite (fără sleep fix).
- Detalii în [TEST_CASES.md](PROJECT_01_Login_Logout_Testing/TEST_CASES.md) per proiect.
