/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.UserDAO;
import Model.User;
import Utils.EmailService;
import jakarta.servlet.ServletContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Random;

/**
 *
 * @author PCASUS
 */
@WebServlet(name = "AdminSettingAddUserController", urlPatterns = {"/admin/add"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 5, // 5MB
        maxRequestSize = 1024 * 1024 * 10 // 10MB
)
public class AdminSettingAddUserController extends HttpServlet {

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

    }

    public static String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder password = new StringBuilder();
        Random rnd = new Random();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return password.toString();
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
        request.getRequestDispatcher("../admin-settingAddUser.jsp").forward(request, response);
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
        String email = request.getParameter("email");
        String fullname = request.getParameter("name");
        String address = request.getParameter("add");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        int roleId = Integer.parseInt(request.getParameter("role"));
        Part listItem = request.getPart("file");
        UserDAO userDAO = new UserDAO();
        User checkExist = new UserDAO().getUserByEmail(email);
        if (checkExist != null) {
            response.sendRedirect("add?fail");
        } else {
            User user = new User();
            user.setEmail(email);
            user.setFullname(fullname);
            user.setAddress(address);
            user.setPhone(phone);
            user.setGender(gender);
            user.setRoleId(roleId);
            String password = generateRandomPassword(8);
            user.setPassword(password);

            String avatar = listItem.getSubmittedFileName();
            ServletContext context = getServletContext();
            String realPath = context.getRealPath("/");
            realPath = realPath.replace("build\\", "") + "img";
            String filePath = realPath + File.separator + avatar;
            String filePath1 = "./img/" + avatar;
            try (InputStream fileInputStream = listItem.getInputStream(); FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
                int data;
                while ((data = fileInputStream.read()) != -1) {
                    fileOutputStream.write(data);
                }
                user.setAvatar(filePath1);
                user.setIsDeleted(false);
                boolean check = userDAO.registerUser(user);
                String subject = "Tài khoản của bạn đã được tạo thành công";
                String content = "Xin chào " + fullname + ",\n\n"
                        + "Tài khoản của bạn đã được tạo thành công tại hệ thống.\n"
                        + "Email: " + email + "\n"
                        + "Mật khẩu: " + password + "\n\n"
                        + "Vui lòng đăng nhập và thay đổi mật khẩu sau khi sử dụng.\n"
                        + "Trân trọng,\nSpa Management System";
                EmailService.sendEmail(email, subject, content);
            } catch (Exception e) {
                log("Error while updating plant: " + e.toString());
                request.setAttribute("isSuccess", false);
            }
          
            
            response.sendRedirect("add?success");

        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
