package view;

import javax.swing.*;

import controller.*;

import java.awt.*;
import java.awt.event.ActionListener;

public class MainView extends JFrame {
    private JMenuBar menuBar;
    private JMenu fieldMenu, bookingMenu, customerMenu, salesMenu, reportMenu, systemMenu;
    private JMenuItem fieldStatusItem, manageFieldsItem;
    private JMenuItem bookingItem, bookingListItem, monthlyBookingItem;
    private JMenuItem customerItem, customerListItem;
    private JMenuItem salesItem, productItem, inventoryItem;
    private JMenuItem revenueReportItem, fieldUsageReportItem, transactionItem;
    private JMenuItem branchItem, userItem, settingsItem;
    private JPanel mainPanel;
    private CardLayout cardLayout;

    public MainView() {
        setTitle("Hệ thống Quản lý Sân Bóng");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        // Initialize layout
        mainPanel = new JPanel();
        cardLayout = new CardLayout();
        mainPanel.setLayout(cardLayout);
        
        // Setup menu bar
        setupMenuBar();
        
        // Add main content panel
        add(mainPanel);
    }

    private void setupMenuBar() {
        menuBar = new JMenuBar();
        
        // Field Menu
        fieldMenu = new JMenu("Quản lý Sân");
        fieldStatusItem = new JMenuItem("Tình trạng Sân");
        manageFieldsItem = new JMenuItem("Quản lý Sân");
        fieldMenu.add(fieldStatusItem);
        fieldMenu.add(manageFieldsItem);
        
        // Booking Menu
        bookingMenu = new JMenu("Đặt Sân");
        bookingItem = new JMenuItem("Đặt Sân");
        bookingListItem = new JMenuItem("Danh sách Đặt Sân");
        monthlyBookingItem = new JMenuItem("Đặt Sân Theo Tháng");
        bookingMenu.add(bookingItem);
        bookingMenu.add(bookingListItem);
        bookingMenu.add(monthlyBookingItem);
        
        // Customer Menu
        customerMenu = new JMenu("Khách Hàng");
        customerItem = new JMenuItem("Thêm Khách Hàng");
        customerListItem = new JMenuItem("Danh sách Khách Hàng");
        customerMenu.add(customerItem);
        customerMenu.add(customerListItem);
        
        // Sales Menu
        salesMenu = new JMenu("Bán Hàng & Kho");
        salesItem = new JMenuItem("Bán Hàng");
        productItem = new JMenuItem("Quản lý Sản Phẩm");
        inventoryItem = new JMenuItem("Quản lý Kho");
        salesMenu.add(salesItem);
        salesMenu.add(productItem);
        salesMenu.add(inventoryItem);
        
        // Report Menu
        reportMenu = new JMenu("Báo Cáo");
        revenueReportItem = new JMenuItem("Báo Cáo Doanh Thu");
        fieldUsageReportItem = new JMenuItem("Báo Cáo Sử Dụng Sân");
        transactionItem = new JMenuItem("Quản lý Thu Chi");
        reportMenu.add(revenueReportItem);
        reportMenu.add(fieldUsageReportItem);
        reportMenu.add(transactionItem);
        
        // System Menu
        systemMenu = new JMenu("Hệ Thống");
        branchItem = new JMenuItem("Quản lý Chi Nhánh");
        userItem = new JMenuItem("Quản lý Người Dùng");
        settingsItem = new JMenuItem("Thiết Lập");
        systemMenu.add(branchItem);
        systemMenu.add(userItem);
        systemMenu.add(settingsItem);
        
        // Add all menus to menu bar
        menuBar.add(fieldMenu);
        menuBar.add(bookingMenu);
        menuBar.add(customerMenu);
        menuBar.add(salesMenu);
        menuBar.add(reportMenu);
        menuBar.add(systemMenu);
        
        // Set menu bar to frame
        setJMenuBar(menuBar);
    }
    
    public void addPanel(JPanel panel, String name) {
        mainPanel.add(panel, name);
    }
    
    public void showPanel(String name) {
        cardLayout.show(mainPanel, name);
    }
    
    // Action listeners for menu items
    public void setFieldStatusAction(ActionListener listener) {
        fieldStatusItem.addActionListener(listener);
    }
    
    public void setManageFieldsAction(ActionListener listener) {
        manageFieldsItem.addActionListener(listener);
    }
    
    public void setBookingAction(ActionListener listener) {
        bookingItem.addActionListener(listener);
    }
    
    public void setBookingListAction(ActionListener listener) {
        bookingListItem.addActionListener(listener);
    }
    
    public void setMonthlyBookingAction(ActionListener listener) {
        monthlyBookingItem.addActionListener(listener);
    }
    
    // Add more action listeners for other menu items...
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainController mainController = new MainController(new MainView(),new LoginView());
            /*mainView.addPanel(bookingView, "BookingView");
            mainView.addPanel(customerView, "CustomerView");
            mainView.addPanel(bookingListView, "heehe");
            mainView.addPanel(customerListView, "CustomerListView");
            mainView.addPanel(monthlyBookingView, "monthlyBookingView");
            mainView.addPanel(pitchStatusView, "pitchStatusView");
            mainView.addPanel(settingsView, "settingsView");
            mainView.addPanel(userView, "userView");*/
            mainController.start();

            //loginView.setVisible(true);
            //mainView.setVisible(false);

        });
    }
}