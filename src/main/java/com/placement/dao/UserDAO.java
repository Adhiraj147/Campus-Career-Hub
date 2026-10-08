package com.placement.dao;

import com.placement.model.User;
import java.sql.SQLException;

public interface UserDAO {
    /**
     * Registers a new user.
     * @param user The user to register
     * @return the generated user_id if successful, or -1 if failed.
     * @throws SQLException
     */
    int registerUser(User user) throws SQLException;

    /**
     * Finds a user by their email address.
     * @param email The email to search for
     * @return The User object if found, otherwise null
     * @throws SQLException
     */
    User getUserByEmail(String email) throws SQLException;
    
    /**
     * Creates the student record associated with a user
     */
    boolean createStudentProfile(int userId, String firstName, String lastName, String rollNo) throws SQLException;
    
    /**
     * Creates the company record associated with a user
     */
    boolean createCompanyProfile(int userId, String companyName) throws SQLException;

    /**
     * Registers a student user in a single transaction
     */
    int registerStudentWithTransaction(User user, String firstName, String lastName, String rollNo) throws SQLException;

    /**
     * Registers a recruiter user in a single transaction
     */
    int registerRecruiterWithTransaction(User user, String companyName) throws SQLException;
}
