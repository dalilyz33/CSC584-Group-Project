package com.careshare.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.careshare.bean.AdminBean;
import com.careshare.util.DBConnection;

public class AdminDao {

    // Same idea as StudentDao.generateNextStudentId(), but Admin codes are only
    // 3 digits wide (A001, A002 ... A999) since we expect far fewer admin accounts
    // than students.
    public String generateNextAdminId() {

        String nextId = "A001"; // used only if the Admin table is currently empty

        // Works correctly because every staff_id is the same length (1 letter + 3
        // digits = 4 characters), so alphabetical MAX() ordering matches numeric order.
        String sql = "SELECT MAX(staff_id) AS maxId FROM Admin";

        try (Connection conn = DBConnection.createConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String maxId = rs.getString("maxId");

                if (maxId != null) {
                    // Remove the "A" prefix, leaving just the digits, e.g. "007".
                    String numberPart = maxId.substring(1);

                    int nextNumber = Integer.parseInt(numberPart) + 1;

                    // Pad to 3 digits, e.g. 8 becomes "008".
                    nextId = "A" + String.format("%03d", nextNumber);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error generating admin ID: " + e.getMessage());
        }

        return nextId;
    }

    // Inserts a new Admin row linking the generated staff_id to the user_id
    // created in AppUser.
    public boolean insertAdmin(AdminBean admin) {

        boolean success = false;

        String sql = "INSERT INTO Admin (staff_id, user_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.createConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, admin.getStaffId());
            stmt.setInt(2, admin.getUserId());

            success = stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error inserting admin: " + e.getMessage());
        }

        return success;
    }

    // Looks up the staff_id belonging to a given user_id, used right after login
    // so the admin's staff_id can be stored in the session.
    public String getStaffIdByUserId(int userId) {

        String staffId = null;

        String sql = "SELECT staff_id FROM Admin WHERE user_id = ?";

        try (Connection conn = DBConnection.createConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    staffId = rs.getString("staff_id");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving staff ID: " + e.getMessage());
        }

        return staffId;
    }
}