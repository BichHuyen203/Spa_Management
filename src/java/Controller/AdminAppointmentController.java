/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AppointmentDAO;
import DAO.RoomDAO;
import DAO.UserDAO;
import Model.Appointment;
import Model.Room;
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
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Legion
 */
@WebServlet(name = "AdminAppointmentController", urlPatterns = {"/admin/appointments"})
public class AdminAppointmentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer staffId = null;
        Integer roomId = null;
        Date scheduledFrom = null;
        Date scheduledTo = null;

        try {
            if (request.getParameter("staffId") != null && !request.getParameter("staffId").isEmpty()) {
                staffId = Integer.valueOf(request.getParameter("staffId"));
            }
            if (request.getParameter("roomId") != null && !request.getParameter("roomId").isEmpty()) {
                roomId = Integer.valueOf(request.getParameter("roomId"));
            }
            if (request.getParameter("scheduledFrom") != null && !request.getParameter("scheduledFrom").isEmpty()) {
                scheduledFrom = java.sql.Date.valueOf(request.getParameter("scheduledFrom")); // format yyyy-MM-dd
            }
            if (request.getParameter("scheduledTo") != null && !request.getParameter("scheduledTo").isEmpty()) {
                scheduledTo = java.sql.Date.valueOf(request.getParameter("scheduledTo"));
            }
        } catch (IllegalArgumentException e) {
            // Xử lý lỗi format date nếu cần
        }

        int page = 1;
        int pageSize = 10;
        try {
            if (request.getParameter("page") != null) {
                page = Integer.parseInt(request.getParameter("page"));
            }
            if (request.getParameter("pageSize") != null) {
                pageSize = Integer.parseInt(request.getParameter("pageSize"));
            }
        } catch (NumberFormatException e) {
            // fallback giá trị mặc định
        }

        AppointmentDAO appointmentDAO = new AppointmentDAO();
        RoomDAO roomDAO = new RoomDAO();
        UserDAO udao = new UserDAO();

        List<Appointment> appointments = appointmentDAO.getAppointments(staffId, roomId, scheduledFrom, scheduledTo, page, pageSize);
        List<Room> rooms = roomDAO.getAllRooms();
        List<User> staffs = udao.getFilteredStaff("", "", -1, "", Boolean.FALSE);

        request.setAttribute("appointments", appointments);
        request.setAttribute("rooms", rooms);
        request.setAttribute("staffs", staffs);
        request.setAttribute("staffId", staffId);
        request.setAttribute("roomId", roomId);
        request.setAttribute("scheduledFrom", scheduledFrom);
        request.setAttribute("scheduledTo", scheduledTo);
        request.setAttribute("currentPage", page);
        int totalItems = appointmentDAO.countAppointments(staffId, roomId, scheduledFrom, scheduledTo);
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalItems", totalItems);

        request.getRequestDispatcher("../admin-appointments.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("appointmentId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        int staffId = Integer.parseInt(request.getParameter("staffId"));
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        String scheduledAtStr = request.getParameter("scheduledAt");
        String status = request.getParameter("status");

        // Parse scheduledAt
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date scheduledAt = new Date();
        try {
            scheduledAt = sdf.parse(scheduledAtStr);
        } catch (ParseException ex) {
            Logger.getLogger(AdminAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Tạo Appointment object
        Appointment appointment = new Appointment();
        appointment.setId(id);
        appointment.setUserId(userId);
        appointment.setServiceId(serviceId);
        appointment.setStaffId(staffId);
        appointment.setRoomId(roomId);
        appointment.setScheduledAt(scheduledAt);
        appointment.setStatus(status);

        // Gọi DAO
        AppointmentDAO dao = new AppointmentDAO();
        boolean success = dao.updateAppointment(appointment);
        response.sendRedirect("appointments" + (success ? "?success" : ""));
    }

}
