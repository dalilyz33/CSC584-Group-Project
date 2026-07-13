package com.careshare.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.careshare.bean.UserBean;
import com.careshare.util.DBConnection;

public class UserDao {

    // Inserts a new row into the AppUser table and returns the user_id that Derby
    // auto-generated for it. We need this ID straight away because the Student,
    // Admin, or Donor row we insert next uses it as a foreign key.
    public int registerUser(UserBean user) {

        // Default value if something goes wrong. -1 is not a valid user_id (identity
        // columns start at 1), so the servlet can check "if (userId == -1)" to detect failure.
        int generatedUserId = -1;

        // The "?" characters are placeholders. Using a PreparedStatement instead of
        // building this string with + concatenation prevents SQL injection, because
        // the values are sent separately from the SQL command itself.
        String sql = "INSERT INTO AppUser (user_fullName, user_email, user_password, user_role) "
                    + "VALUES (?, ?, ?, ?)";

        // try-with-resources: Connection and PreparedStatement are both closed
        // automatically when this block ends, even if an exception is thrown.
        // Without this, unclosed connections build up and can crash the server.
        try (Connection conn = DBConnection.createConnection();
             // Statement.RETURN_GENERATED_KEYS tells Derby: "after this INSERT runs,
             // let me read back the auto-generated user_id it created."
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // setString(position, value) fills in each "?" in order.
            // Position numbering starts at 1, not 0.
            stmt.setString(1, user.getUserFullName());
            stmt.setString(2, user.getUserEmail());
            stmt.setString(3, user.getUserPassword());
            stmt.setString(4, user.getUserRole());

            // executeUpdate() is used for INSERT/UPDATE/DELETE. It returns the number
            // of rows affected, not the data itself.
            int rowsAffected = stmt.executeUpdate();

            // Only try to read the generated key if the insert actually added a row.
            if (rowsAffected > 0) {
                // getGeneratedKeys() gives back a small ResultSet containing just the
                // auto-generated column(s) — in our case, the new user_id.
                try (ResultSet rs = stmt.getGeneratedKeys()) {
                    // rs.next() moves to the first row of that result and returns true
                    // if a row exists.
                    if (rs.next()) {
                        // Column 1 here is the generated user_id (there's only one
                        // generated column, so we don't need its name).
                        generatedUserId = rs.getInt(1);
                    }
                }
            }

        } catch (SQLException e) {
            // Printed for now so you can see errors while testing. In a finished
            // system this would normally be logged instead of printed.
            System.out.println("Error registering user: " + e.getMessage());
        }

        return generatedUserId;
    }

    // Checks whether an email + password combination matches a row in AppUser.
    // Returns a filled-in UserBean if it matches, or null if it doesn't
    // (wrong password, or no account with that email at all).
    public UserBean validateLogin(String email, String password) {

        UserBean user = null; // stays null unless we find a matching row below

        String sql = "SELECT user_id, user_fullName, user_email, user_password, user_role "
                    + "FROM AppUser WHERE user_email = ? AND user_password = ?";

        try (Connection conn = DBConnection.createConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);

            // executeQuery() is used for SELECT statements because they return rows
            // of data, unlike executeUpdate() which only returns a row count.
            try (ResultSet rs = stmt.executeQuery()) {

                // If rs.next() is true, exactly one matching row was found (user_id
                // is unique, so there can never be more than one match).
                if (rs.next()) {
                    user = new UserBean();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUserFullName(rs.getString("user_fullName"));
                    user.setUserEmail(rs.getString("user_email"));
                    user.setUserPassword(rs.getString("user_password"));
                    user.setUserRole(rs.getString("user_role"));
                }
                // If rs.next() is false, no row matched — "user" stays null, and the
                // servlet calling this method will treat null as "login failed".
            }

        } catch (SQLException e) {
            System.out.println("Error validating login: " + e.getMessage());
        }

        return user;
    }

    // Returns true if the given email already has an AppUser row. Used during
    // registration so we can reject duplicate sign-ups with a clear message
    // instead of letting the database throw an error (or worse, silently allowing
    // two accounts with the same email if there's no UNIQUE constraint).
    public boolean emailExists(String email) {

        boolean exists = false;

        // COUNT(*) returns how many rows matched — 0 means the email is free,
        // 1 or more means it's already taken.
        String sql = "SELECT COUNT(*) AS total FROM AppUser WHERE user_email = ?";

        try (Connection conn = DBConnection.createConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // getInt("total") reads the COUNT(*) result. If it's greater than
                    // 0, at least one row already has this email.
                    exists = rs.getInt("total") > 0;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error checking email existence: " + e.getMessage());
        }

        return exists;
    }
}