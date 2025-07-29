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

/**
 *
 * @author lvhn1
 */
@WebServlet(name = "ChangePasswordControl", urlPatterns = {"/common/change-pass"})
public class ChangePasswordControl extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("../change-pass.jsp").forward(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // lấy thông tin người dùng
        User user = (User) request.getSession().getAttribute("user");

        String oldPassword = request.getParameter("oldpassword");
        String password = request.getParameter("password");
        String repassword = request.getParameter("repassword");

        if (password.equals(repassword) && user.getPassword().equals(oldPassword)) {

            user.setPassword(password);
            new UserDAO().updateUser(user);
            
            response.sendRedirect("change-pass?success");

        } else response.sendRedirect("change-pass?fail");

        
    }


}
