package DAO;

import Model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public UserDAO() {
        try {
            // Initialize the connection in the constructor
            conn = new DBContext().getConnection();
        } catch (Exception ex) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<User> getFilteredStaff(String fullName, String email, String phone, int role, String gender, Boolean isDeleted, int pageNumber, int pageSize) {
        List<User> filteredUserList = new ArrayList<>();
        String query = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY ID) AS RowNum FROM [User] WHERE 1=1";
        // Add filter conditions
        if (fullName != null && !fullName.isEmpty()) {
            query += " AND Fullname LIKE '%" + fullName + "%'";
        }
        if (email != null && !email.isEmpty()) {
            query += " AND Email LIKE '%" + email + "%'";
        }
        if (phone != null && !phone.isEmpty()) {
            query += " AND Phone LIKE '%" + phone + "%'";
        }

        query += " AND RoleId = " + role;

        if (gender != null && !gender.isEmpty()) {
            query += " AND Gender = '" + gender + "'";
        }
        if (isDeleted != null) {
            query += "AND IsDeleted = " + (isDeleted ? "1" : "0");
        }
        // Add pagination
        query += ") AS SubQuery WHERE RowNum BETWEEN ? AND ?";
        int startIndex = (pageNumber - 1) * pageSize + 1;
        int endIndex = pageNumber * pageSize;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, startIndex);
            ps.setInt(2, endIndex);
            rs = ps.executeQuery();
            while (rs.next()) {
                User staff = new User();
                staff.setId(rs.getInt("ID"));
                staff.setEmail(rs.getString("Email"));
                staff.setPassword(rs.getString("Password"));
                staff.setFullname(rs.getString("Fullname"));
                staff.setGender(rs.getString("Gender"));
                staff.setAddress(rs.getString("Address"));
                staff.setPhone(rs.getString("Phone"));
                staff.setRoleId(rs.getInt("RoleId"));
                staff.setIsDeleted(rs.getBoolean("IsDeleted"));
                staff.setCreatedAt(rs.getDate("CreatedAt"));
                staff.setCreatedBy(rs.getInt("CreatedBy"));
                staff.setAvatar(rs.getString("Avatar"));
                filteredUserList.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredUserList;
    }

    public List<User> getFilteredStaff(String fullName, String email, int role, String gender, Boolean isDeleted) {
        List<User> filteredUserList = new ArrayList<>();
        String query = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY ID) AS RowNum FROM [User] WHERE RoleID not in (1,4) AND 1=1";
        // Add filter conditions
        if (fullName != null && !fullName.isEmpty()) {
            query += " AND Fullname LIKE '%" + fullName + "%'";
        }
        if (email != null && !email.isEmpty()) {
            query += " AND Email LIKE '%" + email + "%'";
        }
        if (role != -1) {
            query += " AND RoleID = " + role;
        }
        if (gender != null && !gender.isEmpty()) {
            query += " AND Gender LIKE '%" + gender + "%'";
        }
        if (isDeleted != null) {
            query += "AND IsDeleted = " + (isDeleted ? "1" : "0");
        }
        // Add pagination
        query += ") AS SubQuery";
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                User staff = new User();
                staff.setId(rs.getInt("ID"));
                staff.setEmail(rs.getString("Email"));
                staff.setPassword(rs.getString("Password"));
                staff.setFullname(rs.getString("Fullname"));
                staff.setGender(rs.getString("Gender"));
                staff.setAddress(rs.getString("Address"));
                staff.setPhone(rs.getString("Phone"));
                staff.setRoleId(rs.getInt("RoleId"));
                staff.setIsDeleted(rs.getBoolean("IsDeleted"));
                staff.setCreatedAt(rs.getDate("CreatedAt"));
                staff.setCreatedBy(rs.getInt("CreatedBy"));
                staff.setAvatar(rs.getString("Avatar"));
                filteredUserList.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredUserList;
    }

    // Create (Register)
    public boolean registerUser(User user) {
        String query = "INSERT INTO [User] (Email, Password, Fullname, Gender, Address, Phone, CreatedBy, Avatar, RoleId, IsDeleted) VALUES (?, ?, ?, ?, ?, ?, ?, ?, 1, 0)";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullname());
            ps.setString(4, user.getGender());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getPhone());
            ps.setInt(7, user.getCreatedBy());
            ps.setString(8, user.getAvatar());
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return false;
    }

    // Read (Get User by Id)
    public User getUserById(int id) {
        String query = "SELECT * FROM [User] WHERE ID = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setFullname(rs.getString("Fullname"));
                user.setGender(rs.getString("Gender"));
                user.setAddress(rs.getString("Address"));
                user.setPhone(rs.getString("Phone"));
                user.setIsDeleted(rs.getBoolean("IsDeleted"));
                user.setCreatedAt(rs.getDate("CreatedAt"));
                user.setCreatedBy(rs.getInt("CreatedBy"));
                user.setAvatar(rs.getString("Avatar"));
                user.setChangeHistory(rs.getString("ChangeHistory"));
                return user;
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return null;
    }

    public User getStaffById(int id) {
        String query = "SELECT * FROM [User] WHERE ID = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setFullname(rs.getString("Fullname"));
                user.setGender(rs.getString("Gender"));
                user.setAddress(rs.getString("Address"));
                user.setPhone(rs.getString("Phone"));
                user.setIsDeleted(rs.getBoolean("IsDeleted"));
                user.setCreatedAt(rs.getDate("CreatedAt"));
                user.setCreatedBy(rs.getInt("CreatedBy"));
                user.setAvatar(rs.getString("Avatar"));
                user.setChangeHistory(rs.getString("ChangeHistory"));
                user.setRoleId(rs.getInt("RoleId"));
                return user;
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return null;
    }

    // Read (Get User by Email)
    public User getUserByEmail(String email) {
        String query = "SELECT * FROM [User] WHERE Email = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setFullname(rs.getString("Fullname"));
                user.setGender(rs.getString("Gender"));
                user.setAddress(rs.getString("Address"));
                user.setPhone(rs.getString("Phone"));
                user.setIsDeleted(rs.getBoolean("IsDeleted"));
                user.setCreatedAt(rs.getDate("CreatedAt"));
                user.setCreatedBy(rs.getInt("CreatedBy"));
                user.setAvatar(rs.getString("Avatar"));
                user.setChangeHistory(rs.getString("ChangeHistory"));
                return user;
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return null;
    }

    // Get all users
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM [User]";
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setFullname(rs.getString("Fullname"));
                user.setGender(rs.getString("Gender"));
                user.setAddress(rs.getString("Address"));
                user.setPhone(rs.getString("Phone"));
                user.setIsDeleted(rs.getBoolean("IsDeleted"));
                user.setCreatedAt(rs.getDate("CreatedAt"));
                user.setCreatedBy(rs.getInt("CreatedBy"));
                user.setAvatar(rs.getString("Avatar"));
                user.setChangeHistory(rs.getString("ChangeHistory"));
                userList.add(user);
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return userList;
    }
    
    public List<User> getAllCustomers() {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM [User] where roleid not in (2, 4)";
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setFullname(rs.getString("Fullname"));
                user.setGender(rs.getString("Gender"));
                user.setAddress(rs.getString("Address"));
                user.setPhone(rs.getString("Phone"));
                user.setIsDeleted(rs.getBoolean("IsDeleted"));
                user.setCreatedAt(rs.getDate("CreatedAt"));
                user.setCreatedBy(rs.getInt("CreatedBy"));
                user.setAvatar(rs.getString("Avatar"));
                user.setChangeHistory(rs.getString("ChangeHistory"));
                userList.add(user);
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return userList;
    }

    // Get all users with pagination
    public List<User> getAllUsers(int pageNumber, int pageSize) {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY ID) AS RowNum, * FROM [User]) AS SubQuery WHERE RowNum BETWEEN ? AND ?";
        int startIndex = (pageNumber - 1) * pageSize + 1;
        int endIndex = pageNumber * pageSize;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, startIndex);
            ps.setInt(2, endIndex);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setFullname(rs.getString("Fullname"));
                user.setGender(rs.getString("Gender"));
                user.setAddress(rs.getString("Address"));
                user.setPhone(rs.getString("Phone"));
                user.setIsDeleted(rs.getBoolean("IsDeleted"));
                user.setCreatedAt(rs.getDate("CreatedAt"));
                user.setCreatedBy(rs.getInt("CreatedBy"));
                user.setAvatar(rs.getString("Avatar"));
                user.setChangeHistory(rs.getString("ChangeHistory"));
                user.setRoleId(rs.getInt("RoleId"));
                userList.add(user);
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return userList;
    }

    public List<User> getAllPagination(int pageNumber, int pageSize) {
        List<User> userList = new ArrayList<>();
        String query = "SELECT * FROM (SELECT ROW_NUMBER() OVER (ORDER BY UserID) AS RowNum, * FROM User) AS SubQuery WHERE RowNum BETWEEN ? AND ?";
        int startIndex = (pageNumber - 1) * pageSize + 1;
        int endIndex = pageNumber * pageSize;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, startIndex);
            ps.setInt(2, endIndex);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setFullname(rs.getString("Fullname"));
                user.setGender(rs.getString("Gender"));
                user.setAddress(rs.getString("Address"));
                user.setPhone(rs.getString("Phone"));
                user.setIsDeleted(rs.getBoolean("IsDeleted"));
                user.setCreatedAt(rs.getDate("CreatedAt"));
                user.setCreatedBy(rs.getInt("CreatedBy"));
                user.setAvatar(rs.getString("Avatar"));
                user.setChangeHistory(rs.getString("ChangeHistory"));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public List<User> getFilteredUsers(String fullName, String email, String phone, String gender, Boolean status, int pageNumber, int pageSize) {
        List<User> filteredUserList = new ArrayList<>();
        String query = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY ID) AS RowNum FROM [User] WHERE 1=1";
        // Add filter conditions
        if (fullName != null && !fullName.isEmpty()) {
            query += " AND Fullname LIKE '%" + fullName + "%'";
        }
        if (email != null && !email.isEmpty()) {
            query += " AND Email LIKE '%" + email + "%'";
        }
        if (phone != null && !phone.isEmpty()) {
            query += " AND Phone LIKE '%" + phone + "%'";
        }
        if (gender != null && !gender.isEmpty()) {
            query += " AND Gender = '" + gender + "'";
        }
        if (status != null) {
            query += " AND IsDeleted = '" + status.toString() + "'";
        }
        // Add pagination
        query += ") AS SubQuery WHERE RowNum BETWEEN ? AND ?";
        System.out.println(query);
        int startIndex = (pageNumber - 1) * pageSize + 1;
        int endIndex = pageNumber * pageSize;
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, startIndex);
            ps.setInt(2, endIndex);
            rs = ps.executeQuery();
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setFullname(rs.getString("Fullname"));
                user.setGender(rs.getString("Gender"));
                user.setAddress(rs.getString("Address"));
                user.setPhone(rs.getString("Phone"));
                user.setIsDeleted(rs.getBoolean("IsDeleted"));
                user.setCreatedAt(rs.getDate("CreatedAt"));
                user.setCreatedBy(rs.getInt("CreatedBy"));
                user.setAvatar(rs.getString("Avatar"));
                user.setChangeHistory(rs.getString("ChangeHistory"));
                filteredUserList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredUserList;
    }

    public List<User> getFilteredUsers(String fullName, String email, String gender, Boolean status) {
        List<User> filteredUserList = new ArrayList<>();
        String query = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY ID) AS RowNum FROM [User] WHERE 1=1";
        // Add filter conditions
        if (fullName != null && !fullName.isEmpty()) {
            query += " AND Fullname LIKE '%" + fullName + "%'";
        }
        if (email != null && !email.isEmpty()) {
            query += " AND Email LIKE '%" + email + "%'";
        }
        if (gender != null && !gender.isEmpty()) {
            query += " AND Gender = '" + gender + "'";
        }
        if (status != null) {
            query += " AND IsDeleted = '" + status.toString() + "'";
        }
        // Add pagination
        query += ") AS SubQuery";
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                User staff = new User();
                staff.setId(rs.getInt("ID"));
                staff.setEmail(rs.getString("Email"));
                staff.setPassword(rs.getString("Password"));
                staff.setFullname(rs.getString("Fullname"));
                staff.setGender(rs.getString("Gender"));
                staff.setAddress(rs.getString("Address"));
                staff.setPhone(rs.getString("Phone"));
                staff.setIsDeleted(rs.getBoolean("IsDeleted"));
                staff.setCreatedAt(rs.getDate("CreatedAt"));
                staff.setCreatedBy(rs.getInt("CreatedBy"));
                staff.setAvatar(rs.getString("Avatar"));
                staff.setChangeHistory(rs.getString("ChangeHistory"));
                filteredUserList.add(staff);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return filteredUserList;
    }

    // Update (Update User)
    public boolean updateUser(User user) {
        String query = "UPDATE [User] SET Email=?, Password=?, Fullname=?, Gender=?, Address=?, Phone=?, IsDeleted=?, CreatedBy=?, Avatar=?, ChangeHistory=? WHERE ID=?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullname());
            ps.setString(4, user.getGender());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getPhone());
            ps.setBoolean(7, user.isIsDeleted());
            ps.setInt(8, user.getCreatedBy());
            ps.setString(9, user.getAvatar());
            ps.setString(10, user.getChangeHistory());
            ps.setInt(11, user.getId());
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return false;
    }

    public boolean updateStaff(User user) {
        String query = "UPDATE [User] SET Email=?, Password=?, Fullname=?, Gender=?, Address=?, Phone=?, IsDeleted=?, CreatedBy=?, Avatar=?, ChangeHistory=?, RoleId = ? WHERE ID=?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getPassword());
            ps.setString(3, user.getFullname());
            ps.setString(4, user.getGender());
            ps.setString(5, user.getAddress());
            ps.setString(6, user.getPhone());
            ps.setBoolean(7, user.isIsDeleted());
            ps.setInt(8, user.getCreatedBy());
            ps.setString(9, user.getAvatar());
            ps.setString(10, user.getChangeHistory());
            ps.setInt(11, user.getRoleId());
            ps.setInt(12, user.getId());
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return false;
    }

    // Delete (Delete User)
    public boolean deleteUser(int userID) {
        String query = "DELETE FROM [User] WHERE ID=?";
        try {
            ps = conn.prepareStatement(query);
            ps.setInt(1, userID);
            int result = ps.executeUpdate();
            return result > 0;
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return false;
    }

    // Login
    public User loginUser(String email, String password) {
        String query = "SELECT * FROM [User] WHERE Email = ? AND Password = ?";
        try {
            ps = conn.prepareStatement(query);
            ps.setString(1, email);
            ps.setString(2, password);
            rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setFullname(rs.getString("Fullname"));
                user.setGender(rs.getString("Gender"));
                user.setAddress(rs.getString("Address"));
                user.setPhone(rs.getString("Phone"));
                user.setIsDeleted(rs.getBoolean("IsDeleted"));
                user.setRoleId(rs.getInt("RoleId"));
                user.setCreatedAt(rs.getDate("CreatedAt"));
                user.setCreatedBy(rs.getInt("CreatedBy"));
                user.setAvatar(rs.getString("Avatar"));
                user.setChangeHistory(rs.getString("ChangeHistory"));
                return user;
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            closeResources();
        }
        return null;
    }

    public List<User> getUsersBySpaServiceId(int spaServiceId) {
        List<User> users = new ArrayList<>();
        String query = "SELECT u.Id, u.Email, u.Password, u.Fullname, u.Gender, u.Address, u.Phone, "
                + "u.IsDeleted, u.CreatedAt, u.CreatedBy, u.Avatar, u.ChangeHistory, u.RoleId "
                + "FROM [dbo].[User] u "
                + "JOIN [Spa].[dbo].[SpaServiceStaff] sss ON u.Id = sss.UserId "
                + "WHERE sss.SpaServiceId = ? AND u.IsDeleted = 0";

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, spaServiceId);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("Id"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setFullname(rs.getString("Fullname"));
                user.setGender(rs.getString("Gender"));
                user.setAddress(rs.getString("Address"));
                user.setPhone(rs.getString("Phone"));
                user.setIsDeleted(rs.getBoolean("IsDeleted"));
                user.setCreatedAt(rs.getTimestamp("CreatedAt"));
                user.setCreatedBy(rs.getInt("CreatedBy"));
                user.setAvatar(rs.getString("Avatar"));
                user.setChangeHistory(rs.getString("ChangeHistory"));
                user.setRoleId(rs.getInt("RoleId"));

                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exception properly
        }

        return users;
    }

    public int getTotalUsers() {
        String query = "SELECT COUNT(*) AS total FROM [User]";
        int total = 0;
        try {
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            if (rs.next()) {
                total = rs.getInt("total");
            }
        } catch (SQLException e) {
            Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, "Error in getTotalUsers", e);
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                // Do not close conn here since it's initialized in constructor and reused
            } catch (SQLException e) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, "Error closing resources", e);
            }
        }
        return total;
    }

    
    

    public List<User> getStaffList() {
        List<User> staff = new ArrayList<>();
        String sql = "SELECT ID, Fullname FROM [User] WHERE RoleId = (SELECT ID FROM Role WHERE Name = 'Chuyên viên tr? li?u')";

        try (PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("ID"));
                user.setFullname(rs.getString("Fullname"));
                staff.add(user);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staff;

    }

    public boolean updateLoyaltyPoints(int userId, int newPoints) {
        String sql = "UPDATE [User] SET loyaltyPoints = loyaltyPoints + ? WHERE id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, newPoints);
            stmt.setInt(2, userId);
            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace(); // hoặc logging
            return false;
        }


    }

   

    private void closeResources() {

    }
}
