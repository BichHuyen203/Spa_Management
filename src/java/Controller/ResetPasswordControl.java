/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.UserDAO;
import Model.User;
import Utils.EmailService;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Anh Phuong Le
 */
@WebServlet(name = "ResetPasswordControl", urlPatterns = {"/reset-password"})
public class ResetPasswordControl extends HttpServlet {
    
    private static int expiredMinute = 5;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Resetpassword.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String emailaddress = request.getParameter("email");

        try {

            // check in db
            User account = new UserDAO().getUserByEmail(emailaddress);
            if (account == null) {
                request.setAttribute("errorMessage", "Account not found");
                throw new Exception();
            }

            // generate otp
            String otp = generatePassword(12);
            request.getSession().setAttribute(emailaddress + "_reset_otp", otp);
            request.getSession().setAttribute(emailaddress + "_reset_time", getExpiredTime(expiredMinute));
            String resetLink = "http://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + "/new-password?otp=" + otp + "&email=" + emailaddress;

            // send mail
            EmailService.sendEmail(emailaddress, "Reset password", "Your reset password link: " + resetLink);
            

            request.setAttribute("errorMessage", "An email was sent!");
            request.getRequestDispatcher("Resetpassword.jsp").forward(request, response);

        } catch (Exception e) {
            request.getRequestDispatcher("Resetpassword.jsp").forward(request, response);

        }

    }

    

    public static String generatePassword(int length) {
        // Define characters to use in the password (lowercase letters and digits)
        String characters = "abcdefghijklmnopqrstuvwxyz0123456789";

        // Use SecureRandom for better randomness
        SecureRandom random = new SecureRandom();

        // Build the password by randomly selecting characters from the defined set
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(characters.length());
            char randomChar = characters.charAt(randomIndex);
            password.append(randomChar);
        }

        return password.toString();
    }
    
    public String getExpiredTime(int n) {
        // Get current time
        Calendar calendar = Calendar.getInstance();
        Date currentTime = calendar.getTime();

        // Add n minutes
        calendar.add(Calendar.MINUTE, n);
        Date newTime = calendar.getTime();

        // Format the result as a string
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String result = dateFormat.format(newTime);

        return result;
    }


}
