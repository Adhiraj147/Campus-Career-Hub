# Campus Placement and Internship Portal
## Week 2 - Database Design

## 1. Introduction

During Week 2, the database structure for the Campus Placement and
Internship Portal was designed.

The database is responsible for storing user accounts, student
information, company information, job and internship opportunities,
applications and related information.

MySQL was selected as the database management system for the project.

---

## 2. Database Objectives

The main objectives of the database design are:

- Store user authentication information.
- Store student profile information.
- Store recruiter and company information.
- Store job and internship opportunities.
- Store student applications.
- Track application status.
- Maintain relationships between different modules.
- Reduce duplicate data.
- Maintain data consistency and integrity.

---

## 3. Proposed Database Name

```text
campus_placement_db


4. Main Database Tables

The planned database contains the following major tables:

users
students
companies
jobs
applications
5. Users Table

The users table stores common login and account information.

Main Fields
Field	Description
user_id	Unique identifier for the user
email	User's email address
password_hash	Encrypted/hashed password
role	User role
status	Account status
created_at	Account creation date

The user role can identify whether the account belongs to a student,
recruiter or administrator.

6. Students Table

The students table stores student-specific information.

Main Fields
Field	Description
student_id	Unique student identifier
user_id	Reference to users table
first_name	Student first name
last_name	Student last name
roll_no	College roll number
phone	Contact number
course	Student course
branch	Student branch
graduation_year	Expected graduation year
resume_path	Resume location

The user_id connects a student profile with its user account.

7. Companies Table

The companies table stores recruiter and company information.

Main Fields
Field	Description
company_id	Unique company identifier
user_id	Reference to users table
company_name	Name of company
description	Company description
website	Company website
location	Company location
8. Jobs Table

The jobs table stores job and internship opportunities.

Main Fields
Field	Description
job_id	Unique job identifier
company_id	Reference to company
title	Job/internship title
description	Opportunity description
job_type	Job or internship
location	Job location
salary	Salary/stipend
eligibility	Eligibility requirements
deadline	Application deadline
status	Posting status
9. Applications Table

The applications table stores applications submitted by students.

Main Fields
Field	Description
application_id	Unique application identifier
student_id	Reference to student
job_id	Reference to job
status	Application status
applied_at	Application date

Possible application statuses include:

APPLIED
SHORTLISTED
REJECTED
SELECTED
10. Database Relationships

The major relationships are:

Users
  |
  +------ Students
  |
  +------ Companies


Companies
    |
    +------ Jobs
              |
              +------ Applications
                         |
                         +------ Students
Relationship Description
One user can have one student profile.
One user can have one company/recruiter profile.
One company can post multiple jobs or internships.
One student can submit multiple applications.
One job can receive multiple applications.
Applications connect students with jobs.
11. Primary Keys

The following fields are planned as primary keys:

users.user_id
students.student_id
companies.company_id
jobs.job_id
applications.application_id

Primary keys uniquely identify records in each table.

12. Foreign Keys

The following foreign-key relationships are planned:

students.user_id
        ↓
users.user_id

companies.user_id
        ↓
users.user_id

jobs.company_id
        ↓
companies.company_id

applications.student_id
        ↓
students.student_id

applications.job_id
        ↓
jobs.job_id
13. Data Integrity

The database design will use:

Primary keys
Foreign keys
Unique constraints
NOT NULL constraints
Appropriate data types
Referential integrity

These constraints help maintain reliable and consistent data.

14. Database Design Outcome

At the end of Week 2, the major database entities, fields,
relationships and constraints were planned.

The database design provides the foundation for implementing the
backend and database connectivity in the following development phase.