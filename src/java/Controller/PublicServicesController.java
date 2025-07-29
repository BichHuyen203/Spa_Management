/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.SpaServiceDAO;
import Model.SpaService;
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
 * @author Legion
 */
@WebServlet(name = "PublicServicesController", urlPatterns = {"/public-services-list"})
public class PublicServicesController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int page = 1; // Mặc định trang 1
        String pageParam = request.getParameter("page");
        if (pageParam != null) {
            try {
                page = Integer.parseInt(pageParam);
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        SpaServiceDAO dao = new SpaServiceDAO();
        List<SpaService> services = dao.getActiveSpaServices(page);
        int totalPages = dao.getTotalActiveSpaServicePages();

        // Đưa data sang JSP
        request.setAttribute("services", services);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        // Chuyển tiếp sang JSP
        request.getRequestDispatcher("public-services-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }

}
