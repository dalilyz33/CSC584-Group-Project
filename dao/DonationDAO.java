package com.careshare.dao;

import java.sql.*;
import com.careshare.bean.DonationBean;
import java.util.*;
import com.careshare.util.DBConnection;

public class DonationDAO {
    private static final String BASE_SELECT =
            "SELECT d.donation_id, d.donor_id, du.user_fullName AS donor_name, " +
            "       d.staff_id, su.user_fullName AS staff_name, " +
            "       d.foodItem_id, f.foodItem_name, d.donation_quantity, " +
            "       d.donation_dateApplication, d.donation_dateApproved, d.donation_status " +
            "FROM Donation d " +
            "JOIN Donor dn ON d.donor_id = dn.donor_id " +
            "JOIN User du ON dn.user_id = du.user_id " +
            "LEFT JOIN Admin a ON d.staff_id = a.staff_id " +
            "LEFT JOIN User su ON a.user_id = su.user_id " +
            "JOIN FoodItem f ON d.foodItem_id = f.foodItem_id ";

    public int createDonation(DonationBean donation) {
        String sql = "INSERT INTO Donation (donor_id, food_item_id, quantity, expiry_date, status, remarks) " +
                     "VALUES (?, ?, ?, ?, 'PENDING', ?)";
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, donation.getDonorID());
            ps.setString(2, donation.getFoodItemID());
            ps.setDouble(3, donation.getQuantity());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                try (ResultSet keys = ps.getGeneratedKeys()) {
                    if (keys.next()) {
                        return keys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
    
    public List<DonationBean> getDonationsByDonor(String donorID) {
        List<DonationBean> list = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE d.donor_id = ? ORDER BY d.donation_date DESC";
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, donorID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapRow(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<DonationBean> getAllDonations() {
        List<DonationBean> list = new ArrayList<>();
        String sql = BASE_SELECT + "ORDER BY d.donation_date DESC";
        try (Connection con = DBConnection.createConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public List<DonationBean> getPendingDonations() {
        List<DonationBean> list = new ArrayList<>();
        String sql = BASE_SELECT + "WHERE d.status = 'PENDING' ORDER BY d.donation_date ASC";
        try (Connection con = DBConnection.createConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(mapRow(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    
    public DonationBean getDonationById(String donationID) {
        String sql = BASE_SELECT + "WHERE d.donation_id = ?";
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, donationID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public boolean updateDonationStatus(String donationID, String staffID, String status) {
        String sql = "UPDATE Donation SET status = ?, reviewed_by = ?, reviewed_date = NOW(), remarks = ? " +
                     "WHERE donation_id = ?";
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, status);
            ps.setString(2, staffID);
            ps.setString(3, donationID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private DonationBean mapRow(ResultSet rs) throws SQLException {
        DonationBean d = new DonationBean();
        d.setDonationID(rs.getString("donation_id"));
        d.setDonorID(rs.getString("donor_id"));
        d.setDonorName(rs.getString("donor_name"));
        d.setStaffID(rs.getString("staff_id"));
        d.setFoodItemID(rs.getString("food_item_id"));
        d.setFoodItemName(rs.getString("item_name"));
        d.setUnit(rs.getString("unit"));
        d.setQuantity(rs.getInt("quantity"));
        d.setExpiryDate(rs.getDate("expiry_date"));
        d.setDonationDate(rs.getTimestamp("donation_date"));
        d.setStatus(rs.getString("status"));
        d.setRemarks(rs.getString("remarks"));
        d.setReviewedBy(rs.getString("reviewed_by"));
        d.setReviewedDate(rs.getTimestamp("reviewed_date"));
        return d;
    }
}
