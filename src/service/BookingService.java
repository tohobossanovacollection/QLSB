package service;

import DAO.BookingDAO;
import DAO.impl.BookingDAOImpl;
import model.Booking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class BookingService {
    private BookingDAO bookingDAO;
    
    public BookingService() {
        this.bookingDAO = new BookingDAOImpl();
    }
    
    public Booking getBookingById(int id) {
        return bookingDAO.findById(id);
    }
    
    public List<Booking> getAllBookings() {
        return bookingDAO.findAll();
    }
    
    public List<Booking> getBookingsByPitch(int pitchId) {
        return bookingDAO.findByPitch(pitchId);
    }
    
    public List<Booking> getBookingsByCustomer(int customerId) {
        return bookingDAO.findByCustomer(customerId);
    }
    
    public List<Booking> getBookingsByDate(LocalDate date) {
        return bookingDAO.findByDate(date);
    }
    
    public List<Booking> getBookingsByDateRange(LocalDate startDate, LocalDate endDate) {
        return bookingDAO.findByDateRange(startDate, endDate);
    }
    
    public List<Booking> getBookingsByPitchAndDateRange(int PitchId, LocalDateTime startDate, LocalDateTime endDate) {
        return bookingDAO.findByPitchAndDateRange(PitchId, startDate, endDate);
    }
    public boolean checkConflict(LocalDateTime startTime, LocalDateTime endTime) {
        return bookingDAO.checkConflict(startTime, endTime);
    }
    
    public boolean addBooking(Booking booking) {
        // Kiểm tra xung đột trước khi thêm
        if (checkConflict(booking.getStartTime(), booking.getEndTime())) {
            return false;
        }
        return bookingDAO.save(booking);
    }
    
    public boolean updateBooking(Booking booking) {
        // Kiểm tra xung đột trước khi cập nhật
        if (checkConflict(booking.getStartTime(), booking.getEndTime())) {
            return false;
        }
        return bookingDAO.update(booking);
    }
    
    public boolean deleteBooking(int id) {
        return bookingDAO.delete(id);
    }
    
    public boolean cancelBooking(int id) {
        Booking booking = getBookingById(id);
        if (booking != null) {
            booking.setStatus("CANCELLED");
            return bookingDAO.delete(id);
        }
        return false;
    }

    // Phương thức tạo các booking định kỳ
    public boolean createPeriodicBookings(Booking template, String periodicType, int weeks) {
        boolean success = true;
        LocalDateTime currentStart = template.getStartTime();
        LocalDateTime currentEnd = template.getEndTime();
        
        // Thêm booking gốc
        success = addBooking(template);
        
        // Thêm các booking lặp lại
        /*for (int i = 1; i <= weeks; i++) {
            Booking newBooking;
            if ("WEEKLY".equals(periodicType)) {
                // Thêm 7 ngày cho mỗi booking hàng tuần
                currentStart = currentStart.plusDays(7);
                currentEnd = currentEnd.plusDays(7);
                
                newBooking = new Booking(
                    0, // ID sẽ được tạo trong DB
                    template.getPitchId(),
                    template.getCustomerId(),
                    currentStart,
                    currentEnd,
                    template.getTotalPrice(),
                    "CONFIRMED",
                    true,
                    periodicType,
                    "Periodic booking from template ID: " + template.getId()
                );
                
                if (!addBooking(newBooking)) {
                    success = false;
                }
            }
        }*/
        
        return success;
    }
}
