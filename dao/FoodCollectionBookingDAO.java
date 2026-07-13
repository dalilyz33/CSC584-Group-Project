package com.foodbank.dao;

import com.foodbank.model.FoodCollectionBookingBean;
import com.foodbank.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for the FoodCollectionBooking table.
 * Author: Ainaa - Food Booking, Notification & Dashboard Module
 */
public class FoodCollectionBookingDAO {

    // Create a new booking (student books a food collection slot)
    public boolean createBooking(FoodCollectionBookingBean booking) {
        String sql = "INSERT INTO FoodCollectionBooking "
                + "(student_id, booking_date, collection_date, collection_time, status, remarks) "
                + "VALUES (?, NOW(), ?, ?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, booking.getStudentId());
            ps.setString(2, booking.getCollectionDate());
            ps.setString(3, booking.getCollectionTime());
            ps.setString(4, booking.getStatus() == null ? "PENDING" : booking.getStatus());
            ps.setString(5, booking.getRemarks());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Get all bookings made by a specific student
    public List<FoodCollectionBookingBean> getBookingsByStudent(int studentId) {
        List<FoodCollectionBookingBean> list = new ArrayList<>();
        String sql = "SELECT * FROM FoodCollectionBooking WHERE student_id = ? ORDER BY booking_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, studentId);
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

    // Get all bookings (for admin management view), most recent first
    public List<FoodCollectionBookingBean> getAllBookings() {
        List<FoodCollectionBookingBean> list = new ArrayList<>();
        String sql = "SELECT b.*, s.name AS student_name FROM FoodCollectionBooking b "
                + "JOIN Student s ON b.student_id = s.student_id "
                + "ORDER BY b.booking_date DESC";

        try (Connection conn = DBConnection.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                FoodCollectionBookingBean b = mapRow(rs);
                b.setStudentName(rs.getString("student_name"));
                list.add(b);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get bookings filtered by status (e.g. admin viewing only PENDING bookings)
    public List<FoodCollectionBookingBean> getBookingsByStatus(String status) {
        List<FoodCollectionBookingBean> list = new ArrayList<>();
        String sql = "SELECT b.*, s.name AS student_name FROM FoodCollectionBooking b "
                + "JOIN Student s ON b.student_id = s.student_id "
                + "WHERE b.status = ? ORDER BY b.booking_date DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    FoodCollectionBookingBean b = mapRow(rs);
                    b.setStudentName(rs.getString("student_name"));
                    list.add(b);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Get single booking by id
    public FoodCollectionBookingBean getBookingById(int bookingId) {
        String sql = "SELECT * FROM FoodCollectionBooking WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
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

    // Admin/system updates booking status (e.g. APPROVED, REJECTED, COMPLETED)
    public boolean updateBookingStatus(int bookingId, String status) {
        String sql = "UPDATE FoodCollectionBooking SET status = ? WHERE booking_id = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, status);
            ps.setInt(2, bookingId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Student cancels their own booking (only allowed while still PENDING/APPROVED)
    public boolean cancelBooking(int bookingId, int studentId) {
        String sql = "UPDATE FoodCollectionBooking SET status = 'CANCELLED' "
                + "WHERE booking_id = ? AND student_id = ? AND status IN ('PENDING','APPROVED')";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, bookingId);
            ps.setInt(2, studentId);
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private FoodCollectionBookingBean mapRow(ResultSet rs) throws SQLException {
        FoodCollectionBookingBean b = new FoodCollectionBookingBean();
        b.setBookingId(rs.getInt("booking_id"));
        b.setStudentId(rs.getInt("student_id"));
        b.setBookingDate(rs.getTimestamp("booking_date"));
        b.setCollectionDate(rs.getString("collection_date"));
        b.setCollectionTime(rs.getString("collection_time"));
        b.setStatus(rs.getString("status"));
        b.setRemarks(rs.getString("remarks"));
        return b;
    }
}
