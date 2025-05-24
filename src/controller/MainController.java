package controller;

import model.User;
import view.*;


import javax.swing.JOptionPane;

import DAO.UserDAO;
import DAO.impl.UserDAOImpl;

public class MainController {
    private MainView mainView;
    private LoginView loginView;
    private UserDAOImpl userDAO = new UserDAOImpl();
    private BookingController bookingController;
    private CustomerController customerController;
    private UserController userController;
    private User currentUser;
    private BookingView bookingView = new BookingView();
    private BookingListView bookingListView = new BookingListView();
    private CustomerView customerView = new CustomerView();
    private CustomerListView customerListView = new CustomerListView();
    private ManageFieldsView manageFieldsView = new ManageFieldsView();
    private UserListView userListView = new UserListView();
    private BranchListView branchListView = new BranchListView();
    private SettingView settingView = new SettingView();
    private PitchListView pitchListView = new PitchListView();
    private BranchController branchController;
    private PitchController pitchController;
    
    public MainController(MainView mainView,LoginView loginView) {
        this.mainView = mainView;
        this.loginView = loginView;
        this.userDAO = new UserDAOImpl();
    }
    public MainController(){
        this.loginView = new LoginView();
        this.userDAO = new UserDAOImpl();
    }
    
    
    public void start() {
        loginView.setVisible(true);
        loginView.setLoginAction(e -> {
            if (authenticate()) {
                // If authentication is successful, show the main view
                UserDAO userDAO = new UserDAOImpl();
                currentUser = userDAO.findByUsername(loginView.getUsername());
                loginView.setVisible(false); 
                this.mainView = new MainView(currentUser.getRole());
                loadpanel(mainView);
                mainView.setVisible(true);
                
                loginView.showWelcomeMessage(currentUser.getRole());
                handleBookingManagement();
                handleCustomerManagement();
                handlePitchManagement();
                handleUserManagement();
                handleBranchManagement();
                handleSetting();
            } else {
                // Show an error message if authentication fails
                loginView.showError("Tên đăng nhập hoặc mật khẩu không đúng.");
            }
        });
    }

    //khoi tai panel va khoi tao controler ung voi tung view
    private void loadpanel(MainView mainView) {
        
            mainView.addPanel(bookingView, "bookingview");
            mainView.addPanel(bookingListView, "bklist");
            mainView.addPanel(customerView, "customerView");
            mainView.addPanel(customerListView, "customerListView");
            mainView.addPanel(manageFieldsView, "manageFieldsView");
            mainView.addPanel(bookingView, "bookingview");
            mainView.addPanel(bookingListView, "bklist");
            mainView.addPanel(userListView, "userview");
            mainView.addPanel(branchListView, "branchListView");
            mainView.addPanel(settingView, "settingview");
            mainView.addPanel(pitchListView,"pitchListView");
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
            mainView.setUserAction(e->{
                mainView.showPanel("userview");
            });
            mainView.setBranchAction(e->{
                mainView.showPanel("branchListView");       
            });
            mainView.setSettingsAction(e->{
                mainView.showPanel("settingview"); 
            });
            mainView.setFieldStatusAction(e->{
                mainView.showPanel("pitchListView");
            });
        
        
        this.customerController = new CustomerController(customerView,customerListView);
        this.bookingController = new BookingController(bookingView);
        this.userController = new UserController(userListView,settingView);
        this.branchController = new BranchController(branchListView);
        this.pitchController = new PitchController(pitchListView,manageFieldsView);
    }

    private boolean authenticate() {

        //this.userDAO = new UserDAOImpl();
        String username = loginView.getUsername();
        String password = loginView.getPassword();
        
        return userDAO.authenticate(username, password);
    }

    private void handlePitchManagement() {
        pitchController.loadDataForManageFieldsView();
        manageFieldsView.setAddAction(()->{
            bookingView.setData(manageFieldsView.getSelectedBooking());
            mainView.showPanel("bookingview");
        });
        manageFieldsView.setComboBoxAction(e->{
            System.out.println("cbbox");
            pitchController.reloadTimeslotTable();
        });
        manageFieldsView.setCalendarAction(evt->{
            System.out.println("calendar");
            pitchController.reloadTimeslotTable();
        }); 
    }

    private void handleBookingManagement() {
        bookingController.loadData();
        bookingView.setAddCustomerAction(e->{
            mainView.showPanel("customerView");
        });
        
        bookingView.setSaveAction(e->{
            bookingController.processNewBooking();
        });
    }
    

    private void handleCustomerManagement() {
        customerController.loadCustomerList();
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
        customerController.loadCustomerList();
    });
    }

    private void handleUserManagement() {
        userController.loadcbdata();
        userController.loadData();
        userListView.setCBaction(e->{
            userController.loadData();
        });
        userListView.setEditAction(e->{
            userController.processEditUser();
        });
        userListView.setSaveEditAction(e->{
            userController.processUpdateUser();
            userListView.showDialog(false);
        });
        userListView.setCancelAction(e->{
            userListView.showDialog(false);
        });
        userListView.setRefreshAction(e->{
            userController.loadData();
        });
        userListView.setDeleteAction(e->{
            userController.processDeleteCustomer();
        });
        userListView.setAddUserAction(e->{
            userListView.initdialogforadding();
            userListView.showDialog(true);
        });
        userListView.setSaveAddAction(e->{
            userController.processAddUser();
            userListView.showDialog(false);
        });      
    }   
    private void handleBranchManagement() {
        branchController.loadData();
        branchListView.setAddBranchAction(e->{
            branchListView.initdialogforadding();
            branchListView.showDialog(true);
        });
        branchListView.setEditAction(e->{
            branchController.processEditBranch();
        });
        branchListView.setSaveEditAction(e->{
            branchController.processUpdateBranch();
            branchController.loadData();
            branchListView.showDialog(false);
        });
        branchListView.setCancelEditAction(e->{
            branchListView.showDialog(false);
        });
        branchListView.setDeleteAction(e->{
            branchController.processDeleteBranch();
            branchController.loadData();
        });
        branchListView.setSaveAddAction(e->{
            branchController.processAddBranch();
            branchController.loadData();
            branchListView.showDialog(false);
        });
        branchListView.setRefeshAction(e->{
            branchController.loadData();
        });
    }

    private void handleSetting(){
        userController.loadSettingData(currentUser);
        settingView.setChangePasswordAction(e->{
            userController.processChangePassword(currentUser);
            userController.loadSettingData(currentUser);
        });
        settingView.setChangeEmailAction(e->{
            userController.processChangeEmail(currentUser);
            userController.loadSettingData(currentUser);
        });
        settingView.setChangePhoneAction(e->{
            String newPhone = JOptionPane.showInputDialog(settingView,"Nhập số điện thoại mới :",currentUser.getPhone());
            userController.processChangePhone(currentUser,newPhone);
            userController.loadSettingData(currentUser);
        });
        settingView.setLogoutAction(e->{
            this.mainView.dispose();
            new MainController().start();
        });
        settingView.setDeleteAccountAction(e->{
            if(userController.processDeleteUser(currentUser)){
                this.mainView.dispose();
                new MainController().start();
            }
        });
    }
}
