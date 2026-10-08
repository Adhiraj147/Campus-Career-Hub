package com.placement.model;

import java.sql.Date;
import java.sql.Timestamp;

public class Opportunity {
    private int oppId;
    private int companyId;
    private String companyName; // Join helper
    private String title;
    private String type; // 'JOB', 'INTERNSHIP'
    private String description;
    private String location;
    private String workMode; // 'ONSITE', 'REMOTE', 'HYBRID'
    private String salaryStipend;
    private double minCgpa;
    private String eligibleBranches;
    private int graduationYearReq;
    private String requiredSkills;
    private Date deadline;
    private String status; // 'OPEN', 'CLOSED'
    private Timestamp createdAt;

    public Opportunity() {}

    public int getOppId() { return oppId; }
    public void setOppId(int oppId) { this.oppId = oppId; }
    public int getCompanyId() { return companyId; }
    public void setCompanyId(int companyId) { this.companyId = companyId; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getWorkMode() { return workMode; }
    public void setWorkMode(String workMode) { this.workMode = workMode; }
    public String getSalaryStipend() { return salaryStipend; }
    public void setSalaryStipend(String salaryStipend) { this.salaryStipend = salaryStipend; }
    public double getMinCgpa() { return minCgpa; }
    public void setMinCgpa(double minCgpa) { this.minCgpa = minCgpa; }
    public String getEligibleBranches() { return eligibleBranches; }
    public void setEligibleBranches(String eligibleBranches) { this.eligibleBranches = eligibleBranches; }
    public int getGraduationYearReq() { return graduationYearReq; }
    public void setGraduationYearReq(int graduationYearReq) { this.graduationYearReq = graduationYearReq; }
    public String getRequiredSkills() { return requiredSkills; }
    public void setRequiredSkills(String requiredSkills) { this.requiredSkills = requiredSkills; }
    public Date getDeadline() { return deadline; }
    public void setDeadline(Date deadline) { this.deadline = deadline; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
}
