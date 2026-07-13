package com.careshare.bean;

import java.io.Serializable;

public class DonorBean implements Serializable {

    private String donorId; // formatted code, e.g. "D007"
    private int userId;

    // Default constructor
    public DonorBean() {
    }

    // Normal constructor
    public DonorBean(String donorId, int userId) {
        this.donorId = donorId;
        this.userId = userId;
    }

    // Getters and setters
    public String getDonorId() {
        return donorId;
    }

    public void setDonorId(String donorId) {
        this.donorId = donorId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}