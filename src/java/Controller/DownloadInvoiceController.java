/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AppointmentDAO;
import DAO.InvoiceDAO;
import Model.Appointment;
import Model.Invoice;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Legion
 */
@WebServlet(name = "DownloadInvoiceController", urlPatterns = {"/download-invoice"})
public class DownloadInvoiceController extends HttpServlet {

    private InvoiceDAO invoiceDAO;
    private AppointmentDAO appointmentDAO;

    @Override
    public void init() throws ServletException {
        invoiceDAO = new InvoiceDAO();
        appointmentDAO = new AppointmentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String invoiceIdStr = request.getParameter("id");
        if (invoiceIdStr == null || invoiceIdStr.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing invoiceId");
            return;
        }

        int invoiceId;
        try {
            invoiceId = Integer.parseInt(invoiceIdStr);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid invoiceId");
            return;
        }

        Invoice invoice = invoiceDAO.getInvoiceById(invoiceId);
        if (invoice == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invoice not found");
            return;
        }

        Appointment appointment = appointmentDAO.getAppointmentById(invoice.getAppointmentId());
        if (appointment == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Appointment not found");
            return;
        }

        // Thiết lập header response để trình duyệt tải file PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=invoice_" + invoiceId + ".pdf");

        try {
            // Tạo Document iText
            com.lowagie.text.Document document = new com.lowagie.text.Document();
            com.lowagie.text.pdf.PdfWriter.getInstance(document, response.getOutputStream());

            document.open();

            // Thêm nội dung Invoice - bạn có thể customize theo ý
            document.add(new com.lowagie.text.Paragraph("Invoice ID: " + invoice.getId()));
            document.add(new com.lowagie.text.Paragraph("Appointment ID: " + invoice.getAppointmentId()));
            document.add(new com.lowagie.text.Paragraph("Total Amount: " + invoice.getTotalAmount()));
            document.add(new com.lowagie.text.Paragraph("Payment Method: " + invoice.getPaymentMethod()));
            document.add(new com.lowagie.text.Paragraph("Points Change: " + invoice.getPointsChange()));
            document.add(new com.lowagie.text.Paragraph("Created At: " + invoice.getCreatedAt()));

            // Thêm nội dung Appointment
            document.add(new com.lowagie.text.Paragraph("----- Appointment Details -----"));
            document.add(new com.lowagie.text.Paragraph("User ID: " + appointment.getUser().getFullname()));
            document.add(new com.lowagie.text.Paragraph("Service ID: " + appointment.getSpaService().getName()));
            document.add(new com.lowagie.text.Paragraph("Staff ID: " + appointment.getStaff().getFullname()));
            document.add(new com.lowagie.text.Paragraph("Room ID: " + appointment.getRoom().getName()));
            document.add(new com.lowagie.text.Paragraph("Scheduled At: " + appointment.getScheduledAt()));
            document.add(new com.lowagie.text.Paragraph("Status: " + appointment.getStatus()));

            document.close();

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

}
