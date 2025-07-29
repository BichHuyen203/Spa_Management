/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.DiscountCode;
import Model.Feedback;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.sql.Types;

/**
 *
 * @author Admin
 */
public class DiscountCodeDAO extends DBContext {

    private Connection connection;

    public DiscountCodeDAO() {
        try {
            this.connection = getConnection();
        } catch (Exception e) {
            System.out.println("Connect failed");
        }
    }

    public List<DiscountCode> getDiscountCodes(int page, int pageSize, String discountType, String status, String search, String sortBy, String sortOrder) {
        List<DiscountCode> discountCodes = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        StringBuilder query = new StringBuilder("SELECT dc.Id, dc.Code, dc.Description, dc.DiscountType, dc.DiscountValue, dc.MinOrderAmount, dc.StartDate, dc.EndDate, dc.Quantity, dc.Used, dc.IsActive,dc.CreatedAt"
                + " FROM [dbo].[DiscountCodes] dc "
                //                + "JOIN [dbo].[Appointments] a ON fb.AppointmentId = a.Id "
                //                + "LEFT JOIN [dbo].[FeedbackResponse] fr ON fr.FeedbackId = fb.Id "
                + "WHERE 1=1");

        if (discountType != null && !discountType.isEmpty()) {
            query.append("AND dc.DiscountType = ?");
        }
        if (status != null && !status.isEmpty()) {
            query.append(" AND dc.IsActive = ?");
        }
        if (search != null && !search.isEmpty()) {
            query.append(" AND dc.Code LIKE ?");
        }
        if (sortBy != null && !sortBy.isEmpty()) {
            query.append(" ORDER BY ").append(sortBy).append(" ").append(sortOrder);
        } else {
            query.append(" ORDER BY dc.CreatedAt DESC");
        }
        query.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
            int paramIndex = 1;
            if (discountType != null && !discountType.isEmpty()) {
                stmt.setString(paramIndex++, discountType);
            }
            if (status != null && !status.isEmpty()) {
                stmt.setString(paramIndex++, status);
            }
            if (search != null && !search.isEmpty()) {
                stmt.setString(paramIndex++, "%" + search + "%");
            }
            stmt.setInt(paramIndex++, offset);
            stmt.setInt(paramIndex, pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                String code = rs.getString("Code");
                String description = rs.getString("Description");
                String type = rs.getString("DiscountType");
                double discountValue = rs.getDouble("DiscountValue");
                Double minOrderAmount = rs.getDouble("MinOrderAmount");
                Timestamp startDate = rs.getTimestamp("StartDate");
                Timestamp endDate = rs.getTimestamp("EndDate");
                Integer quantity = rs.getInt("Quantity");
                int used = rs.getInt("Used");
                boolean isActive = rs.getBoolean("IsActive");
                Timestamp createdAt = rs.getTimestamp("CreatedAt");

                DiscountCode discount = new DiscountCode();
                discount.setId(id);
                discount.setCode(code);
                discount.setDescription(description);
                discount.setDiscountType(type);
                discount.setDiscountValue(discountValue);
                discount.setMinOrderAmount(minOrderAmount);
                discount.setStartDate(startDate);
                discount.setEndDate(endDate);
                discount.setQuantity(quantity);
                discount.setUsed(used);
                discount.setIsActive(isActive);
                discount.setCreatedAt(createdAt);

                discountCodes.add(discount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discountCodes;
    }

    public int getTotalDiscountCodes(String discountType, String status, String search) {
        int total = 0;
        StringBuilder query = new StringBuilder("SELECT COUNT(*) as Total "
                + "FROM [dbo].[DiscountCodes] dc "
                + "WHERE 1=1");

        if (discountType != null && !discountType.isEmpty()) {
            query.append(" AND dc.DiscountType = ?");
        }
        if (status != null && !status.isEmpty()) {
            query.append(" AND dc.IsActive = ?");
        }
        if (search != null && !search.isEmpty()) {
            query.append(" AND dc.Code LIKE ?");
        }

        try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
            int paramIndex = 1;
            if (discountType != null && !discountType.isEmpty()) {
                stmt.setString(paramIndex++, discountType);
            }
            if (status != null && !status.isEmpty()) {
                stmt.setBoolean(paramIndex++, !"show".equalsIgnoreCase(status));
            }
            if (search != null && !search.isEmpty()) {
                stmt.setString(paramIndex++, "%" + search + "%");
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                total = rs.getInt("Total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return total;
    }

    public void insertDiscountCode(DiscountCode dc) throws SQLException {
        String sql = "INSERT INTO DiscountCodes (Code, Description, DiscountType, DiscountValue, MinOrderAmount, StartDate, EndDate, Quantity, Used, IsActive, CreatedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, 0, ?, GETDATE())";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dc.getCode());
            stmt.setString(2, dc.getDescription());
            stmt.setString(3, dc.getDiscountType());
            stmt.setDouble(4, dc.getDiscountValue());
            if (dc.getMinOrderAmount() != null) {
                stmt.setDouble(5, dc.getMinOrderAmount());
            } else {
                stmt.setNull(5, Types.DOUBLE);
            }
            stmt.setTimestamp(6, dc.getStartDate());
            stmt.setTimestamp(7, dc.getEndDate());
            stmt.setInt(8, dc.getQuantity());
            stmt.setBoolean(9, dc.isIsActive());
            stmt.executeUpdate();
        }
    }

    public void updateDiscountCode(DiscountCode dc) throws SQLException {
        String sql = "UPDATE DiscountCodes SET Code=?, Description=?, DiscountType=?, DiscountValue=?, MinOrderAmount=?, StartDate=?, EndDate=?, Quantity=?, IsActive=? WHERE Id=?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, dc.getCode());
            stmt.setString(2, dc.getDescription());
            stmt.setString(3, dc.getDiscountType());
            stmt.setDouble(4, dc.getDiscountValue());
            if (dc.getMinOrderAmount() != null) {
                stmt.setDouble(5, dc.getMinOrderAmount());
            } else {
                stmt.setNull(5, Types.DOUBLE);
            }
            stmt.setTimestamp(6, dc.getStartDate());
            stmt.setTimestamp(7, dc.getEndDate());
            stmt.setInt(8, dc.getQuantity());
            stmt.setBoolean(9, dc.isIsActive());
            stmt.setInt(10, dc.getId());
            stmt.executeUpdate();
        }
    }

    public DiscountCode getDiscountCodeById(int id) {
        DiscountCode discount = null;
        String sql = "SELECT * FROM DiscountCodes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                discount = new DiscountCode();
                discount.setId(rs.getInt("id"));
                discount.setCode(rs.getString("code"));
                discount.setDescription(rs.getString("description"));
                discount.setDiscountType(rs.getString("discountType"));
                discount.setDiscountValue(rs.getDouble("discountValue"));

                double minOrderAmount = rs.getDouble("minOrderAmount");
                if (!rs.wasNull()) {
                    discount.setMinOrderAmount(minOrderAmount);
                }

                discount.setStartDate(rs.getTimestamp("startDate"));
                discount.setEndDate(rs.getTimestamp("endDate"));
                discount.setQuantity(rs.getInt("quantity"));
                discount.setIsActive(rs.getBoolean("isActive"));

                // Format ngày cho JSP
                discount.setStartDateFormatted(discount.getStartDate().toLocalDateTime().toString().substring(0, 16));
                discount.setEndDateFormatted(discount.getEndDate().toLocalDateTime().toString().substring(0, 16));
            }
        } catch (SQLException e) {
            e.printStackTrace(); // log error properly in real applications
        }
        return discount;
    }

    public boolean deleteDiscountCodeById(int id) {
        String sql = "DELETE FROM DiscountCodes WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void insertUserDiscountCodesForUser(int userId, int startCode, int endCode) {
        String sql = "INSERT INTO UserDiscountCodes (UserId, DiscountCodeId, IsUsed) VALUES (?, ?, 0)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int discountCodeId = startCode; discountCodeId <= endCode; discountCodeId++) {
                stmt.setInt(1, userId);
                stmt.setInt(2, discountCodeId);
                stmt.addBatch();
            }

            stmt.executeBatch();
            System.out.println("Inserted discount codes for user: " + userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean markDiscountAsUsed(int userId, int discountCodeId) {
        String sql = "UPDATE UserDiscountCodes "
                + "SET IsUsed = 1, UsedAt = GetDATE() "
                + "WHERE UserId = ? AND DiscountCodeId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, discountCodeId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public List<DiscountCode> getUnusedDiscountCodesByUserId(int userId) {
        List<DiscountCode> discountCodes = new ArrayList<>();

        String sql = "SELECT dc.* "
                + "FROM UserDiscountCodes udc "
                + "JOIN DiscountCodes dc ON udc.DiscountCodeId = dc.Id "
                + "WHERE udc.UserId = ? AND udc.IsUsed = 0";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                DiscountCode dc = new DiscountCode();
                dc.setId(rs.getInt("Id"));
                dc.setCode(rs.getString("Code"));
                dc.setDescription(rs.getString("Description"));
                dc.setDiscountType(rs.getString("DiscountType"));
                dc.setDiscountValue(rs.getDouble("DiscountValue"));
                dc.setMinOrderAmount(rs.getDouble("MinOrderAmount"));
                dc.setStartDate(rs.getTimestamp("StartDate"));
                dc.setEndDate(rs.getTimestamp("EndDate"));
                dc.setQuantity(rs.getInt("Quantity"));
                dc.setUsed(rs.getInt("Used"));
                dc.setIsActive(rs.getBoolean("IsActive"));
                dc.setCreatedAt(rs.getTimestamp("CreatedAt"));
                // add more if needed...

                discountCodes.add(dc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return discountCodes;
    }

    public DiscountCode getById(int id) {
        String sql = "SELECT * FROM DiscountCodes WHERE Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                DiscountCode code = new DiscountCode();
                code.setId(rs.getInt("Id"));
                code.setCode(rs.getString("Code"));
                code.setDescription(rs.getString("Description"));
                code.setDiscountType(rs.getString("DiscountType"));
                code.setDiscountValue(rs.getDouble("DiscountValue"));
                code.setMinOrderAmount(rs.getDouble("MinOrderAmount"));
                code.setStartDate(rs.getTimestamp("StartDate"));
                code.setEndDate(rs.getTimestamp("EndDate"));
                code.setQuantity(rs.getInt("Quantity"));
                code.setUsed(rs.getInt("Used"));
                code.setIsActive(rs.getBoolean("IsActive"));
                code.setCreatedAt(rs.getTimestamp("CreatedAt"));
                return code;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
