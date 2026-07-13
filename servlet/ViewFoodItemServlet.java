package com.careshare.servlet;

import com.careshare.bean.FoodItemBean;
import com.careshare.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ViewFoodItemServlet")
public class ViewFoodItemServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String role = request.getParameter("role");
        String action = request.getParameter("action");
        List<FoodItemBean> foodItems = new ArrayList<>();

        try (Connection conn = DBConnection.getConnection()) {
            if ("edit".equals(action)) {
                int id = Integer.parseInt(request.getParameter("id"));
                String sql = "SELECT * FROM FoodItem WHERE foodItem_id=?";
                try (PreparedStatement ps = conn.prepareStatement(sql)) {
                    ps.setInt(1, id);
                    try (ResultSet rs = ps.executeQuery()) {
                        if (rs.next()) {
                            FoodItemBean item = new FoodItemBean();
                            item.setFoodItemId(rs.getInt("foodItem_id"));
                            item.setFoodItemName(rs.getString("foodItem_name"));
                            item.setFoodItemType(rs.getString("foodItem_type"));
                            item.setFoodItemDescription(rs.getString("foodItem_description"));
                            item.setCategoryId(rs.getInt("category_id"));
                            request.setAttribute("foodItem", item);
                        }
                    }
                }
                request.getRequestDispatcher("editFoodItem.jsp").forward(request, response);
                return;
            }

            // Default: List all items
            String sql = "SELECT * FROM FoodItem";
            try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FoodItemBean item = new FoodItemBean();
                    item.setFoodItemId(rs.getInt("foodItem_id"));
                    item.setFoodItemName(rs.getString("foodItem_name"));
                    item.setFoodItemType(rs.getString("foodItem_type"));
                    item.setFoodItemDescription(rs.getString("foodItem_description"));
                    item.setCategoryId(rs.getInt("category_id"));
                    foodItems.add(item);
                }
            }
            request.setAttribute("foodItems", foodItems);

            if ("admin".equalsIgnoreCase(role)) {
                request.getRequestDispatcher("foodItemList.jsp").forward(request, response);
            } else {
                request.getRequestDispatcher("studentFoodBooking.jsp").forward(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
