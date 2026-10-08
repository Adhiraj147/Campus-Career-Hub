package com.placement.model;

import java.util.List;

public class Student {
    private int studentId;
    private int userId;
    private String firstName;
    private String lastName;
    private String rollNo;
    private String branch;
    private int graduationYear;
    private double cgpa;
    private String phone;
    private String resumeUrl;
    private boolean profileCompleted;

    // Additional objects to hold full profile data
    private List<Education> educationList;
    private List<Project> projectList;
    private List<String> skills;
    private List<Experience> experienceList;
    private List<Certification> certificationList;

    public Student() {}

    // Getters and Setters
    public int getStudentId() { return studentId; }
    public void setStudentId(int studentId) { this.studentId = studentId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getRollNo() { return rollNo; }
    public void setRollNo(String rollNo) { this.rollNo = rollNo; }

    public String getBranch() { return branch; }
    public void setBranch(String branch) { this.branch = branch; }

    public int getGraduationYear() { return graduationYear; }
    public void setGraduationYear(int graduationYear) { this.graduationYear = graduationYear; }

    public double getCgpa() { return cgpa; }
    public void setCgpa(double cgpa) { this.cgpa = cgpa; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getResumeUrl() { return resumeUrl; }
    public void setResumeUrl(String resumeUrl) { this.resumeUrl = resumeUrl; }

    public boolean isProfileCompleted() { return profileCompleted; }
    public void setProfileCompleted(boolean profileCompleted) { this.profileCompleted = profileCompleted; }

    public List<Education> getEducationList() { return educationList; }
    public void setEducationList(List<Education> educationList) { this.educationList = educationList; }

    public List<Project> getProjectList() { return projectList; }
    public void setProjectList(List<Project> projectList) { this.projectList = projectList; }

    public List<String> getSkills() { return skills; }
    public void setSkills(List<String> skills) { this.skills = skills; }

    public List<Experience> getExperienceList() { return experienceList; }
    public void setExperienceList(List<Experience> experienceList) { this.experienceList = experienceList; }

    public List<Certification> getCertificationList() { return certificationList; }
    public void setCertificationList(List<Certification> certificationList) { this.certificationList = certificationList; }

    public int calculateProfileCompletion() {
        int score = 20; // Base registration info
        if (branch != null && !branch.isEmpty() && cgpa > 0) score += 20;
        if (educationList != null && !educationList.isEmpty()) score += 15;
        if (skills != null && !skills.isEmpty()) score += 15;
        if (projectList != null && !projectList.isEmpty()) score += 10;
        if (resumeUrl != null && !resumeUrl.isEmpty()) score += 20;
        return score;
    }
}
