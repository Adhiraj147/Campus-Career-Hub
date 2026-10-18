# Campus Placement and Internship Portal
## Week 1 - System Overview

## 1. System Overview

The Campus Placement and Internship Portal is a web-based application
that connects students, recruiters and administrators through a
centralized placement management system.

The system provides different functionalities according to the user's
role.

---

## 2. High-Level System Workflow

The basic workflow of the system is:

```text
                    Campus Placement
                    & Internship Portal
                            |
          +-----------------+-----------------+
          |                 |                 |
       Student           Recruiter          Admin
          |                 |                 |
       Login              Login             Login
          |                 |                 |
    Student Profile    Company Profile    Admin Dashboard
          |                 |                 |
   View Opportunities  Post Opportunities   Manage Users
          |                 |                 |
        Apply          View Applications    Manage Jobs
          |                 |                 |
 Track Application     Manage Candidates    Monitor System

 
 Student Workflow
Registration
     |
     v
Login
     |
     v
Student Dashboard
     |
     v
Create / Update Profile
     |
     v
View Jobs & Internships
     |
     v
Apply
     |
     v
Track Application Status


Recruiter Workflow
Registration
     |
     v
Login
     |
     v
Recruiter Dashboard
     |
     v
Company Profile
     |
     v
Post Job / Internship
     |
     v
View Applications
     |
     v
Manage Candidates



Admin Workflow
Login
   |
   v
Admin Dashboard
   |
   +----> Manage Students
   |
   +----> Manage Recruiters
   |
   +----> Manage Companies
   |
   +----> Manage Jobs
   |
   +----> Monitor Applications




   Basic Data Flow
User
 |
 v
Web Interface
 |
 v
JSP / Servlet
 |
 v
Business Logic
 |
 v
DAO Layer
 |
 v
JDBC
 |
 v
MySQL Database



 Planned Architecture

The application will follow a layered structure:

Presentation Layer
       |
       v
JSP / HTML / CSS / JavaScript
       |
       v
Controller Layer
       |
       v
Java Servlets
       |
       v
DAO / Business Logic Layer
       |
       v
JDBC
       |
       v
MySQL Database