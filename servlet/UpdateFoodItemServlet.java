package com.careshare.servlet;

import com.careshare.util.DBConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/UpdateFoodItemServlet")
public class UpdateFoodItemServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("foodItemId"));
        String name = request.getParameter("foodItemName");
        String type = request.getParameter("foodItemType");
        String desc = request.getParameter("foodItemDescription");
        int categoryId = Integer.parseInt(request.getParameter("categoryId"));

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "UPDATE FoodItem SET foodItem_name=?, foodItem_type=?, foodItem_description=?, category_id=? WHERE foodItem_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.setString(2, type);
                ps.setString(3, desc);
                ps.setInt(4, categoryId);
                ps.setInt(5, id);
                ps.executeUpdate();
            }
            response.sendRedirect("ViewFoodItemServlet?role=admin");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
