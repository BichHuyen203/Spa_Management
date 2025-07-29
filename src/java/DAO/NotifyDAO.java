/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import java.sql.Connection;
import java.sql.*;
import java.util.Date;

/**
 *
 * @author PCASUS
 */
public class NotifyDAO extends DBContext {
    private Connection connection;

    public NotifyDAO() {
        try {
            this.connection = getConnection();
        } catch (Exception e) {
            System.out.println("Connect failed");
        }
    }
    
    
    public void createNotification(int staffId, int appointmentId, Timestamp scheduledAt, int serviceId) {

        String sql = "INSERT INTO Notify (UserId, Title, Message, IsRead, CreatedAt) VALUES (?, ?, ?, 0, ?)";


        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, staffId);

            stmt.setString(2, "New Appointment Assignment");

            stmt.setString(3, "You have been assigned to an appointment (ID: " + appointmentId + 
                    ") on " + scheduledAt + " for service ID " + serviceId);

            stmt.setTimestamp(4, new Timestamp(System.currentTimeMillis()));


            stmt.executeUpdate();


        } catch (Exception e) {

            e.printStackTrace();

        }
}

}
