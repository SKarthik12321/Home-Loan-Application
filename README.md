# Home Loan Application

Monolithic **Spring Boot 3** application: REST + **Thymeleaf**, **MySQL** (via `.env`), **JWT**, **JPA/Hibernate**, **Bean Validation**, **global exception handling**, **AOP**, **Swagger UI**, **Actuator**, **Docker Compose**, **Maven** (PMD + Sonar plugins), **JUnit**, **Postman** collection.

---

## Branch naming (what you asked for)

Use **short, topic-based** feature branches — for example:

- `feature/auth`
- `feature/loan-api`
- `feature/docker`

Do **not** need prefix patterns like `feature/UC1-*` unless your course mandates them; an instructor diagram may illustrate **order of work**, not literal branch names.

Suggested layout:

| Branch | Role |
|--------|------|
| `main` | Minimal README-only bootstrap (optional-for-submission hygiene). |
| `dev` | Integration branch with the **full** runnable application (this is where graders usually look). |
| `feature/*` | Short-lived branches merged into `dev` via PR. |

---

## Prerequisites

- JDK **17**
- **MySQL 8** (local or Docker)
- **Docker** (optional; see Docker section)

---

## Secrets — never commit real passwords

```bash
cp .env.example .env
# Edit .env: MYSQL_ROOT_PASSWORD, SPRING_DATASOURCE_*, JWT_SECRET (≥ 32 chars)
```

`.env` is gitignored; values are injected via Spring (`spring-dotenv` + `application.yml` placeholders).

---

## Create database (MySQL CLI — prompts for password)

```bash
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS home_loan_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"
```

Use password `root` if that matches your install / `.env`.

---

## Run locally (IntelliJ terminal)

```bash
./mvnw clean verify
./mvnw spring-boot:run
```

- **Swagger UI:** http://localhost:8080/swagger-ui.html  
- **Thymeleaf:** http://localhost:8080/ and http://localhost:8080/ui/applications  
- **Health:** http://localhost:8080/actuator/health  

**Demo users** (seeded): `demo` / `demo-pass-123`, `admin` / `admin-pass-123`. Log in via `POST /api/v1/auth/login`, then **Authorize** in Swagger with `Bearer <token>`.

---

## Docker

```bash
cp .env.example .env
docker compose build
docker compose up -d
docker compose ps
docker compose logs -f app
docker compose down
```

---

## Git remote & conventional commits

Remote:

```text
https://github.com/SKarthik12321/Home-Loan-Application.git
```

Examples:

```bash
git checkout dev
git pull origin dev
git checkout -b feature/your-topic
git commit -m "feat: add loan listing filters"
git push -u origin feature/your-topic
```

Prefixes: `feat:`, `fix:`, `docs:`, `refactor:`, `chore:`.

---

## Quality tooling

```bash
./mvnw pmd:check
./mvnw sonar:sonar -Dsonar.login=<SONAR_TOKEN>
```

---

## AWS (typical)

Ship the JAR or Docker image to **ECS/EC2**, database on **RDS MySQL**, secrets in **Secrets Manager** — same variables as `.env.example`.

---

## Zip export

From the project root, excluding build output:

```bash
cd ..
zip -r Home-Loan-Application.zip Home-Loan-Application -x "Home-Loan-Application/target/*"
```

Import the folder into IntelliJ as **Maven** project.
