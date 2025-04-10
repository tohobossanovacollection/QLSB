package DAO.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import DAO.BookingDAO;
import model.Booking;
public class BookingDAOImpl implements BookingDAO {
    // Implement the methods defined in BookingDAO interface
    @Override
    public List<Booking> findByPitch(int pitchId) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Booking> findByCustomer(int customerId) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Booking> findByDate(LocalDate date) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Booking> findByDateRange(LocalDate startDate, LocalDate endDate) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Booking> findByPitchAndDateRange(int pitchId, LocalDateTime start, LocalDateTime end) {
        // Implementation code here
        return null;
    }

    @Override
    public boolean checkConflict(Booking booking) {
        // Implementation code here
        return false;
    }
    
    @Override
    public Booking findById(int id) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Booking> findAll() {
        // Implementation code here
        return null;
    }

    @Override
    public boolean save(Booking entity) {
        // Implementation code here
        return false;
    }

    @Override
    public boolean update(Booking entity) {
        // Implementation code here
        return false;
    }

    @Override
    public boolean delete(int id) {
        // Implementation code here
        return false;
    }
}
