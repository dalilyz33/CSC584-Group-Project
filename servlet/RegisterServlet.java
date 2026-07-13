package com.careshare.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.careshare.bean.DonorBean;
import com.careshare.bean.StudentBean;
import com.careshare.bean.UserBean;
import com.careshare.dao.DonorDao;
import com.careshare.dao.StudentDao;
import com.careshare.dao.UserDao;

public class RegisterServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        // Step 1: Read the raw values submitted from register.jsp's form fields.
        // These names must exactly match each <input name="..."> in the JSP.
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String role = request.getParameter("role"); // expected: "Student" or "Donor"

        // These two only apply when role is "Student". register.jsp should only
        // display them when Student is selected, but we read them safely either way —
        // if role is "Donor" these will just be null and are never used below.
        // Note: assistance_type is intentionally NOT collected here. It varies per
        // individual assistance application (see AssistanceApplication.application_type
        // in the ERD), not fixed once at account creation, so it has no place on
        // this form.
        String programCode = request.getParameter("programCode");
        String groupCode = request.getParameter("groupCode");

        // Step 2: Server-side validation. This is required even though the HTML form
        // may also validate with "required" attributes, because client-side checks
        // can be bypassed (e.g. someone submitting the form data directly via a tool,
        // skipping the browser entirely).
        if (fullName == null || fullName.trim().isEmpty()
                || email == null || email.trim().isEmpty()
                || password == null || password.trim().isEmpty()
                || role == null || role.trim().isEmpty()) {

            // setAttribute() stores a value that register.jsp can read and display,
            // e.g. <%= request.getAttribute("errorMessage") %>
            request.setAttribute("errorMessage", "All fields are required.");
            // forward() sends the request back to register.jsp on the server side —
            // the browser's URL bar does not change, unlike sendRedirect().
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return; // stop here so the rest of this method does not run
        }

        // Only Student and Donor may self-register; Admin accounts are created
        // internally (per the storyboard), not through this public form.
        if (!role.equals("Student") && !role.equals("Donor")) {
            request.setAttribute("errorMessage", "Invalid role selected.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Step 3: Reject duplicate emails before touching the database further.
        UserDao userDao = new UserDao();
        if (userDao.emailExists(email)) {
            request.setAttribute("errorMessage", "This email is already registered.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Step 4: Build the UserBean from validated input and insert it into AppUser.
        // This gives us back the auto-generated user_id we need for the next step.
        UserBean newUser = new UserBean();
        newUser.setUserFullName(fullName);
        newUser.setUserEmail(email);
        newUser.setUserPassword(password);
        newUser.setUserRole(role);

        int newUserId = userDao.registerUser(newUser);

        // registerUser() returns -1 if the insert failed (see UserDao). Without a
        // valid user_id we cannot safely insert the Student/Donor row, so stop here.
        if (newUserId == -1) {
            request.setAttribute("errorMessage", "Registration failed. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return;
        }

        // Step 5: Insert the role-specific row using the matching DAO.
        boolean roleInsertSuccess;

        if (role.equals("Student")) {
            StudentDao studentDao = new StudentDao();

            StudentBean newStudent = new StudentBean();
            // Generate the next formatted ID (e.g. S00001) right before inserting,
            // so it reflects the current highest ID at insert time.
            newStudent.setStudentId(studentDao.generateNextStudentId());
            newStudent.setUserId(newUserId); // links this Student row back to AppUser
            // assistanceType is left unset here (defaults to null) since it isn't
            // collected at registration — see the note above.
            newStudent.setProgramCode(programCode);
            newStudent.setGroupCode(groupCode);

            roleInsertSuccess = studentDao.insertStudent(newStudent);

        } else {
            // At this point role must be "Donor", since we already rejected anything
            // else in Step 2.
            DonorDao donorDao = new DonorDao();

            DonorBean newDonor = new DonorBean();
            newDonor.setDonorId(donorDao.generateNextDonorId());
            newDonor.setUserId(newUserId);

            roleInsertSuccess = donorDao.insertDonor(newDonor);
        }

        // Step 6: Decide the next page based on whether the second insert worked.
        if (roleInsertSuccess) {
            // Both inserts succeeded — send the new user to the login page.
            // sendRedirect() causes a fresh browser request to login.jsp, which is
            // appropriate here since registration is now complete.
            response.sendRedirect("login.jsp");
        } else {
            // The AppUser row was created, but the Student/Donor row failed. This
            // leaves an "orphaned" AppUser row with no matching role record. A more
            // advanced implementation would wrap both inserts in a single database
            // transaction so they either both succeed or both roll back — that's
            // beyond what your labs cover, but worth knowing if asked.
            request.setAttribute("errorMessage", "Registration partially failed. Please contact support.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }

    // Following the same pattern your labs use (e.g. ListLibraryServlet): both
    // doGet and doPost call processRequest, so the logic is written once. The
    // register.jsp form should use method="post" since it submits a password,
    // which should not appear in the URL the way GET parameters do.
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }
}