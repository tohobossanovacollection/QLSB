package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class MonthlyBooking {
    private int id;
    //private int customerId;
    //private int pitchId;
    private LocalDate startDate;
    private LocalDate endDate;
    //private LocalTime startTime;
    //private LocalTime endTime;
    private List<String> daysOfWeek; // e.g., "MONDAY", "WEDNESDAY", "FRIDAY"
    //private int sessionsPerMonth;
    //private double pricePerSession;
    //private double totalAmount;
    private double discount;
    //private double finalAmount;
    //private String status; // ACTIVE, INACTIVE, COMPLETED
    //private String note;

    public MonthlyBooking(int id, LocalDate startDate, LocalDate endDate,
                          List<String> daysOfWeek,
                         double discount) {
        this.id = id;
        //this.customerId = customerId;
        //this.pitchId = pitchId;
        this.startDate = startDate;
        this.endDate = endDate;
        //this.startTime = startTime;
        //this.endTime = endTime;
        this.daysOfWeek = daysOfWeek;
        //this.sessionsPerMonth = sessionsPerMonth;
        //this.pricePerSession = pricePerSession;
        //this.totalAmount = sessionsPerMonth * pricePerSession;
        //this.discount = discount;
        //this.finalAmount = totalAmount - discount;
        //this.status = "ACTIVE";
        //this.note = note;
    }
    
    // Getters và Setters
    public int getId() {
        return id;
    }

    /*public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getPitchId() {
        return pitchId;
    }

    public void setPitchId(int pitchId) {
        this.pitchId = pitchId;
    }*/

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    /*public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }*/

    public List<String> getDaysOfWeek() {
        return daysOfWeek;
    }

    public void setDaysOfWeek(List<String> daysOfWeek) {
        this.daysOfWeek = daysOfWeek;
    }

    /*public int getSessionsPerMonth() {
        return sessionsPerMonth;
    }*/

    /*public void setSessionsPerMonth(int sessionsPerMonth) {
        this.sessionsPerMonth = sessionsPerMonth;
        this.totalAmount = sessionsPerMonth * pricePerSession;
        this.finalAmount = totalAmount - discount;
    }*/

    /*public double getPricePerSession() {
        return pricePerSession;
    }

    public void setPricePerSession(double pricePerSession) {
        this.pricePerSession = pricePerSession;
        this.totalAmount = sessionsPerMonth * pricePerSession;
        this.finalAmount = totalAmount - discount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }*/

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
        //this.finalAmount = totalAmount - discount;
    }

    /*public double getFinalAmount() {
        return finalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }*/

    // Phương thức tạo các lịch đặt sân từ đơn tháng
    public List<Booking> generateBookings() {
        List<Booking> bookings = new ArrayList<>();
        // TODO : Logic để tạo các booking cụ thể từ đơn tháng
        // (triển khai theo logic của từng ngày trong tuần, startDate, endDate)
        return bookings;
    }
}