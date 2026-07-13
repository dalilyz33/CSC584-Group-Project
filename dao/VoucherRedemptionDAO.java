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


import com.careshare.bean.VoucherRedemptionBean;
import com.careshare.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class VoucherRedemptionDAO {

    public String addRedemption(
            VoucherRedemptionBean redemption) {

        String sql =
            "INSERT INTO VOUCHERREDEMPTION "
          + "(STUDENT_ID, VOUCHER_ID, REDEMPTION_DATE) "
          + "VALUES (?, ?, ?)";

        try {
            Connection con =
                    DBConnection.createConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, redemption.getStudentId());
            ps.setInt(2, redemption.getVoucherId());
            ps.setDate(3, redemption.getRedemptionDate());

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

    public ArrayList<VoucherRedemptionBean>
            getHistoryByStudent(int studentId) {

        ArrayList<VoucherRedemptionBean> list =
                new ArrayList<VoucherRedemptionBean>();

        String sql =
            "SELECT * FROM VOUCHERREDEMPTION "
          + "WHERE STUDENT_ID = ? "
          + "ORDER BY REDEMPTION_ID DESC";

        try {
            Connection con =
                    DBConnection.createConnection();

            PreparedStatement ps =
                    con.prepareStatement(sql);

            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {

                VoucherRedemptionBean redemption =
                        new VoucherRedemptionBean();

                redemption.setRedemptionId(
                        rs.getInt("REDEMPTION_ID")
                );

                redemption.setStudentId(
                        rs.getInt("STUDENT_ID")
                );

                redemption.setVoucherId(
                        rs.getInt("VOUCHER_ID")
                );

                redemption.setRedemptionDate(
                        rs.getDate("REDEMPTION_DATE")
                );

                list.add(redemption);
            }

            rs.close();
            ps.close();
            con.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}