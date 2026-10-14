# Intl Student Hub

Life assistance and sharing platform for new international students in China
(Course project: Team Standards Project Practicum, Topic 2).

## What it does
- Publish and search posts: shared rentals, second-hand items, events and travel, help requests, language partners
- Private messages and post favorites
- Lightweight intelligent module: tag-based recommendation, keyword matching of help requests to senior students, detection of outdated posts

## Tech stack
| Layer | Technology |
|---|---|
| Backend | Java 17, Spring Boot 4.0.8, MyBatis, MySQL |
| Frontend | Vue 3, Axios |
| Auth | Spring Security, JWT, BCrypt |
| Tools | Git/GitHub, Maven, IntelliJ IDEA |

## Repository layout
```
backend/    Spring Boot application
frontend/   Vue application
database/   schema.sql (tables + seed tags)
docs/       standards, requirements, tests, AI usage log, meetings, weekly reports
.github/    pull request template
```

## How to run (fill in as the project grows)
1. Create the database: `mysql -u root -p < database/schema.sql`
2. Copy `backend/src/main/resources/application-local.yaml.example` to `application-local.yaml` and set your own password (this file is git-ignored).
3. Backend: `cd backend && ./mvnw spring-boot:run`
4. Frontend: `cd frontend && npm install && npm run dev`

## AI usage
See `docs/ai-usage-convention.md` and `docs/04-ai-usage-log.md`. AI-generated code is marked with `// AI-assisted`.
