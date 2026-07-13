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

@WebServlet("/DeleteFoodItemServlet")
public class DeleteFoodItemServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));

        try (Connection conn = DBConnection.getConnection()) {
            String sql = "DELETE FROM FoodItem WHERE foodItem_id=?";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setInt(1, id);
                ps.executeUpdate();
            }
            response.sendRedirect("ViewFoodItemServlet?role=admin");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
