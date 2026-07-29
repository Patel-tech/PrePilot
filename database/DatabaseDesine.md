# Database Overview

                         USERS
                           │
            ┌──────────────┼──────────────┐
            │              │              │
         Documents      Chat Sessions   Study Plans
            │              │
            │              │
      Document Chunks   Chat Messages
            │
            │
     Document Embeddings

Users
├── Resume
├── Quiz Attempts
├── Mock Interviews
├── Flashcards
├── Bookmarks
├── Analytics
└── Audit Logs

# Users Table

| Column          | Type                | Description         |
| --------------- | ------------------- | ------------------- |
| id              | BIGINT PK           | User ID             |
| first_name      | VARCHAR(100)        | First Name          |
| last_name       | VARCHAR(100)        | Last Name           |
| email           | VARCHAR(255) UNIQUE | Login Email         |
| password        | VARCHAR(255)        | Encrypted Password  |
| phone           | VARCHAR(20)         | Mobile Number       |
| profile_picture | VARCHAR(500)        | Image URL           |
| account_type    | VARCHAR(20)         | FREE / PREMIUM      |
| status          | VARCHAR(20)         | ACTIVE / BLOCKED    |
| email_verified  | BOOLEAN             | Verification Status |
| created_at      | TIMESTAMP           | Created Time        |
| updated_at      | TIMESTAMP           | Updated Time        |

# Relationship 
User
|
|---- Documents
|
|---- Chat Sessions
|
|---- Quiz Attempts
|
|---- Resume
|
|---- Bookmarks

# Roles

| Column    | Type    |
| --------- | ------- |
| id        | BIGINT  |
| role_name | VARCHAR |

# Relationship
ADMIN

USER

PREMIUM

# user_roles
| user_id |
| role_id |
# Many users can have multiple roles.
User

↓

User Role

↓

Role

# refresh_tokens
| Column      |
| ----------- |
| id          |
| user_id     |
| token       |
| expiry_date |
| revoked     |

# documents
# Uploaded PDFs.
| Column        |
| ------------- |
| id            |
| user_id       |
| file_name     |
| file_type     |
| file_size     |
| storage_path  |
| upload_status |
| created_at    |

# RelationShip 
User

↓

Documents

# document_chunks

One PDF

↓

Many chunks

| Column      |
| ----------- |
| id          |
| document_id |
| chunk_order |
| chunk_text  |

Document

↓

Chunk 1

Chunk 2

Chunk 3

Chunk 4

# document_embeddings

# Stores vectors.

| Column                 |
| ---------------------- |
| id                     |
| chunk_id               |
| embedding VECTOR(1536) |

Chunk

↓

Embedding

# chat_sessions

| Column     |
| ---------- |
| id         |
| user_id    |
| title      |
| created_at |

# Relationship
One user

↓

Multiple chats

# chat_messages

# Stores conversation.

| Column     |
| ---------- |
| id         |
| session_id |
| sender     |
| message    |
| tokens     |
| created_at |


↓

User

↓

AI

↓

User

↓

AI 

# resumes

# Resume upload.

| Column       |
| ------------ |
| id           |
| user_id      |
| resume_name  |
| storage_path |
| uploaded_at  |


# resume_analysis

# AI report.

| Column           |
| ---------------- |
| id               |
| resume_id        |
| strengths        |
| weaknesses       |
| suggested_topics |
| ats_score        |
| created_at       |

# quizzes

# Quiz metadata.

| Column     |
| ---------- |
| id         |
| topic      |
| difficulty |
| created_by |
| created_at |

# quiz_questions

# Questions.

| Column   |
| -------- |
| id       |
| quiz_id  |
| question |
| option_a |
| option_b |
| option_c |
| option_d |
| answer   |

# quiz_attempts

Stores attempts.

| Column          |
| --------------- |
| id              |
| quiz_id         |
| user_id         |
| score           |
| total_questions |
| completed_at    |

# mock_interview_sessions

| Column       |
| ------------ |
| id           |
| user_id      |
| technology   |
| status       |
| started_at   |
| completed_at |


# mock_interview_questions


| Column      |
| ----------- |
| id          |
| session_id  |
| question    |
| ai_answer   |
| user_answer |
| feedback    |
| score       |

# study_plans

Generated roadmap.

| Column         |
| -------------- |
| id             |
| user_id        |
| experience     |
| target_company |
| target_date    |
| generated_plan |


# flashcards


| Column   |
| -------- |
| id       |
| user_id  |
| topic    |
| question |
| answer   |


# bookmarks

Bookmarks.

| Column        |
| ------------- |
| id            |
| user_id       |
| bookmark_type |
| reference_id  |


# analytics

Dashboard.

| Column             |
| ------------------ |
| id                 |
| user_id            |
| quizzes_completed  |
| mock_interviews    |
| questions_asked    |
| documents_uploaded |
| readiness_score    |
| updated_at         |


# audit_logs

Stores activities.

| Column     |
| ---------- |
| id         |
| user_id    |
| action     |
| ip_address |
| device     |
| created_at |


Example

LOGIN

UPLOAD_DOCUMENT

DELETE_DOCUMENT

QUIZ_SUBMITTED

CHAT_CREATED



