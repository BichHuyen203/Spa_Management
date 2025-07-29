/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Legion
 */
import DAO.AppointmentDAO;
import java.math.BigDecimal;
import java.util.Date;

public class Invoice {
    private int id;
    private int appointmentId;
    private BigDecimal totalAmount;
    private String paymentMethod;
    private int pointsChange;
    private Date createdAt;
    
    private Appointment appointment;

    public Appointment getAppointment() {
        return new AppointmentDAO().getAppointmentById(appointmentId);
    }

    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
    
    
    

    // Constructors
    public Invoice() {}

    public Invoice(int id, int appointmentId, BigDecimal totalAmount, String paymentMethod, int pointsChange, Date createdAt) {
        this.id = id;
        this.appointmentId = appointmentId;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.pointsChange = pointsChange;
        this.createdAt = createdAt;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public int getPointsChange() {
        return pointsChange;
    }

    public void setPointsChange(int pointsChange) {
        this.pointsChange = pointsChange;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
