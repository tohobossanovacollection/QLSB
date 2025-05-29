package view;

import model.Booking;
import model.Customer;
import model.MonthlyBooking;
import model.Pitch;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.toedter.calendar.JDateChooser;
import utils.*;


public class BookingView extends JPanel {
    private JComboBox<Pitch> PitchComboBox;
    private JDateChooser dateChooser;
    private JSpinner startTimeSpinner;
    private JSpinner endTimeSpinner;
    private JComboBox<Customer> customerComboBox;
    private JButton searchCustomerButton;
    private JButton addCustomerButton;
    private JTextArea notesArea;
    private JButton saveEditButton;
    private JButton saveButton;
    private JButton cancelButton;
    private JLabel totalPriceLabel;
    private JCheckBox periodicCheckBox;
    private JPanel periodicPanel;
    private JCheckBox[] weekdayCheckBoxes;
    private JDateChooser periodicStartDateChooser;
    private JDateChooser periodicEndDateChooser;
    private JTextField discountField;
    private JLabel extraInfoLabel;

    private LocalDateTime openTime = LocalDateTime.of(2025, 5, 5, 5, 0);
    private LocalDateTime closeTime = LocalDateTime.of(2025, 5, 5, 23, 0);
    private LocalDateTime now = LocalDateTime.now();
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
        
        //set time for start and end time 
        // Set start time to the next rounded hour, end time to 30 minutes after start
        if(now.isAfter(closeTime) || now.isBefore(openTime)){
            startTimeSpinner.setValue(DateTimeUtils.toDate(openTime));
            endTimeSpinner.setValue(DateTimeUtils.toDate(closeTime.minusMinutes(1050)));
        }
        startTimeSpinner.setValue(DateTimeUtils.toDate(now));
        endTimeSpinner.setValue(DateTimeUtils.toDate(now.plusMinutes(30)));
        

        //add listener cho cbbox

        PitchComboBox.addActionListener(e->{
            
            setTotalPrice();
        });

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
        
        // periodicCheckBox
        periodicCheckBox = new JCheckBox("Đặt sân định kỳ");
        periodicCheckBox.setSelected(false);

       

        // Panel chứa các thành phần định kỳ (ẩn mặc định)
        periodicPanel = new JPanel(new GridBagLayout());
        periodicPanel.setVisible(false);

        // Checkbox các thứ trong tuần
        String[] weekdays = {"T2", "T3", "T4", "T5", "T6", "T7", "CN"};
        weekdayCheckBoxes = new JCheckBox[7];
        JPanel weekdayPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        for (int i = 0; i < 7; i++) {
            weekdayCheckBoxes[i] = new JCheckBox(weekdays[i]);
            weekdayPanel.add(weekdayCheckBoxes[i]);
        }

        // Chọn ngày bắt đầu/kết thúc
        periodicStartDateChooser = new JDateChooser();
        periodicStartDateChooser.setPreferredSize(new Dimension(150, 25));
        periodicEndDateChooser = new JDateChooser();
        periodicEndDateChooser.setPreferredSize(new Dimension(150, 25));

        GridBagConstraints pgbc = new GridBagConstraints();
        pgbc.insets = new Insets(2, 2, 2, 2);
        pgbc.gridx = 0; pgbc.gridy = 0; pgbc.anchor = GridBagConstraints.WEST;
        periodicPanel.add(new JLabel("Chọn các thứ:"), pgbc);
        pgbc.gridx = 1;
        periodicPanel.add(weekdayPanel, pgbc);

        pgbc.gridx = 0; pgbc.gridy = 1;
        periodicPanel.add(new JLabel("Ngày bắt đầu:"), pgbc);
        pgbc.gridx = 1;
        periodicPanel.add(periodicStartDateChooser, pgbc);

        pgbc.gridx = 0; pgbc.gridy = 2;
        periodicPanel.add(new JLabel("Ngày kết thúc:"), pgbc);
        pgbc.gridx = 1;
        periodicPanel.add(periodicEndDateChooser, pgbc);

        pgbc.gridx = 0;pgbc.gridy = 3;
        JLabel discountLabel = new JLabel("Giảm giá:");
        periodicPanel.add(discountLabel, pgbc);
        pgbc.gridx = 1;
        discountField = new JTextField(10);
        periodicPanel.add(discountField, pgbc);

        // Thêm periodicCheckBox và periodicPanel vào formPanel
        gbc.gridx = 1; gbc.gridy = 5; gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(periodicCheckBox, gbc);
        gbc.gridx = 1; gbc.gridy = 6; gbc.gridwidth = 2;
        formPanel.add(periodicPanel, gbc);
        gbc.gridwidth = 1; // reset

