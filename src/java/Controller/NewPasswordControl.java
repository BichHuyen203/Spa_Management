/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.UserDAO;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Anh Phuong Le
 */
@WebServlet(name = "NewPasswordControl", urlPatterns = {"/new-password"})
public class NewPasswordControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String otp = request.getParameter("otp");

        request.setAttribute("otp", otp);
        request.setAttribute("email", email);

        request.getRequestDispatcher("NewPassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String otp = request.getParameter("otp");

        String password = request.getParameter("password");
        String retypePassword = request.getParameter("retypePassword");

        String checkOtp = (String) request.getSession().getAttribute(email + "_reset_otp");
        String expireTime = (String) request.getSession().getAttribute(email + "_reset_time");

        User user = new UserDAO().getUserByEmail(email);

        String msg = "";

        if (user == null) {
            msg = "Email not found";
        } else if (otp.equals(checkOtp)) {

            if (isExpired(expireTime)) {
                msg = "Link expired";
            } else {

                if (password.equals(retypePassword)) {

                    user.setPassword(password);
                    new UserDAO().updateUser(user);
                    msg = "Reset password success";
                    request.getSession().removeAttribute(email + "_reset_otp");

                } else {
                    msg = "2 password not match";
                }

            }

        } else {
            msg = "Error! Cant reset password";
        }

        request.setAttribute("errorMessage", msg);
        request.setAttribute("otp", otp);
        request.setAttribute("email", email);

        request.getRequestDispatcher("Login.jsp").forward(request, response);
    }

    public boolean isExpired(String dateString) {
        // Define the date format
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            // Parse the input date string
            Date expiryDate = dateFormat.parse(dateString);

            // Get current time
            Calendar calendar = Calendar.getInstance();
            Date currentTime = calendar.getTime();

            // Compare the dates
            return currentTime.after(expiryDate);
        } catch (ParseException e) {
            // Handle parsing exception
            System.out.println("Error parsing date: " + e.getMessage());
            return false;
        }
    }

}
