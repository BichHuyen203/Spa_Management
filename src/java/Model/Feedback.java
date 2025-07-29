/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

import DAO.*;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class Feedback {
    private int id;
    private int userId;
    private String content;
    private int rating;
    private boolean responded;
    private Timestamp createdAt;
    private int appointmentId;
    private int responseId;
    
    private User user;
    private Appointment appointment;
    private FeedbackResponse response;

    public Feedback() {
        this.user = new UserDAO().getUserById(userId);
    }
    
    public Feedback(int id, String content, int rating, User user) {
        this.id = id;
        this.content = content;
        this.rating = rating;
        this.user = user;
    }

    public Feedback(int id, int userId, String content, int rating, boolean responded, Timestamp createdAt) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.rating = rating;
        this.responded = responded;
        this.createdAt = createdAt;
    }
    
    public Feedback(int id, int userId, String content, int rating, boolean responded, Timestamp createdAt, Appointment appointment) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.rating = rating;
        this.responded = responded;
        this.createdAt = createdAt;
        this.appointment = appointment;
    }

    // Getters and setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean isResponded() {
        return responded;
    }

    public void setResponded(boolean responded) {
        this.responded = responded;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public User getUser() {
        return new UserDAO().getUserById(userId);
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Appointment getAppointment() {
        return new AppointmentDAO().getAppointmentById(appointmentId);
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public FeedbackResponse getResponse() {
        return new FeedbackResponseDAO().getFeedbackResponseById(responseId);
    }

    public void setResponse(FeedbackResponse response) {
        this.response = response;
    }

    public int getResponseId() {
        return responseId;
    }

    public void setResponseId(int responseId) {
        this.responseId = responseId;
    }

    
    
}
