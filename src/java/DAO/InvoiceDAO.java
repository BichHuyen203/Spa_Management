/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Legion
 */
import Model.Invoice;
import java.sql.*;
import java.util.*;
import java.math.BigDecimal;

public class InvoiceDAO extends DBContext {

    private Connection connection;

    public InvoiceDAO() {
        try {
            this.connection = getConnection();
        } catch (Exception e) {
            System.out.println("Connect failed");
        }
    }

    // Create
    public boolean addInvoice(Invoice invoice) {
        String sql = "INSERT INTO Invoices (AppointmentId, TotalAmount, PaymentMethod, PointsChange, CreatedAt) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, invoice.getAppointmentId());
            stmt.setBigDecimal(2, invoice.getTotalAmount());
            stmt.setString(3, invoice.getPaymentMethod());
            stmt.setInt(4, invoice.getPointsChange());
            stmt.setTimestamp(5, new Timestamp(invoice.getCreatedAt().getTime()));

            int rows = stmt.executeUpdate();
            return rows > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Thêm thất bại
    }

    // Read All
    public List<Invoice> getAllInvoices() {
        List<Invoice> list = new ArrayList<>();
        String sql = "SELECT * FROM Invoices ORDER BY Id DESC";
        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt("Id"));
                invoice.setAppointmentId(rs.getInt("AppointmentId"));
                invoice.setTotalAmount(rs.getBigDecimal("TotalAmount"));
                invoice.setPaymentMethod(rs.getString("PaymentMethod"));
                invoice.setPointsChange(rs.getInt("PointsChange"));
                invoice.setCreatedAt(rs.getTimestamp("CreatedAt"));
                list.add(invoice);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Read by ID
    public Invoice getInvoiceById(int id) {
        String sql = "SELECT * FROM Invoices WHERE Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Invoice invoice = new Invoice();
                    invoice.setId(rs.getInt("Id"));
                    invoice.setAppointmentId(rs.getInt("AppointmentId"));
                    invoice.setTotalAmount(rs.getBigDecimal("TotalAmount"));
                    invoice.setPaymentMethod(rs.getString("PaymentMethod"));
                    invoice.setPointsChange(rs.getInt("PointsChange"));
                    invoice.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    return invoice;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Invoice> getInvoicesByCustomerId(int customerId) {
        List<Invoice> invoices = new ArrayList<>();
        String sql = """
        SELECT i.*
        FROM Invoices i
        JOIN Appointments a ON i.AppointmentId = a.Id
        WHERE a.UserId = ?
        ORDER BY i.Id DESC
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Invoice invoice = new Invoice();
                    invoice.setId(rs.getInt("Id"));
                    invoice.setAppointmentId(rs.getInt("AppointmentId"));
                    invoice.setTotalAmount(rs.getBigDecimal("TotalAmount"));
                    invoice.setPaymentMethod(rs.getString("PaymentMethod"));
                    invoice.setPointsChange(rs.getInt("PointsChange"));
                    invoice.setCreatedAt(rs.getTimestamp("CreatedAt"));

                    invoices.add(invoice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // hoặc log lỗi
        }

        return invoices;
    }

    // Update
    public boolean updateInvoice(Invoice invoice) {
        String sql = "UPDATE Invoices SET AppointmentId = ?, TotalAmount = ?, PaymentMethod = ?, PointsChange = ? WHERE Id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, invoice.getAppointmentId());
            stmt.setBigDecimal(2, invoice.getTotalAmount());
            stmt.setString(3, invoice.getPaymentMethod());
            stmt.setInt(4, invoice.getPointsChange());
            stmt.setInt(5, invoice.getId());

            int rows = stmt.executeUpdate();
            return rows > 0; // Trả về true nếu có ít nhất 1 dòng được cập nhật
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Cập nhật thất bại
    }

    // Delete
    public void deleteInvoice(int id) {
        String sql = "DELETE FROM Invoices WHERE Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Invoice> getInvoicesByCustomerIdPaged(int customerId, int page, int pageSize) {
        List<Invoice> invoices = new ArrayList<>();
        String sql = """
        SELECT i.*
        FROM Invoices i
        JOIN Appointments a ON i.AppointmentId = a.Id
        WHERE a.UserId = ?
        ORDER BY i.Id DESC
        OFFSET ? ROWS FETCH NEXT ? ROWS ONLY
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            int offset = (page - 1) * pageSize;

            stmt.setInt(1, customerId);
            stmt.setInt(2, offset);
            stmt.setInt(3, pageSize);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Invoice invoice = new Invoice();
                    invoice.setId(rs.getInt("Id"));
                    invoice.setAppointmentId(rs.getInt("AppointmentId"));
                    invoice.setTotalAmount(rs.getBigDecimal("TotalAmount"));
                    invoice.setPaymentMethod(rs.getString("PaymentMethod"));
                    invoice.setPointsChange(rs.getInt("PointsChange"));
                    invoice.setCreatedAt(rs.getTimestamp("CreatedAt"));

                    invoices.add(invoice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return invoices;
    }

    public int countInvoicesByCustomerId(int customerId) {
        String sql = """
        SELECT COUNT(*) AS Total
        FROM Invoices i
        JOIN Appointments a ON i.AppointmentId = a.Id
        WHERE a.UserId = ?
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("Total");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public List<Invoice> getInvoices(String paymentMethod, Integer staffId, int offset, int limit) {
        List<Invoice> invoices = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT i.*, a.StaffId "
                + "FROM Invoices i "
                + "JOIN Appointments a ON i.AppointmentId = a.Id "
                + "WHERE 1=1"
        );

        if (paymentMethod != null && !paymentMethod.isEmpty()) {
            sql.append(" AND i.PaymentMethod = ?");
        }

        if (staffId != null) {
            sql.append(" AND a.StaffId = ?");
        }

        sql.append(" ORDER BY i.CreatedAt DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (paymentMethod != null && !paymentMethod.isEmpty()) {
                stmt.setString(paramIndex++, paymentMethod);
            }

            if (staffId != null) {
                stmt.setInt(paramIndex++, staffId);
            }

            stmt.setInt(paramIndex++, offset);
            stmt.setInt(paramIndex, limit);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Invoice invoice = new Invoice();
                invoice.setId(rs.getInt("Id"));
                invoice.setAppointmentId(rs.getInt("AppointmentId"));
                invoice.setTotalAmount(rs.getBigDecimal("TotalAmount"));
                invoice.setPaymentMethod(rs.getString("PaymentMethod"));
                invoice.setPointsChange(rs.getInt("PointsChange"));
                invoice.setCreatedAt(rs.getTimestamp("CreatedAt"));

                // Nếu bạn muốn lưu thêm staffId trong invoice object (custom class)
                // bạn có thể mở rộng class Invoice để chứa staffId hoặc Appointment embedded
                invoices.add(invoice);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return invoices;
    }

    public int countInvoices(String paymentMethod, Integer staffId) {
        StringBuilder sql = new StringBuilder(
                "SELECT COUNT(*) "
                + "FROM Invoices i "
                + "JOIN Appointments a ON i.AppointmentId = a.Id "
                + "WHERE 1=1"
        );

        if (paymentMethod != null && !paymentMethod.isEmpty()) {
            sql.append(" AND i.PaymentMethod = ?");
        }

        if (staffId != null) {
            sql.append(" AND a.StaffId = ?");
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;

            if (paymentMethod != null && !paymentMethod.isEmpty()) {
                stmt.setString(paramIndex++, paymentMethod);
            }

            if (staffId != null) {
                stmt.setInt(paramIndex++, staffId);
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

}
