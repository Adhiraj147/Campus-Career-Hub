package com.placement.dao;

import com.placement.model.Company;
import java.sql.SQLException;

public interface CompanyDAO {
    Company getCompanyByUserId(int userId) throws SQLException;
    Company getCompanyById(int companyId) throws SQLException;
    boolean updateCompanyProfile(Company company) throws SQLException;
}
