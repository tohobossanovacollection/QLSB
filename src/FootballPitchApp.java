

import controller.BookingController;
import controller.CustomerController;
import controller.MainController;
import controller.PitchController;
import service.BookingService;
import service.CustomerService;
import service.PitchService;
import service.UserService;
import view.BookingView;
import view.CustomerView;
import view.MainView;
import view.PitchView;

public class FootballPitchApp {
    public static void main(String[] args) {
        // Initialize DAOs
        
        
        // Initialize Services
        PitchService pitchService = new PitchService();
        CustomerService customerService = new CustomerService();
        BookingService bookingService = new BookingService();
        UserService userService = new UserService();
        
        // Initialize Views
        MainView mainView = new MainView();
        PitchView pitchView = new PitchView();
        CustomerView customerView = new CustomerView();
        BookingView bookingView = new BookingView(pitchService, customerService);
        
        // Initialize Controllers
        PitchController pitchController = new PitchController(pitchService, pitchView);
        CustomerController customerController = new CustomerController(customerService, customerView);
        BookingController bookingController = new BookingController(bookingService, bookingView);
        
        // Initialize Main Controller
        MainController mainController = new MainController(


                mainView, 
                userService, 
                bookingController, 
                pitchController, 
                customerController);
        
        // Start the application
        mainController.start();
        
        // Close resources
        mainView.close();
    }
}