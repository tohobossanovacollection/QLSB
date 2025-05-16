package service;

import DAO.BookingDAO;
import DAO.impl.BookingDAOImpl;
import model.Booking;
import utils.DateTimeUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class BookingService {
    private BookingDAO bookingDAO;
    private MonthlyBookingService monthlyBookingService;
    public BookingService() {
        this.bookingDAO = new BookingDAOImpl();
        this.monthlyBookingService = new MonthlyBookingService();
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
    
    public List<Booking> getBookingsByDate(LocalDateTime date) {
        return bookingDAO.findByDate(date);
    }
    
    public List<Booking> getBookingsByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return bookingDAO.findByDateRange(startDate, endDate);
    }
    
    public List<Booking> getBookingsByPitchAndDateRange(int PitchId, LocalDateTime startDate, LocalDateTime endDate) {
        return bookingDAO.findByPitchAndDateRange(PitchId, startDate, endDate);
    }

    //return true neu ko co conflict
    public boolean checkConflict(int pitchId, LocalDateTime date, LocalDateTime startTime, LocalDateTime endTime) {
        // Only check bookings with the same pitch and same date
        List<Map<String, Object>> filterDateAndPitchId = getAllBookingsMap().stream()
            .filter(booking -> (int) booking.get("pitchId") == pitchId
                && booking.get("date").equals(DateTimeUtils.formatDate(date)))
            .collect(Collectors.toList());

        // If no bookings found, no conflict
        if (filterDateAndPitchId.isEmpty()) {
            return true;
        }

        // Check for time overlap
        for (Map<String, Object> booking : filterDateAndPitchId) {
            LocalDateTime existingStart = (LocalDateTime) booking.get("startTime");
            LocalDateTime existingEnd = (LocalDateTime) booking.get("endTime");
            // Only compare time part
            if (existingStart.toLocalTime().isBefore(endTime.toLocalTime()) &&
                startTime.toLocalTime().isBefore(existingEnd.toLocalTime())) {
                return false; // Conflict found
            }
        }
        return true; // No conflict
    }
    //return true neu ko co conflict
    public boolean checkConflict(int pitchId, LocalDateTime date, LocalDateTime startTime, LocalDateTime endTime,List<Map<String,Object>> data) {
        // Only check bookings with the same pitch and same date
        //add data vua nhap vao voi data cua databases
        List<Map<String, Object>> metadata = getAllBookingsMap();
        for(Map<String,Object> bookingmap : data){
            metadata.add(bookingmap);
        }

        List<Map<String, Object>> filterDateAndPitchId = metadata.stream()
            .filter(booking -> (int) booking.get("pitchId") == pitchId
                && booking.get("date").equals(DateTimeUtils.formatDate(date)))
            .collect(Collectors.toList());
        // If no bookings found, no conflict
        if (filterDateAndPitchId.isEmpty()) {
            return true;

        }

        // Check for time overlap
        for (Map<String, Object> booking : filterDateAndPitchId) {
            LocalDateTime existingStart = (LocalDateTime) booking.get("startTime");
            LocalDateTime existingEnd = (LocalDateTime) booking.get("endTime");
            // Only compare time part
            if (existingStart.toLocalTime().isBefore(endTime.toLocalTime()) &&
                startTime.toLocalTime().isBefore(existingEnd.toLocalTime())) {
                return false; // Conflict found
            }
        }
        return true; // No conflict
    }
    
    public boolean addBooking(Booking booking) {
        return bookingDAO.save(booking);
    }
    
    
    public boolean updateBooking(Booking booking) {
        // Kiểm tra xung đột trước khi cập nhật
        if (checkConflict(booking.getPitchId(),booking.getDate(),booking.getStartTime(), booking.getEndTime())) {
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

    public List<Map<String, Object>> getAllBookingsMap() {
        List<Map<String, Object>> result = new ArrayList<>();
        Map<String,Integer> data = monthlyBookingService.getAllDateMonthlyBoookings();
        List<Booking> bookings = getAllBookings();
        //Map<String, Object> obj = new HashMap<>();
        for(Booking booking : bookings){
            if(booking.getDate() == null){
                for(Map.Entry<String, Integer> entry : data.entrySet()) {
                    if(booking.getId() == entry.getValue()){
                    //String[] days = entry.getKey().split(",");
                Map<String, Object> obj = new HashMap<>();
                obj.put("id", booking.getId());
                obj.put("pitchId", booking.getPitchId());
                obj.put("date",entry.getKey());
                
                obj.put("startTime", booking.getStartTime());
                obj.put("endTime", booking.getEndTime());
                // Thêm các trường khác nếu cần
                result.add(obj);}
            }
        }
        else {
                Map<String, Object> obj = new HashMap<>();
                obj.put("id", booking.getId());
                obj.put("pitchId", booking.getPitchId());
                obj.put("date", DateTimeUtils.formatDate(booking.getDate()));
                obj.put("startTime", booking.getStartTime());
                obj.put("endTime", booking.getEndTime());
                // Thêm các trường khác nếu cần
                result.add(obj);
            }
    }
    return result;
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
