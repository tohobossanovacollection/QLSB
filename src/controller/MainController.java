package controller;

import model.User;
import service.UserService;
import view.MainView;
import view.LoginView;
import DAO.UserDAO;
import DAO.impl.UserDAOImpl;

public class MainController {
    private MainView mainView;
    private LoginView loginView;
    private UserDAOImpl userDAO = new
        UserDAOImpl();
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
    public MainController(MainView mainView,LoginView loginView) {
        this.mainView = mainView;
        this.loginView = loginView;
        this.userDAO = new UserDAOImpl();
    }
    
    public void start() {
        loginView.setVisible(true);
        loginView.setLoginAction(e -> {
            if (authenticate()) {
                // If authentication is successful, show the main view
                UserDAO userDAO = new UserDAOImpl();
                User currentUser = userDAO.findByUsername(loginView.getUsername());
                loginView.setVisible(false); 
                mainView.setVisible(true);
                loginView.showWelcomeMessage(currentUser.getRole());
            } else {
                // Show an error message if authentication fails
                loginView.showError("Tên đăng nhập hoặc mật khẩu không đúng.");
            }
        });
        // Method intentionally left empty

    }
    
    private boolean authenticate() {
        // Method intentionally left empty
        //this.userDAO = new UserDAOImpl();
        String username = loginView.getUsername();
        String password = loginView.getPassword();
        
        return userDAO.authenticate(username, password);
    }
    
    private void handlePitchManagement() {
        // Method intentionally left empty
    }
    
    private void handleBookingManagement() {
        // Method intentionally left empty
    }
    
    private void handleCustomerManagement() {
        // Method intentionally left empty
    }
}
