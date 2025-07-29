package Controller;

import DAO.AppointmentDAO;
import DAO.SpaServiceDAO;
import DAO.UserDAO;
import Model.Appointment;
import Model.SpaService;
import Model.User;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "AdminWorkScheduleManagement", urlPatterns = {"/admin/workSchedule"})
public class AdminWorkScheduleManagement extends HttpServlet {

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
        String action = request.getPathInfo() != null ? request.getPathInfo().substring(1) : "list";

        switch (action) {
            case "list":
                List<Appointment> appointments = appointmentDAO.getAppointments();
                List<Map<String, Object>> eventList = new ArrayList<>();
                SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

               
                Map<Integer, String> serviceNameMap = new HashMap<>();
                for (SpaService service : spaServiceDAO.getActiveSpaServices()) {
                    serviceNameMap.put(service.getId(), service.getName());
                }
                
                

                for (Appointment appointment : appointments) {
                    Map<String, Object> event = new HashMap<>();
                    event.put("id", appointment.getId());
                    
                    for(User staff : userDao.getStaffList()) {
                        if(appointment.getStaffId() == staff.getId()) {
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
                request.getRequestDispatcher("/admin-workSchedule.jsp").forward(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getPathInfo() != null ? request.getPathInfo().substring(1) : "update";

        switch (action) {
            case "update":
                int appointmentId = Integer.parseInt(request.getParameter("appointmentId"));
                int staffId = Integer.parseInt(request.getParameter("staffId"));
                Timestamp newStart = Timestamp.valueOf(request.getParameter("start").replace("T", " ") + ":00");
                int serviceId = Integer.parseInt(request.getParameter("serviceId"));

                boolean success = appointmentDAO.updateAppointment(appointmentId, staffId, newStart, serviceId);
                response.setContentType("application/json");
                response.getWriter().write("{\"success\": " + success + "}");
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}