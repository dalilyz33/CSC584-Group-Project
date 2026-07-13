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

public class VoucherBean implements Serializable {

    private int voucherId;
    private int studentId;
    private int categoryId;
    private int voucherAssignedBy;
    private int voucherQuantity;
    private double voucherValue;
    private String voucherStatus;
    private Date voucherAssignedDate;
    private Date voucherExpiryDate;

    public VoucherBean() {
    }

    public int getVoucherId() {
        return voucherId;
    }

    public void setVoucherId(int voucherId) {
        this.voucherId = voucherId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getVoucherAssignedBy() {
        return voucherAssignedBy;
    }

    public void setVoucherAssignedBy(int voucherAssignedBy) {
        this.voucherAssignedBy = voucherAssignedBy;
    }

    public int getVoucherQuantity() {
        return voucherQuantity;
    }

    public void setVoucherQuantity(int voucherQuantity) {
        this.voucherQuantity = voucherQuantity;
    }

    public double getVoucherValue() {
        return voucherValue;
    }

    public void setVoucherValue(double voucherValue) {
        this.voucherValue = voucherValue;
    }

    public String getVoucherStatus() {
        return voucherStatus;
    }

    public void setVoucherStatus(String voucherStatus) {
        this.voucherStatus = voucherStatus;
    }

    public Date getVoucherAssignedDate() {
        return voucherAssignedDate;
    }

    public void setVoucherAssignedDate(Date voucherAssignedDate) {
        this.voucherAssignedDate = voucherAssignedDate;
    }

    public Date getVoucherExpiryDate() {
        return voucherExpiryDate;
    }

    public void setVoucherExpiryDate(Date voucherExpiryDate) {
        this.voucherExpiryDate = voucherExpiryDate;
    }
}