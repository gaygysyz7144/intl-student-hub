# Team Standards

**Team:** one-person team running the full team process (agreed with the instructor).
**Member:** [Your name], [Student ID]

## 1. Roles
All roles are held by one person. Each weekly report states which role was the main focus that week.

| Role | Responsibility |
|---|---|
| Product Manager | Requirements, priorities, Sprint planning, milestones |
| Backend | Spring Boot, MyBatis, database, security |
| Frontend | Vue pages, API integration |
| AI / Algorithm | Recommendation, keyword matching, outdated-post detection |
| Test / Documentation | Test plan, test cases, README, docs |

## 2. Meetings
- **Daily stand-up (written, 15 min max):** what I did, what I will do, what blocks me. Kept in the weekly report.
- **Sprint review (end of each Sprint):** demo to myself, compare against Sprint goals, list what carries over.
- Minutes go in `docs/meetings/` using `_template.md`.

## 3. Sprints
| Sprint | Weeks | Goal |
|---|---|---|
| 1 | 8-9 | Requirements, prototype, backend skeleton, register/login |
| 2 | 10-11 | Post CRUD, search, favorites, first Vue pages, working demo |
| 3 | 12-13 | Messages, intelligent module, integration |
| 4 | 14-15 | Testing, AI security tests, demo video, defense prep |

## 4. Code standards
- Java packages: `controller`, `service`, `mapper`, `entity`, `dto`, `config`. No business logic in controllers; no SQL outside mappers.
- Names: classes `PascalCase`, methods and variables `camelCase`, constants `UPPER_SNAKE_CASE`, tables and columns `snake_case`.
- Vue: components `PascalCase`, one component per file.
- Commit messages: `feat:`, `fix:`, `docs:`, `test:`, `refactor:` followed by a short description.

## 5. Branch model
- `main`: stable, tagged at each milestone (`m1` to `m6`).
- `develop`: integration branch.
- `feature/<short-name>`: one branch per task, created from `develop`.
- No direct commits to `main` or `develop`. Everything goes through a pull request into `develop`.

## 6. Code review (one-person version)
Every PR is reviewed before merging:
1. Self-review with the checklist in `.github/pull_request_template.md`, preferably after a break (next day if possible).
2. An AI first-pass review (PR summary, possible bugs) is allowed. The human decision is final and is written in the PR.
3. Review notes stay in the PR as evidence for the process score.

## 7. Documentation standards
Requirements, design, tests and README must be complete and current. Required files are listed in `docs/` (numbered 01 to 04, plus the individual summary).

## 8. Definition of Done
A task is done when: the code runs locally, it was tested, the PR checklist is complete, docs are updated, and any AI-generated code is marked and understood.
