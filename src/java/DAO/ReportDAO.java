/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author PCASUS
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Model.CustomerBehavior;
import Model.RevenueReport;
import Model.ServicePopularity;
import Model.StaffPerformance;

public class ReportDAO {

    private DBContext dbContext;

    public ReportDAO() {
        this.dbContext = new DBContext();
    }

    // Báo cáo doanh thu
    public List<RevenueReport> getRevenueReport(String period) throws Exception {
        List<RevenueReport> reports = new ArrayList<>();
        String sql = "";

        if ("daily".equals(period)) {
            sql = "SELECT CAST(CreatedAt AS DATE) as ReportDate, SUM(TotalAmount) as TotalRevenue "
                    + "FROM Invoices "
                    + "WHERE CreatedAt IS NOT NULL "
                    + "GROUP BY CAST(CreatedAt AS DATE) "
                    + "ORDER BY ReportDate DESC";
        } else if ("weekly".equals(period)) {
            sql = "SELECT DATEPART(YEAR, CreatedAt) as ReportYear, DATEPART(WEEK, CreatedAt) as ReportWeek, SUM(TotalAmount) as TotalRevenue "
                    + "FROM Invoices "
                    + "WHERE CreatedAt IS NOT NULL "
                    + "GROUP BY DATEPART(YEAR, CreatedAt), DATEPART(WEEK, CreatedAt) "
                    + "ORDER BY ReportYear DESC, ReportWeek DESC";
        } else if ("monthly".equals(period)) {
            sql = "SELECT DATEPART(YEAR, CreatedAt) as ReportYear, DATEPART(MONTH, CreatedAt) as ReportMonth, SUM(TotalAmount) as TotalRevenue "
                    + "FROM Invoices "
                    + "WHERE CreatedAt IS NOT NULL "
                    + "GROUP BY DATEPART(YEAR, CreatedAt), DATEPART(MONTH, CreatedAt) "
                    + "ORDER BY ReportYear DESC, ReportMonth DESC";
        }

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                RevenueReport report = new RevenueReport();

                if ("daily".equals(period)) {
                    report.setReportDate(rs.getDate("ReportDate"));
                    report.setTotalRevenue(rs.getDouble("TotalRevenue"));

                } else if ("weekly".equals(period)) {
                    report.setReportYear(rs.getInt("ReportYear"));
                    report.setReportWeek(rs.getInt("ReportWeek"));
                    // Không gọi rs.getInt("ReportMonth") vì không tồn tại trong câu SQL
                    report.setTotalRevenue(rs.getDouble("TotalRevenue"));

                } else if ("monthly".equals(period)) {
                    report.setReportYear(rs.getInt("ReportYear"));
                    report.setReportMonth(rs.getInt("ReportMonth"));
                    // Không gọi rs.getInt("ReportWeek") vì không tồn tại trong câu SQL
                    report.setTotalRevenue(rs.getDouble("TotalRevenue"));
                }

                reports.add(report);
            }

        }

        return reports;
    }

    // Mức độ phổ biến của dịch vụ
    public List<ServicePopularity> getServicePopularity() throws Exception {
        List<ServicePopularity> reports = new ArrayList<>();
        String sql = "SELECT s.Name, COUNT(a.ServiceId) as BookingCount "
                + "FROM SpaService s LEFT JOIN Appointments a ON s.Id = a.ServiceId "
                + "GROUP BY s.Name ORDER BY BookingCount DESC";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ServicePopularity report = new ServicePopularity();
                report.setServiceName(rs.getString("Name"));
                report.setBookingCount(rs.getInt("BookingCount"));
                reports.add(report);
            }
        }
        return reports;
    }

    // Hiệu suất nhân viên
    public List<StaffPerformance> getStaffPerformance() throws Exception {
        List<StaffPerformance> reports = new ArrayList<>();
        String sql = "SELECT u.Fullname, COUNT(a.StaffId) as AppointmentCount "
                + "FROM [User] u LEFT JOIN Appointments a ON u.ID = a.StaffId "
                + "WHERE u.RoleId IN (SELECT ID FROM Role WHERE Name LIKE '%staff%') "
                + "GROUP BY u.Fullname ORDER BY AppointmentCount DESC";
        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                StaffPerformance report = new StaffPerformance();
                report.setStaffName(rs.getString("Fullname"));
                report.setAppointmentCount(rs.getInt("AppointmentCount"));
                reports.add(report);
            }
        }
        return reports;
    }

    // Hành vi khách hàng
    public List<CustomerBehavior> getCustomerBehavior() throws Exception {
        List<CustomerBehavior> reports = new ArrayList<>();
        String sql = "SELECT u.Fullname, COUNT(f.Id) as FeedbackCount, COUNT(sr.Id) as SupportRequestCount "
                + "FROM [User] u "
                + "LEFT JOIN Feedback f ON u.ID = f.UserId "
                + "LEFT JOIN SupportRequests sr ON u.ID = sr.UserId "
                + "GROUP BY u.Fullname "
                + "ORDER BY COUNT(f.Id) + COUNT(sr.Id) DESC";  // sửa lại ORDER BY dùng lại biểu thức gốc

        try (Connection conn = dbContext.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                CustomerBehavior report = new CustomerBehavior();
                report.setCustomerName(rs.getString("Fullname"));
                report.setFeedbackCount(rs.getInt("FeedbackCount"));
                report.setSupportRequestCount(rs.getInt("SupportRequestCount"));
                reports.add(report);
            }
        }
        return reports;
    }
}

// Các lớp POJO (Plain Old Java Object) để lưu dữ liệu báo cáo

