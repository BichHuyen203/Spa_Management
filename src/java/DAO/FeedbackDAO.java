/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Feedback;
import Model.FeedbackResponse;
import Model.Post;
import Model.SpaService;
import Model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class FeedbackDAO extends DBContext {

    private Connection connection;

    public FeedbackDAO() {
        try {
            this.connection = getConnection();
        } catch (Exception e) {
            System.out.println("Connect failed");
        }
    }

    public List<Feedback> getRecentFeedbacks(int limit) {
        List<Feedback> list = new ArrayList<>();
        String sql = "SELECT TOP " + limit + " * FROM Feedback ORDER BY CreatedAt DESC";

        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet rs = statement.executeQuery()) {

            while (rs.next()) {
                int userId = rs.getInt("UserId");
                User user = new UserDAO().getUserById(userId); // Fetch user details

                Feedback feedback = new Feedback();
                feedback.setId(rs.getInt("Id"));
                feedback.setContent(rs.getString("Content"));
                feedback.setRating(rs.getInt("Rating"));
                feedback.setUser(user);

                list.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<Feedback> getFeebacks(int page, int pageSize, String rating, String author, String status, String search, String sortBy, String sortOrder) {
        List<Feedback> feedbacks = new ArrayList<>();
        int offset = (page - 1) * pageSize;
        StringBuilder query = new StringBuilder("SELECT fb.Id, fb.UserId, fb.Content, fb.Rating, fb.Responded, fb.CreatedAt, fb.AppointmentId, fr.Id as ResponseId"
                + " FROM [dbo].[Feedback] fb "
                + "JOIN [dbo].[Appointments] a ON fb.AppointmentId = a.Id "
                + "LEFT JOIN [dbo].[FeedbackResponse] fr ON fr.FeedbackId = fb.Id "
                + "WHERE 1=1");

        if (rating != null && !rating.isEmpty()) {
            query.append("AND fb.Rating = ?");
        }
        if (author != null && !author.isEmpty()) {
            query.append(" AND u.Fullname = ?");
        }
        if (status != null && !status.isEmpty()) {
            query.append(" AND fb.Responded = ?");
        }
        if (search != null && !search.isEmpty()) {
            query.append(" AND fb.Content LIKE ?");
        }
        if (sortBy != null && !sortBy.isEmpty()) {
            query.append(" ORDER BY ").append(sortBy).append(" ").append(sortOrder);
        } else {
            query.append(" ORDER BY fb.CreatedAt DESC");
        }
        query.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
            int paramIndex = 1;
            if (rating != null && !rating.isEmpty()) {
                stmt.setString(paramIndex++, rating);
            }
            if (author != null && !author.isEmpty()) {
                stmt.setString(paramIndex++, author);
            }
            if (status != null && !status.isEmpty()) {
                stmt.setString(paramIndex++, status);
            }
            if (search != null && !search.isEmpty()) {
                stmt.setString(paramIndex++, "%" + search + "%");
            }
            stmt.setInt(paramIndex++, offset);
            stmt.setInt(paramIndex, pageSize);

            System.out.println(query);
            System.out.println(status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("Id");
                int userId = rs.getInt("UserId");
                int rate = rs.getInt("Rating");
                String content = rs.getString("Content");
                boolean responded = rs.getBoolean("Responded");
                Timestamp createdAt = rs.getTimestamp("CreatedAt");
                int appointmentId = rs.getInt("AppointmentId");
                int responseId = rs.getInt("ResponseId");

                Feedback feedback = new Feedback();
                feedback.setId(id);
                feedback.setRating(rate);
                feedback.setContent(content);
                feedback.setResponded(responded);
                feedback.setCreatedAt(createdAt);
                feedback.setUserId(userId);
                feedback.setAppointmentId(appointmentId);
                feedback.setResponseId(responseId);

                feedbacks.add(feedback);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return feedbacks;
    }

    public int getTotalFeedbacks(String rating, String author, String status, String search) {
        int totalFeedbacks = 0;
        StringBuilder query = new StringBuilder("SELECT COUNT(*) as Total "
                + "FROM [dbo].[Feedback] fb "
                + "JOIN [dbo].[User] u ON fb.UserId = u.ID "
                + "WHERE 1=1");

        if (rating != null && !rating.isEmpty()) {
            query.append(" AND fb.Rating = ?");
        }
        if (author != null && !author.isEmpty()) {
            query.append(" AND u.Fullname = ?");
        }
        if (status != null && !status.isEmpty()) {
            query.append(" AND fb.Responded = ?");
        }
        if (search != null && !search.isEmpty()) {
            query.append(" AND fb.Title LIKE ?");
        }

        try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {
            int paramIndex = 1;
            if (rating != null && !rating.isEmpty()) {
                stmt.setString(paramIndex++, rating);
            }
            if (author != null && !author.isEmpty()) {
                stmt.setString(paramIndex++, author);
            }
            if (status != null && !status.isEmpty()) {
                stmt.setBoolean(paramIndex++, !"show".equalsIgnoreCase(status));
            }
            if (search != null && !search.isEmpty()) {
                stmt.setString(paramIndex++, "%" + search + "%");
            }

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                totalFeedbacks = rs.getInt("Total");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return totalFeedbacks;
    }

    public void replyToFeedback(int feedbackId, String responseContent) {
        String sql = "UPDATE Feedback SET Responded = 1 WHERE Id = ?";
        String sqlInsert = "INSERT INTO FeedbackResponse (FeedbackId, Content, RespondedAt) VALUES (?, ?, GETDATE())";

        try (PreparedStatement stmt1 = connection.prepareStatement(sql); PreparedStatement stmt2 = connection.prepareStatement(sqlInsert)) {
            stmt1.setInt(1, feedbackId);
            stmt1.executeUpdate();

            stmt2.setInt(1, feedbackId);
            stmt2.setString(2, responseContent);
            stmt2.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean addFeedback(Feedback fb) {
        String sql = "INSERT INTO Feedback (AppointmentId, Responded, UserId, Content, Rating, CreatedAt) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, fb.getAppointmentId());
            stmt.setBoolean(2, fb.isResponded());
            stmt.setInt(3, fb.getUserId());
            stmt.setString(4, fb.getContent());
            stmt.setInt(5, fb.getRating());
            stmt.setTimestamp(6, fb.getCreatedAt());

            return stmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Feedback> getFeedbacksByUserId(int userId) {
        List<Feedback> feedbacks = new ArrayList<>();
        String sql = "SELECT * FROM Feedback WHERE userId = ? ";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Feedback fb = new Feedback();
                    fb.setId(rs.getInt("Id"));
                    fb.setUserId(rs.getInt("UserId"));
                    fb.setContent(rs.getString("Content"));
                    fb.setRating(rs.getInt("Rating"));
                    fb.setAppointmentId(rs.getInt("AppointmentId"));
                    fb.setCreatedAt(rs.getTimestamp("CreatedAt"));
                    fb.setResponded(rs.getBoolean("Responded"));

                    feedbacks.add(fb);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return feedbacks;
    }

    public List<Feedback> getFeedbackByServiceId(int serviceId) {
        List<Feedback> feedbackList = new ArrayList<>();

        String sql = """
        SELECT f.Id AS FeedbackId,
               f.UserId,
               f.Content,
               f.Rating,
               f.Responded,
               f.CreatedAt AS FeedbackCreatedAt,
               f.AppointmentId,
               a.ServiceId,
               s.Name AS ServiceName,
               fr.Id as ResponseId
        FROM Feedback f
        JOIN Appointments a ON f.AppointmentId = a.Id
        JOIN SpaService s ON a.ServiceId = s.Id
        LEFT JOIN [dbo].[FeedbackResponse] fr ON fr.FeedbackId = f.Id
        WHERE s.Id = ?
    """;

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, serviceId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Feedback fb = new Feedback();
                    fb.setId(rs.getInt("FeedbackId"));
                    fb.setUserId(rs.getInt("UserId"));
                    fb.setContent(rs.getString("Content"));
                    fb.setRating(rs.getInt("Rating"));
                    fb.setResponded(rs.getBoolean("Responded"));
                    fb.setCreatedAt(rs.getTimestamp("FeedbackCreatedAt"));
                    fb.setResponseId(rs.getInt("ResponseId"));
                    feedbackList.add(fb);
                }
            }
        } catch (SQLException e) {
            System.out.println("getFeedbackByServiceId");
        }

        return feedbackList;
    }

}
