package com.mentorshipSystem.crud;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TutorDAO {
    private DBConnectionManager dbManager = new DBConnectionManager(); 

    public void registerTutor(int userId, String name, String phoneNumber, String area) {
        String sqlInsert = "INSERT INTO Tutors (user_id, name, phone_number, area) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbManager.connect(); PreparedStatement statement = conn.prepareStatement(sqlInsert)) {
            statement.setInt(1, userId);
            statement.setString(2, name);
            statement.setString(3, phoneNumber);
            statement.setString(4, area);
            statement.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error establishing connection or preparing query: " + e.getMessage());
        }
    }

    public int getTutorId(int userId) {
        String sqlQuery = "SELECT tutor_id FROM Tutors WHERE user_id = ?";
        int id = 0;
        try (Connection conn = dbManager.connect(); PreparedStatement statement = conn.prepareStatement(sqlQuery)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    id = rs.getInt("tutor_id");
                } else {
                    throw new SQLException("Tutor id not found");
                }
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error establishing connection or preparing query: " + e.getMessage());
        }
        return id;
    }

    public String createMentorship(String dateTime, int tutorId, int subjectId) {
        String sqlInsert = "INSERT INTO Mentorships (date_time, tutor_id, subject_id) VALUES (?, ?, ?, ?)";
        try (Connection conn = dbManager.connect(); PreparedStatement statement = conn.prepareStatement(sqlInsert)) {
            statement.setString(1, dateTime);
            statement.setInt(2, tutorId);
            statement.setInt(3, subjectId);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return "+++ Mentorship succesfully created and available for scheduling.";
            } else {
                return "--- Error: Mentorship could not be created";
            }
        } catch (SQLException e) {
            return "Error creating mentorship: " + e.getMessage();
        }
    }

    public List<String> getAllSubjects() {
        List<String> subjects = new ArrayList<>();
        String sqlQuery = "SELECT * FROM Subjects";

        try (Connection conn = dbManager.connect(); PreparedStatement statement = conn.prepareStatement(sqlQuery); ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("subject_id");
                String name = rs.getString("name");
                int term = rs.getInt("term");
                subjects.add(id + "," + name + "," + term);
            }
        } catch (SQLException e) {
            System.out.println("Error establishing connection or preparing query: " + e.getMessage());
        }
        return subjects;
    }

    public String registerSubject(String subjectName, int subjectTerm) {
        String sqlInsert = "INSERT INTO Subjects (name, term) VALUES (?, ?)";
        try (Connection conn = dbManager.connect(); PreparedStatement statement = conn.prepareStatement(sqlInsert)) {
            statement.setString(1, subjectName);
            statement.setInt(2, subjectTerm);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                return "+++ Subject succesfully registered.";
            } else {
                return "--- Error: Subject could not be registered";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Error registering subject: " + e.getMessage();
        }
    }

    public List<String> getMentorships(int tutorId) {
        List<String> mentorships = new ArrayList<>();
        String sqlQuery = "SELECT m.mentorship_id, s.name, st.name, m.date_time " +
                "FROM Mentorships m " +
                "JOIN Subjects s ON m.subject_id = s.subject_id " +
                "LEFT JOIN Students st ON m.student_id = st.student_id " +
                "WHERE m.tutor_id = ?";

        try (Connection conn = dbManager.connect(); PreparedStatement statement = conn.prepareStatement(sqlQuery)) {
            statement.setInt(1, tutorId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int idMentorship = rs.getInt("mentorship_id");
                    String subjectName = rs.getString("s.name");
                    String studentName = rs.getString("st.name");
                    String date = rs.getString("m.date_time");
                    mentorships.add(idMentorship + "," + subjectName + "," + studentName + "," + date);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mentorships;
    }
}

