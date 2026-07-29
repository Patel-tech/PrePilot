# Table Relationships

## Overview

This document describes the relationships between all database tables used in the PrepPilot application.

---

## Users → Roles

Relationship:
Many-to-Many

Description:
A user can have multiple roles such as USER or ADMIN.

Tables:
- users
- roles
- user_roles

---

## Users → Refresh Tokens

Relationship:
One-to-Many

Description:
A user can have multiple refresh tokens.

Tables:
- users
- refresh_tokens

---

## Users → Documents

Relationship:
One-to-Many

Description:
Each user can upload multiple documents.

Tables:
- users
- documents

---

## Documents → Document Chunks

Relationship:
One-to-Many

Description:
Each uploaded document is divided into multiple chunks.

Tables:
- documents
- document_chunks

---

## Document Chunks → Document Embeddings

Relationship:
One-to-One

Description:
Each document chunk has one vector embedding.

Tables:
- document_chunks
- document_embeddings

---

## Users → Chat Sessions

Relationship:
One-to-Many

Description:
Each user can create multiple AI chat sessions.

Tables:
- users
- chat_sessions

---

## Chat Sessions → Chat Messages

Relationship:
One-to-Many

Description:
Each session contains multiple messages.

Tables:
- chat_sessions
- chat_messages

---

## Users → Resume

Relationship:
One-to-One

Description:
Each user can upload one active resume.

Tables:
- users
- resumes

---

## Resume → Resume Analysis

Relationship:
One-to-One

Description:
Each uploaded resume has one AI-generated analysis.

Tables:
- resumes
- resume_analysis

---

## Users → Quiz Attempts

Relationship:
One-to-Many

Description:
Users can attempt multiple quizzes.

Tables:
- users
- quiz_attempts

---

## Quiz → Quiz Questions

Relationship:
One-to-Many

Description:
Each quiz contains multiple questions.

Tables:
- quizzes
- quiz_questions

---

## Users → Study Plans

Relationship:
One-to-Many

Description:
Users can generate multiple AI study plans.

Tables:
- users
- study_plans

---

## Users → Flashcards

Relationship:
One-to-Many

Description:
Users can create and manage flashcards.

Tables:
- users
- flashcards

---

## Users → Bookmarks

Relationship:
One-to-Many

Description:
Users can bookmark quizzes, notes, or documents.

Tables:
- users
- bookmarks

---

## Users → Analytics

Relationship:
One-to-One

Description:
Stores user learning statistics.

Tables:
- users
- analytics

---

## Users → Audit Logs

Relationship:
One-to-Many

Description:
Stores user activities such as login, uploads, and quiz submissions.

Tables:
- users
- audit_logs