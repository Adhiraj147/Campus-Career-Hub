package com.placement.model;

public class Education {
    private int eduId;
    private int studentId;
    private String degree;
    private String institution;
    private int passingYear;
    private double percentage;

    public Education() {}

    public int getEduId() { return eduId; }
    public void setEduId(int eduId) { this.eduId = eduId; }

    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }

    public String getInstitution() { return institution; }
    public void setInstitution(String institution) { this.institution = institution; }

    public int getPassingYear() { return passingYear; }
    public void setPassingYear(int passingYear) { this.passingYear = passingYear; }

    public double getPercentage() { return percentage; }
    public void setPercentage(double percentage) { this.percentage = percentage; }
}
