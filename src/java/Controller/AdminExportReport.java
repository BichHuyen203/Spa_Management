/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package Controller;

import DAO.ReportDAO;
import Model.CustomerBehavior;
import Model.RevenueReport;
import Model.ServicePopularity;
import Model.StaffPerformance;
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
 * @author PCASUS
 */
@WebServlet(name = "AdminExportReport", urlPatterns = {"/admin/export"})
public class AdminExportReport extends HttpServlet {

    private ReportDAO reportDAO = new ReportDAO();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String reportType = request.getParameter("reportType");
        String period = request.getParameter("period");
        List<?> reportData = null;

        try {
            switch (reportType) {
                case "revenue":
                    reportData = reportDAO.getRevenueReport(period);
                    break;
                case "servicePopularity":
                    reportData = reportDAO.getServicePopularity();
                    break;
                case "staffPerformance":
                    reportData = reportDAO.getStaffPerformance();
                    break;
                case "customerBehavior":
                    reportData = reportDAO.getCustomerBehavior();
                    break;
            }
            exportToCSV(response, reportData, reportType);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error exporting report");
        }
    }

    private void exportToCSV(HttpServletResponse response, List<?> reportData, String reportType) throws IOException {
        response.setHeader("Content-Disposition", "attachment; filename=\"report_" + reportType + ".csv\"");

        try (PrintWriter writer = response.getWriter()) {
            if (reportData == null || reportData.isEmpty()) {
                writer.println("No data available");
                return;
            }

            switch (reportType) {
                case "revenue":
                    writer.println("ReportDate,ReportYear,ReportMonth,ReportWeek,TotalRevenue");
                    for (Object obj : reportData) {
                        RevenueReport r = (RevenueReport) obj;
                        writer.printf("%s,%d,%d,%d,%.2f%n",
                                r.getReportDate() != null ? r.getReportDate() : "",
                                r.getReportYear(),
                                r.getReportMonth(),
                                r.getReportWeek(),
                                r.getTotalRevenue());
                    }
                    break;

                case "servicePopularity":
                    writer.println("ServiceName,BookingCount");
                    for (Object obj : reportData) {
                        ServicePopularity r = (ServicePopularity) obj;
                        writer.printf("%s,%d%n",
                                r.getServiceName(),
                                r.getBookingCount());
                    }
                    break;

                case "staffPerformance":
                    writer.println("StaffName,CompletedBookings");
                    for (Object obj : reportData) {
                        StaffPerformance r = (StaffPerformance) obj;
                        writer.printf("%s,%d%n",
                                r.getStaffName(),
                                r.getAppointmentCount());
                    }
                    break;

                case "customerBehavior":
                    writer.println("CustomerName,FeedbackCount,SupportRequestCount");
                    for (Object obj : reportData) {
                        CustomerBehavior r = (CustomerBehavior) obj;
                        writer.printf("%s,%d,%d%n",
                                r.getCustomerName(),
                                r.getFeedbackCount(),
                                r.getSupportRequestCount());
                    }
                    break;

                default:
                    writer.println("Invalid report type");
                    break;
            }
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
