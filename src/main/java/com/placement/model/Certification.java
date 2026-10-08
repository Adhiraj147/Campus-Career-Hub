package com.placement.model;
import java.sql.Date;

public class Certification {
    private int certId;
    private int studentId;
    private String name;
    private String authority;
    private Date date;

    public Certification() {}

    public int getCertId() { return certId; }
    public void setCertId(int certId) { this.certId = certId; }
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getAuthority() { return authority; }
    public void setAuthority(String authority) { this.authority = authority; }
    public Date getDate() { return date; }
    public void setDate(Date date) { this.date = date; }
}
