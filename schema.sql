-- CareShare Database Schema
-- Member 1: Authentication & User Role Module
-- Tables: AppUser, Admin, Student, Donor

CREATE TABLE AppUser (
    user_id INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
    user_fullName VARCHAR(100) NOT NULL,
    user_email VARCHAR(100) NOT NULL,
    user_password VARCHAR(100) NOT NULL,
    user_role VARCHAR(20) NOT NULL,
    PRIMARY KEY (user_id),
    CONSTRAINT chk_role CHECK (user_role IN ('Student','Admin','Donor'))
);

CREATE TABLE Admin (
    staff_id VARCHAR(10) NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (staff_id),
    FOREIGN KEY (user_id) REFERENCES AppUser(user_id)
);

CREATE TABLE Student (
    student_id VARCHAR(10) NOT NULL,
    user_id INT NOT NULL,
    assistance_type VARCHAR(50),
    program_code VARCHAR(20),
    group_code VARCHAR(20),
    PRIMARY KEY (student_id),
    FOREIGN KEY (user_id) REFERENCES AppUser(user_id)
);

CREATE TABLE Donor (
    donor_id VARCHAR(10) NOT NULL,
    user_id INT NOT NULL,
    PRIMARY KEY (donor_id),
    FOREIGN KEY (user_id) REFERENCES AppUser(user_id)
);
