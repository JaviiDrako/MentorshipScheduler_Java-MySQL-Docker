package com.mentorshipSystem.crud;

import java.sql.*;

public class UserDAO {
    private DBConnectionManager dbManager = new DBConnectionManager();
  
    public void registerUser(String email, String password, String role) {
        String sqlUpdate = "INSERT INTO Users (email, password, role) VALUES (?, ?, ?)";
        try (Connection conn = dbManager.connect(); PreparedStatement statement = conn.prepareStatement(sqlUpdate)) {
            statement.setString(1, email);
            statement.setString(2, password);
            statement.setString(3, role);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error establishing connection or preparing query: " + e.getMessage());
        }
    }

    public int getUserId(String email) {
        String sqlQuery = "SELECT user_id FROM Users WHERE email = ?";
        int id = 0;
        try (Connection conn = dbManager.connect(); PreparedStatement statement = conn.prepareStatement(sqlQuery)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("user_id");
                } else {
                    throw new SQLException("User id not found");
                }
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error establishing connection or preparing query: " + e.getMessage());
        }
        return id;
    }

    public boolean logIn(String email, String password) {
        String sqlQuery = "SELECT password FROM Users WHERE email = ?";
        boolean found = false;
        String passwordQuery = "";

        try (Connection conn = dbManager.connect(); PreparedStatement statement = conn.prepareStatement(sqlQuery)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    passwordQuery = rs.getString("password");
                    if (password.equals(passwordQuery)) {
                        found = true;
                    }else {
                        throw new SQLException("Invalid password");
                    }
                } else {
                    throw new SQLException("Email does not exist in the system");
                }
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error establishing connection or preparing query: " + e.getMessage());
        }
        return found;
    }

    public String getRole(String email) {
        String sqlQuery = "SELECT role FROM Users WHERE email = ?";
        String role = "";
        try (Connection conn = dbManager.connect(); PreparedStatement statement = conn.prepareStatement(sqlQuery)) {
            statement.setString(1, email);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    role = rs.getString("role");
                } else {
                    throw new SQLException("User not found");
                }
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error establishing connection or preparing query: " + e.getMessage());
        }
        return role;
    }


}



