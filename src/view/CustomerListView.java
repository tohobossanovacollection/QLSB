package view;

import model.Customer;
import view.components.TableComponent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class CustomerListView extends JPanel {
    private JTextField searchField;
    private JButton searchButton;
    private JButton addButton;
    private TableComponent<Customer> customerTable;
    private JButton editButton;
    private JButton deleteButton;
    private JButton viewHistoryButton;
    
    public CustomerListView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Title
        JLabel titleLabel = new JLabel("DANH SÁCH KHÁCH HÀNG", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);
        
        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        searchPanel.add(new JLabel("Tìm kiếm:"));
        
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        
        searchButton = new JButton("Tìm");
        searchPanel.add(searchButton);
        
        addButton = new JButton("Thêm Khách Hàng");
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButtonPanel.add(addButton);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(addButtonPanel, BorderLayout.EAST);
        
        add(topPanel, BorderLayout.CENTER);
        
        // Customer table
        
        String[] columnNames = {"Mã KH", "Tên khách hàng", "Số điện thoại", "Email", "Loại KH"};
        customerTable = new TableComponent<Customer>(columnNames);
        //customerTable.setColumnNames(columnNames);
        
        JScrollPane tableScrollPane = new JScrollPane(customerTable);
        tableScrollPane.setPreferredSize(new Dimension(800, 400));
        add(tableScrollPane, BorderLayout.SOUTH);
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        editButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        viewHistoryButton = new JButton("Xem lịch sử");
        
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewHistoryButton);
        
        add(buttonPanel, BorderLayout.EAST);
    }
    /* 
    public void setCustomers(List<Customer> customers) {
        Object[][] data = new Object[customers.size()][5];
        
        for (int i = 0; i < customers.size(); i++) {
            Customer customer = customers.get(i);
            data[i][0] = customer.getId();
            data[i][1] = customer.getName();
            data[i][2] = customer.getPhone();
            data[i][3] = customer.getEmail();
            data[i][4] = customer.getCustomerType();
        }
        
        customerTable.setData(data, customers);
    }
    */
    public int getSelectedCustomerIndex() {
        return customerTable.getSelectedRow();
    }
    
    public String getSearchText() {
        return searchField.getText();
    }
    
    public void setAddAction(ActionListener listener) {
        addButton.addActionListener(listener);
    }
    
    public void setEditAction(ActionListener listener) {
        editButton.addActionListener(listener);
    }
    
    public void setDeleteAction(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
    
    public void setViewHistoryAction(ActionListener listener) {
        viewHistoryButton.addActionListener(listener);
    }
    
    public void setSearchAction(ActionListener listener) {
        searchButton.addActionListener(listener);
    }
}