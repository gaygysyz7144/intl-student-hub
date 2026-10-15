# AI Usage Log

One row per meaningful use. This supports the "AI collaboration reflection" score.

| Date | Tool | Task / stage | Key prompt idea | Output used? | How I verified | Notes (problems, boundaries) |
|---|---|---|---|---|---|---|
| 2026-10-14 | Claude | Repo scaffold | Asked for a full team-process starter repo (standards, PR template, AI convention, docs templates) for a one-person team | Yes, then edited | Read each file, checked the structure in git, filled in my own details | Plan assumes a team; adapted code review to a one-person version |
| 2026-10-14 | Claude | Database schema | Asked for tables for the student platform (users, posts, tags, favorites, messages) | Yes | Ran database/schema.sql and confirmed 7 tables with SHOW TABLES | Design must still be checked against requirements before use |
| 2026-10-14 | Claude | Backend skeleton | start.spring.io was not available; asked for the Maven project files | Yes (pom.xml, main class, config files) | Opened in IntelliJ, app starts on Java 17 with Spring Boot 4.0.8; git status showed the password file is ignored | Could not compare with Initializr output; verified by a successful start |
| 2026-10-14 | Claude | Git and GitHub workflow | Asked how to push, open a PR and set the default branch | Yes | Followed the steps; checked branches with git branch -a | I renamed main with the pencil icon instead of switching the default branch (arrow icon). Fixed with git commands. Lesson: read the icon labels before clicking |
