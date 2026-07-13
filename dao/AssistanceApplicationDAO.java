package com.careshare.dao;

import com.careshare.bean.AssistanceApplicationBean;
import com.careshare.util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class AssistanceApplicationDAO {

    /*
     * CREATE
     * Student submits a new assistance application.
     */
    public String addApplication(
            AssistanceApplicationBean application) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql =
                "INSERT INTO ASSISTANCEAPPLICATION "
                + "(STUDENT_ID, APPLICATION_TYPE, "
                + "APPLICATION_DATE, APPLICATION_STATUS, "
                + "APPLICATION_SUPPORTINGDOCUMENT) "
                + "VALUES (?, ?, ?, ?, ?)";

        try {

            con = DBConnection.createConnection();

            /*
             * Prevent NullPointerException if the connection
             * could not be created.
             */
            if (con == null) {
                return "ERROR: Database connection could not be created.";
            }

            ps = con.prepareStatement(sql);

            ps.setInt(
                    1,
                    application.getStudentId()
            );

            ps.setString(
                    2,
                    application.getApplicationType()
            );

            ps.setDate(
                    3,
                    application.getApplicationDate()
            );

            ps.setString(
                    4,
                    application.getApplicationStatus()
            );

            ps.setString(
                    5,
                    application.getApplicationSupportingDocument()
            );

            int result = ps.executeUpdate();

            if (result > 0) {
                return "SUCCESS";
            }

            return "ERROR: No application record was inserted.";

        } catch (Exception e) {

            e.printStackTrace();

            return "ERROR: " + e.getMessage();

        } finally {

            /*
             * Close PreparedStatement.
             */
            if (ps != null) {

                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            /*
             * Close database connection.
             */
            if (con != null) {

                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * READ
     * Get all applications belonging to one student.
     */
    public ArrayList<AssistanceApplicationBean>
            getApplicationsByStudent(int studentId) {

        ArrayList<AssistanceApplicationBean> list =
                new ArrayList<AssistanceApplicationBean>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql =
                "SELECT * FROM ASSISTANCEAPPLICATION "
                + "WHERE STUDENT_ID = ? "
                + "ORDER BY APPLICATION_ID DESC";

        try {

            con = DBConnection.createConnection();

            if (con == null) {

                System.out.println(
                        "Database connection could not be created."
                );

                return list;
            }

            ps = con.prepareStatement(sql);

            ps.setInt(1, studentId);

            rs = ps.executeQuery();

            while (rs.next()) {

                AssistanceApplicationBean application =
                        new AssistanceApplicationBean();

                application.setApplicationId(
                        rs.getInt("APPLICATION_ID")
                );

                application.setStudentId(
                        rs.getInt("STUDENT_ID")
                );

                /*
                 * APPLICATION_REVIEWEDBY may be NULL
                 * before the admin reviews it.
                 */
                application.setApplicationReviewedBy(
                        rs.getInt("APPLICATION_REVIEWEDBY")
                );

                application.setApplicationType(
                        rs.getString("APPLICATION_TYPE")
                );

                application.setApplicationDate(
                        rs.getDate("APPLICATION_DATE")
                );

                application.setApplicationStatus(
                        rs.getString("APPLICATION_STATUS")
                );

                application.setApplicationSupportingDocument(
                        rs.getString(
                                "APPLICATION_SUPPORTINGDOCUMENT"
                        )
                );

                list.add(application);
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (rs != null) {

                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (ps != null) {

                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {

                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    /*
     * READ
     * Admin views all student applications.
     */
    public ArrayList<AssistanceApplicationBean>
            getAllApplications() {

        ArrayList<AssistanceApplicationBean> list =
                new ArrayList<AssistanceApplicationBean>();

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql =
                "SELECT * FROM ASSISTANCEAPPLICATION "
                + "ORDER BY APPLICATION_ID DESC";

        try {

            con = DBConnection.createConnection();

            if (con == null) {

                System.out.println(
                        "Database connection could not be created."
                );

                return list;
            }

            ps = con.prepareStatement(sql);

            rs = ps.executeQuery();

            while (rs.next()) {

                AssistanceApplicationBean application =
                        new AssistanceApplicationBean();

                application.setApplicationId(
                        rs.getInt("APPLICATION_ID")
                );

                application.setStudentId(
                        rs.getInt("STUDENT_ID")
                );

                application.setApplicationReviewedBy(
                        rs.getInt("APPLICATION_REVIEWEDBY")
                );

                application.setApplicationType(
                        rs.getString("APPLICATION_TYPE")
                );

                application.setApplicationDate(
                        rs.getDate("APPLICATION_DATE")
                );

                application.setApplicationStatus(
                        rs.getString("APPLICATION_STATUS")
                );

                application.setApplicationSupportingDocument(
                        rs.getString(
                                "APPLICATION_SUPPORTINGDOCUMENT"
                        )
                );

                list.add(application);
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (rs != null) {

                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (ps != null) {

                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {

                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return list;
    }

    /*
     * READ
     * Get one application using its application ID.
     */
    public AssistanceApplicationBean
            getApplicationById(int applicationId) {

        AssistanceApplicationBean application = null;

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        String sql =
                "SELECT * FROM ASSISTANCEAPPLICATION "
                + "WHERE APPLICATION_ID = ?";

        try {

            con = DBConnection.createConnection();

            if (con == null) {

                System.out.println(
                        "Database connection could not be created."
                );

                return null;
            }

            ps = con.prepareStatement(sql);

            ps.setInt(1, applicationId);

            rs = ps.executeQuery();

            if (rs.next()) {

                application =
                        new AssistanceApplicationBean();

                application.setApplicationId(
                        rs.getInt("APPLICATION_ID")
                );

                application.setStudentId(
                        rs.getInt("STUDENT_ID")
                );

                application.setApplicationReviewedBy(
                        rs.getInt("APPLICATION_REVIEWEDBY")
                );

                application.setApplicationType(
                        rs.getString("APPLICATION_TYPE")
                );

                application.setApplicationDate(
                        rs.getDate("APPLICATION_DATE")
                );

                application.setApplicationStatus(
                        rs.getString("APPLICATION_STATUS")
                );

                application.setApplicationSupportingDocument(
                        rs.getString(
                                "APPLICATION_SUPPORTINGDOCUMENT"
                        )
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if (rs != null) {

                try {
                    rs.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (ps != null) {

                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {

                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        return application;
    }

    /*
     * UPDATE
     * Admin approves or rejects an application.
     */
    public String reviewApplication(
            int applicationId,
            int staffId,
            String status) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql =
                "UPDATE ASSISTANCEAPPLICATION "
                + "SET APPLICATION_REVIEWEDBY = ?, "
                + "APPLICATION_STATUS = ? "
                + "WHERE APPLICATION_ID = ?";

        try {

            con = DBConnection.createConnection();

            if (con == null) {
                return "ERROR: Database connection could not be created.";
            }

            ps = con.prepareStatement(sql);

            ps.setInt(1, staffId);
            ps.setString(2, status);
            ps.setInt(3, applicationId);

            int result = ps.executeUpdate();

            if (result > 0) {
                return "SUCCESS";
            }

            return "ERROR: Application record was not updated.";

        } catch (Exception e) {

            e.printStackTrace();

            return "ERROR: " + e.getMessage();

        } finally {

            if (ps != null) {

                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {

                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /*
     * DELETE
     * Student can delete only their own pending application.
     */
    public String deleteApplication(
            int applicationId,
            int studentId) {

        Connection con = null;
        PreparedStatement ps = null;

        String sql =
                "DELETE FROM ASSISTANCEAPPLICATION "
                + "WHERE APPLICATION_ID = ? "
                + "AND STUDENT_ID = ? "
                + "AND APPLICATION_STATUS = 'Pending'";

        try {

            con = DBConnection.createConnection();

            if (con == null) {
                return "ERROR: Database connection could not be created.";
            }

            ps = con.prepareStatement(sql);

            ps.setInt(1, applicationId);
            ps.setInt(2, studentId);

            int result = ps.executeUpdate();

            if (result > 0) {
                return "SUCCESS";
            }

            return "ERROR: Application cannot be deleted. "
                    + "Only pending applications can be deleted.";

        } catch (Exception e) {

            e.printStackTrace();

            return "ERROR: " + e.getMessage();

        } finally {

            if (ps != null) {

                try {
                    ps.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            if (con != null) {

                try {
                    con.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}