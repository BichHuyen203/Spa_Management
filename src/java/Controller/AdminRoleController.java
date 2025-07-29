/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.RoleDAO;
import Model.Role;
import jakarta.servlet.RequestDispatcher;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Legion
 */
@WebServlet(name = "AdminRoleController", urlPatterns = {"/admin/roles"})
public class AdminRoleController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        RoleDAO roleDao = new RoleDAO(); // Giả sử bạn đã có DAO
        List<Role> roles = roleDao.getAllRoles(); // Lấy tất cả Role

        // Gắn dữ liệu vào request để truyền sang JSP
        request.setAttribute("roles", roles);

        // Chuyển sang trang JSP để hiển thị
        RequestDispatcher dispatcher = request.getRequestDispatcher("../admin-roles.jsp");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8"); // xử lý tiếng Việt nếu cần
        String action = request.getParameter("action");

        RoleDAO roleDao = new RoleDAO();

        try {
            boolean success = false;
            if ("add".equals(action)) {
                // --- TẠO MỚI ROLE ---
                String name = request.getParameter("name");
                boolean isDeleted = Boolean.parseBoolean(request.getParameter("isDeleted"));

                Role role = new Role();
                role.setName(name);
                role.setIsDeleted(isDeleted);
                role.setCreatedAt(new Timestamp(System.currentTimeMillis()));

                success = roleDao.addRole(role);
                // Optional: set attribute to show success message

            } else if ("update".equals(action)) {
                // --- CẬP NHẬT ROLE ---
                int id = Integer.parseInt(request.getParameter("roleId"));
                String name = request.getParameter("name");
                boolean isDeleted = Boolean.parseBoolean(request.getParameter("isDeleted"));

                Role role = new Role();
                role.setId(id);
                role.setName(name);
                role.setIsDeleted(isDeleted);

                success = roleDao.updateRole(role);
                // Optional: set attribute to show update message
            }

            // Sau khi xử lý, chuyển hướng về trang danh sách Role
            if (success) {
                response.sendRedirect("roles?success");
            } else {
                response.sendRedirect("roles?fail");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Có lỗi xảy ra khi xử lý role.");
            request.getRequestDispatcher("../admin-roles.jsp").forward(request, response);
        }
    }

}
