/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.FeedbackDAO;
import DAO.PostDAO;
import Model.Feedback;
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
 * @author Admin
 */
@WebServlet(name = "AdminFeedbackController", urlPatterns = {"/admin/feedback"})
public class AdminFeedbackController extends HttpServlet {

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
            out.println("<title>Servlet AdminPostController</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminPostController at " + request.getContextPath() + "</h1>");
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
        int PAGE_SIZE = 10;
        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            page = Integer.parseInt(pageStr);
        }

        // Get filtering and sorting parameters
        String rating = request.getParameter("rating");
        String author = request.getParameter("author");
        String status = request.getParameter("status");
        String search = request.getParameter("search");
        String sortBy = request.getParameter("sortBy");
        String sortOrder = request.getParameter("sortOrder");

        // Fetch feedbacks for the requested page with filters and sorting
        FeedbackDAO feedbackDAO = new FeedbackDAO();
        List<Feedback> feedbacks = feedbackDAO.getFeebacks(page, PAGE_SIZE, rating, author, status, search, sortBy, sortOrder);
        System.out.println(feedbacks);
        // Get the total number of feedbacks for pagination
        int totalFeedbacks = feedbackDAO.getTotalFeedbacks(rating, author, status, search);
        int totalPages = (int) Math.ceil((double) totalFeedbacks / PAGE_SIZE);

//        // Fetch filter options
//        List<Category> categories = postDAO.getUniqueCategories();
//        List<String> authors = postDAO.getUniqueAuthors();

        // Set attributes for the JSP
        request.setAttribute("feedbacks", feedbacks);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
//        request.setAttribute("categories", categories);
//        request.setAttribute("authors", authors);
//        request.setAttribute("category", category);
        request.setAttribute("author", author);
        request.setAttribute("status", status);
        request.setAttribute("search", search);
        request.setAttribute("sortBy", sortBy);
        request.setAttribute("sortOrder", sortOrder);
        request.setAttribute("isSuccess", request.getParameter("isSuccess"));
        
        request.getRequestDispatcher("/admin-feedback.jsp").forward(request, response);
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
