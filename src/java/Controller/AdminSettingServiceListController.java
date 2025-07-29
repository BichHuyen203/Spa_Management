package Controller;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */


import DAO.SpaServiceDAO;
import DAO.UserDAO;
import Model.SpaService;
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
@WebServlet(name = "AdminSettingServiceListController", urlPatterns = {"/admin/settingService"})
public class AdminSettingServiceListController extends HttpServlet {

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
            out.println("<title>Servlet AdminSettingController</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminSettingController at " + request.getContextPath() + "</h1>");
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
        // Get tab parameter
        String tab = request.getParameter("tab");
        if (tab == null || tab.isEmpty()) {
            tab = "service"; // Mặc định là tab user
        }

        // Get page parameter
//        int pageUser = 1;
        int pageService = 1;
//        UserDAO userDAO = new UserDAO();
        SpaServiceDAO spaServiceDAO = new SpaServiceDAO();

//        String pageUserStr = request.getParameter("pageUser");
        String pageServiceStr = request.getParameter("pageService");

//        if (pageUserStr != null) {
//            try {
//                pageUser = Integer.parseInt(pageUserStr);
//                if (pageUser < 1) {
//                    pageUser = 1;
//                }
//            } catch (NumberFormatException ignored) {
//            }
//        }

        if (pageServiceStr != null) {
            try {
                pageService = Integer.parseInt(pageServiceStr);
                if (pageService < 1) {
                    pageService = 1;
                }
            } catch (NumberFormatException ignored) {
            }
        }

//        List<User> users = userDAO.getAllUsers(pageUser, PAGE_SIZE);
        List<SpaService> spaServices = spaServiceDAO.getAllPagination(pageService, PAGE_SIZE);

//        int totalUsers = userDAO.getTotalUsers();
        int totalServices = spaServiceDAO.getTotalSpaServices();

//        int totalPagesUser = (int) Math.ceil((double) totalUsers / PAGE_SIZE);
        int totalPagesService = (int) Math.ceil((double) totalServices / PAGE_SIZE);

//        request.setAttribute("users", users);
        request.setAttribute("Services", spaServices);
//        request.setAttribute("pageUser", pageUser);
        request.setAttribute("pageService", pageService);
//        request.setAttribute("totalPagesUser", totalPagesUser);
        request.setAttribute("totalPagesService", totalPagesService);
        request.setAttribute("tab", tab); // Đặt thuộc tính tab để JSP sử dụng

        request.getRequestDispatcher("/admin-settingListService.jsp").forward(request, response);
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
        processRequest(request, response);
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
