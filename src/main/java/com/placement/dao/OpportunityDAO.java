package com.placement.dao;

import com.placement.model.Opportunity;
import java.sql.SQLException;
import java.util.List;

public interface OpportunityDAO {
    boolean addOpportunity(Opportunity opp) throws SQLException;
    boolean updateOpportunity(Opportunity opp) throws SQLException;
    boolean closeOpportunity(int oppId) throws SQLException;
    Opportunity getOpportunityById(int oppId) throws SQLException;
    List<Opportunity> getOpportunitiesByCompany(int companyId) throws SQLException;
    List<Opportunity> getAllOpenOpportunities() throws SQLException;
    List<Opportunity> searchOpportunities(String keyword, String type) throws SQLException;
}
