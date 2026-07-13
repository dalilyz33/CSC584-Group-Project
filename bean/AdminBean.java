package com.careshare.bean;

import java.io.Serializable;

public class AdminBean implements Serializable {

    private String staffId; // formatted code, e.g. "A007"
    private int userId;

    // Default constructor
    public AdminBean() {
    }

    // Normal constructor
    public AdminBean(String staffId, int userId) {
        this.staffId = staffId;
        this.userId = userId;
    }

    // Getters and setters
    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
