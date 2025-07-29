package Controller;

import DAO.ReportDAO;
import Model.CustomerBehavior;
import Model.RevenueReport;
import Model.ServicePopularity;
import Model.StaffPerformance;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.System.Logger;
import java.util.Arrays;
import java.util.List;


/**
 * Servlet xử lý các báo cáo quản trị.
 */
@WebServlet(name = "AdminReportController", urlPatterns = {"/admin/report"})
public class AdminReportController extends HttpServlet {

    private final ReportDAO reportDAO = new ReportDAO();
    private static final List<String> VALID_REPORT_TYPES = Arrays.asList("revenue", "servicePopularity", "staffPerformance", "customerBehavior");
    private static final List<String> VALID_PERIODS = Arrays.asList("daily", "weekly", "monthly");

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        request.getRequestDispatcher("/admin-revenueReport.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Xử lý yêu cầu tạo báo cáo báo cáo.
     */
    private void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String reportType = request.getParameter("reportType");
        String period = request.getParameter("period");

//        // Xác thực tham số đầu vào
//        if (reportType == null || !VALID_REPORT_TYPES.contains(reportType.toLowerCase())) {
//            reportType = "revenue"; 
//        }
//        if (reportType.equals("revenue") && (period == null || !VALID_PERIODS.contains(period))) {
//            period = "daily"; 
//        }

        try {
            List<?> reportData = fetchReportData(reportType, period);
            String reportTitle = generateReportTitle(reportType, period);

            request.setAttribute("reportData", convertToTableData(reportData, reportType, period));
            request.setAttribute("reportTitle", reportTitle);
            request.setAttribute("reportHeaders", getHeaders(reportType));

            request.getRequestDispatcher("/admin-revenueReport.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Lấy dữ liệu báo cáo từ DAO dựa trên loại báo cáo.
     */
    private List<?> fetchReportData(String reportType, String period) throws Exception {
        switch (reportType) {
            case "revenue":
                return reportDAO.getRevenueReport(period);
            case "servicePopularity":
                return reportDAO.getServicePopularity();
            case "staffPerformance":
                return reportDAO.getStaffPerformance();
            case "customerBehavior":
                return reportDAO.getCustomerBehavior();
            default:
                return reportDAO.getRevenueReport(period); // Mặc định
        }
    }

    /**
     * Tạo tiêu đề báo cáo dựa trên loại và khoảng thời gian.
     */
    private String generateReportTitle(String reportType, String period) {
        switch (reportType) {
            case "revenue":
                return "Revenue Report (" + (period != null ? period : "daily") + ")";
            case "servicePopularity":
                return "Service Popularity Report";
            case "staffPerformance":
                return "Staff Performance Report";
            case "customerBehavior":
                return "Customer Behavior Report";
            default:
                return "Revenue Report (" + (period != null ? period : "daily") + ")";
        }
    }

    /**
     * Chuyển đổi dữ liệu báo cáo thành định dạng bảng.
     */
    private Object[][] convertToTableData(List<?> data, String reportType, String period) {
        if (data == null || data.isEmpty()) {
            return new Object[0][0];
        }

        int numColumns = getHeaders(reportType).length;
        Object[][] tableData = new Object[data.size()][numColumns];

        switch (reportType) {
            case "revenue":
                List<RevenueReport> revenueReports = (List<RevenueReport>) data;
                for (int i = 0; i < revenueReports.size(); i++) {
                    RevenueReport report = revenueReports.get(i);
                    if ("daily".equals(period)) {
                        tableData[i][0] = report.getReportDate() != null ? report.getReportDate().toString() : "N/A";
                    } else {
                        tableData[i][0] = report.getReportYear() + "-" + (report.getReportMonth() > 0 ? report.getReportMonth() : report.getReportWeek());
                    }
                    tableData[i][1] = report.getTotalRevenue();
                }
                break;

            case "servicePopularity":
                List<ServicePopularity> serviceReports = (List<ServicePopularity>) data;
                for (int i = 0; i < serviceReports.size(); i++) {
                    ServicePopularity report = serviceReports.get(i);
                    tableData[i][0] = report.getServiceName();
                    tableData[i][1] = report.getBookingCount();
                }
                break;

            case "staffPerformance":
                List<StaffPerformance> staffReports = (List<StaffPerformance>) data;
                for (int i = 0; i < staffReports.size(); i++) {
                    StaffPerformance report = staffReports.get(i);
                    tableData[i][0] = report.getStaffName();
                    tableData[i][1] = report.getAppointmentCount();
                }
                break;

            case "customerBehavior":
                List<CustomerBehavior> customerReports = (List<CustomerBehavior>) data;
                for (int i = 0; i < customerReports.size(); i++) {
                    CustomerBehavior report = customerReports.get(i);
                    tableData[i][0] = report.getCustomerName();
                    tableData[i][1] = report.getFeedbackCount();
                    tableData[i][2] = report.getSupportRequestCount();
                }
                break;
        }

        return tableData;
    }

    /**
     * Lấy tiêu đề cột cho bảng báo cáo.
     */
    private String[] getHeaders(String reportType) {
        switch (reportType) {
            case "revenue":
                return new String[]{"Date", "Total Revenue"};
            case "servicePopularity":
                return new String[]{"Service Name", "Booking Count"};
            case "staffPerformance":
                return new String[]{"Staff Name", "Appointment Count"};
            case "customerBehavior":
                return new String[]{"Customer Name", "Feedback Count", "Support Request Count"};
            default:
                return new String[]{"Date", "Total Revenue"};
        }
    }

    @Override
    public String getServletInfo() {
        return "Admin Report Controller for generating various reports.";
    }
}