package view;

import model.Customer;
import model.Pitch;
import model.MonthlyBooking;
import view.components.TableComponent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.util.Calendar;

public class MonthlyBookingView extends JPanel {
    private JComboBox<Customer> customerComboBox;
    private JButton searchCustomerButton;
    private JComboBox<Pitch> PitchComboBox;
    private JPanel weekdaySelectionPanel;
    private JCheckBox[] weekdayCheckboxes;
    private JTextField startTimePitch;
    private JTextField endTimePitch;
    private JTextField pricePitch;
    private JComboBox<String> monthComboBox;
    private JSpinner yearSpinner;
    private JTextArea notesArea;
    private JButton saveButton;
    private JButton cancelButton;
    private TableComponent<MonthlyBooking> bookingTable;
    
    public MonthlyBookingView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JLabel titleLabel = new JLabel("QUẢN LÝ ĐẶT SÂN THEO THÁNG", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createTitledBorder("Thông tin đặt sân theo tháng"));
        
        // Form Pitchs
        JPanel PitchsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Customer
        gbc.gridx = 0;
        gbc.gridy = 0;
        PitchsPanel.add(new JLabel("Khách hàng:"), gbc);
        
        JPanel customerPanel = new JPanel(new BorderLayout(5, 0));
        customerComboBox = new JComboBox<>();
        customerPanel.add(customerComboBox, BorderLayout.CENTER);
        
        searchCustomerButton = new JButton("Tìm");
        searchCustomerButton.setPreferredSize(new Dimension(60, 25));
        customerPanel.add(searchCustomerButton, BorderLayout.EAST);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        PitchsPanel.add(customerPanel, gbc);
        
        // Pitch
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        PitchsPanel.add(new JLabel("Sân:"), gbc);
        
        PitchComboBox = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        PitchsPanel.add(PitchComboBox, gbc);
        
        // Weekday selection
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        PitchsPanel.add(new JLabel("Ngày trong tuần:"), gbc);
        
        weekdaySelectionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String[] weekdays = {"Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "CN"};
        weekdayCheckboxes = new JCheckBox[7];
        
        for (int i = 0; i < weekdays.length; i++) {
            weekdayCheckboxes[i] = new JCheckBox(weekdays[i]);
            weekdaySelectionPanel.add(weekdayCheckboxes[i]);
        }
        
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        PitchsPanel.add(weekdaySelectionPanel, gbc);
        
        // Time
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        PitchsPanel.add(new JLabel("Thời gian:"), gbc);
        
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        startTimePitch = new JTextField(5);
        endTimePitch = new JTextField(5);
        timePanel.add(startTimePitch);
        timePanel.add(new JLabel(" - "));
        timePanel.add(endTimePitch);
        
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        PitchsPanel.add(timePanel, gbc);
        
        // Month and Year
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        PitchsPanel.add(new JLabel("Tháng/Năm:"), gbc);
        
        JPanel monthYearPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        String[] months = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
        monthComboBox = new JComboBox<>(months);
        
        SpinnerNumberModel yearModel = new SpinnerNumberModel(
                Calendar.getInstance().get(Calendar.YEAR), 
                2000, 
                2100, 
                1);
        yearSpinner = new JSpinner(yearModel);
        
        monthYearPanel.add(monthComboBox);
        monthYearPanel.add(new JLabel("/"));
        monthYearPanel.add(yearSpinner);
        
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        PitchsPanel.add(monthYearPanel, gbc);
        
        // Price
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        PitchsPanel.add(new JLabel("Giá tháng:"), gbc);
        
        pricePitch = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        PitchsPanel.add(pricePitch, gbc);
        
        // Notes
        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        PitchsPanel.add(new JLabel("Ghi chú:"), gbc);
        
        notesArea = new JTextArea(3, 20);
        notesArea.setLineWrap(true);
        JScrollPane notesScrollPane = new JScrollPane(notesArea);
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.BOTH;
        PitchsPanel.add(notesScrollPane, gbc);
        
        formPanel.add(PitchsPanel);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        cancelButton = new JButton("Hủy");
        saveButton = new JButton("Lưu");
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        
        formPanel.add(buttonPanel);
        
        add(formPanel, BorderLayout.WEST);
        
        // Table panel
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBorder(BorderFactory.createTitledBorder("Danh sách đặt sân theo tháng"));
        
        bookingTable = new TableComponent<>();
        String[] columnNames = {"ID", "Khách hàng", "Sân", "Ngày trong tuần", "Thời gian", "Tháng/Năm", "Giá"};
        bookingTable.setColumnNames(columnNames);
        
        tablePanel.add(new JScrollPane(bookingTable), BorderLayout.CENTER);
        
        add(tablePanel, BorderLayout.CENTER);
    }
    
