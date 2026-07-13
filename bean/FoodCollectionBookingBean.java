package com.careshare.model;

import java.sql.Timestamp;

/**
 * JavaBean representing a row in the FoodCollectionBooking table.
 * Author: Ainaa - Food Booking, Notification & Dashboard Module
 */
public class FoodCollectionBookingBean {

    private int bookingId;
    private int studentId;
    private String studentName;      // convenience field for display (joined from Student)
    private Timestamp bookingDate;   // when the booking was created
    private String collectionDate;  // date student will collect food (yyyy-MM-dd)
    private String collectionTime;  // time slot (e.g. "10:00 - 11:00")
    private String status;          // PENDING, APPROVED, REJECTED, COMPLETED, CANCELLED
    private String remarks;

    public FoodCollectionBookingBean() {
    }

    public FoodCollectionBookingBean(int studentId, String collectionDate, String collectionTime, String remarks) {
        this.studentId = studentId;
        this.collectionDate = collectionDate;
        this.collectionTime = collectionTime;
        this.remarks = remarks;
        this.status = "PENDING";
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Timestamp getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(Timestamp bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getCollectionDate() {
        return collectionDate;
    }

    public void setCollectionDate(String collectionDate) {
        this.collectionDate = collectionDate;
    }

    public String getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
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
}
