package com.careshare.servlet;

import com.careshare.bean.NotificationCategoryBean;
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

@WebServlet("/NotificationCategoryServlet")
public class NotificationCategoryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<NotificationCategoryBean> categories = new ArrayList<>();
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "SELECT * FROM NotificationCategory";
            try (PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NotificationCategoryBean c = new NotificationCategoryBean();
                    c.setCategoryId(rs.getInt("category_id"));
                    c.setCategoryName(rs.getString("category_name"));
                    categories.add(c);
                }
            }
            request.setAttribute("categories", categories);
            request.getRequestDispatcher("notificationCategory.jsp").forward(request, response);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("categoryName");
        try (Connection conn = DBConnection.getConnection()) {
            String sql = "INSERT INTO NotificationCategory (category_name) VALUES (?)";
            try (PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setString(1, name);
                ps.executeUpdate();
            }
            response.sendRedirect("NotificationCategoryServlet");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
