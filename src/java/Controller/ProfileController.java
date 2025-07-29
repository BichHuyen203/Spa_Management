/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.UserDAO;
import Model.User;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author anhdu
 */
@WebServlet(name = "ProfileController", urlPatterns = {"/common/profile"})
public class ProfileController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/profile.jsp").forward(request, response);
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int id = Integer.parseInt(request.getParameter("id"));
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String fullname = request.getParameter("fullname");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String phone = request.getParameter("phone");
        String avatar = request.getParameter("avatar");

        
        User user = new UserDAO().getUserById(id);
        
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setFullname(fullname);
        user.setGender(gender);
        user.setAddress(address);
        user.setPhone(phone);
        user.setAvatar(avatar);
              
        new UserDAO().updateUser(user);
        
        request.getSession().setAttribute("user", user);
        
        response.sendRedirect("profile?success");
    }


}
