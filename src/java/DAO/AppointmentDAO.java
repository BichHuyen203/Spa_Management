package DAO;

import Model.Appointment;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class AppointmentDAO extends DBContext {

    private Connection connection;

    public AppointmentDAO() {
        try {
            this.connection = getConnection();
        } catch (Exception e) {
            System.out.println("Connect failed");
        }
    }

    public int insertAppointment(Appointment appointment) {
        String query = "INSERT INTO [Spa].[dbo].[Appointments] "
                + "(UserId, ServiceId, StaffId, RoomId, ScheduledAt, Status, CreatedAt) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, appointment.getUserId());
            statement.setInt(2, appointment.getServiceId());
            statement.setInt(3, appointment.getStaffId());
            statement.setInt(4, appointment.getRoomId());
            statement.setTimestamp(5, new java.sql.Timestamp(appointment.getScheduledAt().getTime()));
            statement.setString(6, appointment.getStatus());
            statement.setTimestamp(7, new java.sql.Timestamp(appointment.getCreatedAt().getTime()));

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // return the generated appointment ID
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return -1; // or 0 if you prefer that as a default fail value
    }

    public boolean updateAppointmentStatus(int appointmentId, String status) {
        String query = "UPDATE [Spa].[dbo].[Appointments] SET Status = ? WHERE Id = ?";

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, status);
            statement.setInt(2, appointmentId);

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean updateScheduledAt(int appointmentId, Date scheduledAt) {
        String sql = "UPDATE Appointments SET scheduledAt = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setTimestamp(1, new java.sql.Timestamp(scheduledAt.getTime()));
            stmt.setInt(2, appointmentId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Appointment> getAppointmentsByUserId(int userId, String status, int page) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT * FROM Appointments WHERE userId = ? ";

        if (status != null) {
            sql += " AND status = ? ";
        }

        sql += " ORDER BY scheduledAt DESC OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            int offset = (page - 1) * 5;

            stmt.setInt(1, userId);
            if (status != null) {
                stmt.setString(2, status);
                stmt.setInt(3, offset);

            } else {
                stmt.setInt(2, offset);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setId(rs.getInt("id"));
                    appointment.setUserId(rs.getInt("userId"));
                    appointment.setServiceId(rs.getInt("serviceId"));
                    appointment.setStaffId(rs.getInt("staffId"));
                    appointment.setRoomId(rs.getInt("roomId"));
                    appointment.setScheduledAt(rs.getTimestamp("scheduledAt"));
                    appointment.setStatus(rs.getString("status"));
                    appointment.setCreatedAt(rs.getTimestamp("createdAt"));

                    appointments.add(appointment);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }
    
    public int countAppointments(
            Integer staffId,
            Integer roomId,
            Date scheduledFrom,
            Date scheduledTo
    ) {
        int total = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM appointments WHERE 1=1");

        if (staffId != null) {
            sql.append(" AND staffId = ?");
        }
        if (roomId != null) {
            sql.append(" AND roomId = ?");
        }
        if (scheduledFrom != null) {
            sql.append(" AND scheduledAt >= ?");
        }
        if (scheduledTo != null) {
            sql.append(" AND scheduledAt <= ?");
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (staffId != null) {
                stmt.setInt(paramIndex++, staffId);
            }
            if (roomId != null) {
                stmt.setInt(paramIndex++, roomId);
            }
            if (scheduledFrom != null) {
                stmt.setDate(paramIndex++, new java.sql.Date(scheduledFrom.getTime()));
            }
            if (scheduledTo != null) {
                stmt.setDate(paramIndex++, new java.sql.Date(scheduledTo.getTime()));
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    total = rs.getInt(1);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return total;
    }

    public int countAppointmentsByUserId(int userId, String status) {
        int count = 0;
        String sql = "SELECT COUNT(*) FROM Appointments WHERE userId = ?";
        if (status != null && !status.isEmpty()) {
            sql += " AND status = ?";
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            if (status != null && !status.isEmpty()) {
                stmt.setString(2, status);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    count = rs.getInt(1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return count;
    }
    
     public boolean updateStatus(int appointmentId, String status) {
        String sql = "UPDATE Appointments SET status = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, status);
            stmt.setInt(2, appointmentId);

            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Appointment> getAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT a.Id, a.UserId, a.ServiceId, a.StaffId, a.RoomId, a.ScheduledAt, a.Status, u.Fullname "
                + "FROM Appointments a LEFT JOIN [User] u ON a.UserId = u.ID";

        try (PreparedStatement stmt = connection.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Appointment app = new Appointment();
                app.setId(rs.getInt("Id"));
                app.setUserId(rs.getInt("UserId"));
                app.setServiceId(rs.getInt("ServiceId"));
                app.setStaffId(rs.getInt("StaffId"));
                app.setRoomId(rs.getInt("RoomId"));
                app.setScheduledAt(rs.getTimestamp("ScheduledAt"));
                app.setStatus(rs.getString("Status"));
                appointments.add(app);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return appointments;
    }

    public boolean updateAppointment(int appointmentId, int staffId, Timestamp newStart, int serviceId) {
        String sql = "UPDATE Appointments SET StaffId = ?, ScheduledAt = ?, ServiceId = ? WHERE Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, staffId);
            stmt.setTimestamp(2, newStart);
            stmt.setInt(3, serviceId);
            stmt.setInt(4, appointmentId);
            int rows = stmt.executeUpdate();
            if (rows > 0) {
//                NotifyDAO.createNotification(staffId, appointmentId, newStart, serviceId);
                NotifyDAO notifyDAO = new NotifyDAO();
                notifyDAO.createNotification(staffId, appointmentId, newStart, serviceId);
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Appointment getAppointmentById(int appointmentId) {
        Appointment appointment = null;
        String sql = "SELECT * FROM Appointments WHERE Id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, appointmentId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    appointment = new Appointment();
                    appointment.setId(rs.getInt("Id"));
                    appointment.setUserId(rs.getInt("UserId"));
                    appointment.setServiceId(rs.getInt("ServiceId"));
                    appointment.setStaffId(rs.getInt("StaffId"));
                    appointment.setRoomId(rs.getInt("RoomId"));
                    appointment.setScheduledAt(rs.getTimestamp("ScheduledAt"));
                    appointment.setStatus(rs.getString("Status"));
                    appointment.setCreatedAt(rs.getTimestamp("CreatedAt"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointment;
    }

    public List<Appointment> getAppointments(
            Integer staffId,
            Integer roomId,
            Date scheduledFrom,
            Date scheduledTo,
            int page,
            int pageSize
    ) {
        List<Appointment> appointments = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM appointments WHERE 1=1");

        if (staffId != null) {
            sql.append(" AND staffId = ?");
        }
        if (roomId != null) {
            sql.append(" AND roomId = ?");
        }
        if (scheduledFrom != null) {
            sql.append(" AND scheduledAt >= ?");
        }
        if (scheduledTo != null) {
            sql.append(" AND scheduledAt <= ?");
        }

        sql.append(" ORDER BY id DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (staffId != null) {
                stmt.setInt(paramIndex++, staffId);
            }
            if (roomId != null) {
                stmt.setInt(paramIndex++, roomId);
            }
            if (scheduledFrom != null) {
                stmt.setDate(paramIndex++, new java.sql.Date(scheduledFrom.getTime()));
            }
            if (scheduledTo != null) {
                stmt.setDate(paramIndex++, new java.sql.Date(scheduledTo.getTime()));
            }

            int offset = (page - 1) * pageSize;
            stmt.setInt(paramIndex++, offset);
            stmt.setInt(paramIndex, pageSize);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Appointment appointment = new Appointment();
                    appointment.setId(rs.getInt("id"));
                    appointment.setUserId(rs.getInt("userId"));
                    appointment.setServiceId(rs.getInt("serviceId"));
                    appointment.setStaffId(rs.getInt("staffId"));
                    appointment.setRoomId(rs.getInt("roomId"));
                    appointment.setScheduledAt(rs.getTimestamp("scheduledAt"));
                    appointment.setStatus(rs.getString("status"));
                    appointment.setCreatedAt(rs.getTimestamp("createdAt"));

                    appointments.add(appointment);
                }

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }


    public boolean updateAppointment(Appointment appointment) {
        String sql = "UPDATE Appointments SET StaffId = ?, ScheduledAt = ?, ServiceId = ?, RoomId = ?, Status = ? WHERE Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, appointment.getStaffId());
            stmt.setTimestamp(2, new Timestamp(appointment.getScheduledAt().getTime()));
            stmt.setInt(3, appointment.getServiceId());
            stmt.setInt(4, appointment.getRoomId());
            stmt.setString(5, appointment.getStatus());
            stmt.setInt(6, appointment.getId());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
        
    }



    public boolean addAppointment(Appointment appointment) {
        String sql = "INSERT INTO appointments (userid, serviceid, staffid, roomid, scheduledat, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, appointment.getUserId());
            stmt.setInt(2, appointment.getServiceId());
            stmt.setInt(3, appointment.getStaffId());
            stmt.setInt(4, appointment.getRoomId());
            stmt.setTimestamp(5, new Timestamp(appointment.getScheduledAt().getTime()));
            stmt.setString(6, appointment.getStatus());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public List<Appointment> getStaffAppointments(int id) {
        List<Appointment> appointments = new ArrayList<>();
        String sql = "SELECT a.Id, a.UserId, a.ServiceId, a.StaffId, a.RoomId, a.ScheduledAt, a.Status, u.Fullname " +
                     "FROM Appointments a LEFT JOIN [User] u ON a.UserId = u.ID WHERE a.StaffId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Appointment app = new Appointment();
                    app.setId(rs.getInt("Id"));
                    app.setUserId(rs.getInt("UserId"));
                    app.setServiceId(rs.getInt("ServiceId"));
                    app.setStaffId(rs.getInt("StaffId"));
                    app.setRoomId(rs.getInt("RoomId"));
                    app.setScheduledAt(rs.getTimestamp("ScheduledAt"));
                    app.setStatus(rs.getString("Status"));
                    appointments.add(app);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        return appointments;
    }

        
    

}
