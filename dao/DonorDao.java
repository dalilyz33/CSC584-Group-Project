package com.careshare.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.careshare.bean.DonorBean;
import com.careshare.util.DBConnection;

public class DonorDao {

    // Same pattern as AdminDao.generateNextAdminId() — 3-digit padding,
    // "D" prefix instead of "A".
    public String generateNextDonorId() {

        String nextId = "D001"; // used only if the Donor table is currently empty

        String sql = "SELECT MAX(donor_id) AS maxId FROM Donor";

        try (Connection conn = DBConnection.createConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                String maxId = rs.getString("maxId");

                if (maxId != null) {
                    // Remove the "D" prefix, leaving just the digits, e.g. "089".
                    String numberPart = maxId.substring(1);

                    int nextNumber = Integer.parseInt(numberPart) + 1;

                    // Pad to 3 digits, e.g. 90 becomes "090".
                    nextId = "D" + String.format("%03d", nextNumber);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error generating donor ID: " + e.getMessage());
        }

        return nextId;
    }

    // Inserts a new Donor row linking the generated donor_id to the user_id
    // created in AppUser.
    public boolean insertDonor(DonorBean donor) {

        boolean success = false;

        String sql = "INSERT INTO Donor (donor_id, user_id) VALUES (?, ?)";

        try (Connection conn = DBConnection.createConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, donor.getDonorId());
            stmt.setInt(2, donor.getUserId());

            success = stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error inserting donor: " + e.getMessage());
        }

        return success;
    }

    // Looks up the donor_id belonging to a given user_id, used right after login
    // so the donor's donor_id can be stored in the session.
    public String getDonorIdByUserId(int userId) {

        String donorId = null;

        String sql = "SELECT donor_id FROM Donor WHERE user_id = ?";

        try (Connection conn = DBConnection.createConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    donorId = rs.getString("donor_id");
                }
            }

        } catch (SQLException e) {
            System.out.println("Error retrieving donor ID: " + e.getMessage());
        }

        return donorId;
    }
}