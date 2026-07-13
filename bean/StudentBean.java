package com.careshare.bean;

import java.io.Serializable;

public class StudentBean implements Serializable {

    private String studentId; // formatted code, e.g. "S007"
    private int userId;
    private String assistanceType;
    private String programCode;
    private String groupCode;

    // Default constructor
    public StudentBean() {
    }

    // Normal constructor
    public StudentBean(String studentId, int userId, String assistanceType, String programCode, String groupCode) {
        this.studentId = studentId;
        this.userId = userId;
        this.assistanceType = assistanceType;
        this.programCode = programCode;
        this.groupCode = groupCode;
    }

    // Getters and setters
    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAssistanceType() {
        return assistanceType;
    }

    public void setAssistanceType(String assistanceType) {
        this.assistanceType = assistanceType;
    }

    public String getProgramCode() {
        return programCode;
    }

    public void setProgramCode(String programCode) {
        this.programCode = programCode;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }
}