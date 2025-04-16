package controller;

import model.User;
import service.UserService;
import view.MainView;

public class MainController {
    private MainView mainView;
    private UserService userService;
    private BookingController bookingController;
    private PitchController pitchController;
    private CustomerController customerController;
    private User currentUser;
    
    public MainController(
            MainView mainView, 
            UserService userService,
            BookingController bookingController,
            PitchController pitchController,
            CustomerController customerController) {
        this.mainView = mainView;
        this.userService = userService;
        this.bookingController = bookingController;
        this.pitchController = pitchController;
        this.customerController = customerController;
    }
    
    public void start() {
        boolean running = true;
        
        // Authentication
        if (!authenticate()) {
            mainView.displayMessage("Authentication failed. Exiting system.");
            return;
        }
        
        while (running) {
            int choice = mainView.displayMainMenu();
            
            switch (choice) {
                case 1: // Pitch Management
                    handlePitchManagement();
                    break;
                case 2: // Booking Management
                    handleBookingManagement();
                    break;
                case 3: // Customer Management
                    handleCustomerManagement();
                    break;
                case 0: // Exit
                    running = false;
                    mainView.displayMessage("Thank you for using the Football Pitch Management System!");
                    break;
                default:
                    mainView.displayMessage("Invalid option, please try again.");
            }
        }
    }
    
    private boolean authenticate() {
        String username = mainView.getUsername();
        String password = mainView.getPassword();
        
        try {
            if(userService.authenticate(username, password)) {
                currentUser = userService.getUserByUsername(username);
                mainView.displayMessage("Welcome," +currentUser.getFullName()+ " !");
                return true;
            } else {
                currentUser = null;
                mainView.displayMessage("Invalid username or password.");
                return false;
            }
            
        } catch (Exception e) {
            mainView.displayMessage("Authentication error: " + e.getMessage());
            return false;
        }
    }
    
    private void handlePitchManagement() {
        boolean pitchMenuRunning = true;
        
        while (pitchMenuRunning) {
            int choice = mainView.displayPitchMenu();
            
            switch (choice) {
                case 1: // View all pitches
                    pitchController.displayAllPitches();
                    break;
                case 2: // Add new pitch
                    pitchController.processNewPitch();
                    break;
                case 3: // Update pitch
                    pitchController.updatePitch();
                    break;
                case 4: // Delete pitch
                    pitchController.deletePitch();
                    break;
                case 5: // View available pitches
                    pitchController.displayAvailablePitches();
                    break;
                case 0: // Return to main menu
                    pitchMenuRunning = false;
                    break;
                default:
                    mainView.displayMessage("Invalid option, please try again.");
            }
        }
    }
    
    private void handleBookingManagement() {
        boolean bookingMenuRunning = true;
        
        while (bookingMenuRunning) {
            int choice = mainView.displayBookingMenu();
            
            switch (choice) {
                case 1: // Create new booking
                    bookingController.processNewBooking();
                    break;
                case 2: // View all bookings
                    bookingController.displayAllBookings();
                    break;
                case 3: // Search bookings by date
                    // Date input would be handled by BookingView
                    bookingController.searchBookingsByDate(mainView.getDateForSearch().toLocalDate());
                    break;
                case 4: // Search bookings by customer
                    // Customer selection would be handled by views
                    bookingController.searchBookingsByCustomer(customerController.selectCustomer());
                    break;
                case 5: // Cancel booking
                    bookingController.cancelBooking();
                    break;
                case 0: // Return to main menu
                    bookingMenuRunning = false;
                    break;
                default:
                    mainView.displayMessage("Invalid option, please try again.");
            }
        }
    }
    
    private void handleCustomerManagement() {
        boolean customerMenuRunning = true;
        
        while (customerMenuRunning) {
            int choice = mainView.displayCustomerMenu();
            
            switch (choice) {
                case 1: // View all customers
                    customerController.displayAllCustomers();
                    break;
                case 2: // Add new customer
                    customerController.processNewCustomer();
                    break;
                case 3: // Update customer
                    customerController.updateCustomer();
                    break;
                case 4: // Search customer by phone
                    customerController.searchCustomerByPhone();
                    break;
                case 0: // Return to main menu
                    customerMenuRunning = false;
                    break;
                default:
                    mainView.displayMessage("Invalid option, please try again.");
            }
        }
    }
}