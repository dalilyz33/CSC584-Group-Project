package com.careshare.bean;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Time;

public class FoodCollectionBookingBean implements Serializable {
    private int bookingId;
    private Date bookingDate;
    private Time bookingTime;
    private int bookingFoodItemId;
    private int bookingStudentId;
    private int categoryId;
    
    // Display properties for table joins
    private String foodItemName;
    private String studentName;

    public FoodCollectionBookingBean() {}

    public int getBookingId() { return bookingId; }
    public void setBookingId(int bookingId) { this.bookingId = bookingId; }
    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }
    public Time getBookingTime() { return bookingTime; }
    public void setBookingTime(Time bookingTime) { this.bookingTime = bookingTime; }
    public int getBookingFoodItemId() { return bookingFoodItemId; }
    public void setBookingFoodItemId(int bookingFoodItemId) { this.bookingFoodItemId = bookingFoodItemId; }
    public int getBookingStudentId() { return bookingStudentId; }
    public void setBookingStudentId(int bookingStudentId) { this.bookingStudentId = bookingStudentId; }
    public int getCategoryId() { return categoryId; }
    public void setCategoryId(int categoryId) { this.categoryId = categoryId; }
    public String getFoodItemName() { return foodItemName; }
    public void setFoodItemName(String foodItemName) { this.foodItemName = foodItemName; }
    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }
}
