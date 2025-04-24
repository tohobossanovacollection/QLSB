package DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import DAO.BookingDAO;
import model.Booking;

public class BookingDAOImpl implements BookingDAO {
    // Implement the methods defined in BookingDAO interface
    @Override
    public List<Booking> findByPitch(int pitchId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE pitchId = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySB");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pitchId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> findByCustomer(int customerId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE customerId = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySB");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> findByDate(LocalDate date) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE DATE(startTime) = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySB");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, date.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> findByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE DATE(startTime) BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySB");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, startDate.toString());
            stmt.setString(2, endDate.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public List<Booking> findByPitchAndDateRange(int pitchId, LocalDateTime start, LocalDateTime end) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE pitchId = ? AND startTime >= ? AND endTime <= ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySB");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, pitchId);
            stmt.setString(2, start.toString());
            stmt.setString(3, end.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public boolean checkConflict(Booking booking) {
        String sql = "SELECT COUNT(*) FROM bookings WHERE " +
        "pitchId = ? AND ((startTime < ? AND endTime > ?) OR (startTime < ? AND endTime > ?))";
        try (Connection conn = DatabaseConnector.connect("QuanLySB");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, booking.getPitchId());
            stmt.setString(2, booking.getEndTime().toString());
            stmt.setString(3, booking.getStartTime().toString());
            stmt.setString(4, booking.getEndTime().toString());
            stmt.setString(5, booking.getStartTime().toString());

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    @Override
    public Booking findById(int id) {
        String sql = "SELECT * FROM bookings WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySB");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToBooking(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Booking> findAll() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (Connection conn = DatabaseConnector.connect("QuanLySB");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                bookings.add(mapResultSetToBooking(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    @Override
    public boolean save(Booking booking) {
        String sql = "INSERT INTO bookings (pitchId, customerId, startTime, endTime, totalPrice, status, isPeriodic) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.connect("QuanLySB");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, booking.getPitchId());
            stmt.setInt(2, booking.getCustomerId());
            stmt.setString(3, booking.getStartTime().toString());
            stmt.setString(4, booking.getEndTime().toString());
            stmt.setDouble(5, booking.getTotalPrice());
            stmt.setString(6, booking.getStatus());
            stmt.setBoolean(7, booking.isPeriodic());
            //stmt.setString(8, booking.getPeriodicType());
            //stmt.setString(9, booking.getNote());
            //stmt.setString(10, booking.getCreatedAt().toString());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Booking booking) {
        String sql = "UPDATE bookings SET pitchId = ?, customerId = ?, startTime = ?, endTime = ?, totalPrice = ?, status = ?, isPeriodic = ? WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySB");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, booking.getPitchId());
            stmt.setInt(2, booking.getCustomerId());
            stmt.setString(3, booking.getStartTime().toString());
            stmt.setString(4, booking.getEndTime().toString());
            stmt.setDouble(5, booking.getTotalPrice());
            stmt.setString(6, booking.getStatus());
            stmt.setBoolean(7, booking.isPeriodic());
            //
            //stmt.setString(8, booking.getPeriodicType());
            //stmt.setString(9, booking.getNote());
            stmt.setInt(10, booking.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM bookings WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySB");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Booking mapResultSetToBooking(ResultSet rs) throws SQLException {
        return new Booking(
                rs.getInt("id"),
                rs.getInt("pitchId"),
                rs.getInt("customerId"),
                LocalDateTime.parse(rs.getString("startTime")),
                LocalDateTime.parse(rs.getString("endTime")),
                rs.getDouble("totalPrice"),
                rs.getString("status"),
                rs.getBoolean("isPeriodic")
                //rs.getString("periodicType"),
                //rs.getString("note")
        );
    }
}
