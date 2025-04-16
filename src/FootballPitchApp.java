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
        // Initialize Services
        PitchService pitchService = new PitchService();
        CustomerService customerService = new CustomerService();
        BookingService bookingService = new BookingService();
        UserService userService = new UserService();

        // Initialize Views
        MainView mainView = new MainView();
        BookingView bookingView = new BookingView(pitchService, customerService);

        // Initialize Controllers
         PitchController pitchController = new PitchController(pitchService, new PitchView());
         CustomerController customerController = new CustomerController(customerService, new CustomerView());
         BookingController bookingController = new BookingController(bookingService, bookingView);

        // Initialize Main Controller
        MainController mainController = new MainController(
                mainView,
                userService,
                bookingController,
                pitchController,
                customerController
        );

        // Start the application
        mainController.start();

        // Close resources
        mainView.close();
    }
}
