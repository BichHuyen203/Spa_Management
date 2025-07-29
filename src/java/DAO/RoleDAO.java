/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Model.Role;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Legion
 */
public class RoleDAO extends DBContext {

    private Connection connection;

    public RoleDAO() {
        try {
            this.connection = getConnection();
        } catch (Exception e) {
            System.out.println("Connect failed");
        }
    }

    public boolean addRole(Role role) {
        String sql = "INSERT INTO Role (Name, IsDeleted, CreatedAt) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, role.getName());
            stmt.setBoolean(2, role.isIsDeleted());
            stmt.setTimestamp(3, role.getCreatedAt());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Role getRoleById(int id) {
        String sql = "SELECT ID, Name, IsDeleted, CreatedAt FROM Role WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("ID"));
                role.setName(rs.getString("Name"));
                role.setIsDeleted(rs.getBoolean("IsDeleted"));
                role.setCreatedAt(rs.getTimestamp("CreatedAt"));
                return role;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Role> getAllRoles() {
        List<Role> roles = new ArrayList<>();
        String sql = "SELECT ID, Name, IsDeleted, CreatedAt FROM Role";

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Role role = new Role();
                role.setId(rs.getInt("ID"));
                role.setName(rs.getString("Name"));
                role.setIsDeleted(rs.getBoolean("IsDeleted"));
                role.setCreatedAt(rs.getTimestamp("CreatedAt"));
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return roles;
    }

    public boolean updateRole(Role role) {
        String sql = "UPDATE Role SET Name = ?, IsDeleted = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, role.getName());
            stmt.setBoolean(2, role.isIsDeleted());
            stmt.setInt(3, role.getId());

            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean softDeleteRole(int id) {
        String sql = "UPDATE Role SET IsDeleted = 1 WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
