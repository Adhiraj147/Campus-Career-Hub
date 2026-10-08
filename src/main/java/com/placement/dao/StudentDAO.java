package com.placement.dao;

import com.placement.model.Student;
import com.placement.model.Education;
import com.placement.model.Project;
import java.sql.SQLException;

public interface StudentDAO {
    Student getStudentByUserId(int userId) throws SQLException;
    boolean updateStudentBasicProfile(Student student) throws SQLException;
    
    boolean addEducation(Education edu) throws SQLException;
    boolean deleteEducation(int eduId) throws SQLException;
    
    boolean addProject(Project proj) throws SQLException;
    boolean deleteProject(int projectId) throws SQLException;
    
    boolean addSkill(int studentId, String skillName) throws SQLException;
    boolean removeSkill(int studentId, String skillName) throws SQLException;
}
