/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AppointmentDAO;
import DAO.RoomDAO;
import DAO.SpaServiceDAO;
import DAO.UserDAO;
import Model.Appointment;
import Model.Room;
import Model.SpaService;
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
@WebServlet(name = "StaffAppointmentController", urlPatterns = {"/staff/appointments"})
public class StaffAppointmentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer roomId = null;
        Date scheduledFrom = null;
        Date scheduledTo = null;

        User currentUser = (User) request.getSession().getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect("../login");
            return;
        }

        int staffId = currentUser.getId();

        try {

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
        List<User> users = udao.getAllCustomers();
        List<SpaService> spaServices = new SpaServiceDAO().getActiveSpaServices();
        List<Room> rooms = roomDAO.getAllRooms();

        request.setAttribute("appointments", appointments);
        request.setAttribute("rooms", rooms);
        request.setAttribute("spaServices", spaServices);
        request.setAttribute("users", users);
        request.setAttribute("staffId", staffId);
        request.setAttribute("roomId", roomId);
        request.setAttribute("scheduledFrom", scheduledFrom);
        request.setAttribute("scheduledTo", scheduledTo);
        request.setAttribute("currentPage", page);
        int totalItems = appointmentDAO.countAppointments(staffId, roomId, scheduledFrom, scheduledTo);
        int totalPages = (int) Math.ceil((double) totalItems / pageSize);

        request.setAttribute("totalPages", totalPages);
        request.setAttribute("totalItems", totalItems);

        request.getRequestDispatcher("../staff-appointments.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "create":
                    addAppoinment(request, response);
                    break;
                case "update":
                    updateAppoinment(request, response);
                    break;
            }
        } else {
            // Handle missing action parameter
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    private void addAppoinment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        User currentUser = (User) request.getSession().getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect("../login");
            return;
        }

        int staffId = currentUser.getId();
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        String scheduledAtStr = request.getParameter("scheduledAt");
        String status = request.getParameter("status");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date scheduledAt = new Date();
        try {
            scheduledAt = sdf.parse(scheduledAtStr);
        } catch (ParseException ex) {
            Logger.getLogger(StaffAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Tạo Appointment object
        Appointment appointment = new Appointment();
        appointment.setUserId(userId);
        appointment.setServiceId(serviceId);
        appointment.setStaffId(staffId);
        appointment.setRoomId(roomId);
        appointment.setScheduledAt(scheduledAt);
        appointment.setStatus(status);

        AppointmentDAO dao = new AppointmentDAO();
        boolean success = dao.addAppointment(appointment);
        response.sendRedirect("appointments" + (success ? "?success" : ""));
    }

    private void updateAppoinment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int id = Integer.parseInt(request.getParameter("appointmentId"));
        int userId = Integer.parseInt(request.getParameter("userId"));
        int serviceId = Integer.parseInt(request.getParameter("serviceId"));
        User currentUser = (User) request.getSession().getAttribute("user");

        if (currentUser == null) {
            response.sendRedirect("../login");
            return;
        }
        int roomId = Integer.parseInt(request.getParameter("roomId"));
        String scheduledAtStr = request.getParameter("scheduledAt");
        String status = request.getParameter("status");

        // Parse scheduledAt
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date scheduledAt = new Date();
        try {
            scheduledAt = sdf.parse(scheduledAtStr);
        } catch (ParseException ex) {
            Logger.getLogger(StaffAppointmentController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Tạo Appointment object
        Appointment appointment = new Appointment();
        appointment.setId(id);
        appointment.setUserId(userId);
        appointment.setServiceId(serviceId);
        appointment.setStaffId(currentUser.getId());
        appointment.setRoomId(roomId);
        appointment.setScheduledAt(scheduledAt);
        appointment.setStatus(status);

        // Gọi DAO
        AppointmentDAO dao = new AppointmentDAO();
        boolean success = dao.updateAppointment(appointment);
        response.sendRedirect("appointments" + (success ? "?success" : ""));
    }

}
