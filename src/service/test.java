package service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import model.MonthlyBooking;
import utils.DateTimeUtils;

public class test {
    public static void main(String[] args) {
        MonthlyBookingService monthlyBookingService = new MonthlyBookingService();
       //System.out.println(monthlyBookingService.getAllDateMonthlyBoookings());
       BookingService bookingService = new BookingService();
       System.out.println(bookingService.checkConflict(1, LocalDateTime.of(2026, 5, 14, 10, 0), LocalDateTime.of(2025, 5, 10, 10, 0), LocalDateTime.of(2025, 5, 10, 11, 0)));
       for(var booking : bookingService.getAllBookingsMap()){
        System.out.println(booking);
       }
    //    for(Map<String, Object> booking : bookingService.getAllBookingsMaptest()){
    //     System.out.println(booking);
    //    }
    }
}
