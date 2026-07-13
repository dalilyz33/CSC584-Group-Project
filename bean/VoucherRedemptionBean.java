/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.careshare.bean;

/**
 *
 * @author pc
 */
import java.io.Serializable;
import java.sql.Date;

public class VoucherRedemptionBean implements Serializable {

    private int redemptionId;
    private int studentId;
    private int voucherId;
    private Date redemptionDate;

    public VoucherRedemptionBean() {
    }

    public int getRedemptionId() {
        return redemptionId;
    }

    public void setRedemptionId(int redemptionId) {
        this.redemptionId = redemptionId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public Date getRedemptionDate() {
        return redemptionDate;
    }

    public void setRedemptionDate(Date redemptionDate) {
        this.redemptionDate = redemptionDate;
    }
}