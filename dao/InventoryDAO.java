package com.careshare.dao;

import com.careshare.bean.DonationBean;
import com.careshare.bean.FoodItemBean;
import com.careshare.bean.InventoryBean;
import com.careshare.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    private static final String BASE_SELECT =
            "SELECT i.inventory_id, i.food_item_id, f.item_name, f.unit, i.donation_id, " +
            "       i.quantity, i.expiry_date, i.food_status, i.last_updated " +
            "FROM Inventory i " +
            "JOIN FoodItem f ON i.food_item_id = f.food_item_id ";

    public boolean addInventoryFromDonation(DonationBean donation) {
        String findSql =
                "SELECT inventory_id, quantity FROM Inventory " +
                "WHERE food_item_id = ? AND food_status = 'AVAILABLE' " +
                "AND ((expiry_date IS NULL AND ? IS NULL) OR expiry_date = ?) " +
                "LIMIT 1";

        try (Connection con = DBConnection.createConnection()) {
            String existingID = null;
            double existingQty = 0;

            try (PreparedStatement ps = con.prepareStatement(findSql)) {
                ps.setString(1, donation.getFoodItemID());
                ps.setDate(2, donation.getExpiryDate());
                ps.setDate(3, donation.getExpiryDate());
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        existingID = rs.getString("inventory_id");
                        existingQty = rs.getDouble("quantity");
                    }
                }
            }

            if (existingID != null) {
                String updateSql = "UPDATE Inventory SET quantity = ?, donation_id = ? WHERE inventory_id = ?";
                try (PreparedStatement ps = con.prepareStatement(updateSql)) {
                    ps.setDouble(1, existingQty + donation.getQuantity());
                    ps.setString(2, donation.getDonationID());
                    ps.setString(3, existingID);
                    return ps.executeUpdate() > 0;
                }
            } else {
                String insertSql =
                        "INSERT INTO Inventory (food_item_id, donation_id, quantity, expiry_date, food_status) " +
                        "VALUES (?, ?, ?, ?, 'AVAILABLE')";
                try (PreparedStatement ps = con.prepareStatement(insertSql)) {
                    ps.setString(1, donation.getFoodItemID());
                    ps.setString(2, donation.getDonationID());
                    ps.setDouble(3, donation.getQuantity());
                    ps.setDate(4, donation.getExpiryDate());
                    return ps.executeUpdate() > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public List<InventoryBean> getAllInventory() {
        List<InventoryBean> list = new ArrayList<>();
        String sql = BASE_SELECT + "ORDER BY i.expiry_date ASC";
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
    
    public InventoryBean getInventoryById(int inventoryId) {
        String sql = BASE_SELECT + "WHERE i.inventory_id = ?";
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, inventoryId);
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
    
    public boolean updateInventory(InventoryBean inv) {
        String sql = "UPDATE Inventory SET quantity = ?, expiry_date = ?, food_status = ? WHERE inventory_id = ?";
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDouble(1, inv.getQuantity());
            ps.setDate(2, inv.getExpiryDate());
            ps.setString(3, inv.getFoodStatus());
            ps.setString(4, inv.getInventoryID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    private InventoryBean mapRow(ResultSet rs) throws SQLException {
        InventoryBean inv = new InventoryBean();
        inv.setInventoryID(rs.getString("inventory_id"));
        inv.setFoodItemID(rs.getString("food_item_id"));
        inv.setFoodItemName(rs.getString("item_name"));
        inv.setDonationID(rs.getString(donation_id));
        inv.setQuantity(rs.getInt("quantity"));
        inv.setExpiryDate(rs.getDate("expiry_date"));
        inv.setFoodStatus(rs.getString("food_status"));
        return inv;
    }
}
