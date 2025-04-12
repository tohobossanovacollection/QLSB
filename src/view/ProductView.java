package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import model.Product;

public class ProductView extends JFrame {
    private JLabel titleLabel;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, deleteButton, refreshButton;
    private JTextField searchField;
    private JButton searchButton;
    
    // For add/edit product
    private JDialog productDialog;
    private JTextField nameField, priceField, stockField;
    private JButton saveButton, cancelDialogButton;
    
    public ProductView() {
        initComponents();
        setupLayout();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Quản lý sản phẩm");
    }
    
    private void initComponents() {
        titleLabel = new JLabel("Quản lý sản phẩm");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Bảng sản phẩm
        String[] columnNames = {"Mã", "Tên sản phẩm", "Đơn giá", "Số lượng tồn"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        
        // Search components
        searchField = new JTextField(20);
        searchButton = new JButton("Tìm kiếm");
        
        // Action buttons
        addButton = new JButton("Thêm mới");
        editButton = new JButton("Chỉnh sửa");
        deleteButton = new JButton("Xóa");
        refreshButton = new JButton("Làm mới");
        
        // Initialize dialog components
        initDialogComponents();
    }
    
    private void initDialogComponents() {
        productDialog = new JDialog(this, "Thông tin sản phẩm", true);
        productDialog.setSize(400, 300);
        productDialog.setLocationRelativeTo(this);
        
        JPanel dialogPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel nameLabel = new JLabel("Tên sản phẩm:");
        JLabel priceLabel = new JLabel("Đơn giá:");
        JLabel stockLabel = new JLabel("Số lượng tồn:");
        
        nameField = new JTextField(20);
        priceField = new JTextField(20);
        stockField = new JTextField(20);
        
        saveButton = new JButton("Lưu");
        cancelDialogButton = new JButton("Hủy");
        
        // Add components to dialog
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialogPanel.add(nameLabel, gbc);
        
        gbc.gridx = 1;
        dialogPanel.add(nameField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialogPanel.add(priceLabel, gbc);
        
        gbc.gridx = 1;
        dialogPanel.add(priceField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        dialogPanel.add(stockLabel, gbc);
        
        gbc.gridx = 1;
        dialogPanel.add(stockField, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelDialogButton);
        
        productDialog.setLayout(new BorderLayout());
        productDialog.add(dialogPanel, BorderLayout.CENTER);
        productDialog.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.add(titleLabel);
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(productTable), BorderLayout.CENTER);
        add(topPanel, BorderLayout.SOUTH);
    }
    
    // Phương thức để load danh sách sản phẩm
    public void loadProducts(List<Product> products) {
        tableModel.setRowCount(0);
        for (Product product : products) {
            Object[] row = {
                product.getId(),
                product.getName(),
                //product.getPrice(),
                //product.getStockQuantity()
            };
            tableModel.addRow(row);
        }
    }
    
    // Phương thức để hiển thị dialog thêm/sửa sản phẩm
    public void showProductDialog(Product product, boolean isEdit) {
        if (isEdit && product != null) {
            nameField.setText(product.getName());
            //priceField.setText(String.valueOf(product.getPrice()));
            //stockField.setText(String.valueOf(product.getStockQuantity()));
            productDialog.setTitle("Chỉnh sửa sản phẩm");
        } else {
            nameField.setText("");
            priceField.setText("");
            stockField.setText("");
            productDialog.setTitle("Thêm sản phẩm mới");
        }
        
        productDialog.setVisible(true);
    }
    
    // Phương thức để lấy thông tin sản phẩm từ form
    public Product getProductFromForm() {
        Product product = new Product();
        product.setName(nameField.getText());
        
        try {
            //product.setPrice(Double.parseDouble(priceField.getText()));
            //product.setStockQuantity(Integer.parseInt(stockField.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số hợp lệ cho đơn giá và số lượng");
            return null;
        }
        
        return product;
    }
    
    // Các phương thức set ActionListener
    public void setAddButtonListener(ActionListener listener) {
        addButton.addActionListener(listener);
    }
    
    public void setEditButtonListener(ActionListener listener) {
        editButton.addActionListener(listener);
    }
    
    public void setDeleteButtonListener(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }
    
    public void setRefreshButtonListener(ActionListener listener) {
        refreshButton.addActionListener(listener);
    }
    
    public void setSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }
    
    public void setSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
    
    public void setCancelDialogButtonListener(ActionListener listener) {
        cancelDialogButton.addActionListener(listener);
    }
    
    // Getter
    public JTable getProductTable() {
        return productTable;
    }
    
    public String getSearchKeyword() {
        return searchField.getText();
    }
    
    public void closeDialog() {
        productDialog.setVisible(false);
    }
}