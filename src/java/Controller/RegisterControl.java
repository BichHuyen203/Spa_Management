/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.UserDAO;
import Model.User;
import Utils.EmailService;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 *
 * @author anhdu
 */
@WebServlet(name = "RegisterControl", urlPatterns = {"/register"})
public class RegisterControl extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterControl</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet RegisterControl at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("Register.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve parameters from the request
//        String username = request.getParameter("username");
        String fullName = request.getParameter("fullName");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String retypePassword = request.getParameter("retypePassword");
        boolean gender = Boolean.parseBoolean(request.getParameter("gender"));
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");

        // Perform additional validation checks
        String validationError = validateRegistrationInput(fullName, email, password, retypePassword, phone, address);

        if (validationError != null) {
            // Validation failed
            request.setAttribute("errorMessage", validationError);
            request.getRequestDispatcher("Register.jsp").forward(request, response);
            return;
        }

        UserDAO userDAO = new UserDAO();

        // Check if the email is already registered
        if (userDAO.getUserByEmail(email) != null) {
            // Email already exists
            // You may set an error message and forward back to the registration page
            request.setAttribute("errorMessage", "Email is already registered");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        } else {
            // Check if the password and retype password match
            if (!password.equals(retypePassword)) {
                // Passwords do not match
                request.setAttribute("errorMessage", "Passwords do not match");
                request.getRequestDispatcher("Register.jsp").forward(request, response);
                return;
            }

            // Email is not registered, and passwords match, proceed with registration
//            boolean registrationSuccessful = userDAO.registerUser(fullName, email, password, role);
            // Verify OTP
            String otp = generateRandomSixDigit() + "";
            String verifyLink = "http://" + request.getServerName() + ":" + request.getServerPort()
                    + request.getContextPath() + "/verify?otp=" + otp + "&email=" + email;

            // Send mail verify
            EmailService.sendEmail(email, "Verify email", "Click here to verify: " + verifyLink);
            
            System.out.println(otp);
            // Set register info session
            User user = new User();
            user.setEmail(email);
            user.setPassword(password);
            user.setFullname(fullName);
            user.setGender(gender ? "Male" : "Female");
            user.setAddress(address);
            user.setPhone(phone);
            user.setIsDeleted(false);
            
            request.getSession().setAttribute("verify_" + email, user);
            request.getSession().setAttribute("verify_otp_" + email, otp);

            // Registration successful
            request.setAttribute("errorMessage", "Verify link was sent!");
            request.getRequestDispatcher("Register.jsp").forward(request, response);
        }

    }

    // Additional validation method
    private String validateRegistrationInput(String fullName, String email, String password, String retypePassword, String phone, String address) {
        // Validate full name
        if (!isValidFullName(fullName)) {
            return "Invalid full name. Please enter a valid full name.";
        }

        // Validate email
        if (!isValidEmail(email)) {
            return "Invalid email address. Please enter a valid email address.";
        }

        // Validate password
        if (!isValidPassword(password)) {
            return "Invalid password. Password must have at least 8 characters.";
        }

        // Validate retype password
        if (!isValidRetypePassword(password, retypePassword)) {
            return "Passwords do not match. Please ensure that the entered passwords match.";
        }

        // Validate phone
        if (!isValidPhone(phone)) {
            return "Invalid phone number. Please enter a valid phone number.";
        }

        // Validate address
        if (!isValidAddress(address)) {
            return "Invalid address. Please enter a valid address.";
        }

        return null; // No validation error
    }

// Additional validation methods for phone and address
    private boolean isValidPhone(String phone) {
        // Implement your validation logic for phone number
        // For example, you can check if it contains only digits
        return phone.matches("^\\d+$");
    }

    private boolean isValidAddress(String address) {
        // Implement your validation logic for address
        // For example, you can check if it's not empty
        return !address.trim().isEmpty();
    }

// Updated validation methods
    private boolean isValidFullName(String fullName) {
        // Implement your validation logic for full name format
        // For example, you can check if it contains only letters
        return fullName.matches("^[a-zA-Z]+( [a-zA-Z]+)*$");
    }

    private boolean isValidPassword(String password) {
        // Implement your simplified validation logic for password length
        return password.length() >= 8;
    }

    private boolean isValidEmail(String email) {
        // Implement your validation logic for email format and length
        // For example, you can use a regular expression for a basic email format check
        return email.matches("^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
    }

    private boolean isValidRetypePassword(String password, String retypePassword) {
        // Implement your validation logic for retype password
        // For example, you can check if it matches the original password
        return password.equals(retypePassword);
    }

    public int generateRandomSixDigit() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }

}
