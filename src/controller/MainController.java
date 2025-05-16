package controller;

import model.Booking;
import model.User;
import service.UserService;
import view.*;

import javax.swing.JOptionPane;

import DAO.UserDAO;
import DAO.impl.UserDAOImpl;

public class MainController {
    private MainView mainView;
    private LoginView loginView;
    private UserDAOImpl userDAO = new UserDAOImpl();
    private UserService userService;
    private BookingController bookingController;
    private PitchController pitchController;
    private CustomerController customerController;
    private User currentUser;
    private BookingView bookingView = new BookingView();
    private BookingListView bookingListView = new BookingListView();
    private CustomerView customerView = new CustomerView();
    private CustomerListView customerListView = new CustomerListView();
    private ManageFieldsView manageFieldsView = new ManageFieldsView();
    
    // public MainController(
    //         MainView mainView, 
    //         UserService userService,
    //         BookingController bookingController,
    //         PitchController pitchController,
    //         CustomerController customerController) {
    //     this.mainView = mainView;
    //     this.userService = userService;
    //     this.bookingController = bookingController;
    //     this.pitchController = pitchController;
    //     this.customerController = customerController;
    // }
    public MainController(MainView mainView,LoginView loginView) {
        this.mainView = mainView;
        this.loginView = loginView;
        this.userDAO = new UserDAOImpl();
        //this.bookingController = new BookingController();
        //this.customerController = new CustomerController();
    }
    
    public void start() {
        loginView.setVisible(true);
        loginView.setLoginAction(e -> {
            if (authenticate()) {
                // If authentication is successful, show the main view
                UserDAO userDAO = new UserDAOImpl();
                User currentUser = userDAO.findByUsername(loginView.getUsername());
                loginView.setVisible(false); 
                loadpanel(mainView,currentUser.getRole());
                mainView.setVisible(true);
                
                loginView.showWelcomeMessage(currentUser.getRole());
                handleBookingManagement();
                handleCustomerManagement();
                handlePitchManagement();
            } else {
                // Show an error message if authentication fails
                loginView.showError("Tên đăng nhập hoặc mật khẩu không đúng.");
            }
        });
        mainView.setBookingListAction(e -> {
            mainView.addPanel(new BookingListView(),"bklist");
            mainView.showPanel("bklist");
        });
    }

    //khoi tai panel va khoi tao controler ung voi tung view
    private void loadpanel(MainView mainView,String role) {
        if(role.equals("ADMIN")){
            mainView.addPanel(bookingView, "bookingview");
            mainView.addPanel(bookingListView, "bklist");
            mainView.addPanel(customerView, "customerView");
            mainView.addPanel(customerListView, "customerListView");
            mainView.addPanel(manageFieldsView, "manageFieldsView");
            mainView.addPanel(bookingView, "bookingview");
            mainView.addPanel(bookingListView, "bklist");
            mainView.setBookingAction(e->{
                mainView.showPanel("bookingview");
            });
            mainView.setBookingListAction(e->{
                mainView.showPanel("bklist");
            });
            mainView.setCustomerAction(e->{
                mainView.showPanel("customerView");
            });
            mainView.setCustomerListAction(e->{
                mainView.showPanel("customerListView");
            });
            mainView.setManageFieldsAction(e->{
                mainView.showPanel("manageFieldsView");
            });
        }
        
        
            
        
        
        this.customerController = new CustomerController(customerView,customerListView);
        this.bookingController = new BookingController(bookingView);
    }
    private boolean authenticate() {

        //this.userDAO = new UserDAOImpl();
        String username = loginView.getUsername();
        String password = loginView.getPassword();
        
        return userDAO.authenticate(username, password);
    }

    private void handlePitchManagement() {
        manageFieldsView.setAddAction(()->{
            bookingView.setData(manageFieldsView.getSelectedBooking());
            mainView.showPanel("bookingview");
        });
    }

    private void handleBookingManagement() {
        bookingView.setAddCustomerAction(e->{
            mainView.showPanel("customerView");
        });
        
        bookingView.setSaveAction(e->{
            bookingController.processNewBooking();
        });
    }
    

    private void handleCustomerManagement() {

        customerView.setCancelAction(e->{
            mainView.showPanel("bookingview");
        });
        customerView.setSaveAction(e->{
            customerController.processNewCustomer();
        });        
        customerListView.setAddAction(e->{
            mainView.showPanel("customerView");
        });
        customerListView.setEditAction(e->{            
            int selectedIndex = customerListView.getSelectedCustomerIndex();
            if (selectedIndex == -1) {
                customerListView.showError("Vui lòng chọn một khách hàng để chỉnh sửa.");
                return;
            } 
            else{
                customerController.displayUpdateCustomer();
                customerListView.showDialog(true);                
            }
        });
        customerListView.setSaveEditAction(e->{
            customerController.processUpdateCustomer();
        });
        customerListView.setCancelEditAction(e->{
            customerListView.showDialog(false);
        });
    customerListView.setDeleteAction(e->{
        customerController.processDeleteCustomer();
    });
    customerListView.setRefreshAction(e->{
        customerListView.loadCustomerList();
    });
    }
}
