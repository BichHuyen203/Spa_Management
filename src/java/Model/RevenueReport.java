/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author PCASUS
 */
public class RevenueReport {
    private java.sql.Date reportDate;
    private int reportYear;
    private int reportWeek;
    private int reportMonth;
    private double totalRevenue;

    // Getters và Setters
    public java.sql.Date getReportDate() { return reportDate; }
    public void setReportDate(java.sql.Date reportDate) { this.reportDate = reportDate; }
    public int getReportYear() { return reportYear; }
    public void setReportYear(int reportYear) { this.reportYear = reportYear; }
    public int getReportWeek() { return reportWeek; }
    public void setReportWeek(int reportWeek) { this.reportWeek = reportWeek; }
    public int getReportMonth() { return reportMonth; }
    public void setReportMonth(int reportMonth) { this.reportMonth = reportMonth; }
    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }
}
