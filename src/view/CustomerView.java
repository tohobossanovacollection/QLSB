package view;

import javax.swing.*;

import model.Customer;

import java.awt.*;
import java.awt.event.ActionListener;

public class CustomerView extends JPanel {
    private JTextField idField;
    private JTextField nameField;
    private JTextField phoneField;
    private JTextField emailField;
    private JComboBox<String> customerTypeComboBox;
    private JTextArea notesArea;
    private JButton saveButton;
    private JButton cancelButton;
    
    public CustomerView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JLabel titleLabel = new JLabel("THÔNG TIN KHÁCH HÀNG", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.WEST;
        
        // ID field
        gbc.gridx = 0;
        gbc.gridy = 0;
        formPanel.add(new JLabel("Mã khách hàng:"), gbc);
        
        idField = new JTextField(20);
        idField.setEditable(false);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(idField, gbc);
        
        // Name field
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Tên khách hàng:"), gbc);
        
        nameField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(nameField, gbc);
        
        // Phone field
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Số điện thoại:"), gbc);
        
        phoneField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(phoneField, gbc);
        
        // Email field
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Email:"), gbc);
        
        emailField = new JTextField(20);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(emailField, gbc);
        
        // Customer type
        /*gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        formPanel.add(new JLabel("Loại khách hàng:"), gbc);
        
        String[] customerTypes = {"Thường", "VIP", "Đối tác"};
        customerTypeComboBox = new JComboBox<>(customerTypes);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        formPanel.add(customerTypeComboBox, gbc);*/
        
        // Notes
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        formPanel.add(new JLabel("Địa chỉ:"), gbc);
        
        notesArea = new JTextArea(4, 20);
        notesArea.setLineWrap(true);
        JScrollPane notesScrollPane = new JScrollPane(notesArea);
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.BOTH;
        formPanel.add(notesScrollPane, gbc);
        
        add(formPanel, BorderLayout.CENTER);
        
        // Buttons panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        cancelButton = new JButton("Hủy");
        saveButton = new JButton("Lưu");
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);
        
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    public String getId() {
        return idField.getText();
    }
    
    public void setId(String id) {
        idField.setText(id);
    }
    
    public String getName() {
        return nameField.getText();
    }
    
    public void setName(String name) {
        nameField.setText(name);
    }
    
    public String getPhone() {
        return phoneField.getText();
    }
    
    public void setPhone(String phone) {
        phoneField.setText(phone);
    }
    
    public String getEmail() {
        return emailField.getText();
    }
    
    public void setEmail(String email) {
        emailField.setText(email);
    }
    
    public String getCustomerType() {
        return (String) customerTypeComboBox.getSelectedItem();
    }
    
    public void setCustomerType(String type) {
        customerTypeComboBox.setSelectedItem(type);
    }
    
    public String getNotes() {
        return notesArea.getText();
    }
    
    public void setNotes(String notes) {
        notesArea.setText(notes);
    }
    
    public void setSaveAction(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
    
    public void setCancelAction(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }
    
    public Customer getCustomerData() {
        Customer customer = new Customer();
        //customer.setId(idField.getText());
        customer.setName(nameField.getText());
        customer.setPhone(phoneField.getText());
        customer.setEmail(emailField.getText());
        //customer.setCustomerType((String) customerTypeComboBox.getSelectedItem());
        customer.setAddress(notesArea.getText());
        System.out.println("Name: " + nameField.getText());
        System.out.println("Phone: " + phoneField.getText());
        System.out.println("Email: " + emailField.getText());
        System.out.println("Address: " + emailField.getText());

    System.out.println("Customer data: " + customer.toString());
        System.out.println("Customer data: " + customer.toString());
        return customer;
    }

    public void displayCustomerCreationSuccess(Customer customer) {
        JOptionPane.showMessageDialog(this, "Khách hàng " + customer.getName() + " đã được tạo thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    public void displayError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public void clear() {
        idField.setText("heeh");
        nameField.setText("hehe");
        phoneField.setText("hehe");
        emailField.setText("hehe");
        //customerTypeComboBox.setSelectedIndex(0);
        notesArea.setText("hehe");
    }
}

