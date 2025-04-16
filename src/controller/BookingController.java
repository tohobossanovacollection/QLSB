package controller;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import model.Booking;
import model.Customer;
import model.Pitch;
import service.BookingService;
import view.BookingView;

public class BookingController {
    private BookingService bookingService;
    private BookingView bookingView;
    
    public BookingController(BookingService bookingService, BookingView bookingView) {
        this.bookingService = bookingService;
        this.bookingView = bookingView;
    }
    
    public void processNewBooking() {
        // Get booking info from view
        Booking bookingData = bookingView.getBookingData();
        
        try {
            // Validate and save booking
            //boolean isAvailable = bookingService.checkConflict(bookingData);
                
            
            
            if (bookingService.addBooking(bookingData)) {
                //Booking savedBooking = ;
                bookingView.displayBookingSuccess(bookingData);
            } else {
                bookingView.displayBookingUnavailable();
            }
        } catch (Exception e) {
            bookingView.displayError("Error processing booking: " + e.getMessage());
        }
    }
    
    public void displayAllBookings() {
        try {
            List<Booking> bookings = bookingService.getAllBookings();
            bookingView.displayBookingList(bookings);
        } catch (Exception e) {
            bookingView.displayError("Error retrieving bookings: " + e.getMessage());
        }
    }
    
    public void searchBookingsByDate(LocalDate date) {
        try {
            List<Booking> bookings = bookingService.getBookingsByDate(date);
            bookingView.displayBookingList(bookings);
        } catch (Exception e) {
            bookingView.displayError("Error searching bookings: " + e.getMessage());
        }
    }
    
    public void searchBookingsByCustomer(Customer customer) {
        try {
            List<Booking> bookings = bookingService.getBookingsByCustomer(customer.getId());
            bookingView.displayBookingList(bookings);
        } catch (Exception e) {
            bookingView.displayError("Error searching bookings: " + e.getMessage());
        }
    }
    
    public void cancelBooking() {
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
    }
}