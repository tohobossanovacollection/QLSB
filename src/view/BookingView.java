package view;

import model.Booking;
import model.Customer;
import model.Pitch;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import com.toedter.calendar.JDateChooser;
import service.PitchService;
import service.CustomerService;
import utils.DateTimeUtils;

import controller.MainController;

public class BookingView extends JPanel {
    private JComboBox<Pitch> PitchComboBox;
    private JDateChooser dateChooser;
    private JSpinner startTimeSpinner;
    private JSpinner endTimeSpinner;
    private JComboBox<Customer> customerComboBox;
    private JButton searchCustomerButton;
    private JButton addCustomerButton;
    private JTextArea notesArea;
    private JButton saveButton;
    private JButton cancelButton;
    private JLabel totalPriceLabel;
    
    public BookingView() {
        setLayout(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("ĐẶT SÂN", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        // Main form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Pitch selection
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Chọn sân:"), gbc);
        
        PitchComboBox = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(PitchComboBox, gbc);
        
        // Date selection
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Ngày:"), gbc);
        
        dateChooser = new JDateChooser();
        dateChooser.setDate(new Date());
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(dateChooser, gbc);
        
        // Start time
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Giờ bắt đầu:"), gbc);
        
        SpinnerDateModel startModel = new SpinnerDateModel();
        startTimeSpinner = new JSpinner(startModel);
        JSpinner.DateEditor startEditor = new JSpinner.DateEditor(startTimeSpinner, "HH:mm:ss");
        startTimeSpinner.setEditor(startEditor);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(startTimeSpinner, gbc);
        
        // End time
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Giờ kết thúc:"), gbc);
        
        SpinnerDateModel endModel = new SpinnerDateModel();
        endTimeSpinner = new JSpinner(endModel);
        JSpinner.DateEditor endEditor = new JSpinner.DateEditor(endTimeSpinner, "HH:mm:ss");
        endTimeSpinner.setEditor(endEditor);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(endTimeSpinner, gbc);
        
        // Customer selection
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Khách hàng:"), gbc);
        
        JPanel customerPanel = new JPanel(new BorderLayout(5, 0));
        customerComboBox = new JComboBox<>();
        customerPanel.add(customerComboBox, BorderLayout.CENTER);
        
        /*searchCustomerButton = new JButton("Tìm");
        searchCustomerButton.setPreferredSize(new Dimension(60, 25));
        customerPanel.add(searchCustomerButton, BorderLayout.EAST);*/
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(customerPanel, gbc);
        
        // Add customer button
        addCustomerButton = new JButton("Thêm Khách Hàng Mới");
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.EAST;
        formPanel.add(addCustomerButton, gbc);
        
        // Notes
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Ghi chú:"), gbc);
        
        notesArea = new JTextArea(4, 30);
        notesArea.setLineWrap(true);
        JScrollPane notesScrollPane = new JScrollPane(notesArea);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(notesScrollPane, gbc);
        
        // Total price
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("Tổng tiền:"), gbc);
        
        totalPriceLabel = new JLabel("0 VNĐ");
        totalPriceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        gbc.gridy = 7;
        formPanel.add(totalPriceLabel, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        cancelButton = new JButton("Hủy");
        saveButton = new JButton("Lưu");
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
        loadpitches();
        loadcustomers();
    }
    
    private void loadcustomers() {
        CustomerService customerService = new CustomerService();
        List<Customer> customers = customerService.getAllCustomers();
        for (Customer customer : customers) {
            customerComboBox.addItem(customer);
        }
    }
    private void loadpitches(){
        PitchService pitchService = new PitchService();
        List<Pitch> pitches = pitchService.getAllPitches();
        for (Pitch pitch : pitches) {
            PitchComboBox.addItem(pitch);
        }
    }
    public void setPitchs(List<Pitch> Pitchs) {
        PitchComboBox.removeAllItems();
        for (Pitch Pitch : Pitchs) {
            PitchComboBox.addItem(Pitch);
        }
    }
    
    public void setCustomers(List<Customer> customers) {
        customerComboBox.removeAllItems();
        for (Customer customer : customers) {
            customerComboBox.addItem(customer);
        }
    }
    
    public Pitch getSelectedPitch() {
        return (Pitch) PitchComboBox.getSelectedItem();
    }
    
    private Date getSelectedDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        //Date newdate = dateFormat.format(dateChooser.getDate());
        return (Date) dateChooser.getDate();
    }
    
    private Date getSelectedStartTime() {
        return  (Date) startTimeSpinner.getValue();
    }
    
    private Date getSelectedEndTime() {
        return (Date) endTimeSpinner.getValue();
    }
    public LocalDateTime getStartTime(){
        String startTime = DateTimeUtils.getDateFromDate(getSelectedDate()) +" "+ DateTimeUtils.getTimeFromDate(getSelectedStartTime());
        return DateTimeUtils.parseDateTime(startTime);
    }
    public LocalDateTime getEndTime(){
        String endTime = DateTimeUtils.getDateFromDate(getSelectedDate()) +" "+ DateTimeUtils.getTimeFromDate(getSelectedEndTime());
        return DateTimeUtils.parseDateTime(endTime);
    }
    
    public Customer getSelectedCustomer() {
        return (Customer) customerComboBox.getSelectedItem();
    }
    
    public String getNotes() {
        return notesArea.getText();
    }
    
    public void setTotalPrice(double price) {
        totalPriceLabel.setText(String.format("%,.0f VNĐ", price));
    }
    
    public void setSaveAction(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
    
    public void setCancelAction(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }
    
    public void setSearchCustomerAction(ActionListener listener) {
        searchCustomerButton.addActionListener(listener);
    }
    
    public void setAddCustomerAction(ActionListener listener) {
        addCustomerButton.addActionListener(listener);
    }
    
    public void setPitchSelectionAction(ActionListener listener) {
        PitchComboBox.addActionListener(listener);
    }

    public Booking getBookingData(){
        return new Booking(0,getSelectedPitch().getId(),
         getSelectedCustomer().getId(),getStartTime(),
          getEndTime(),getSelectedPitch().getPricePerHour()
          ,"PENDING",true);
    }
    
    public void clear() {
        dateChooser.setDate(new Date());
        notesArea.setText("");
        totalPriceLabel.setText("0 VNĐ");
    }
    public void displaySucess(){
        JOptionPane.showMessageDialog(this, "Đặt sân thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookingView bookingView = new BookingView();
            MainView mainView = new MainView();
            mainView.addPanel(bookingView, "1");
            mainView.showPanel("1");
            mainView.setVisible(true);
        });
    }
}
