package com.careshare.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.careshare.bean.StudentBean;
import com.careshare.util.DBConnection;

public class StudentDao {

    // Works out the next student_id in the format S00001, S00002, S00003, and so on.
    // student_id is a VARCHAR, not an auto-increment column (Derby can't
    // auto-generate a letter-prefixed code), so we calculate the next one ourselves
    // before every insert.
    public String generateNextStudentId() {

        // Used only if the Student table is currently empty — the very first student.
        String nextId = "S00001";

        // MAX(student_id) gives us the highest existing ID as a string. This only
        // works correctly because every ID is the same length (1 letter + 5 digits =
        // 6 characters). When strings being compared are the same length, comparing
        // them alphabetically gives the same order as comparing the numbers would —
        // e.g. "S00009" comes before "S00010" alphabetically, same as 9 < 10 numerically.
        String sql = "SELECT MAX(student_id) AS maxId FROM Student";

        try (Connection conn = DBConnection.createConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // A SELECT MAX(...) query always returns exactly one row, even if the
            // table is empty (in that case maxId will be null, not "no row").
            if (rs.next()) {
                String maxId = rs.getString("maxId");

                // maxId is null only when there are zero rows in the Student table.
                if (maxId != null) {
                    // substring(1) removes the first character ("S"), leaving just the
                    // digits, e.g. "S00007" becomes "00007".
                    String numberPart = maxId.substring(1);

                    // Convert the digit text into an actual int so we can add 1 to it.
                    // Leading zeros are ignored automatically by parseInt, e.g. "00007" becomes 7.
                    int nextNumber = Integer.parseInt(numberPart) + 1;

                    // String.format("%05d", nextNumber) pads the number with leading
                    // zeros until it is 5 digits wide, e.g. 8 becomes "00008".
                    nextId = "S" + String.format("%05d", nextNumber);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error generating student ID: " + e.getMessage());
        }

        return nextId;
    }

    // Inserts a new Student row using the student_id generated above and the
    // user_id that was just created in AppUser (passed in via the StudentBean).
    public boolean insertStudent(StudentBean student) {

        boolean success = false;

        String sql = "INSERT INTO Student (student_id, user_id, assistance_type, program_code, group_code) "
                    + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DBConnection.createConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getStudentId());
            stmt.setInt(2, student.getUserId());
            stmt.setString(3, student.getAssistanceType());
            stmt.setString(4, student.getProgramCode());
            stmt.setString(5, student.getGroupCode());

            // executeUpdate() returns the number of rows changed. A successful
            // single-row insert returns 1, so ">0" is true.
            success = stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error inserting student: " + e.getMessage());
        }

        return success;
    }

    // Looks up the student_id belonging to a given user_id. Called right after a
    // successful login so we can store both IDs in the session — Member 4's
    // booking servlet, for example, will need student_id from the session later.
    public String getStudentIdByUserId(int userId) {

        String studentId = null;

        String sql = "SELECT student_id FROM Student WHERE user_id = ?";

        try (Connection conn = DBConnection.createConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    studentId = rs.getString("student_id");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving student ID: " + e.getMessage());
        }

        return studentId;
    }
}