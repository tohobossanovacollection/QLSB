package service;

// Service cho Quản lý đơn tháng

import DAO.MonthlyBookingDAO;
import DAO.impl.MonthlyBookingDAOImpl;
import model.MonthlyBooking;
import utils.DateTimeUtils;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MonthlyBookingService {
    private MonthlyBookingDAO monthlyBookingDAO;
    //private MonthlyBookingService monthlyBookingService;
    
    public MonthlyBookingService() {
        this.monthlyBookingDAO = new MonthlyBookingDAOImpl();
        //this.monthlyBookingService = new MonthlyBookingService();
    }
    
    public MonthlyBooking getMonthlyBookingById(int id) {
        return monthlyBookingDAO.findById(id);
    }
    
    public List<MonthlyBooking> getAllMonthlyBookings() {
        return monthlyBookingDAO.findAll();
    }
    
    public List<MonthlyBooking> getMonthlyBookingsByCustomer(int customerId) {
        return monthlyBookingDAO.findByCustomer(customerId);
    }
    
    public List<MonthlyBooking> getMonthlyBookingsByPitch(int pitchId) {
        return monthlyBookingDAO.findByPitch(pitchId);
    }
    
    public List<MonthlyBooking> getMonthlyBookingsByStatus(String status) {
        return monthlyBookingDAO.findByStatus(status);
    }
    
    public List<MonthlyBooking> getMonthlyBookingsByMonth(int month, int year) {
        return monthlyBookingDAO.findByMonth(month, year);
    }
    
    public boolean addMonthlyBooking(MonthlyBooking monthlyBooking) {
        boolean saved = monthlyBookingDAO.save(monthlyBooking);
        
        if (saved) {
            // Tạo các booking cụ thể từ đơn tháng
           // generateBookingsFromMonthly(monthlyBooking);
        }
        
        return saved;
    }
    
    public boolean updateMonthlyBooking(MonthlyBooking monthlyBooking) {
        // Cập nhật đơn tháng
        boolean updated = monthlyBookingDAO.update(monthlyBooking);
        
        if (updated) {
            // Có thể cần xóa các booking cũ và tạo lại booking mới
            // (tùy thuộc vào yêu cầu nghiệp vụ)
        }
        
        return updated;
    }
    
    public boolean deleteMonthlyBooking(int id) {
        // Có thể cần xóa các booking liên quan
        return monthlyBookingDAO.delete(id);
    }
    
    // Phương thức tạo rs qua các đơn tháng
    public Map<String,Integer> getAllDateMonthlyBoookings(){
        Map<String,Integer> data = new HashMap<>();
        List<MonthlyBooking> monthlyBookings = getAllMonthlyBookings();
        for(MonthlyBooking monthlyBooking : monthlyBookings){
            for(LocalDate day : DateTimeUtils.getMatchingDays(monthlyBooking.getStartDate(), monthlyBooking.getEndDate(), monthlyBooking.getDaysOfWeek())){
                data.put(day.toString(), monthlyBooking.getId());
            }      
        }
        return data;
    }

    

    /* 
    private void generateBookingsFromMonthly(MonthlyBooking monthlyBooking) {
        LocalDate currentDate = monthlyBooking.getStartDate();
        LocalDate endDate = monthlyBooking.getEndDate();
        
        List<DayOfWeek> daysOfWeek = new ArrayList<>();
        for (String dayName : monthlyBooking.getDaysOfWeek()) {
            daysOfWeek.add(DayOfWeek.valueOf(dayName));
        }
        
        while (!currentDate.isAfter(endDate)) {
            if (daysOfWeek.contains(currentDate.getDayOfWeek())) {
                // Tạo booking cho ngày này
                LocalDateTime startDateTime = LocalDateTime.of(currentDate, monthlyBooking.getStartTime());
                LocalDateTime endDateTime = LocalDateTime.of(currentDate, monthlyBooking.getEndTime());
                
                Booking booking = new Booking(
                    // ID sẽ được tạo trong DB
                    startDateTime,
                    endDateTime,
                    //monthlyBooking.getPricePerSession(),
                    "CONFIRMED",
                    true
                    //"MONTHLY",
                    //"From monthly booking ID: " + monthlyBooking.getId()
                );
                
                bookingService.addBooking(booking);
            }
            
            // Chuyển sang ngày tiếp theo
            currentDate = currentDate.plusDays(1);
        }
    }*/
}

