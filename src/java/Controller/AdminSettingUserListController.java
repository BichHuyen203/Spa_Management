package Controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import DAO.UserDAO;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

/**
 *
 * @author PCASUS
 */
@WebServlet(name = "AdminSettingUserListController", urlPatterns = {"/admin/settingUser"})
public class AdminSettingUserListController extends HttpServlet {
    
    private static final int PAGE_SIZE = 5;

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
            out.println("<title>Servlet AdminSettingUserListController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminSettingUserListController at " + request.getContextPath() + "</h1>");
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
         String tab = request.getParameter("tab");
        if (tab == null || tab.isEmpty()) {
            tab = "user"; 
        }
        int pageUser = 1;
        UserDAO userDAO = new UserDAO();
        String pageUserStr = request.getParameter("pageUser");

        if (pageUserStr != null) {
            try {
                pageUser = Integer.parseInt(pageUserStr);
                if (pageUser < 1) {
                    pageUser = 1;
                }
            } catch (NumberFormatException ignored) {
            }
        }

            List<User> users = userDAO.getAllUsers(pageUser, PAGE_SIZE);
            int totalUsers = userDAO.getTotalUsers();
            int totalPagesUser = (int) Math.ceil((double) totalUsers / PAGE_SIZE);
             request.setAttribute("users", users);
             request.setAttribute("pageUser", pageUser);
             request.setAttribute("totalPagesUser", totalPagesUser);
             
             request.getRequestDispatcher("/admin-settingListUser.jsp").forward(request, response);

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
        protected void doPost
        (HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo
        
            () {
        return "Short description";
        }// </editor-fold>

    }
