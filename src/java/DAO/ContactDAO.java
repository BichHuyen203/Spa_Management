/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Contact;
import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 *
 * @author Admin
 */
public class ContactDAO  extends DBContext{
     private Connection connection;

    public ContactDAO() {
        try {
            this.connection = getConnection();
        } catch (Exception e) {
            System.out.println("Connect failed");
        }
    }

    public void save(Contact contact) {
        String sql = "INSERT INTO Contact (Name, Email,PhoneNumber, Subject, Message, CreatedAt) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, contact.getName());
            stmt.setString(2, contact.getEmail());
            stmt.setString(3, contact.getPhoneNumber());
            stmt.setString(4, contact.getSubject());
            stmt.setString(5, contact.getMessage());
            stmt.setTimestamp(6, contact.getCreatedAt());
            
            stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
