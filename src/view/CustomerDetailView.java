package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import model.Customer;

public class CustomerDetailView extends JFrame {
    private Customer customer;
    private JLabel titleLabel;
    private JLabel idLabel, nameLabel, phoneLabel, emailLabel, typeLabel, notesLabel;
    private JTextField idField, nameField, phoneField, emailField, typeField;
    private JTextArea notesArea;
    private JButton editButton, saveButton, cancelButton, backButton;
    private JPanel bookingHistoryPanel;
    private JTable bookingHistoryTable;
    
    public CustomerDetailView() {
        initComponents();
        setupLayout();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Chi tiết khách hàng");
    }
    
    private void initComponents() {
        titleLabel = new JLabel("Thông tin chi tiết khách hàng");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        idLabel = new JLabel("Mã khách hàng:");
        nameLabel = new JLabel("Tên khách hàng:");
        phoneLabel = new JLabel("Số điện thoại:");
        emailLabel = new JLabel("Email:");
        typeLabel = new JLabel("Loại khách hàng:");
        notesLabel = new JLabel("Ghi chú:");
        
        idField = new JTextField(20);
        idField.setEditable(false);
        nameField = new JTextField(20);
        phoneField = new JTextField(20);
        emailField = new JTextField(20);
        typeField = new JTextField(20);
        
        notesArea = new JTextArea(5, 20);
        notesArea.setLineWrap(true);
        JScrollPane notesScrollPane = new JScrollPane(notesArea);
        
        editButton = new JButton("Chỉnh sửa");
        saveButton = new JButton("Lưu");
        cancelButton = new JButton("Hủy");
        backButton = new JButton("Quay lại");
        
        // Bảng lịch sử đặt sân
        String[] columnNames = {"Ngày đặt", "Sân", "Thời gian", "Trạng thái", "Số tiền"};
        Object[][] data = {}; // Sẽ được điền khi load dữ liệu
        bookingHistoryTable = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(bookingHistoryTable);
        
        bookingHistoryPanel = new JPanel(new BorderLayout());
        bookingHistoryPanel.setBorder(BorderFactory.createTitledBorder("Lịch sử đặt sân"));
        bookingHistoryPanel.add(tableScrollPane, BorderLayout.CENTER);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.add(titleLabel);
        
        JPanel infoPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        // Thêm các components vào panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        infoPanel.add(idLabel, gbc);
        
        gbc.gridx = 1;
        infoPanel.add(idField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        infoPanel.add(nameLabel, gbc);
        
        gbc.gridx = 1;
        infoPanel.add(nameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        infoPanel.add(phoneLabel, gbc);
        
        gbc.gridx = 1;
        infoPanel.add(phoneField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        infoPanel.add(emailLabel, gbc);
        
        gbc.gridx = 1;
        infoPanel.add(emailField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        infoPanel.add(typeLabel, gbc);
        
        gbc.gridx = 1;
        infoPanel.add(typeField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        infoPanel.add(notesLabel, gbc);
        
        gbc.gridx = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        infoPanel.add(new JScrollPane(notesArea), gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.add(editButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(backButton);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(infoPanel, BorderLayout.NORTH);
        mainPanel.add(bookingHistoryPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    public void displayCustomerDetails(Customer customer) {
        this.customer = customer;
        //idField.setText(String.valueOf(customer.getId()));
        nameField.setText(customer.getName());
        phoneField.setText(customer.getPhone());
        emailField.setText(customer.getEmail());
        typeField.setText(customer.getCustomerType());
        //notesArea.setText(customer.getNotes());
        
        // Cập nhật bảng lịch sử đặt sân
        // loadBookingHistory(customer.getId());
    }
    
    // Phương thức để load lịch sử đặt sân của khách hàng
    public void loadBookingHistory(int customerId) {
        // Sẽ được cài đặt để load dữ liệu từ BookingService
    }
    
    // Các phương thức set ActionListener
    public void setEditButtonListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }
    
    public void setSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
    
    public void setCancelButtonListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }
    
    public void setBackButtonListener(ActionListener listener) {
        backButton.addActionListener(listener);
    }
    
    // Getter để lấy dữ liệu từ form
    public Customer getCustomerFromForm() {
        Customer updatedCustomer = new Customer();
        //updatedCustomer.setId(Integer.parseInt(idField.getText()));
        updatedCustomer.setName(nameField.getText());
        updatedCustomer.setPhone(phoneField.getText());
        updatedCustomer.setEmail(emailField.getText());
        updatedCustomer.setCustomerType(typeField.getText());
        //updatedCustomer.setNotes(notesArea.getText());
        return updatedCustomer;
    }
    
    // Phương thức để chuyển form sang chế độ chỉnh sửa hoặc xem
    public void setEditMode(boolean editMode) {
        nameField.setEditable(editMode);
        phoneField.setEditable(editMode);
        emailField.setEditable(editMode);
        typeField.setEditable(editMode);
        notesArea.setEditable(editMode);
        saveButton.setVisible(editMode);
        cancelButton.setVisible(editMode);
        editButton.setVisible(!editMode);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            CustomerDetailView view = new CustomerDetailView();
            view.setVisible(true);
        });
    }
}
