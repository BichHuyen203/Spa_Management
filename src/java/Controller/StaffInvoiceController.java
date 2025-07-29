/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AppointmentDAO;
import DAO.InvoiceDAO;
import Model.Appointment;
import Model.Invoice;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;

/**
 *
 * @author Legion
 */
@WebServlet(name = "StaffInvoiceController", urlPatterns = {"/staff/invoices"})
public class StaffInvoiceController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        InvoiceDAO invoiceDAO = new InvoiceDAO();
        AppointmentDAO appointmentDAO = new AppointmentDAO();

        User currentUser = (User) request.getSession().getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect("../login");
            return;
        }

        int staffId = currentUser.getId();

        // Lấy paymentMethod từ query parameter
        String paymentMethod = request.getParameter("paymentMethod"); // "vnpay", "direct", hoặc null

        // Phân trang
        int page = 1;
        int pageSize = 10;

        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        int offset = (page - 1) * pageSize;

        // Lấy danh sách hóa đơn
        List<Invoice> invoices = invoiceDAO.getInvoices(paymentMethod, staffId, offset, pageSize);

        List<Appointment> appointments = appointmentDAO.getStaffAppointments(staffId);

        // Lấy tổng số bản ghi để tính số trang
        int totalRecords = invoiceDAO.countInvoices(paymentMethod, staffId);
        int totalPages = (int) Math.ceil((double) totalRecords / pageSize);

        // Gửi về JSP
        request.setAttribute("invoices", invoices);
        request.setAttribute("appointments", appointments);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("paymentMethod", paymentMethod);

        request.getRequestDispatcher("../staff-invoice-list.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "create":
                    addInvoice(request, response);
                    break;
                case "update":
                    updateInvoice(request, response);
                    break;
            }
        } else {
            // Handle missing action parameter
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    private void addInvoice(HttpServletRequest request, HttpServletResponse response) {
        try {
            int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
            BigDecimal totalAmount = new BigDecimal(request.getParameter("totalAmount"));
            String paymentMethod = request.getParameter("paymentMethod");
            int pointsChange = Integer.parseInt(request.getParameter("pointsChange"));

            Invoice invoice = new Invoice();
            invoice.setAppointmentId(appointmentId);
            invoice.setTotalAmount(totalAmount);
            invoice.setPaymentMethod(paymentMethod);
            invoice.setPointsChange(pointsChange);
            invoice.setCreatedAt(new java.util.Date());

            InvoiceDAO invoiceDAO = new InvoiceDAO();
            boolean success = invoiceDAO.addInvoice(invoice);

            if (success) {
                response.sendRedirect("invoices?success");
            } else {
                response.sendRedirect("invoices?fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendRedirect("invoices?fail=invalidData");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void updateInvoice(HttpServletRequest request, HttpServletResponse response) {
        try {
            int invoiceId = Integer.parseInt(request.getParameter("invoiceId"));
            int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
            BigDecimal totalAmount = new BigDecimal(request.getParameter("totalAmount"));
            String paymentMethod = request.getParameter("paymentMethod");
            int pointsChange = Integer.parseInt(request.getParameter("pointsChange"));

            Invoice invoice = new Invoice();
            invoice.setId(invoiceId);
            invoice.setAppointmentId(appointmentId);
            invoice.setTotalAmount(totalAmount);
            invoice.setPaymentMethod(paymentMethod);
            invoice.setPointsChange(pointsChange);
            // Not updating createdAt

            InvoiceDAO invoiceDAO = new InvoiceDAO();
            boolean success = invoiceDAO.updateInvoice(invoice);

            if (success) {
                response.sendRedirect("invoices?success");
            } else {
                response.sendRedirect("invoices?fail");
            }
        } catch (Exception e) {
            e.printStackTrace();
            try {
                response.sendRedirect("invoices?fail=invalidData");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
