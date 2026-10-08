package com.placement.dao;

import com.placement.model.Application;
import java.sql.SQLException;
import java.util.List;

public interface ApplicationDAO {
    boolean apply(int studentId, int oppId) throws SQLException;
    boolean hasApplied(int studentId, int oppId) throws SQLException;
    
    List<Application> getApplicationsByStudent(int studentId) throws SQLException;
    List<Application> getApplicationsByOpportunity(int oppId) throws SQLException;
    
    boolean updateStatus(int applicationId, String status, String remarks, int changedByUserId) throws SQLException;
}
