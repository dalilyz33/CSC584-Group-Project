package com.careshare.servlet;

import com.careshare.bean.AssistanceApplicationBean;
import com.careshare.dao.AssistanceApplicationDAO;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet(
        name = "ApplyAssistanceServlet",
        urlPatterns = {"/ApplyAssistanceServlet"}
)
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 5 * 1024 * 1024,
        maxRequestSize = 10 * 1024 * 1024
)
public class ApplyAssistanceServlet extends HttpServlet {

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        /*
         * STEP 1:
         * Check whether the student session exists.
         */
        HttpSession session =
                request.getSession(false);

        if (session == null
                || session.getAttribute("studentId") == null) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/login.jsp"
            );

            return;
        }

        /*
         * STEP 2:
         * Get the student ID from the session.
         */
        Integer studentId =
                (Integer) session.getAttribute("studentId");

        /*
         * STEP 3:
         * Get the normal form inputs.
         */
        String applicationType =
                request.getParameter("applicationType");

        String applicationDateText =
                request.getParameter("applicationDate");

        /*
         * STEP 4:
         * Validate the required form inputs.
         */
        if (applicationType == null
                || applicationType.trim().isEmpty()
                || applicationDateText == null
                || applicationDateText.trim().isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "Please complete all required fields."
            );

            request.getRequestDispatcher(
                    "/studentAssistanceApplication.jsp"
            ).forward(request, response);

            return;
        }

        /*
         * STEP 5:
         * Receive the uploaded supporting document.
         */
        Part filePart;

        try {

            filePart =
                    request.getPart("supportingDocument");

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "errorMessage",
                    "The document could not be received. "
                    + "Make sure the file is less than 5 MB."
            );

            request.getRequestDispatcher(
                    "/studentAssistanceApplication.jsp"
            ).forward(request, response);

            return;
        }

        /*
         * Make sure the student selected a document.
         */
        if (filePart == null
                || filePart.getSize() == 0) {

            request.setAttribute(
                    "errorMessage",
                    "Please choose a supporting document."
            );

            request.getRequestDispatcher(
                    "/studentAssistanceApplication.jsp"
            ).forward(request, response);

            return;
        }

        /*
         * STEP 6:
         * Get the uploaded document's original name.
         */
        String submittedFileName =
                filePart.getSubmittedFileName();

        if (submittedFileName == null
                || submittedFileName.trim().isEmpty()) {

            request.setAttribute(
                    "errorMessage",
                    "The selected document does not have "
                    + "a valid file name."
            );

            request.getRequestDispatcher(
                    "/studentAssistanceApplication.jsp"
            ).forward(request, response);

            return;
        }

        /*
         * Remove any directory information from the file name.
         */
        String originalFileName =
                new File(submittedFileName).getName();

        /*
         * STEP 7:
         * Check the uploaded document's extension.
         */
        String lowerFileName =
                originalFileName.toLowerCase();

        boolean validFile =
                lowerFileName.endsWith(".pdf")
                || lowerFileName.endsWith(".jpg")
                || lowerFileName.endsWith(".jpeg")
                || lowerFileName.endsWith(".png");

        if (!validFile) {

            request.setAttribute(
                    "errorMessage",
                    "Only PDF, JPG, JPEG and PNG "
                    + "documents are allowed."
            );

            request.getRequestDispatcher(
                    "/studentAssistanceApplication.jsp"
            ).forward(request, response);

            return;
        }

        /*
         * STEP 8:
         * Clean the original file name.
         */
        String cleanFileName =
                originalFileName.replaceAll(
                        "[^a-zA-Z0-9._-]",
                        "_"
                );

        /*
         * Add the current time to prevent documents
         * with the same name from replacing each other.
         */
        String savedFileName =
                System.currentTimeMillis()
                + "_"
                + cleanFileName;

        /*
         * STEP 9:
         * Find the deployed application's real folder.
         */
        String applicationPath =
                getServletContext().getRealPath("");

        if (applicationPath == null) {

            request.setAttribute(
                    "errorMessage",
                    "The application upload directory "
                    + "could not be found."
            );

            request.getRequestDispatcher(
                    "/studentAssistanceApplication.jsp"
            ).forward(request, response);

            return;
        }

        /*
         * Build the uploads folder location.
         */
        String uploadPath =
                applicationPath
                + File.separator
                + "uploads";

        File uploadFolder =
                new File(uploadPath);

        /*
         * Make sure uploads is a folder.
         */
        if (uploadFolder.exists()
                && !uploadFolder.isDirectory()) {

            request.setAttribute(
                    "errorMessage",
                    "The uploads location is not a folder."
            );

            request.getRequestDispatcher(
                    "/studentAssistanceApplication.jsp"
            ).forward(request, response);

            return;
        }

        /*
         * Create the uploads folder if it does not exist.
         */
        if (!uploadFolder.exists()) {

            boolean folderCreated =
                    uploadFolder.mkdirs();

            if (!folderCreated) {

                request.setAttribute(
                        "errorMessage",
                        "The uploads folder could not be created."
                );

                request.getRequestDispatcher(
                        "/studentAssistanceApplication.jsp"
                ).forward(request, response);

                return;
            }
        }

        /*
         * STEP 10:
         * Create the complete destination file.
         */
        File savedFile =
                new File(
                        uploadFolder,
                        savedFileName
                );

        /*
         * Save the document using InputStream and Files.copy().
         *
         * Do not use filePart.write(fullFilePath), because
         * GlassFish may combine two Windows paths.
         */
        InputStream inputStream = null;

        try {

            inputStream =
                    filePart.getInputStream();

            Files.copy(
                    inputStream,
                    savedFile.toPath(),
                    StandardCopyOption.REPLACE_EXISTING
            );

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "errorMessage",
                    "The document could not be uploaded. "
                    + e.getMessage()
            );

            request.getRequestDispatcher(
                    "/studentAssistanceApplication.jsp"
            ).forward(request, response);

            return;

        } finally {

            if (inputStream != null) {

                try {

                    inputStream.close();

                } catch (Exception e) {

                    e.printStackTrace();
                }
            }
        }

        /*
         * STEP 11:
         * Convert the application date into java.sql.Date.
         */
        Date applicationDate;

        try {

            applicationDate =
                    Date.valueOf(applicationDateText);

        } catch (IllegalArgumentException e) {

            /*
             * Delete the uploaded document because the
             * application cannot be inserted.
             */
            if (savedFile.exists()) {

                savedFile.delete();
            }

            request.setAttribute(
                    "errorMessage",
                    "The application date is invalid."
            );

            request.getRequestDispatcher(
                    "/studentAssistanceApplication.jsp"
            ).forward(request, response);

            return;
        }

        /*
         * STEP 12:
         * Create the JavaBean and set its values.
         */
        AssistanceApplicationBean application =
                new AssistanceApplicationBean();

        application.setStudentId(
                studentId
        );

        application.setApplicationType(
                applicationType
        );

        application.setApplicationDate(
                applicationDate
        );

        application.setApplicationStatus(
                "Pending"
        );

        /*
         * Store only the saved document name
         * inside the database.
         */
        application.setApplicationSupportingDocument(
                savedFileName
        );

        /*
         * STEP 13:
         * Send the JavaBean to the DAO.
         */
        AssistanceApplicationDAO dao =
                new AssistanceApplicationDAO();

        String result =
                dao.addApplication(application);

        /*
         * STEP 14:
         * Check whether the database insertion succeeded.
         */
        if ("SUCCESS".equals(result)) {

            session.setAttribute(
                    "successMessage",
                    "Assistance application "
                    + "submitted successfully."
            );

            response.sendRedirect(
                    request.getContextPath()
                    + "/ViewApplicationServlet"
            );

        } else {

            /*
             * Delete the uploaded document because no
             * matching database record was created.
             */
            if (savedFile.exists()) {

                savedFile.delete();
            }

            request.setAttribute(
                    "errorMessage",
                    "Application could not be submitted. "
                    + result
            );

            request.getRequestDispatcher(
                    "/studentAssistanceApplication.jsp"
            ).forward(request, response);
        }
    }

    /*
     * If the Servlet is opened using GET,
     * return the student to the application form.
     */
    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        response.sendRedirect(
                request.getContextPath()
                + "/studentAssistanceApplication.jsp"
        );
    }
}