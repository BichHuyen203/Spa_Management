/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class FeedbackResponse {
    private int id;
    private int feedbackId;
    private String content;
    private Timestamp respondedAt;

    public FeedbackResponse() {
    }

    public FeedbackResponse(int id, int feedbackId, String content, Timestamp respondedAt) {
        this.id = id;
        this.feedbackId = feedbackId;
        this.content = content;
        this.respondedAt = respondedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(int feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Timestamp getRespondedAt() {
        return respondedAt;
    }

    public void setRespondedAt(Timestamp respondedAt) {
        this.respondedAt = respondedAt;
    }

    @Override
    public String toString() {
        return "FeedbackResponse{" +
                "id=" + id +
                ", feedbackId=" + feedbackId +
                ", content='" + content + '\'' +
                ", respondedAt=" + respondedAt +
                '}';
    }
}