        // Sự kiện ẩn/hiện panel định kỳ
        periodicCheckBox.addActionListener(e -> {
            //addCustomerButton.setVisible(!periodicCheckBox.isSelected());
            dateChooser.setEnabled(!periodicCheckBox.isSelected());//an datechooser hiien thi preiodicdatechooser
            periodicPanel.setVisible(periodicCheckBox.isSelected());
            formPanel.revalidate();
            formPanel.repaint();
        });

        // Notes
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Ghi chú:"), gbc);
        
        notesArea = new JTextArea(4, 30);
        notesArea.setLineWrap(true);
        JScrollPane notesScrollPane = new JScrollPane(notesArea);
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(notesScrollPane, gbc);
        
        // Total price
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.WEST;
        formPanel.add(new JLabel("Tổng tiền:"), gbc);
        
        totalPriceLabel = new JLabel("0 VNĐ");
        totalPriceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        gbc.gridx = 1;
        gbc.gridy = 8;
        formPanel.add(totalPriceLabel, gbc);

        extraInfoLabel = new JLabel("tét"); // hoặc text mặc định bạn muốn
        extraInfoLabel.setFont(new Font("Arial", Font.ITALIC, 15));
        extraInfoLabel.setForeground(Color.GRAY);
        gbc.gridx = 1;
        gbc.gridy = 9;
        formPanel.add(extraInfoLabel, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        cancelButton = new JButton("Quay lại danh sách");
        saveButton = new JButton("Lưu");
        saveEditButton = new JButton("Xác nhận sửa");
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(saveEditButton);
        saveEditButton.setVisible(false); // ẩn nút sửa ban đầu
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setButtonForEdit(Boolean isEditing) {
        if(isEditing ==  true )
        {
            saveButton.setVisible(false);
            saveEditButton.setVisible(true);
            saveEditButton.setText("Xác nhận sửa");
            cancelButton.setText("Hủy bỏ");
        }
        
        else{
            saveButton.setVisible(true);
            saveEditButton.setVisible(false);
            cancelButton.setText("Quay lại danh sách");
        }
    }


    public boolean checkFalseTimeRange(){
        return getMinutesBetween() < 31;
    }
    
    public void loadcustomers(List<Customer> customers) {
        customerComboBox.removeAllItems(); // Clear existing items
        for (Customer customer : customers) {
            customerComboBox.addItem(customer);
        }
    }
    public void loadpitches(List<Pitch> pitches){
        PitchComboBox.removeAllItems(); // Clear existing items
        for (Pitch pitch : pitches) {
            PitchComboBox.addItem(pitch);
        }
    }

    public boolean isPeriodic() {
        return periodicCheckBox.isSelected();
    }
    public void setPeriodic(boolean periodic) {
        periodicCheckBox.setSelected(periodic);
        periodicPanel.setVisible(periodic);
        dateChooser.setEnabled(!periodic);
    }
    
    public List<String> getDayOfWeek() {
        List<String> days = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            if (weekdayCheckBoxes[i].isSelected()) {
                days.add(DayEnum.day(i));
            } 
        }
        return days;
    }
    public void setDayOfWeek(List<String> days) {//cam on chatgpt
        for (int i = 0; i < 7; i++) {
            weekdayCheckBoxes[i].setSelected(days.contains(DayEnum.day(i)));
        }
    }
    

    
    public Pitch getSelectedPitch() {
        return (Pitch) PitchComboBox.getSelectedItem();
    }

    public void setSelectedPitch(Pitch pitch){
        PitchComboBox.setSelectedItem(pitch);
    }

    public Customer getSelectedCustomer() {
        return (Customer) customerComboBox.getSelectedItem();
    }

    public void setSelectedCustomer(Customer customer){
        customerComboBox.setSelectedItem(customer);
    }

    public String getNotes() {
        return notesArea.getText();
    }

    public double getDiscount(){
        try{
            return Double.parseDouble(discountField.getText());
        }
        catch(NumberFormatException e){
            return -1.0;
        }
    }

    public void setDiscount(double discount){
        discountField.setText(String.valueOf(discount));
    }

    public void setTotalPrice(double price) {
        totalPriceLabel.setText(ConvertToVnd.formatCurrency((long)price));
        extraInfoLabel.setText(ConvertToVnd.convertNumberToTextVND((long)price));
    }

    public void setTotalPrice(){
        double price = getSelectedPitch().getPricePerHour() * getHoursBetween();
        if(price != 0)
        {
            totalPriceLabel.setText(ConvertToVnd.formatCurrency((long)price) );
        extraInfoLabel.setText(ConvertToVnd.convertNumberToTextVND((long)price));
        }
        else{
            price = -1;
        }
    }

