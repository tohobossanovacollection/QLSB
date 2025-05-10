package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Booking;
import model.Customer;
import model.MonthlyBooking;
import model.Pitch;
import service.BookingService;
import service.MonthlyBookingService;
import utils.DateTimeUtils;
import view.BookingView;


public class BookingController {
    private BookingService bookingService;
    private BookingView bookingView;
    private MonthlyBookingService monthlyBookingService;
    public BookingController(BookingView bookingView) {
        this.bookingService = new BookingService();
        this.bookingView = bookingView;
        this.monthlyBookingService = new MonthlyBookingService();
    }
    public void processNewBooking() {
        // Get booking info from view
        Booking bookingData = bookingView.getBookingData();
        //boolean isAvailable = bookingService.checkConflict(bookingData.getPitchId(),bookingData.getDate(),bookingData.getStartTime(),bookingData.getEndTime());
        if(bookingView.getHoursBetween()<0){
            bookingView.displayError("thoi gian dat khong hop le");
            return;
        }
        // if (isAvailable) {
            
        // }      
        // else bookingView.displayError("unavailable pitch!");
        if (bookingView.isPeriodic()) {
            processMonthlyBooking(bookingData);
        } else {
            processRegularBooking(bookingData);
        }
    }
    private void processRegularBooking(Booking bookingData){
        System.out.println("bookingData: "+ bookingData);
        boolean isAvailable = bookingService.checkConflict(bookingData.getPitchId(),bookingData.getDate(),bookingData.getStartTime(),bookingData.getEndTime());
        if(isAvailable){
            try {
                // Validate and save booking
                bookingService.addBooking(bookingData);
                bookingView.displaySucess();
        }
         catch (Exception e) {
            bookingView.displayError("Error processing booking: " + e.getMessage());
        }
    }
        else bookingView.displayError("unavailable pitch!");
    }

    private void processMonthlyBooking(Booking bookingdata){
        if(bookingView.getDaysBetween() < 7 ){
            bookingView.displayError("thoi gian dat theo dinh ky phai tren 1 tuan");
            return;
        }
        MonthlyBooking monthlyBookingData = bookingView.getMonthlyBookingData();
        boolean isAvailable = true;

        //checkconflict monhtlynooking dinh nhap
        List<Map<String,Object>> data = new ArrayList<>();
        //check tung ngay 1 trong khoang thoi gian dinh ky cua monthlybooking
        List<LocalDate> dates =  DateTimeUtils.getMatchingDays(monthlyBookingData.getStartDate(), 
                            monthlyBookingData.getEndDate(), monthlyBookingData.getDaysOfWeek());
        
        for(LocalDate date : dates){
            //check conflict true = ko co conflict
            if(!bookingService.checkConflict(bookingdata.getPitchId(),DateTimeUtils.parseDate(date.toString()),
                bookingdata.getStartTime(),bookingdata.getEndTime(),data)){
               isAvailable = false;
            }
            Map<String,Object> obj = new HashMap<>();
            obj.put("pitchId", bookingdata.getPitchId());
            obj.put("date", date.toString());
            obj.put("startTime", bookingdata.getStartTime());
            obj.put("endTime", bookingdata.getEndTime());
            data.add(obj);//check xong se add vao data cua database
        }
        if(isAvailable){
        try {
            //luu monthly booking xong roi lay id cua monthlybooking vua luu de tao monthlybooking
            System.out.println("monthlyBookingData: "+ monthlyBookingData);
            bookingdata.setDate(null);
            bookingService.addBooking(bookingdata);
            //lay id cua monthlybooking vua luu de tao monthlybooking
            bookingdata = bookingService.getAllBookings().get(bookingService.getAllBookings().size()-1);
            monthlyBookingData.setId(bookingdata.getId());
            System.out.println(monthlyBookingData);
            if (monthlyBookingService.addMonthlyBooking(monthlyBookingData)) {
                bookingView.displaySucess();       
            } else {
                bookingView.displayError("loi dat monthy booking");
            }
        } catch (Exception e) {
            bookingView.displayError("Error processing booking: " + e.getMessage());
        }
    }
    else bookingView.displayError("unavailable pitch!");
}

    /*public void displayAllBookings() {
        try {
            List<Booking> bookings = bookingService.getAllBookings();
            bookingView.displayBookingList(bookings);
        } catch (Exception e) {
            bookingView.displayError("Error retrieving bookings: " + e.getMessage());
        }
    }*/
    
    /*public void searchBookingsByDate(LocalDate date) {
        try {
            List<Booking> bookings = bookingService.getBookingsByDate(date);
            bookingView.displayBookingList(bookings);
        } catch (Exception e) {
            bookingView.displayError("Error searching bookings: " + e.getMessage());
        }
    }*/
    
    /*public void searchBookingsByCustomer(Customer customer) {
        try {
            List<Booking> bookings = bookingService.getBookingsByCustomer(customer.getId());
            bookingView.displayBookingList(bookings);
        } catch (Exception e) {
            bookingView.displayError("Error searching bookings: " + e.getMessage());
        }
    }*/
    
    /*public void cancelBooking() {
        int bookingId = bookingView.getBookingIdForCancellation();
        
        try {
            boolean canceled = bookingService.cancelBooking(bookingId);
            if (canceled) {
                bookingView.displayCancellationSuccess();
            } else {
                bookingView.displayError("Booking could not be canceled");
            }
        } catch (Exception e) {
            bookingView.displayError("Error canceling booking: " + e.getMessage());
        }
    }*/
}