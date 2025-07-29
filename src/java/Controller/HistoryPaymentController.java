/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.InvoiceDAO;
import Model.Invoice;
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
 * @author Legion
 */
@WebServlet(name = "HistoryPaymentController", urlPatterns = {"/history-payment"})
public class HistoryPaymentController extends HttpServlet {

    private InvoiceDAO invoiceDAO;

    @Override
    public void init() throws ServletException {
        invoiceDAO = new InvoiceDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Giả sử bạn lấy userId từ session (nếu đã đăng nhập)

        User currentUser = (User) request.getSession().getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        int customerId = currentUser.getId();

        int page = 1;
        int pageSize = 10;

        if (request.getParameter("page") != null) {
            page = Integer.parseInt(request.getParameter("page"));
        }

        List<Invoice> invoiceList = invoiceDAO.getInvoicesByCustomerIdPaged(customerId, page, pageSize);
        int totalItems = invoiceDAO.countInvoicesByCustomerId(customerId);
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        request.setAttribute("invoiceList", invoiceList);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);

        request.getRequestDispatcher("history-payment.jsp").forward(request, response);
    }

}
