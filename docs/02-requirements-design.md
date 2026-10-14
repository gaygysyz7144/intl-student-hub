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
[Insert ER diagram here.]

## 4. Architecture
Vue front end -> REST API (Spring Boot: controller, service, mapper) -> MySQL.
[Insert architecture diagram here.]

## 5. API description
| Method | URL | Purpose | Auth |
|---|---|---|---|
| POST | /api/auth/register | Register | No |
| POST | /api/auth/login | Log in, returns JWT | No |
| GET | /api/posts | List and search posts | No |
| POST | /api/posts | Create a post | Yes |
| GET | /api/posts/{id} | Post details | No |
| PUT | /api/posts/{id} | Update own post | Yes |
| DELETE | /api/posts/{id} | Delete own post | Yes |
| POST | /api/favorites/{postId} | Bookmark | Yes |
| GET | /api/messages | My messages | Yes |
| POST | /api/messages | Send a message | Yes |
| GET | /api/recommendations | Recommended posts | Yes |

(Extend this table as endpoints are added.)
