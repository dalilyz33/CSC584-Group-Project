package com.careshare.bean;

import java.io.Serializable;

public class UserBean implements Serializable {

    private int userId;
    private String userFullName;
    private String userEmail;
    private String userPassword;
    private String userRole; // "Student", "Admin", or "Donor"

    // Default constructor
    public UserBean() {
    }

    // Normal constructor
    public UserBean(int userId, String userFullName, String userEmail, String userPassword, String userRole) {
        this.userId = userId;
        this.userFullName = userFullName;
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userRole = userRole;
    }

    // Getters and setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}