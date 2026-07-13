package com.careshare.bean;

import java.sql.*;

public class InventoryBean {
    private String inventoryID;
    private String staffID;
    private String staffName;
    private String donationID;
    private String foodItemID;
    private String foodItemName;
    private int quantity;
    private Date expiryDate;
    private String foodStatus;

    public InventoryBean() {
    }

    public InventoryBean(String inventoryID, String staffID, String donationID, String foodItemID, String foodItemName, int quantity, Date expiryDate, String foodStatus) {
        this.inventoryID = inventoryID;
        this.staffID = staffID;
        this.donationID = donationID;
        this.foodItemID = foodItemID;
        this.foodItemName = foodItemName;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.foodStatus = foodStatus;
    }

    public String getInventoryID() {
        return inventoryID;
    }

    public void setInventoryID(String inventoryID) {
        this.inventoryID = inventoryID;
    }

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
    }

    public String getDonationID() {
        return donationID;
    }

    public void setDonationID(String donationID) {
        this.donationID = donationID;
    }

    public String getFoodItemID() {
        return foodItemID;
    }

    public void setFoodItemID(String foodItemID) {
        this.foodItemID = foodItemID;
    }

    public String getFoodItemName() {
        return foodItemName;
    }

    public void setFoodItemName(String foodItemName) {
        this.foodItemName = foodItemName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getFoodStatus() {
        return foodStatus;
    }

    public void setFoodStatus(String foodStatus) {
        this.foodStatus = foodStatus;
    }
    
}
