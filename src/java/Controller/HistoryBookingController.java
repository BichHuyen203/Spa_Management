/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AppointmentDAO;
import DAO.FeedbackDAO;
import DAO.FeedbackResponseDAO;
import Model.Appointment;
import Model.Feedback;
import Model.FeedbackResponse;
import Model.User;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Legion
 */
@WebServlet(name = "HistoryBookingController", urlPatterns = {"/history-booking"})
public class HistoryBookingController extends HttpServlet {

    private AppointmentDAO appointmentDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        appointmentDAO = new AppointmentDAO(); // hoặc inject nếu dùng DI
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println(request.getSession().getAttribute("user"));
        int userId = ((User) request.getSession().getAttribute("user")).getId();
        String status = request.getParameter("status"); // có thể là null
        int page = 1;
        if (request.getParameter("page") != null) {
            try {
                page = Integer.parseInt(request.getParameter("page"));
            } catch (NumberFormatException e) {
                page = 1;
            }
        }

        List<Appointment> appointments = appointmentDAO.getAppointmentsByUserId(userId, status, page);
        int totalAppointments = appointmentDAO.countAppointmentsByUserId(userId, status);
        int totalPages = (int) Math.ceil((double) totalAppointments / 5);
        List<Feedback> feedbacks = new FeedbackDAO().getFeedbacksByUserId(userId);
        List<Integer> feedbackIds = new ArrayList();
        for(Feedback fb :feedbacks){
            feedbackIds.add(fb.getId());
        }
        
        List<FeedbackResponse> fbRes = new FeedbackResponseDAO().getFeedbackResponsesByFbIds(feedbackIds);

        System.out.println(totalAppointments);

        // Gán vào request scope
        request.setAttribute("appointments", appointments);
        request.setAttribute("feedbacks", feedbacks);
        request.setAttribute("feedbackResponses", fbRes);
        request.setAttribute("currentPage", page);
        request.setAttribute("totalPages", totalPages);
        request.setAttribute("userId", userId);
        request.setAttribute("status", status);

        // Chuyển tiếp tới JSP để hiển thị
        request.getRequestDispatcher("history-booking.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            int appointmentId = Integer.parseInt(request.getParameter("id"));
            String scheduledAtStr = request.getParameter("scheduledAt");
            
            // Chuyển chuỗi "2025-06-03T15:30" -> java.util.Date
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date scheduledAt = formatter.parse(scheduledAtStr);
            
            boolean updated = appointmentDAO.updateScheduledAt(appointmentId, scheduledAt);
            
            if (updated) {
                request.getSession().setAttribute("message", "Appointment updated successfully.");
            } else {
                request.getSession().setAttribute("error", "Failed to update appointment.");
            }
            
            response.sendRedirect("history-booking?success");
        } catch (ParseException ex) {
            Logger.getLogger(HistoryBookingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
