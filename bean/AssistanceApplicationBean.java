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

public class AssistanceApplicationBean implements Serializable {

    private int applicationId;
    private int studentId;
    private int applicationReviewedBy;
    private String applicationType;
    private Date applicationDate;
    private String applicationStatus;
    private String applicationSupportingDocument;

    public AssistanceApplicationBean() {
    }

    public AssistanceApplicationBean(
            int applicationId,
            int studentId,
            int applicationReviewedBy,
            String applicationType,
            Date applicationDate,
            String applicationStatus,
            String applicationSupportingDocument) {

        this.applicationId = applicationId;
        this.studentId = studentId;
        this.applicationReviewedBy = applicationReviewedBy;
        this.applicationType = applicationType;
        this.applicationDate = applicationDate;
        this.applicationStatus = applicationStatus;
        this.applicationSupportingDocument =
                applicationSupportingDocument;
    }

    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getApplicationReviewedBy() {
        return applicationReviewedBy;
    }

    public void setApplicationReviewedBy(int applicationReviewedBy) {
        this.applicationReviewedBy = applicationReviewedBy;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public Date getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Date applicationDate) {
        this.applicationDate = applicationDate;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getApplicationSupportingDocument() {
        return applicationSupportingDocument;
    }

    public void setApplicationSupportingDocument(
            String applicationSupportingDocument) {

        this.applicationSupportingDocument =
                applicationSupportingDocument;
    }
}