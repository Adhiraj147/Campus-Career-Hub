package com.placement.model;
import java.sql.Date;

public class Experience {
    private int expId;
    private int studentId;
    private String company;
    private String role;
    private Date startDate;
    private Date endDate;
    private String description;

    public Experience() {}

    public int getExpId() { return expId; }
    public void setExpId(int expId) { this.expId = expId; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public String getCompany() { return company; }
    public void setCompany(String company) { this.company = company; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
