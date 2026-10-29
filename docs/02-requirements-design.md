# Requirements and Design

## 1. User stories
| ID | As a... | I want to... | So that... | Priority |
|---|---|---|---|---|
| US-01 | new student | register and log in | I have my own account | Must |
| US-02 | student | publish a post in a category (housing, second-hand, event, help, language) | others can see my offer or request | Must |
| US-03 | student | search and filter posts by category, city and keyword | I find what I need quickly | Must |
| US-04 | student | bookmark posts | I can find useful guides later | Should |
| US-05 | student | send private messages | I can contact the poster | Should |
| US-06 | student | choose need tags (renting, learn Chinese, same city) | I get relevant recommendations | Should |
| US-07 | student | get a senior student suggested for my help request | I get experienced help | Could |
| US-08 | poster | be reminded when my post is outdated | old posts do not mislead people | Could |
| US-09 | admin | remove inappropriate posts | the platform stays safe | Should |

## 2. Feature list
Register/login (JWT), post CRUD, search and filter, favorites, messages, tag-based recommendation, help-request keyword matching, outdated-post detection, admin moderation.

## 3. Data model
See `database/schema.sql`. Tables: user, tag, user_tag, post, post_tag, favorite, message.
```mermaid
erDiagram
    user ||--o{ post : writes
    user ||--o{ favorite : bookmarks
    post ||--o{ favorite : "is bookmarked in"
    user ||--o{ message : sends
    user ||--o{ message : receives
    user ||--o{ user_tag : has
    tag  ||--o{ user_tag : "is used by"
    post ||--o{ post_tag : has
    tag  ||--o{ post_tag : "is used by"

    user {
        BIGINT id PK
        VARCHAR username UK
        VARCHAR email UK
        VARCHAR password_hash
        VARCHAR nationality
        VARCHAR city
        VARCHAR role
        TINYINT is_senior
        DATETIME created_at
    }
    post {
        BIGINT id PK
        BIGINT user_id FK
        VARCHAR category
        VARCHAR title
        TEXT content
        DECIMAL price
        VARCHAR city
        VARCHAR status
        DATETIME created_at
        DATETIME updated_at
    }
    tag {
        BIGINT id PK
        VARCHAR name UK
    }
    user_tag {
        BIGINT user_id PK
        BIGINT tag_id PK
    }
    post_tag {
        BIGINT post_id PK
        BIGINT tag_id PK
    }
    favorite {
        BIGINT user_id PK
        BIGINT post_id PK
        DATETIME created_at
    }
    message {
        BIGINT id PK
        BIGINT sender_id FK
        BIGINT receiver_id FK
        VARCHAR content
        TINYINT is_read
        DATETIME created_at
    }
```All foreign keys use ON DELETE CASCADE: deleting a user also deletes their posts, favorites and messages. user_tag, post_tag and favorite are join tables for many-to-many relations.

## 4. Architecture
Vue front end -> REST API (Spring Boot: controller, service, mapper) -> MySQL.
```mermaid
flowchart LR
    Vue["Vue 3 + Axios (planned)"] -->|"HTTP + JSON"| Interceptor
    subgraph Backend["Spring Boot backend"]
        Interceptor["JwtInterceptor"] --> Controller["controller"]
        Controller --> Service["service"]
        Service --> Mapper["mapper (MyBatis)"]
        Service -.->|"ApiException"| Handler["GlobalExceptionHandler"]
    end
    Mapper --> DB[("MySQL intl_hub")]
```

The interceptor checks the JWT and puts userId and role on the request. Controllers only pass data on, business rules (ownership, validation) live in services, and SQL lives only in mappers with `#{}` placeholders.

## 5. API description
| Method | URL | Purpose | Auth | Status |
|---|---|---|---|---|
| POST | /api/auth/register | Register | No | Implemented |
| POST | /api/auth/login | Log in, returns JWT | No | Implemented |
| GET | /api/users/me | Current user | Yes | Implemented |
| GET | /api/posts | List and search posts (category, city, keyword, page, size) | No | Implemented |
| POST | /api/posts | Create a post | Yes | Implemented |
| GET | /api/posts/{id} | Post details | No | Implemented |
| PUT | /api/posts/{id} | Update own post (owner or admin) | Yes | Implemented |
| DELETE | /api/posts/{id} | Delete own post (owner or admin) | Yes | Implemented |
| POST | /api/favorites/{postId} | Bookmark | Yes | Planned |
| GET | /api/messages | My messages | Yes | Planned |
| POST | /api/messages | Send a message | Yes | Planned |
| GET | /api/recommendations | Recommended posts | Yes | Planned |

Errors are returned as `{"error": "..."}`. Validation errors also include a `fields` object, for example `{"fields": {"title": "..."}, "error": "Validation failed"}`. Verified status codes: register 201 (duplicate 409, invalid input 400), create post 201 (invalid input 400, no token 401), update/delete someone else's post 403, missing or deleted post 404, delete 204.Update 2026-10-28: read-only post endpoints (GET /api/posts and GET /api/posts/{id}) are also public so that visitors can browse without an account; all other /api/** URLs need a valid token.

## 6. Design decisions
**Authentication (decided 2026-10-15):** passwords are hashed with BCrypt (spring-security-crypto). Login returns a signed JWT (HS256, jjwt, 24 h). A HandlerInterceptor checks the token on every /api/** URL except /api/auth/**. Full Spring Security is not used, to keep the configuration small; it can be added later if complex roles are needed. The JWT secret lives in the git-ignored application-local.yaml.
