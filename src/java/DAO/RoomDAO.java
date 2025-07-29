/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Room;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Legion
 */
public class RoomDAO extends DBContext {

    private Connection connection;

    public RoomDAO() {
        try {
            this.connection = getConnection();
        } catch (Exception e) {
            System.out.println("Connect failed");
        }
    }

    public List<Room> getRoomsBySpaServiceId(int spaServiceId) {
        List<Room> rooms = new ArrayList<>();
        String query = "SELECT r.Id, r.Name, r.IsAvailable "
                + "FROM [dbo].[Rooms] r "
                + "JOIN [dbo].[SpaServiceRoom] ssr ON r.Id = ssr.RoomId "
                + "WHERE ssr.SpaServiceId = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, spaServiceId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                Room room = new Room();
                room.setId(rs.getInt("Id"));
                room.setName(rs.getString("Name"));
                room.setAvailable(rs.getBoolean("IsAvailable"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception as needed
        }

        return rooms;
    }
    
    
    public Room getRoomId(int roomId) {
        String query = "SELECT r.Id, r.Name, r.IsAvailable "
                + "FROM [dbo].[Rooms] r  where r.Id = ?";
        Room room = new Room();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, roomId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                room.setId(rs.getInt("Id"));
                room.setName(rs.getString("Name"));
                room.setAvailable(rs.getBoolean("IsAvailable"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception as needed
        }

        return room;
    }
    
    
    
    public List<Room> getAllRooms() {
        String query = "SELECT r.Id, r.Name, r.IsAvailable "
                + "FROM [dbo].[Rooms] r";
        List<Room> rooms = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                        Room room = new Room();

                room.setId(rs.getInt("Id"));
                room.setName(rs.getString("Name"));
                room.setAvailable(rs.getBoolean("IsAvailable"));
                rooms.add(room);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception as needed
        }

        return rooms;
    }
    

}
