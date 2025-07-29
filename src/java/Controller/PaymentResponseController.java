/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AppointmentDAO;
import DAO.InvoiceDAO;
import DAO.UserDAO;
import Model.AppointmentStatus;
import Model.Invoice;
import Model.User;
import Utils.Config;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Legion
 */
@WebServlet(name = "PaymentResponseController", urlPatterns = {"/payment-response"})
public class PaymentResponseController extends HttpServlet {

    private AppointmentDAO appointmentDAO;
    private InvoiceDAO invoiceDAO;
    private UserDAO userDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        appointmentDAO = new AppointmentDAO();
        invoiceDAO = new InvoiceDAO();
        userDAO = new UserDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        User currentUser = (User) request.getSession().getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect("login");
            return;
        }

        int customerId = currentUser.getId();
        
        Map fields = new HashMap();
        for (Enumeration params = request.getParameterNames(); params.hasMoreElements();) {
            String fieldName = URLEncoder.encode((String) params.nextElement(), StandardCharsets.US_ASCII.toString());
            String fieldValue = URLEncoder.encode(request.getParameter(fieldName), StandardCharsets.US_ASCII.toString());
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                fields.put(fieldName, fieldValue);
            }
        }

        String vnp_SecureHash = request.getParameter("vnp_SecureHash");
        if (fields.containsKey("vnp_SecureHashType")) {
            fields.remove("vnp_SecureHashType");
        }
        if (fields.containsKey("vnp_SecureHash")) {
            fields.remove("vnp_SecureHash");
        }
        String signValue = Config.hashAllFields(fields);

        if (signValue.equals(vnp_SecureHash)) {
            if ("00".equals(request.getParameter("vnp_TransactionStatus"))) {
                appointmentDAO.updateAppointmentStatus(Config.orderID, AppointmentStatus.Success.toString());
                String amountStr = request.getParameter("vnp_Amount");
                String paymentMethod = "VNPAY";
                BigDecimal totalAmount = new BigDecimal(amountStr).divide(BigDecimal.valueOf(100)); // because VNPAY * 100                
                // ✅ 2. Tạo hóa đơn
                Invoice invoice = new Invoice();
                invoice.setAppointmentId(Config.orderID);
                invoice.setTotalAmount(totalAmount);
                invoice.setPaymentMethod(paymentMethod);
                invoice.setPointsChange((int) totalAmount.divide(BigDecimal.TEN).doubleValue()); // tùy logic
                invoice.setCreatedAt(new Date());

                invoiceDAO.addInvoice(invoice);
                
                
                
                userDAO.updateLoyaltyPoints(customerId, invoice.getPointsChange());
                
            }
        }
        response.sendRedirect("thanks-you.jsp");
    }

}
