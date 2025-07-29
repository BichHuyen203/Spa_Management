/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.AppointmentDAO;
import DAO.DiscountCodeDAO;
import Model.Appointment;
import Model.DiscountCode;
import Model.User;
import com.google.gson.JsonObject;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import Utils.Config;
import com.google.gson.Gson;
import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "PaymentController", urlPatterns = {"/public/payment"})
public class PaymentController extends HttpServlet {

    private AppointmentDAO appointmentDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        appointmentDAO = new AppointmentDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse respone) throws ServletException, IOException {
        
        User currentUser = (User) request.getSession().getAttribute("user");

        if (currentUser == null) {
            respone.sendRedirect("../login");
            return;
        }

        String paymentMethod = request.getParameter("paymentMethod");
        
        

        String amount_raw = request.getParameter("amount");
        int staffId = Integer.parseInt(request.getParameter("staff"));
        int roomId = Integer.parseInt(request.getParameter("room"));
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        double amount_d = Double.parseDouble(amount_raw);
        
        
        
        int userId = currentUser.getId();

        if (request.getParameter("discountCodeId") != null && !request.getParameter("discountCodeId").isEmpty()) {
            int discountCodeId = Integer.parseInt(request.getParameter("discountCodeId"));

            DiscountCodeDAO discountCodeDAO = new DiscountCodeDAO();
            DiscountCode code = discountCodeDAO.getById(discountCodeId);

            if (code != null) {
                String discountType = code.getDiscountType();
                double discountValue = code.getDiscountValue(); // Giả sử là BigDecimal

                if ("Percentage".equals(discountType)) {
                    amount_d = amount_d - (amount_d * discountValue / 100.0);
                } else if ("FixedAmount".equals(discountType)) {
                    amount_d = amount_d - discountValue;
                }

                // Không cho âm tiền
                if (amount_d < 0) {
                    amount_d = 0;
                }
            }
            
            discountCodeDAO.markDiscountAsUsed(discountCodeId, userId);

        }

        int amount = (int) amount_d * 100 * 25000;
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_OrderInfo = "pay pay";
        String orderType = "other";
        String vnp_TxnRef = Config.getRandomNumber(8);
        String vnp_IpAddr = Config.getIpAddress(request);
        String vnp_TmnCode = Config.vnp_TmnCode;
        Map vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version); //Phiên bản cũ là 2.0.0, 2.0.1 thay đổi sang 2.1.0
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        String bank_code = request.getParameter("bankcode");
        if (bank_code != null && !bank_code.isEmpty()) {
            vnp_Params.put("vnp_BankCode", bank_code);
        }
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", orderType);

        String locate = request.getParameter("language");
        if (locate != null && !locate.isEmpty()) {
            vnp_Params.put("vnp_Locale", locate);
        } else {
            vnp_Params.put("vnp_Locale", "vn");
        }
        vnp_Params.put("vnp_ReturnUrl", Config.vnp_Returnurl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));

        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());

        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        //Build data to hash and querystring
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();

        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String queryUrl = query.toString();
        String vnp_SecureHash = Config.hmacSHA512(Config.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = Config.vnp_PayUrl + "?" + queryUrl;

        String scheduledAtStr = request.getParameter("scheduledAt");
        Date scheduledAt;
        try {
            scheduledAt = formatter.parse(scheduledAtStr);

        } catch (ParseException ex) {
            scheduledAt = new Date();
        }

        Appointment appointment = new Appointment();
        appointment.setUserId(userId);
        appointment.setServiceId(serviceId);
        appointment.setStaffId(staffId);
        appointment.setRoomId(roomId);
        appointment.setScheduledAt(scheduledAt);
        appointment.setStatus("Pending");
        appointment.setCreatedAt(new Date()); // set thời gian tạo hiện tại
        
        
        if(paymentMethod.equalsIgnoreCase("Direct")) {
            respone.sendRedirect("../thanks-you.jsp");
            return;
        }

        Config.orderID = appointmentDAO.insertAppointment(appointment);
        respone.sendRedirect(paymentUrl);

    }

}
