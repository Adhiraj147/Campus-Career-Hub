# Campus Placement and Internship Portal — PBL Project Report Generation Documentation

This document explains the technical methodology, source code inspection, architecture mapping, and generation process utilized to produce the official **50-Page B.Tech Project-Based Learning (PBL) Report** for the **Campus Placement and Internship Portal (Campus Career Hub)**.

---

## 1. Executive Summary & File Deliverables

The complete documentation suite has been compiled and saved into the `project-report/` directory:

| File / Folder Path | Description | Verification Status |
| :--- | :--- | :--- |
| `Campus_Placement_and_Internship_Portal_Report.docx` | Editable Microsoft Word document with A4 geometry, 1.25" left margin, 1.0" top/bottom/right margins, Times New Roman typography. | **Verified** |
| `Campus_Placement_and_Internship_Portal_Report.pdf` | Print-ready, high-resolution PDF exported natively via Microsoft Word engine. | **Verified: Exactly 50 A4 Pages** |
| `diagrams/` | High-resolution 300 DPI vector-styled technical diagrams (Architecture, DFDs, ERD, Use Case, Sequence, Component, Deployment). | **11 Diagrams Generated** |
| `screenshots/` | Real production screenshots (Dark ATS candidate table) and structured UI placeholders. | **8 Assets Prepared** |
| `report_assets/` | Supplementary icons, assets, and metadata files. | **Configured** |

---

## 2. Project Metadata & Academic Profile

