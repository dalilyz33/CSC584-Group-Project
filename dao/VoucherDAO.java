/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.careshare.dao;

/**
 *
 * @author pc
 */
import com.careshare.bean.VoucherBean;
import com.careshare.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class VoucherDAO {

    public String assignVoucher(VoucherBean voucher) {

        String sql =
            "INSERT INTO VOUCHER "
          + "(STUDENT_ID, CATEGORY_ID, "
          + "VOUCHER_ASSIGNEDBY, VOUCHER_QUANTITY, "
          + "VOUCHER_VALUE, VOUCHER_STATUS, "
          + "VOUCHER_ASSIGNEDDATE, VOUCHER_EXPIRYDATE) "
          + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            Connection con =
                    DBConnection.createConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, voucher.getStudentId());
            ps.setInt(2, voucher.getCategoryId());
            ps.setInt(3, voucher.getVoucherAssignedBy());
            ps.setInt(4, voucher.getVoucherQuantity());
            ps.setDouble(5, voucher.getVoucherValue());
            ps.setString(6, voucher.getVoucherStatus());
            ps.setDate(7, voucher.getVoucherAssignedDate());
            ps.setDate(8, voucher.getVoucherExpiryDate());

            int result = ps.executeUpdate();

            ps.close();
            con.close();

            if (result > 0) {
                return "SUCCESS";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "ERROR";
    }

    public ArrayList<VoucherBean>
            getVouchersByStudent(int studentId) {

        ArrayList<VoucherBean> list =
                new ArrayList<VoucherBean>();

        String sql =
            "SELECT * FROM VOUCHER "
          + "WHERE STUDENT_ID = ? "
          + "ORDER BY VOUCHER_ID DESC";

        try {
            Connection con =
                    DBConnection.createConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                VoucherBean voucher =
                        new VoucherBean();

                voucher.setVoucherId(
                        rs.getInt("VOUCHER_ID")
                );

                voucher.setStudentId(
                        rs.getInt("STUDENT_ID")
                );

                voucher.setCategoryId(
                        rs.getInt("CATEGORY_ID")
                );

                voucher.setVoucherAssignedBy(
                        rs.getInt("VOUCHER_ASSIGNEDBY")
                );

                voucher.setVoucherQuantity(
                        rs.getInt("VOUCHER_QUANTITY")
                );

                voucher.setVoucherValue(
                        rs.getDouble("VOUCHER_VALUE")
                );

                voucher.setVoucherStatus(
                        rs.getString("VOUCHER_STATUS")
                );

                voucher.setVoucherAssignedDate(
                        rs.getDate("VOUCHER_ASSIGNEDDATE")
                );

                voucher.setVoucherExpiryDate(
                        rs.getDate("VOUCHER_EXPIRYDATE")
                );

                list.add(voucher);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public VoucherBean getVoucherById(int voucherId) {

        VoucherBean voucher = null;

        String sql =
            "SELECT * FROM VOUCHER "
          + "WHERE VOUCHER_ID = ?";

        try {
            Connection con =
                    DBConnection.createConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, voucherId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {

                voucher = new VoucherBean();

                voucher.setVoucherId(
                        rs.getInt("VOUCHER_ID")
                );

                voucher.setStudentId(
                        rs.getInt("STUDENT_ID")
                );

                voucher.setCategoryId(
                        rs.getInt("CATEGORY_ID")
                );

                voucher.setVoucherAssignedBy(
                        rs.getInt("VOUCHER_ASSIGNEDBY")
                );

                voucher.setVoucherQuantity(
                        rs.getInt("VOUCHER_QUANTITY")
                );

                voucher.setVoucherValue(
                        rs.getDouble("VOUCHER_VALUE")
                );

                voucher.setVoucherStatus(
                        rs.getString("VOUCHER_STATUS")
                );

                voucher.setVoucherAssignedDate(
                        rs.getDate("VOUCHER_ASSIGNEDDATE")
                );

                voucher.setVoucherExpiryDate(
                        rs.getDate("VOUCHER_EXPIRYDATE")
                );
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return voucher;
    }

    public String markVoucherUsed(int voucherId) {

        String sql =
            "UPDATE VOUCHER "
          + "SET VOUCHER_STATUS = 'Used' "
          + "WHERE VOUCHER_ID = ? "
          + "AND VOUCHER_STATUS = 'Active'";

        try {
            Connection con =
                    DBConnection.createConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, voucherId);

            int result = ps.executeUpdate();

            ps.close();
            con.close();

            if (result > 0) {
                return "SUCCESS";
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "ERROR";
    }
}