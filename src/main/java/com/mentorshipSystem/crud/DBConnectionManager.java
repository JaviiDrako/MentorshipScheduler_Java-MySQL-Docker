package com.mentorshipSystem.crud;
import java.sql.*;

public class DBConnectionManager {
    private final String JDBC_URL = "jdbc:mysql://localhost:3307/MentorshipsDB";
    private final String USERNAME = "userDev";
    private final String PASSWORD = "theBestPasswordEver123";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
}