- **Project Title:** Campus Placement and Internship Portal (Campus Career Hub)
- **Academic Degree:** Bachelor of Technology (B.Tech), 5th Semester
- **Branch:** Information Technology
- **Institution:** Arya College of Engineering and IT, Kukas, Jaipur, Rajasthan
- **Affiliation:** Rajasthan Technical University (RTU), Kota
- **Academic Session:** 2026 – 2027
- **GitHub Repository:** [https://github.com/Adhiraj147/PBL-project-Campus-Placement-Portal.git](https://github.com/Adhiraj147/PBL-project-Campus-Placement-Portal.git)
- **Live Deployed Application:** [https://campus-career-hub-1-4azg.onrender.com](https://campus-career-hub-1-4azg.onrender.com)
- **Database Engine:** MySQL 8.0 (`campus_placement_db`), Hosted on Aiven Cloud

---

## 3. Team Members & Assigned Modules

| Team Member Name | University Roll No. | Assigned Engineering Module | Primary Implementation Focus |
| :--- | :--- | :--- | :--- |
| **Adhiraj Singh Shekhawat** *(Lead)* | `24E1ARITM40P003` | Authentication & User Management | BCrypt hashing, `LoginServlet`, transactional `RegisterServlet`, `AuthFilter` RBAC, session management. |
| **Shlok Verma** | `24E1ARITM40P057` | Student Portal | `StudentDashboardServlet`, profile builder, dynamic eligibility checks, job application tracker. |
| **Saurabh Gupta** | `24E1ARITM40P054` | Recruiter & Company Portal | `OpportunityServlet`, ATS applicant pipeline, shortlisting, candidate status updates, interview workflow. |
| **Krishna Dahima** | `24E1ARITM40P027` | Admin & Analytics | `AdminDashboardServlet`, TPO metrics aggregation, recruiter verification, drive moderation. |
| **Himanshu Yadav** | `[Verify College Records]` | Database, Integration & Testing | MySQL 3NF schema, JDBC DAO layer, connection pooling, automated rollback tests, Docker support. |

---

## 4. Source Files Inspected

Before generating the report, the existing project repository was analyzed:

1. **Configuration & Dependencies:**
   - `pom.xml`: Verified Java 11 target, Servlet API 4.0.1, JSP API 2.3.3, JSTL 1.2, MySQL Connector/J 8.0.33, jBCrypt 0.4, Gson 2.10.1, and Tomcat Maven Plugin 2.2.
   - `Dockerfile`: Verified multi-stage build (`maven:3.9-eclipse-temurin-11` -> `tomcat:9.0-jdk11`), `ROOT.war` mapping, and `EXPOSE 8080`.
   - `web.xml`: Verified servlet mappings, filter chain declarations, and session timeout configurations.
2. **Database Layer:**
   - `sql/schema.sql`: Analyzed all 9 normalized tables (`users`, `students`, `companies`, `admins`, `opportunities`, `applications`, `education`, `skills`, `projects`), foreign key constraints, and pre-seeded admin user.
   - `DatabaseConnection.java`: Inspected JDBC connection acquisition, SSL flags, and resource cleanup.
3. **Controller & Filter Layer:**
   - `AuthFilter.java`: Evaluated session role validation logic protecting `/student/*`, `/recruiter/*`, and `/admin/*`.
   - `LoginServlet.java` & `RegisterServlet.java`: Examined BCrypt password verification and atomic multi-table registration transactions.
   - `StudentDashboardServlet.java` & `RecruiterDashboardServlet.java`: Reviewed auto-heal logic for handling missing profiles.
   - `OpportunityServlet.java` & `StudentApplicationServlet.java`: Traced job posting parameters and automated CGPA/branch eligibility enforcement.
4. **Data Access Layer (DAO):**
   - `UserDAOImpl.java`, `StudentDAOImpl.java`, `CompanyDAOImpl.java`, `OpportunityDAOImpl.java`, `ApplicationDAOImpl.java`, `AdminDAOImpl.java`.
5. **Presentation Layer:**
   - Dark theme CSS (`src/main/webapp/css/style.css`), `.table-premium-dark` styling, modular `header.jsp`/`footer.jsp`, and dashboard JSP views.

---

## 5. Technical Diagrams Included

All 11 diagrams were rendered at 300 DPI into `project-report/diagrams/`:

- `Figure 3.1`: 3-Tier MVC System Architecture Diagram (Page 14)
- `Figure 3.4`: Data Flow Diagram - Level 0 Context Diagram (Page 15)
- `Figure 3.5`: Data Flow Diagram - Level 1 Functional Decomposition (Page 15)
- `Figure 3.6`: System Use Case Diagram (Page 16)
- `Figure 3.7`: Student Job Application Activity Diagram (Page 16)
- `Figure 3.8`: Job Application & Eligibility Check Sequence Diagram (Page 17)
- `Figure 3.9`: Software Component Diagram (Page 17)
- `Figure 3.10`: Entity Relationship Diagram (ERD) with Multiplicities (Page 18)
- `Figure 3.12`: Relational Database Foreign Key Map (Page 19)
- `Figure 5.22`: Inter-Module Integration Architecture Diagram (Page 29)
- `Figure 11.1`: Production Cloud Deployment Architecture (Docker & Render) (Page 47)

---

## 6. Identified Placeholders for Final College Submission

The following clearly-marked placeholders should be updated with verified administrative records prior to printing:
1. **Himanshu Yadav's Roll Number:** Currently marked as `[Verify from College Records]` on Pages 1, 2, 3, 44, and in the table of contents.
2. **Faculty Signatures & Dates:** Marked as `Date: [DD/MM/YYYY]` on Pages 2, 3, and 50. Replace with actual physical or digital signatures.
3. **Optional Live Screenshots:** The report contains authentic figures, including the real dark ATS candidate table (Page 41) and structured UI figure mockups. If you wish to replace any figure mockup with a specific live browser screenshot, simply overwrite the PNG file in `project-report/screenshots/` with the same name and rerun `python main_assembler.py`.

---

## 7. How to Re-Generate or Modify the Report

If you update text, member details, or diagrams:

```bash
# 1. Regenerate technical diagrams (if modified)
python generate_diagrams.py

# 2. Re-assemble the 50-page DOCX and compile the PDF
python main_assembler.py
```

The script will automatically layout the pages, invoke Microsoft Word in headless mode, export the PDF, and verify using `pypdf` that the final document is **EXACTLY 50 A4 PAGES**.
