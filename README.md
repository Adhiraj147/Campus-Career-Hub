<div align="center">

# 🎓 Campus Career Hub
### *Campus Placement and Internship Management Portal*

[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)](https://www.oracle.com/java/)
[![Maven](https://img.shields.io/badge/Apache%20Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![Tomcat](https://img.shields.io/badge/Apache%20Tomcat-F8DC75?style=for-the-badge&logo=apachetomcat&logoColor=black)](https://tomcat.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)](https://www.docker.com/)
[![Render](https://img.shields.io/badge/Render-46E3B7?style=for-the-badge&logo=render&logoColor=black)](https://render.com/)
[![Bootstrap](https://img.shields.io/badge/Bootstrap%205-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white)](https://getbootstrap.com/)
[![Live Demo](https://img.shields.io/badge/Live%20Demo-Online%2024%2F7-brightgreen?style=for-the-badge&logo=googlechrome&logoColor=white)](https://campus-career-hub-1-4azg.onrender.com)

<p align="center">
  A production-ready, full-stack recruitment portal bridging college talent, corporate recruiters, and placement offices (TPO). Engineered with Core Java, Servlets, JSP, JDBC, and MySQL following strict MVC architectural principles, packaged with Docker, and cloud-deployed on Render.
</p>

<p align="center">
  🌐 <b>Live Application:</b> <a href="https://campus-career-hub-1-4azg.onrender.com" target="_blank"><b>https://campus-career-hub-1-4azg.onrender.com</b></a>
</p>

[🌐 Live Demo](https://campus-career-hub-1-4azg.onrender.com) • [Explore Features](#-core-features) • [Architecture](#-system-architecture) • [Quick Start](#-local-setup--installation) • [Docker & Deployment](#-docker--cloud-deployment) • [Endpoints](#-routing--endpoint-directory)

</div>

---

## 📌 Table of Contents
- [Overview](#-overview)
- [Key Highlights](#-key-highlights)
- [Core Features](#-core-features)
  - [Student Module](#1-student-module)
  - [Recruiter Module](#2-recruiter-module)
  - [Admin / TPO Module](#3-admin--placement-cell-module)
- [Tech Stack](#-technology-stack)
- [System Architecture](#-system-architecture)
- [Database Schema & ER Design](#-database-schema)
- [Local Setup & Installation](#-local-setup--installation)
- [Docker & Cloud Deployment](#-docker--cloud-deployment)
- [Default Demo Credentials](#-demo-accounts)
- [Project Directory Structure](#-project-structure)
- [Routing & Endpoint Directory](#-routing--endpoint-directory)
- [Future Enhancements](#-roadmap)

---

## 🌟 Overview

The **Campus Placement & Internship Portal (Campus Career Hub)** is an enterprise-grade university recruitment management system designed to eliminate manual spreadsheet tracking, fragmented email communication, and eligibility calculation errors during campus hiring drives.

Traditional college placement operations suffer from duplicate registrations, missed deadlines, and students applying to opportunities they do not qualify for. **Campus Career Hub** automates the entire recruitment lifecycle—from job posting and rule-based candidate eligibility filtering to multi-round interview tracking and final offers.

> [!TIP]
> Built without bulky enterprise frameworks (such as Spring or Hibernate) to demonstrate mastery of foundational **Java EE, JDBC Transactions, Custom Filter-based RBAC, and Clean MVC separation**.

---

## 🚀 Key Highlights

- ⚡ **Zero-Framework Purity**: Pure Java Servlets 4.0, JSP 2.3, JSTL, and Raw JDBC with `PreparedStatement` optimization.
- 🎯 **Automated Eligibility Engine**: Server-side validation instantly verifies minimum CGPA, active backlogs, graduation year, and departmental branch before allowing applications.
- 🎨 **Modern SaaS UI (Dark Palette)**: Crafted with Deep Navy (`#0d1527`), Electric Blue, subtle CSS particle animations, high-contrast readable tables, and full mobile responsiveness.
- 🛡️ **Enterprise Security**:
  - BCrypt (`jbcrypt`) adaptive password hashing with cryptographic salting.
  - ACID-compliant SQL transactions (`setAutoCommit(false)` + `rollback()`) to guarantee zero orphan records.
  - Centralized authentication & Role-Based Access Control (RBAC) via `AuthFilter`.
- ☁️ **Containerized Cloud Deployment**: Multi-stage `Dockerfile` running on Apache Tomcat 9, seamlessly deployable to **Render, AWS, or Railway** with 24/7 uptime.

---

## 💼 Core Features

### 1. 🎓 Student Module
- **Profile Completion Score**: Dynamic calculation assessing personal details, branch, graduation year, CGPA, and resume availability.
- **Academic & Portfolio Builder**: Add, edit, and delete multiple education tiers, technical skills chips, and live project portfolios.
- **Job & Internship Discovery**: Live filtering by job type (`JOB` vs. `INTERNSHIP`), company name, and location.
- **Intelligent Application Engine**:
  - One-click application with real-time feedback.
  - Automatically disallows applications if eligibility criteria (CGPA/Branch) are not met.
- **Application Tracker**: Status tracker displaying five distinct pipeline stages: `APPLIED`, `SHORTLISTED`, `INTERVIEW_SCHEDULED`, `SELECTED`, and `REJECTED`.
- **Interview Portal**: Dedicated dashboard displaying confirmed interview rounds and schedules.

### 2. 🏢 Recruiter Module
- **Corporate Profile Setup**: Maintain verified company name, industry category, official website, office location, and designated contact person.
- **Opportunity Posting Engine**: Create and publish comprehensive job and internship requisitions specifying title, salary/stipend package, job description, minimum CGPA cutoff, and eligible branches.
- **Applicant Tracking System (ATS)**: Filter applicants per posting with tabular displays of branch, CGPA, application date, and one-click resume access.
- **Candidate Pipeline Workflow**: Update application statuses through modal dialogues (e.g., Shortlist, Schedule Interview, Mark Selected, Reject).

### 3. 🛡️ Admin / Placement Cell Module
- **Executive TPO Dashboard**: High-level real-time analytics displaying:
  - Total registered students & approved recruiters.
  - Total active postings & total applications processed.
  - System-wide placement conversion metrics.
- **User Management & Verification**: View and manage candidate records and verify recruiter corporate identities.
- **Drive Oversight**: Audit drive status across departments and monitor placement health.

---

## 🛠️ Technology Stack

| Component | Technology | Version / Specification |
| :--- | :--- | :--- |
| **Language** | Java (JDK) | OpenJDK 11 |
| **Backend Architecture** | Java EE Servlets | Servlet API 4.0.1 (MVC Pattern) |
| **View Technology** | JavaServer Pages (JSP) | JSP 2.3.3 / JSTL 1.2 |
| **Database** | MySQL | 8.0.33 (Aiven Cloud / Local MySQL) |
| **Persistence Layer** | JDBC | Pure JDBC with PreparedStatement & Connection Pooling |
| **Authentication & Crypto**| jBCrypt | 0.4 (Blowfish-based adaptive hashing) |
| **Build & Dependency Tool** | Apache Maven | 3.9+ |
| **Local Testing Container**| Tomcat Maven Plugin | Tomcat 7 embedded plugin / Tomcat 9 runtime |
| **Frontend Framework** | Bootstrap | 5.3 + Bootstrap Icons |
| **Styling & UI** | Custom CSS3 | Modern Dark Theme, CSS Animations, Custom Scrollbars |
| **Containerization** | Docker | Multi-stage build (`maven:3.9` -> `tomcat:9.0-jdk11`) |
| **Cloud Hosting** | Render | Docker Web Service (24/7 Availability) |

---

## 🏛️ System Architecture

The application strictly implements the **Model-View-Controller (MVC)** architectural pattern, enforcing separation of concerns across presentation, business logic, and database access:

```
                          ┌───────────────────────────┐
                          │   Client Browser / Mobile │
                          └─────────────┬─────────────┘
                                        │ HTTP / HTTPS
                                        ▼
                          ┌───────────────────────────┐
                          │   AuthFilter (Security)   │
                          └─────────────┬─────────────┘
                                        │ Authenticated & Authorized
                                        ▼
    ┌────────────────────────────────────────────────────────────────────────┐
    │                       CONTROLLER LAYER (Servlets)                      │
    │  [LoginServlet] [RegisterServlet] [StudentDashboardServlet]            │
    │  [StudentOpportunityServlet] [RecruiterDashboardServlet] [AdminServlet]│
    └──────────────┬──────────────────────────────────────────▲──────────────┘
                   │                                          │
    Business Logic │                                          │ Model Data Binding
    & Eligibility  ▼                                          │ Forward / Redirect
    ┌───────────────────────────┐                             │
    │      SERVICE LAYER        │                             │
    │   [EligibilityService]    │                             │
    └──────────────┬────────────┘                             │
                   │                                          │
                   ▼                                          │
    ┌─────────────────────────────────────────────────────────┴──────────────┐
    │                         DATA ACCESS LAYER (DAO)                        │
    │   UserDAO  │  StudentDAO  │  CompanyDAO  │  OpportunityDAO  │  AdminDAO│
    └──────────────┬─────────────────────────────────────────────────────────┘
                   │ JDBC Statements & Transactions (ACID)
                   ▼
    ┌────────────────────────────────────────────────────────────────────────┐
    │                      DATABASE LAYER (MySQL 8.0)                        │
    │  users ──< students ──< education / skills / projects                  │
    │        ──< companies ──< opportunities ──< applications                │
    └────────────────────────────────────────────────────────────────────────┘
```

---

## 🗄️ Database Schema

The relational schema is configured in `sql/schema.sql`. Key tables and relationships include:

- **`users`**: Core authentication table storing email, BCrypt password hash, role (`STUDENT`, `RECRUITER`, `ADMIN`), and account status.
- **`students`**: Academic profile table referencing `user_id`, storing roll number (unique), branch, CGPA, and resume links.
- **`companies`**: Recruiter profile table referencing `user_id`, storing company name, industry, and contact details.
- **`admins`**: Placement officer profile referencing `user_id`.
- **`opportunities`**: Job and internship listings published by companies with compensation, eligibility cutoffs, and deadlines.
- **`applications`**: Junction table tracking candidate submissions for specific opportunities with compound unique constraints (`student_id`, `opp_id`).
- **`education`**, **`skills`**, **`projects`**: Normalized profile extensions linked via cascading foreign keys (`ON DELETE CASCADE`).

---

## 💻 Local Setup & Installation

Follow these instructions to run the project locally on your machine:

### Prerequisites
- **Java Development Kit (JDK)**: Version 11 or higher installed ([Download JDK 11](https://adoptium.net/))
- **Apache Maven**: Version 3.8+ ([Download Maven](https://maven.apache.org/download.cgi))
- **MySQL Server**: Local MySQL 8.0 instance or a managed cloud database (e.g. Aiven)
- **Git**: Installed on your system

### Step 1: Clone the Repository
```bash
git clone https://github.com/YOUR_USERNAME/campus-placement-portal.git
cd campus-placement-portal
```

### Step 2: Database Initialization
1. Start your local MySQL instance.
2. Execute the provided SQL schema script:
```bash
mysql -u root -p < sql/schema.sql
```
*(Or open `sql/schema.sql` in MySQL Workbench or DBeaver and execute the entire script).*

### Step 3: Configure Database Connection
Open `src/main/java/com/placement/util/DatabaseConnection.java` and adjust your credentials if using a local MySQL instance:

```java
private static final String URL = "jdbc:mysql://localhost:3306/campus_placement_db?useSSL=false&serverTimezone=UTC";
private static final String USER = "your_mysql_username";
private static final String PASSWORD = "your_mysql_password";
```

### Step 4: Build & Run with Maven
You can start the built-in Tomcat server with a single command:
```bash
# Clean previous builds and run embedded Tomcat
mvn clean tomcat7:run
```

Once started, open your web browser and navigate to:
```
http://localhost:8080/campus-placement-portal
```

---

## 🐳 Docker & Cloud Deployment

The repository includes a ready-to-deploy multi-stage `Dockerfile` optimized for minimal container size and cloud hosting platforms like **Render**, **Railway**, or **AWS ECS**.

### Local Docker Build & Run
```bash
# 1. Build the Docker image
docker build -t campus-career-hub .

# 2. Run the container on port 8080
docker run -p 8080:8080 campus-career-hub
```
Access the application at `http://localhost:8080`.

### Deploying to Render
1. Push your repository to **GitHub**.
2. Log into [Render.com](https://render.com) and click **New +** → **Web Service**.
3. Select your GitHub repository.
4. Set the following options:
   - **Environment**: `Docker`
   - **Branch**: `main`
   - **Instance Type**: `Free`
5. Click **Deploy Web Service**. Render will automatically execute the multi-stage build, expose port `8080`, and provision a live public URL.

> [!NOTE]
> **Production Instance**: This project is currently live and active 24/7 at:
> 🔗 **[https://campus-career-hub-1-4azg.onrender.com](https://campus-career-hub-1-4azg.onrender.com)**

---

## 🔑 Demo Accounts

The database schema pre-seeds an administrator account for immediate testing:

| Role | Email | Password | Access Details |
| :--- | :--- | :--- | :--- |
| **Admin (TPO)** | `admin@college.edu` | `admin123` | Full dashboard metrics & audit views |
| **Student** | *Register via `/register`* | *Custom* | Default role on registration |
| **Recruiter** | *Register via `/register`* | *Custom* | Choose **Recruiter** toggle during sign up |

---

## 📂 Project Structure

```
campus-placement-portal/
├── Dockerfile                  # Multi-stage Docker build for container deployment
├── .dockerignore               # Build optimization exclusions
├── .gitignore                  # Git repository ignore rules
├── pom.xml                     # Maven project descriptor & dependencies
├── README.md                   # Project documentation
├── sql/
│   └── schema.sql              # Database schema, foreign keys & sample seed data
└── src/
    └── main/
        ├── java/
        │   └── com/placement/
        │       ├── controller/ # Servlets processing requests & dispatching views
        │       │   ├── AdminDashboardServlet.java
        │       │   ├── LoginServlet.java
        │       │   ├── LogoutServlet.java
        │       │   ├── OpportunityServlet.java
        │       │   ├── RecruiterApplicationServlet.java
        │       │   ├── RecruiterDashboardServlet.java
        │       │   ├── RecruiterProfileServlet.java
        │       │   ├── RegisterServlet.java
        │       │   ├── StudentApplicationServlet.java
        │       │   ├── StudentDashboardServlet.java
        │       │   ├── StudentInterviewServlet.java
        │       │   ├── StudentOpportunityServlet.java
        │       │   └── StudentProfileServlet.java
        │       ├── dao/        # Data Access Objects & JDBC implementations
        │       │   ├── AdminDAO.java & AdminDAOImpl.java
        │       │   ├── ApplicationDAO.java & ApplicationDAOImpl.java
        │       │   ├── CompanyDAO.java & CompanyDAOImpl.java
        │       │   ├── OpportunityDAO.java & OpportunityDAOImpl.java
        │       │   ├── StudentDAO.java & StudentDAOImpl.java
        │       │   └── UserDAO.java & UserDAOImpl.java
        │       ├── filter/     # Security & Role-Based Access Control
        │       │   └── AuthFilter.java
        │       ├── model/      # Plain Old Java Objects (POJOs) mapping DB entities
        │       │   ├── Application.java
        │       │   ├── Company.java
        │       │   ├── Education.java
        │       │   ├── Opportunity.java
        │       │   ├── Project.java
        │       │   ├── Student.java
        │       │   └── User.java
        │       ├── service/    # Business rules & validation engine
        │       │   └── EligibilityService.java
        │       └── util/       # Infrastructure helpers (DB connection, BCrypt)
        │           ├── DatabaseConnection.java
        │           └── PasswordUtil.java
        └── webapp/
            ├── index.jsp       # Modern SaaS Landing Page
            ├── login.jsp       # Authentication Page
            ├── register.jsp    # Multi-role Registration Page
            ├── css/
            │   └── style.css   # Global Dark Theme Design System & Animations
            ├── jsp/
            │   ├── admin/      # Admin dashboard views
            │   ├── common/     # Modular header.jsp & footer.jsp
            │   ├── recruiter/  # Postings, ATS applicant tables, profile views
            │   └── student/    # Opportunities, application tracking, profile views
            └── WEB-INF/
                └── web.xml     # Deployment descriptor
```

---

## 🗺️ Routing & Endpoint Directory

| Endpoint URL | HTTP Method | Authorized Roles | Controller Servlet | Primary View |
| :--- | :---: | :---: | :--- | :--- |
| `/` | `GET` | Public | *Direct View* | `index.jsp` |
| `/login` | `GET`, `POST` | Public | `LoginServlet` | `login.jsp` |
| `/register` | `GET`, `POST` | Public | `RegisterServlet` | `register.jsp` |
| `/logout` | `GET` | Authenticated | `LogoutServlet` | Redirect to `/login` |
| `/student/dashboard` | `GET` | `STUDENT` | `StudentDashboardServlet` | `jsp/student/dashboard.jsp` |
| `/student/profile` | `GET`, `POST` | `STUDENT` | `StudentProfileServlet` | `jsp/student/profile.jsp` |
| `/student/opportunities`| `GET` | `STUDENT` | `StudentOpportunityServlet`| `jsp/student/opportunities.jsp`|
| `/student/apply` | `POST` | `STUDENT` | `StudentApplicationServlet`| Redirect with application status |
| `/student/applications` | `GET` | `STUDENT` | `StudentApplicationServlet`| `jsp/student/applications.jsp` |
| `/student/interviews` | `GET` | `STUDENT` | `StudentInterviewServlet` | `jsp/student/interviews.jsp` |
| `/recruiter/dashboard` | `GET` | `RECRUITER` | `RecruiterDashboardServlet`| `jsp/recruiter/dashboard.jsp` |
| `/recruiter/profile` | `GET`, `POST` | `RECRUITER` | `RecruiterProfileServlet` | `jsp/recruiter/profile.jsp` |
| `/recruiter/postings` | `GET`, `POST` | `RECRUITER` | `OpportunityServlet` | `jsp/recruiter/postings.jsp` |
| `/recruiter/applicants` | `GET`, `POST` | `RECRUITER` | `RecruiterApplicationServlet`| `jsp/recruiter/applicants.jsp` |
| `/admin/dashboard` | `GET` | `ADMIN` | `AdminDashboardServlet` | `jsp/admin/dashboard.jsp` |

---

## 🔮 Roadmap

- [ ] **Resume AI Parser**: Automatic extraction of skills, education, and experience from uploaded PDF resumes.
- [ ] **Email & SMS Notifications**: Automated alerts for interview scheduling and shortlisting via SendGrid / Twilio.
- [ ] **Direct Chat / Inquiries**: Real-time communication channel between eligible candidates and company recruiters.
- [ ] **Exportable Analytics Reports**: Download placement drive statistics as formatted PDF / Excel reports for official college accreditations (NBA / NAAC).

---

## 📄 License
This project was developed for educational and academic project purposes (B.Tech PBL). Feel free to fork, customize, and extend it for your college placement cell.

---

<div align="center">
  <b>Built with ❤️ by Adhiraj Singh & Team</b><br>
  <sub>Contributions and feature suggestions are welcome!</sub>
</div>
