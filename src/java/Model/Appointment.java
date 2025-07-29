/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Legion
 */
import DAO.RoomDAO;
import DAO.SpaServiceDAO;
import DAO.UserDAO;
import java.util.Date;
import java.util.List;

public class Appointment {
    private int id;
    private int userId;
    private int serviceId;
    private int staffId;
    private int roomId;
    private Date scheduledAt;
    private String status;
    private Date createdAt;
    
    
    private User user;
    private User staff;
    private SpaService spaService;
    private Room room;
    
    public User getStaff() {
        return new UserDAO().getUserById(staffId);
    }

    public void setStaff(User staff) {
        this.staff = staff;
    }
    
    public Room getRoom() {
        return new RoomDAO().getRoomId(roomId);
    }

    public SpaService getSpaService() {
        return new SpaServiceDAO().getSpaServiceById(serviceId);
    }

    public User getUser() {
        return new UserDAO().getUserById(userId);
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void setSpaService(SpaService spaService) {
        this.spaService = spaService;
    }

    public void setUser(User user) {
        this.user = user;
    }
    
    
    

    // Constructors
    public Appointment() {}

    public Appointment(int id, int userId, int serviceId, int staffId, int roomId,
                       Date scheduledAt, String status, Date createdAt) {
        this.id = id;
        this.userId = userId;
        this.serviceId = serviceId;
        this.staffId = staffId;
        this.roomId = roomId;
        this.scheduledAt = scheduledAt;
        this.status = status;
        this.createdAt = createdAt;
    }

    // Getters and Setters
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

    public int getServiceId() {
        return serviceId;
    }

    public void setServiceId(int serviceId) {
        this.serviceId = serviceId;
    }

    public int getStaffId() {
        return staffId;
    }

    public void setStaffId(int staffId) {
        this.staffId = staffId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public Date getScheduledAt() {
        return scheduledAt;
    }

    public void setScheduledAt(Date scheduledAt) {
        this.scheduledAt = scheduledAt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    // Optional: toString
    @Override
    public String toString() {
        return "Appointment{" +
                "id=" + id +
                ", userId=" + userId +
                ", serviceId=" + serviceId +
                ", staffId=" + staffId +
                ", roomId=" + roomId +
                ", scheduledAt=" + scheduledAt +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}
