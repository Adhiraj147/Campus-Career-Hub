package com.placement.model;

import java.sql.Timestamp;

public class Application {
    private int applicationId;
    private int studentId;
    private int oppId;
    private String status; // 'APPLIED', 'UNDER_REVIEW', 'SHORTLISTED', 'INTERVIEW_SCHEDULED', 'SELECTED', 'REJECTED'
    private Timestamp appliedAt;

    // Helper fields for display
    private String studentName;
    private String studentBranch;
    private double studentCgpa;
    private String studentResumeUrl;
    
    private String oppTitle;
    private String companyName;

    public Application() {}

    public int getApplicationId() { return applicationId; }
    public void setApplicationId(int applicationId) { this.applicationId = applicationId; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public int getOppId() { return oppId; }
    public void setOppId(int oppId) { this.oppId = oppId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Timestamp getAppliedAt() { return appliedAt; }
    public void setAppliedAt(Timestamp appliedAt) { this.appliedAt = appliedAt; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
    public String getStudentBranch() { return studentBranch; }
    public void setStudentBranch(String studentBranch) { this.studentBranch = studentBranch; }
    public double getStudentCgpa() { return studentCgpa; }
    public void setStudentCgpa(double studentCgpa) { this.studentCgpa = studentCgpa; }
    public String getStudentResumeUrl() { return studentResumeUrl; }
    public void setStudentResumeUrl(String studentResumeUrl) { this.studentResumeUrl = studentResumeUrl; }

    public String getOppTitle() { return oppTitle; }
    public void setOppTitle(String oppTitle) { this.oppTitle = oppTitle; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
}
