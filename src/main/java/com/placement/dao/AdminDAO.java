package com.placement.dao;

import java.sql.SQLException;
import java.util.Map;

public interface AdminDAO {
    Map<String, Integer> getSystemStatistics() throws SQLException;
}
