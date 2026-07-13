package com.foodbank.dao;

import com.foodbank.model.InventoryItemBean;
import com.foodbank.util.DBConnection;
import com.foodbank.util.NotificationHelper;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * DAO that gathers statistics and inventory data for the Admin Dashboard.
 * Reads from Inventory and FoodItem (owned by Lily's module) and
 * FoodCollectionBooking (owned by this module).
 * Author: Ainaa - Food Booking, Notification & Dashboard Module
 */
public class DashboardDAO {

    // Items at or below this quantity trigger a low-stock alert
    private static final int LOW_STOCK_THRESHOLD = 10;
    // Items expiring within this many days trigger an expiring-soon alert
    private static final int EXPIRY_WARNING_DAYS = 3;

    public Map<String, Integer> getDashboardStats() {
        Map<String, Integer> stats = new LinkedHashMap<>();

        stats.put("totalAvailableFood", sumColumn(
                "SELECT SUM(quantity) FROM Inventory WHERE status = 'AVAILABLE'"));
        stats.put("foodDistributed", sumColumn(
                "SELECT SUM(quantity) FROM Inventory WHERE status = 'DISTRIBUTED'"));
        stats.put("noOfRequests", countRows(
                "SELECT COUNT(*) FROM FoodCollectionBooking"));

        return stats;
    }

    // Full inventory list for the dashboard table (No. / Name / Category / Status / Expiry Date)
    public List<InventoryItemBean> getInventoryList() {
        List<InventoryItemBean> list = new ArrayList<>();
        String sql = "SELECT i.inventory_id, i.food_item_id, f.name AS item_name, f.category, "
                + "i.status, i.quantity, i.expiry_date "
                + "FROM Inventory i JOIN FoodItem f ON i.food_item_id = f.food_item_id "
                + "ORDER BY i.expiry_date ASC";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                InventoryItemBean item = new InventoryItemBean();
                item.setInventoryId(rs.getInt("inventory_id"));
                item.setFoodItemId(rs.getInt("food_item_id"));
                item.setItemName(rs.getString("item_name"));
                item.setCategory(rs.getString("category"));
                item.setStatus(rs.getString("status"));
                item.setQuantity(rs.getInt("quantity"));
                item.setExpiryDate(rs.getString("expiry_date"));
                list.add(item);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Scans inventory and fires STOCK_LOW / EXPIRING_SOON notifications to admin where needed.
    // Call this once when the admin dashboard loads.
    public void checkInventoryAlerts() {
        for (InventoryItemBean item : getInventoryList()) {
            if (item.getQuantity() <= LOW_STOCK_THRESHOLD) {
                NotificationHelper.notifyLowStock(item.getItemName(), item.getQuantity());
            }

            if (item.getExpiryDate() != null) {
                try {
                    LocalDate expiry = LocalDate.parse(item.getExpiryDate());
                    if (!expiry.isBefore(LocalDate.now())
                            && expiry.isBefore(LocalDate.now().plusDays(EXPIRY_WARNING_DAYS + 1))) {
                        NotificationHelper.notifyExpiringSoon(item.getItemName(), item.getExpiryDate());
                    }
                } catch (Exception ignored) {
                    // skip items with an unparseable/blank expiry date
                }
            }
        }
    }

    private int countRows(String sql) {
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    private int sumColumn(String sql) {
        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            if (rs.next()) {
                return rs.getInt(1); // returns 0 automatically if SUM is NULL
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