    public void setStartDate(LocalDateTime startDate){
        periodicStartDateChooser.setDate(DateTimeUtils.toDate(startDate));
    }
    public void setEndDate(LocalDateTime endDate){
        periodicEndDateChooser.setDate(DateTimeUtils.toDate(endDate));
    }


    //#region get date and time from view
    //1 : đổi từ java.date sang string của time hoặc date  
    //2: dùng string đã đổi chuyển sang LocalDateTime
    public  LocalDateTime getStarDate(){
        String startDate = DateTimeUtils.getDateFromDate(periodicStartDateChooser.getDate());
        return DateTimeUtils.parseDate(startDate);
    }
    public LocalDateTime getEndDate(){
        String endDate = DateTimeUtils.getDateFromDate(periodicEndDateChooser.getDate());
        return DateTimeUtils.parseDate(endDate);
    }
    private Date getSelectedDate() {
        return dateChooser.getDate();
    }
    
    private Date getSelectedStartTime() {
        return  (Date) startTimeSpinner.getValue();
    }
    
    private Date getSelectedEndTime() {
        return (Date) endTimeSpinner.getValue();
    }
    public LocalDateTime getStartTime(){
        String startTime = DateTimeUtils.getTimeFromDate(getSelectedStartTime());
        return DateTimeUtils.parseTime(startTime);
    }
    public LocalDateTime getEndTime(){
        String endTime = DateTimeUtils.getTimeFromDate(getSelectedEndTime());
        return DateTimeUtils.parseTime(endTime);
    }
    public LocalDateTime getDate(){
        //String date = DateTimeUtils.getDateFromDate(getSelectedDate());
        return DateTimeUtils.parseDate(DateTimeUtils.getDateFromDate(getSelectedDate()));
    }
    //#endregion
    
    
    
    
    
    public void setSaveEditAction(ActionListener listener) {
        saveEditButton.addActionListener(listener);
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

    public Booking getBookingData(){
        Booking bookingdata = new Booking();
        bookingdata.setPitchId(getSelectedPitch().getId());
        bookingdata.setCustomerId(getSelectedCustomer().getId());
        bookingdata.setDate(getDate());
        bookingdata.setStartTime(getStartTime());
        bookingdata.setEndTime(getEndTime());
        bookingdata.setTotalPrice(getSelectedPitch().getPricePerHour());
        bookingdata.setStatus("PENDING");
        bookingdata.setPeriodic(isPeriodic());
        bookingdata.setNote(getNotes());
        return bookingdata;
    }
    public MonthlyBooking getMonthlyBookingData(){
        MonthlyBooking monthlyBookingdata = new MonthlyBooking();
        //id xu ly o controller
        //monthlyBookingdata.setId(bookingdata.getId());
        monthlyBookingdata.setDaysOfWeek(getDayOfWeek());
        monthlyBookingdata.setStartDate(getStarDate());
        monthlyBookingdata.setEndDate(getEndDate());
        monthlyBookingdata.setDiscount(getDiscount());
        return monthlyBookingdata;
    }
    
    public int getDaysBetween(){
        return DateTimeUtils.calculateDaysBetween(getStarDate(), getEndDate());
    }

    public double getHoursBetween(){
        return DateTimeUtils.calculateHoursBetween(getStartTime(), getEndTime());
    }

    public double getMinutesBetween(){
        return DateTimeUtils.calculateMinutesBetween(getStartTime(), getEndTime());
    }

    public void clear() {
        dateChooser.setDate(new Date());
        notesArea.setText("");
        totalPriceLabel.setText("");
        customerComboBox.setSelectedIndex(0);
        PitchComboBox.setSelectedIndex(0);
    }

    public void setData(Booking booking){
        clear();
        for (int i = 0; i < customerComboBox.getItemCount(); i++) {
            Customer customer = (Customer) customerComboBox.getItemAt(i);
            if (customer.getId() == booking.getCustomerId()) {
                customerComboBox.setSelectedIndex(i);
                break;
            }
        }
        for (int i = 0; i < PitchComboBox.getItemCount(); i++) {
            Pitch pitch = (Pitch) PitchComboBox.getItemAt(i);
            if (pitch.getId() == booking.getPitchId()) {
                PitchComboBox.setSelectedIndex(i);
                break;
            }
        }
        dateChooser.setDate(DateTimeUtils.toDate(booking.getDate()));
        startTimeSpinner.setValue(DateTimeUtils.toDate(booking.getStartTime()));
        endTimeSpinner.setValue(DateTimeUtils.toDate(booking.getEndTime()));
        notesArea.setText(booking.getNote());
        setTotalPrice();
    }
    public void displaySucess(){
        JOptionPane.showMessageDialog(this, "Đặt sân thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    public void displayError(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
}
