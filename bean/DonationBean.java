package com.careshare.bean;

import java.sql.Date;
import java.sql.Timestamp;

public class DonationBean {
    private String donationID;
    private String donorID;
    private String donorName;
    private String foodItemID;
    private String foodItemName;
    private String staffID;
    private int quantity;
    private Date expiryDate;
    private Timestamp donationDate;
    private String status;
    private String remarks;
    private String reviewedBy;
    private Timestamp reviewedDate;
    
    //default constructor
    public DonationBean() {
    }
    
    //normal constructor
    public DonationBean(String donationID, String donorID, String donorName, String foodItemID, String foodItemName, int quantity, Date expiryDate, Timestamp donationDate, String status, String remarks, String reviewedBy, Timestamp reviewedDate) {
        this.donationID = donationID;
        this.donorID = donorID;
        this.donorName = donorName;
        this.foodItemID = foodItemID;
        this.foodItemName = foodItemName;
        this.quantity = quantity;
        this.expiryDate = expiryDate;
        this.donationDate = donationDate;
        this.status = status;
        this.remarks = remarks;
        this.reviewedBy = reviewedBy;
        this.reviewedDate = reviewedDate;
    }
    
    public String getDonationID() {
        return donationID;
    }
    
    public void setDonationID(String donationID) {
        this.donationID = donationID;
    }

    public String getDonorID() {
        return donorID;
    }

    public void setDonorID(String donorID) {
        this.donorID = donorID;
    }

    public String getDonorName() {
        return donorName;
    }

    public void setDonorName(String donorName) {
        this.donorName = donorName;
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

    public String getStaffID() {
        return staffID;
    }

    public void setStaffID(String staffID) {
        this.staffID = staffID;
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

    public Timestamp getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(Timestamp donationDate) {
        this.donationDate = donationDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getReviewedBy() {
        return reviewedBy;
    }

    public void setReviewedBy(String reviewedBy) {
        this.reviewedBy = reviewedBy;
    }

    public Timestamp getReviewedDate() {
        return reviewedDate;
    }

    public void setReviewedDate(Timestamp reviewedDate) {
        this.reviewedDate = reviewedDate;
    }
}
