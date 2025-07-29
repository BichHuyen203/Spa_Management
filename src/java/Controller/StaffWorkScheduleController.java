/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.AppointmentDAO;
import DAO.SpaServiceDAO;
import DAO.UserDAO;
import Model.Appointment;
import Model.SpaService;
import Model.User;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author PCASUS
 */
@WebServlet(name = "StaffWorkScheduleController", urlPatterns = {"/staff/schedule"})
public class StaffWorkScheduleController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    private Gson gson;
    private UserDAO userDao;
    private SpaServiceDAO spaServiceDAO;
    private AppointmentDAO appointmentDAO;

    @Override
    public void init() throws ServletException {
        super.init();
        gson = new Gson();
        userDao = new UserDAO();
        spaServiceDAO = new SpaServiceDAO();
        appointmentDAO = new AppointmentDAO();
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession();
        User acc = (User) session.getAttribute("user");
        String action = request.getPathInfo() != null ? request.getPathInfo().substring(1) : "list";

        switch (action) {
            case "list":
                List<Appointment> appointments = appointmentDAO.getStaffAppointments(acc.getId());
                List<Map<String, Object>> eventList = new ArrayList<>();
                SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                Map<Integer, String> serviceNameMap = new HashMap<>();
                for (SpaService service : spaServiceDAO.getActiveSpaServices()) {
                    serviceNameMap.put(service.getId(), service.getName());
                }

                for (Appointment appointment : appointments) {
                    Map<String, Object> event = new HashMap<>();
                    event.put("id", appointment.getId());

                    for (User staff : userDao.getStaffList()) {
                        if (appointment.getStaffId() == staff.getId()) {
                            event.put("title", staff.getFullname());
                        }
                        break;
                    }

                    event.put("start", isoFormat.format(appointment.getScheduledAt()));
                    event.put("end", isoFormat.format(new Timestamp(appointment.getScheduledAt().getTime() + 60 * 60 * 1000)));
                    event.put("staffId", appointment.getStaffId());
                    event.put("serviceId", appointment.getServiceId());
//                    event.put("status", appointment.getStatus());
                    eventList.add(event);
                }

                String jsonAppointments = gson.toJson(eventList);
                request.setAttribute("appointmentsJson", jsonAppointments);
                request.setAttribute("staffList", userDao.getStaffList());
                request.setAttribute("serviceList", spaServiceDAO.getActiveSpaServices());
                request.getRequestDispatcher("/staff_workSchedule.jsp").forward(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
        processRequest(request, response);
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