    public void setCustomers(List<Customer> customers) {
        customerComboBox.removeAllItems();
        for (Customer customer : customers) {
            customerComboBox.addItem(customer);
        }
    }
    
    public void setPitchs(List<Pitch> Pitchs) {
        PitchComboBox.removeAllItems();
        for (Pitch Pitch : Pitchs) {
            PitchComboBox.addItem(Pitch);
        }
    }
    
    public Customer getSelectedCustomer() {
        return (Customer) customerComboBox.getSelectedItem();
    }
    
    public Pitch getSelectedPitch() {
        return (Pitch) PitchComboBox.getSelectedItem();
    }
    
    public boolean[] getSelectedWeekdays() {
        boolean[] selected = new boolean[7];
        for (int i = 0; i < 7; i++) {
            selected[i] = weekdayCheckboxes[i].isSelected();
        }
        return selected;
    }
    
    public String getStartTime() {
        return startTimePitch.getText();
    }
    
    public String getEndTime() {
        return endTimePitch.getText();
    }
    
    public int getSelectedMonth() {
        return Integer.parseInt((String) monthComboBox.getSelectedItem());
    }
    
    public int getSelectedYear() {
        return (Integer) yearSpinner.getValue();
    }
    
    public double getPrice() {
        try {
            return Double.parseDouble(pricePitch.getText().replace(",", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    
    public String getNotes() {
        return notesArea.getText();
    }
    
    public void setMonthlyBookings(List<MonthlyBooking> bookings) {
        Object[][] data = new Object[bookings.size()][7];
        
        for (int i = 0; i < bookings.size(); i++) {
            MonthlyBooking booking = bookings.get(i);
            data[i][0] = booking.getId();
            data[i][1] = booking.getCustomer().getName();
            data[i][2] = booking.getPitch().getName();
            
            // Format weekdays
            StringBuilder weekdaysStr = new StringBuilder();
            boolean[] weekdays = booking.getWeekdays();
            String[] weekdayNames = {"T2", "T3", "T4", "T5", "T6", "T7", "CN"};
            for (int j = 0; j < weekdays.length; j++) {
                if (weekdays[j]) {
                    if (weekdaysStr.length() > 0) {
                        weekdaysStr.append(", ");
                    }
                    weekdaysStr.append(weekdayNames[j]);
                }
            }
            data[i][3] = weekdaysStr.toString();
            
            data[i][4] = booking.getStartTime() + " - " + booking.getEndTime();
            data[i][5] = booking.getMonth() + "/" + booking.getYear();
            data[i][6] = String.format("%,.0f VNĐ", booking.getPrice());
        }
        
        bookingTable.setData(data, bookings);
    }
    
    public int getSelectedBookingIndex() {
        return bookingTable.getSelectedRow();
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
    
    public void clear() {
        for (JCheckBox checkbox : weekdayCheckboxes) {
            checkbox.setSelected(false);
        }
        startTimePitch.setText("");
        endTimePitch.setText("");
        pricePitch.setText("");
        notesArea.setText("");
    }
}
