/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.UserDAO;
import Model.User;
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

/**
 *
 * @author PCASUS
 */
@WebServlet(name = "AdminSettingUserController", urlPatterns = {"/admin/setting"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, // 1MB
        maxFileSize = 1024 * 1024 * 5, // 5MB
        maxRequestSize = 1024 * 1024 * 10 // 10MB
)
public class AdminSettingUserController extends HttpServlet {

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
            out.println("<title>Servlet UpdatePostController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet UpdatePostController at " + request.getContextPath() + "</h1>");
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
        int id = Integer.parseInt(request.getParameter("id"));
        User user = new UserDAO().getStaffById(id);

        request.setAttribute("user", user);

        // Chuyển hướng đến JSP
        request.getRequestDispatcher("/admin-settingUser.jsp").forward(request, response);
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

        request.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(request.getParameter("Id"));
        String fullname = request.getParameter("name");
        String address = request.getParameter("add");
        String phone = request.getParameter("phone");
        String gender = request.getParameter("gender");
        int roleId = Integer.parseInt(request.getParameter("role"));
        Part listItem = request.getPart("file");
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(id);
        user.setFullname(fullname);
        user.setAddress(address);
        user.setPhone(phone);
        user.setGender(gender);
        user.setRoleId(roleId);
        if (listItem == null || listItem.getSize() == 0) {
            userDAO.updateStaff(user);
        } else {
            String avatar = listItem.getSubmittedFileName();
            ServletContext context = getServletContext();
            String realPath = context.getRealPath("/");
            realPath = realPath.replace("build\\", "") + "img";
            String filePath = realPath + File.separator + avatar;
            String filePath1 = "/img/" + avatar;
            try (InputStream fileInputStream = listItem.getInputStream(); FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {

                int data;
                while ((data = fileInputStream.read()) != -1) {
                    fileOutputStream.write(data);
                }
                user.setAvatar(filePath1);
                userDAO.updateStaff(user);
            } catch (Exception e) {
                log("Error while updating plant: " + e.toString());
                request.setAttribute("isSuccess", false);
            }
        }

        request.setAttribute("isSuccess", true);

        request.setAttribute("user", user);
        // Chuyển hướng đến JSP
        request.getRequestDispatcher("/admin-settingUser.jsp").forward(request, response);
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
