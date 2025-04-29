package view;

import model.Booking;
import view.components.TableComponent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.text.SimpleDateFormat;

public class BookingListView extends JPanel {
    private JComboBox<String> statusComboBox;
    private JTextField searchField;
    private JButton searchButton;
    private JButton addBookingButton;
    private TableComponent<Booking> bookingTable;
    private JButton editButton;
    private JButton deleteButton;
    private JButton printButton;
    
    public BookingListView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("DANH SÁCH ĐẶT SÂN", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel, BorderLayout.CENTER);
        
        add(titlePanel, BorderLayout.NORTH);
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        searchPanel.add(new JLabel("Trạng thái:"));
        
        String[] statuses = {"Tất cả", "Chờ xác nhận", "Đã xác nhận", "Đã hoàn thành", "Đã hủy"};
        statusComboBox = new JComboBox<>(statuses);
        searchPanel.add(statusComboBox);
        
        searchPanel.add(new JLabel("Tìm kiếm:"));
        
        searchField = new JTextField(15);
        searchPanel.add(searchField);
        
        searchButton = new JButton("Tìm");
        searchPanel.add(searchButton);
        
        addBookingButton = new JButton("Thêm Đặt Sân");
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButtonPanel.add(addBookingButton);
        
        JPanel topControlPanel = new JPanel(new BorderLayout());
        topControlPanel.add(searchPanel, BorderLayout.WEST);
        topControlPanel.add(addButtonPanel, BorderLayout.EAST);
        
        add(topControlPanel, BorderLayout.CENTER);
        
        // Booking table
        
        String[] columnNames = {"Mã đặt sân", "Sân", "Ngày", "Giờ bắt đầu", "Giờ kết thúc", "Khách hàng", "Trạng thái"};
        bookingTable = new TableComponent<Booking>(columnNames);
        //bookingTable.setColumnNames(columnNames);
        
        JScrollPane tableScrollPane = new JScrollPane(bookingTable);
        tableScrollPane.setPreferredSize(new Dimension(800, 400));
        add(tableScrollPane, BorderLayout.SOUTH);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        editButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        printButton = new JButton("In");
        
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(printButton);
        
        add(buttonPanel, BorderLayout.EAST);
    }
    
    /*public void setBookings(List<Booking> bookings) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        
        Object[][] data = new Object[bookings.size()][7];
        
        for (int i = 0; i < bookings.size(); i++) {
            Booking booking = bookings.get(i);
            data[i][0] = booking.getId();
            data[i][1] = booking.getField().getName();
            data[i][2] = dateFormat.format(booking.getStartTime());
            data[i][3] = timeFormat.format(booking.getStartTime());
            data[i][4] = timeFormat.format(booking.getEndTime());
            data[i][5] = booking.getCustomer().getName();
            data[i][6] = booking.getStatus();
        }
        
        bookingTable.setData(data, bookings);
    }*/
    
    public int getSelectedBookingIndex() {
        return bookingTable.getSelectedRow();
    }
    
    public String getSearchText() {
        return searchField.getText();
    }
    
    public String getSelectedStatus() {
        return (String) statusComboBox.getSelectedItem();
    }
    
    public void setAddBookingAction(ActionListener listener) {
        addBookingButton.addActionListener(listener);
    }
    
    public void setEditAction(ActionListener listener) {
        editButton.addActionListener(listener);
    }
    
    public void setDeleteAction(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
    
    public void setPrintAction(ActionListener listener) {
        printButton.addActionListener(listener);
    }
    
    // BookingListView.java (tiếp tục)
    public void setSearchAction(ActionListener listener) {
        searchButton.addActionListener(listener);
    }
    
    public void setStatusSelectionAction(ActionListener listener) {
        statusComboBox.addActionListener(listener);
    }
}