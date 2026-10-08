# Campus Placement and Internship Portal

A complete B.Tech PBL Project developed using Core Java, Servlets, JSP, JDBC, MySQL, and MVC Architecture.

## Features
- **3 Roles:** Student, Recruiter, Admin
- **Automated Eligibility Checking:** Prevents students from applying if they don't meet CGPA or Branch criteria.
- **Dynamic Profile:** Students can add education, skills, and projects.
- **Job Management:** Recruiters can post jobs and update application statuses.
- **Analytics:** Admin dashboard with system-wide placement statistics.
- **Security:** BCrypt password hashing, session management, and SQL Injection prevention.

## Setup Instructions
1. Import the database schema and dummy data from `sql/schema.sql` into MySQL.
2. Update the database credentials in `src/main/java/com/placement/util/DatabaseConnection.java`.
3. Import the project as a Maven project into your IDE (Eclipse, IntelliJ, etc.).
4. Configure an Apache Tomcat Server (v9.0+) in your IDE and add this project to it.
5. Run the server. The application will be accessible at `http://localhost:8080/campus-placement-portal` (or similar depending on your context root).

## Demo Accounts
- **Admin:** admin@college.edu / admin123
- **To test Student/Recruiter:** Please register a new account on the portal.

> Note: For detailed documentation, please refer to the `BTech_PBL_Documentation.md` file provided by the assistant.
