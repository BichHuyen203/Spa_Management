/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Appointment;
import Model.FeedbackResponse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class FeedbackResponseDAO extends DBContext {

    private Connection connection;

    public FeedbackResponseDAO() {
        try {
            this.connection = getConnection();
        } catch (Exception e) {
            System.out.println("Connect failed");
        }
    }
    
    public FeedbackResponse getFeedbackResponseById(int responseId) {
        FeedbackResponse response = null;
        String sql = "SELECT * FROM FeedbackResponse WHERE Id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, responseId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    response = new FeedbackResponse();
                    response.setId(rs.getInt("Id"));
                    response.setFeedbackId(rs.getInt("FeedbackId"));
                    response.setContent(rs.getString("Content"));
                    response.setRespondedAt(rs.getTimestamp("RespondedAt"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // log error properly in real applications
        }

        return response;
    }

    public List<FeedbackResponse> getFeedbackResponsesByFbIds(List<Integer> feedbackIds) {
    List<FeedbackResponse> responses = new ArrayList<>();

    if (feedbackIds == null || feedbackIds.isEmpty()) {
        return responses; // Trả về danh sách rỗng nếu không có ID nào
    }

    StringBuilder query = new StringBuilder("SELECT * FROM FeedbackResponse WHERE FeedbackId IN (");
    for (int i = 0; i < feedbackIds.size(); i++) {
        query.append("?");
        if (i < feedbackIds.size() - 1) {
            query.append(", ");
        }
    }
    query.append(")");

    try (PreparedStatement stmt = connection.prepareStatement(query.toString())) {

        for (int i = 0; i < feedbackIds.size(); i++) {
            stmt.setInt(i + 1, feedbackIds.get(i));
        }

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                FeedbackResponse response = new FeedbackResponse();
                response.setId(rs.getInt("Id"));
                response.setFeedbackId(rs.getInt("FeedbackId"));
                response.setContent(rs.getString("Content"));
                response.setRespondedAt(rs.getTimestamp("RespondedAt"));
                responses.add(response);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return responses;
}
}
