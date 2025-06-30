package com.mentorshipSystem.crud;

import com.mentorshipSystem.models.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private DBConnectionManager dbManager = new DBConnectionManager();

    public void registerStudent(int userId, String name, int term, String phoneNumber) {
        String sqlUpdate = "INSERT INTO Students (user_id, name, term, phone_number) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = dbManager.connect(); PreparedStatement statement = conn.prepareStatement(sqlUpdate)) {
            statement.setInt(1, userId);
            statement.setString(2, name);
            statement.setInt(3, term);
            statement.setString(4, phoneNumber);
            statement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error establishing connection or preparing query: " + e.getMessage());
        }
    }

    public Student getStudentInfo(int userId) {
        String sqlQuery = "SELECT * FROM Students WHERE user_id = ?";
        Student student = null;
        try (Connection conn = dbManager.connect(); PreparedStatement statement = conn.prepareStatement(sqlQuery)) {
            statement.setInt(1, userId);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    student = new Student(rs.getInt("student_id"),
                            rs.getString("name"),
                            rs.getInt("term"),
                            rs.getString("phone_number"));
                } else {
                    throw new SQLException("User id not found");
                }
            } catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error establishing connection or preparing query: " + e.getMessage());
        }
        return student;
    }

    public List<String> getAvailableMentorshipsBySubject(int subjectId) {
        List<String> freeMentorships = new ArrayList<>();
        String sqlQuery = "SELECT m.mentorship_id, t.name, s.name, m.date_time " +
                "FROM Mentorships m " +
                "JOIN Tutors t ON m.tutor_id = t.tutor_id " +
                "JOIN Subjects s ON m.subject_id = s.subject_id " +
                "WHERE m.student_id IS NULL AND s.subject_id = ? AND m.state != 'Canceled' " +
                "ORDER BY m.mentorship_id ASC";

        try (Connection conn = dbManager.connect(); PreparedStatement statement = conn.prepareStatement(sqlQuery)) {
            statement.setInt(1, subjectId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("mentorship_id");
                    String tutor = rs.getString("t.name");
                    String subject = rs.getString("s.name");
                    String date = rs.getString("date_time");
                    freeMentorships.add(id + "," + tutor + "," + subject + "," + date);
                }
            }catch (SQLException e) {
                System.out.println("Error: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error establishing connection or preparing query: " + e.getMessage());
        }
        return freeMentorships;
    }

    public String scheduleMentorship(int mentorshipId, int studentId) {
        String sqlUpdate = "UPDATE Mentorships SET student_id = ?, state = 'Scheduled' WHERE mentorship_id = ? AND student_id IS NULL";
        try (Connection conn = dbManager.connect(); PreparedStatement statement = conn.prepareStatement(sqlUpdate)) {
            statement.setInt(1, studentId);
            statement.setInt(2, mentorshipId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return "+++ Mentorship successfully scheduled.";
            } else {
                return "--- Error: Mentorship is not available.";
            }
        } catch (SQLException e) {
            return "Error processing schedule: " + e.getMessage();
        }
    }

    public String cancelMentorship(int mentorshipId) {
        String sqlUpdate = "UPDATE Mentorships SET student_id = NULL, state = 'Available' WHERE mentorship_id = ?";
        try (Connection conn = dbManager.connect(); PreparedStatement statement = conn.prepareStatement(sqlUpdate)) {
            statement.setInt(1, mentorshipId);
            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                return "+++ Mentorship succesfully canceled.";
            } else {
                return "--- Error: There is no mentorship found with that id.";
            }
        } catch (SQLException e) {
            return "Error procesing cancellation: " + e.getMessage();
        }
    }

    public List<String> getMentorships(int studentId) {
        List<String> mentorships = new ArrayList<>();
        String sqlQuery = "SELECT m.mentorship_id, s.name, m.date_time, t.name " +
                    "FROM Mentorships m " +
                    "INNER JOIN Subjects s ON m.subject_id = s.subject_id " +
                    "INNER JOIN Tutors t ON m.tutor_id = t.tutor_id " +
                    "WHERE m.student_id = ? " +
                    "ORDER BY m.mentorship_id ASC";

        try (Connection conn = dbManager.connect(); PreparedStatement statement = conn.prepareStatement(sqlQuery)) {
            statement.setInt(1, studentId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int mentorshipId = rs.getInt("mentorship_id");
                    String subjectName = rs.getString("s.name");
                    String dateTimeMentorship = rs.getString("date_time");
                    String tutorName = rs.getString("t.name");
                    mentorships.add(mentorshipId + "," + subjectName + "," + dateTimeMentorship + "," + tutorName);
                }
            } catch (SQLException e) {
                System.out.println("Error executing query or processing results: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error establishing connection or preparing query: " + e.getMessage());
        }
        return mentorships;
    }

    public List<String> getAvailableSubjectsByTerm(int term) {
        List<String> subjects = new ArrayList<>();
        String sqlQuery = "SELECT s.subject_id, s.name " +
                    "FROM Subjects s " +
                    "INNER JOIN Mentorships m ON s.subject_id = m.subject_id " +
                    "WHERE m.student_id IS NULL AND s.term = ? " +
                    "GROUP BY s.subject_id, s.name " +
                    "ORDER BY s.subject_id ASC";

        try (Connection conn = dbManager.connect(); PreparedStatement statement = conn.prepareStatement(sqlQuery)) {
            statement.setInt(1, term);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("subject_id");
                    String name = rs.getString("name");
                    subjects.add(id + "," + name);
                }
            } catch (SQLException e) {
                System.out.println("Error executing query or processing results: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error establishing connection or preparing query: " + e.getMessage());

        }
        return subjects;
    }



}
















