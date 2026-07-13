package com.careshare.dao;

import com.careshare.bean.FoodItemBean;
import java.sql.*;
import java.util.*;
import com.careshare.util.DBConnection;

public class FoodItemDAO {
    private static final String BASE_SELECT =
            "SELECT f.foodItem_id, f.category_id, c.category_name, f.foodItem_name, " +
            "       f.foodItem_type, f.foodItem_description " +
            "FROM FoodItem f " +
            "JOIN NotificationCategory c ON f.category_id = c.category_id ";
    
    public int addFoodItem(FoodItemBean item) {
        String sql = "INSERT INTO FoodItem (category_id, foodItem_name, foodItem_type, foodItem_description) " +
                     "VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, item.getCategory());
            ps.setString(2, item.getFoodItemName());
            ps.setString(3, item.getFoodItemType());
            ps.setString(4, item.getDescription());

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
    
    public boolean updateFoodItem(FoodItemBean item) {
        String sql = "UPDATE FoodItem SET item_name = ?, category = ?, unit = ?, description = ? WHERE food_item_id = ?";
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, item.getFoodItemName());
            ps.setString(2, item.getFoodItemType());
            ps.setString(3, item.getCategory());
            ps.setString(4, item.getDescription());
            ps.setString(5, item.getFoodItemID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean deleteFoodItem(String foodItemID) {
        String sql = "DELETE FROM FoodItem WHERE food_item_id = ?";
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, foodItemID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public FoodItemBean getFoodItemById(String foodItemID) {
        String sql = "SELECT * FROM FoodItem WHERE food_item_id = ?";
        try (Connection con = DBConnection.createConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, foodItemID);
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
    
    public List<FoodItemBean> getAllFoodItems() {
        List<FoodItemBean> list = new ArrayList<>();
        String sql = "SELECT * FROM FoodItem ORDER BY item_name ASC";
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
    
    private FoodItemBean mapRow(ResultSet rs) throws SQLException {
        FoodItemBean item = new FoodItemBean();
        item.setFoodItemID(rs.getString("food_item_id"));
        item.setFoodItemName(rs.getString("item_name"));
        item.setCategory(rs.getString("category"));
        item.setDescription(rs.getString("description"));
        return item;
    }
}
