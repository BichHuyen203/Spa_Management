/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.DiscountCodeDAO;
import Model.DiscountCode;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AdminUpdateDiscountCode", urlPatterns = {"/admin/edit-discount-code"})
public class AdminUpdateDiscountCode extends HttpServlet {

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
            out.println("<title>Servlet AdminUpdateDiscountCode</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet AdminUpdateDiscountCode at " + request.getContextPath() + "</h1>");
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
        try {
            int id = Integer.parseInt(request.getParameter("id"));
        DiscountCodeDAO dao = new DiscountCodeDAO();
        DiscountCode discount = dao.getDiscountCodeById(id);

        // Format date for input[type="datetime-local"]
        discount.setStartDateFormatted(discount.getStartDate().toLocalDateTime().toString().replace("T", "T").substring(0,16));
        discount.setEndDateFormatted(discount.getEndDate().toLocalDateTime().toString().replace("T", "T").substring(0,16));

        request.setAttribute("discount", discount);
        request.getRequestDispatcher("../edit-discount-code.jsp").forward(request, response);
    } catch (Exception e) {
        response.sendRedirect("../discount-code?isSuccess=false");
    }
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
         try {
            request.setCharacterEncoding("UTF-8");

            int id = Integer.parseInt(request.getParameter("id"));
            String code = request.getParameter("code");
            String description = request.getParameter("description");
            String discountType = request.getParameter("discountType");
            double discountValue = Double.parseDouble(request.getParameter("discountValue"));

            String minOrderStr = request.getParameter("minOrderAmount");
            Double minOrderAmount = (minOrderStr == null || minOrderStr.isEmpty()) ? null : Double.parseDouble(minOrderStr);

            Timestamp startDate = Timestamp.valueOf(request.getParameter("startDate").replace("T", " ") + ":00");
            Timestamp endDate = Timestamp.valueOf(request.getParameter("endDate").replace("T", " ") + ":00");

            int quantity = Integer.parseInt(request.getParameter("quantity"));
            boolean isActive = request.getParameter("isActive") != null;

            DiscountCode discount = new DiscountCode();
            discount.setId(id);
            discount.setCode(code);
            discount.setDescription(description);
            discount.setDiscountType(discountType);
            discount.setDiscountValue(discountValue);
            discount.setMinOrderAmount(minOrderAmount);
            discount.setStartDate(startDate);
            discount.setEndDate(endDate);
            discount.setQuantity(quantity);
            discount.setIsActive(isActive);

            new DiscountCodeDAO().updateDiscountCode(discount);

            response.sendRedirect("../admin/discount-code?isSuccess=true");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("admin/discount-code.jsp?isSuccess=false");
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
