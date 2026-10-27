# Weekly Report: Week [n]  (Sprint 1)

**Main role this week:** Backend

## Completed
- Registration, login and JWT authentication (PR #3)
- Post CRUD, search (keyword, category, city) and pagination (PR #4)
- Manual tests passed: login, create post, search by keyword, category and city (T-02, T-03, T-04)
- Test plan and AI usage log updated (PR #5)

## In progress
- 
- Automated tests for AuthService and PostService

## Problems and how I solved them
- I could not explain the ownership check in PostService at first. I traced userId and role from JwtInterceptor to the controller and then to the service.
- Search could not be tested because the database had no posts. I registered a test user, created a post and then ran the search tests.

## Plan for next week
- Automated tests
- Start Sprint 2 features (favorites, messages)

## PRs merged this week
- #3 feature/auth, #4 feature/posts, #5 docs/posts-test-and-log

## AI usage this week (also logged in 04-ai-usage-log.md)
- Claude: posts feature, PR review, test planning, documentation