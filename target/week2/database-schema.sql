-- Create database
CREATE DATABASE IF NOT EXISTS campus_placement_db;
USE campus_placement_db;

-- 1. users
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('STUDENT', 'RECRUITER', 'ADMIN') NOT NULL,
    status ENUM('ACTIVE', 'PENDING', 'INACTIVE') DEFAULT 'ACTIVE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- 2. students
CREATE TABLE IF NOT EXISTS students (
    student_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    roll_no VARCHAR(20) UNIQUE,
    branch VARCHAR(50),
    graduation_year INT,
    cgpa DECIMAL(4,2),
    phone VARCHAR(15),
    resume_url VARCHAR(255),
    profile_completed BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- 3. companies
CREATE TABLE IF NOT EXISTS companies (
    company_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    company_name VARCHAR(100) NOT NULL,
    description TEXT,
    website VARCHAR(100),
    industry VARCHAR(50),
    location VARCHAR(100),
    contact_person VARCHAR(100),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- 4. admins
CREATE TABLE IF NOT EXISTS admins (
    admin_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT UNIQUE,
    name VARCHAR(100) NOT NULL,
    employee_id VARCHAR(20) UNIQUE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- 5. opportunities (Jobs and Internships)
CREATE TABLE IF NOT EXISTS opportunities (
    opp_id INT AUTO_INCREMENT PRIMARY KEY,
    company_id INT,
    title VARCHAR(100) NOT NULL,
    type ENUM('JOB', 'INTERNSHIP') NOT NULL,
    description TEXT NOT NULL,
    location VARCHAR(100),
    work_mode ENUM('ONSITE', 'REMOTE', 'HYBRID') DEFAULT 'ONSITE',
    salary_stipend VARCHAR(50),
    min_cgpa DECIMAL(4,2) DEFAULT 0.0,
    eligible_branches VARCHAR(255), -- Comma separated branches
    graduation_year_req INT,
    required_skills VARCHAR(255),
    deadline DATE,
    status ENUM('OPEN', 'CLOSED') DEFAULT 'OPEN',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (company_id) REFERENCES companies(company_id) ON DELETE CASCADE
);

-- 6. applications
CREATE TABLE IF NOT EXISTS applications (
    application_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    opp_id INT,
    status ENUM('APPLIED', 'UNDER_REVIEW', 'SHORTLISTED', 'INTERVIEW_SCHEDULED', 'SELECTED', 'REJECTED') DEFAULT 'APPLIED',
    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(student_id, opp_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (opp_id) REFERENCES opportunities(opp_id) ON DELETE CASCADE
);

-- 7. application_status_history
CREATE TABLE IF NOT EXISTS application_status_history (
    history_id INT AUTO_INCREMENT PRIMARY KEY,
    application_id INT,
    status ENUM('APPLIED', 'UNDER_REVIEW', 'SHORTLISTED', 'INTERVIEW_SCHEDULED', 'SELECTED', 'REJECTED'),
    remarks TEXT,
    changed_by INT,
    changed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (application_id) REFERENCES applications(application_id) ON DELETE CASCADE,
    FOREIGN KEY (changed_by) REFERENCES users(user_id) ON DELETE SET NULL
);

-- 8. interviews
CREATE TABLE IF NOT EXISTS interviews (
    interview_id INT AUTO_INCREMENT PRIMARY KEY,
    application_id INT,
    interview_date DATE NOT NULL,
    interview_time TIME NOT NULL,
    type ENUM('TECHNICAL', 'HR', 'ONLINE_TEST') NOT NULL,
    location_link VARCHAR(255),
    instructions TEXT,
    FOREIGN KEY (application_id) REFERENCES applications(application_id) ON DELETE CASCADE
);

-- 9. notifications
CREATE TABLE IF NOT EXISTS notifications (
    notif_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT,
    message TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- 10. Student Profile Data
CREATE TABLE IF NOT EXISTS education (
    edu_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    degree VARCHAR(50),
    institution VARCHAR(100),
    passing_year INT,
    percentage DECIMAL(5,2),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS skills (
    skill_id INT AUTO_INCREMENT PRIMARY KEY,
    skill_name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS student_skills (
    student_id INT,
    skill_id INT,
    PRIMARY KEY (student_id, skill_id),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES skills(skill_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS projects (
    project_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    title VARCHAR(100),
    description TEXT,
    link VARCHAR(255),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS certifications (
    cert_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    name VARCHAR(100),
    authority VARCHAR(100),
    date DATE,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS experiences (
    exp_id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    company VARCHAR(100),
    role VARCHAR(100),
    start_date DATE,
    end_date DATE,
    description TEXT,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);

-- Insert Dummy Data

-- Create Admin User (password is 'admin123' using bcrypt)
-- Note: You should generate actual bcrypt hash in java. For demo we insert a known hash.
-- '$2a$10$wYQe43xZ75GZ7O8G.P39GOMrA/Y5.c.eH0/mYvB/Z5y6BqKk.o9oO' = 'admin123'
INSERT IGNORE INTO users (email, password_hash, role, status) 
VALUES ('admin@college.edu', '$2a$10$wYQe43xZ75GZ7O8G.P39GOMrA/Y5.c.eH0/mYvB/Z5y6BqKk.o9oO', 'ADMIN', 'ACTIVE');

SET @admin_user_id = LAST_INSERT_ID();

INSERT IGNORE INTO admins (user_id, name, employee_id) 
VALUES (@admin_user_id, 'System Admin', 'EMP001');

-- Insert Some Common Skills
INSERT IGNORE INTO skills (skill_name) VALUES 
('Java'), ('Python'), ('C++'), ('JavaScript'), ('React'), ('Node.js'), ('MySQL'), ('Spring Boot'), ('HTML/CSS'), ('Data Structures');
