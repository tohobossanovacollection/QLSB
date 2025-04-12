package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class SalesView extends JFrame {
    private JLabel titleLabel, customerLabel, totalLabel;
    private JComboBox<Customer> customerComboBox;
    private JTable productTable;
    private DefaultTableModel tableModel;
    private JButton addItemButton, removeItemButton, checkoutButton, cancelButton;
    private JTextField totalField;
    private JPanel cartPanel;
    
    public SalesView() {
        initComponents();
        setupLayout();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
        setTitle("Bán hàng");
    }
    
    private void initComponents() {
        titleLabel = new JLabel("Bán hàng");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        customerLabel = new JLabel("Khách hàng:");
        customerComboBox = new JComboBox<>();
        
        // Bảng sản phẩm
        String[] columnNames = {"Mã", "Tên sản phẩm", "Đơn giá", "Số lượng", "Thành tiền"};
        tableModel = new DefaultTableModel(columnNames, 0);
        productTable = new JTable(tableModel);
        
        addItemButton = new JButton("Thêm sản phẩm");
        removeItemButton = new JButton("Xóa sản phẩm");
        checkoutButton = new JButton("Thanh toán");
        cancelButton = new JButton("Hủy");
        
        totalLabel = new JLabel("Tổng tiền:");
        totalField = new JTextField(15);
        totalField.setEditable(false);
        
        cartPanel = new JPanel(new BorderLayout());
        cartPanel.setBorder(BorderFactory.createTitledBorder("Giỏ hàng"));
        cartPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.add(titleLabel);
        
        JPanel customerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        customerPanel.add(customerLabel);
        customerPanel.add(customerComboBox);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addItemButton);
        buttonPanel.add(removeItemButton);
        
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        footerPanel.add(totalLabel);
        footerPanel.add(totalField);
        footerPanel.add(checkoutButton);
        footerPanel.add(cancelButton);
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(customerPanel, BorderLayout.NORTH);
        mainPanel.add(cartPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(headerPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    // Phương thức để load danh sách khách hàng vào ComboBox
    public void loadCustomers(List<Customer> customers) {
        customerComboBox.removeAllItems();
        for (Customer customer : customers) {
            customerComboBox.addItem(customer);
        }
    }
    
    // Phương thức để thêm sản phẩm vào giỏ hàng
    public void addProductToCart(OrderItem item) {
        Object[] row = {
            item.getProduct().getId(),
            item.getProduct().getName(),
            item.getPrice(),
            item.getQuantity(),
            item.getPrice() * item.getQuantity()
        };
        tableModel.addRow(row);
        updateTotal();
    }
    
    // Phương thức để xóa sản phẩm khỏi giỏ hàng
    public void removeProductFromCart(int selectedRow) {
        if (selectedRow >= 0 && selectedRow < tableModel.getRowCount()) {
            tableModel.removeRow(selectedRow);
            updateTotal();
        }
    }
    
    // Phương thức để cập nhật tổng tiền
    private void updateTotal() {
        double total = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            total += (double) tableModel.getValueAt(i, 4);
        }
        totalField.setText(String.format("%.2f", total));
    }
    
    // Phương thức để lấy thông tin giỏ hàng
    public Order getOrderFromCart() {
        Order order = new Order();
        if (customerComboBox.getSelectedItem() != null) {
            order.setCustomer((Customer) customerComboBox.getSelectedItem());
        }
        
        // Tạo danh sách OrderItem từ table
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            OrderItem item = new OrderItem();
            Product product = new Product();
            product.setId((int) tableModel.getValueAt(i, 0));
            product.setName((String) tableModel.getValueAt(i, 1));
            product.setPrice((double) tableModel.getValueAt(i, 2));
            
            item.setProduct(product);
            item.setQuantity((int) tableModel.getValueAt(i, 3));
            item.setPrice((double) tableModel.getValueAt(i, 2));
            
            order.addItem(item);
        }
        
        order.setTotalAmount(Double.parseDouble(totalField.getText()));
        return order;
    }
    
    // Phương thức để xóa giỏ hàng
    public void clearCart() {
        tableModel.setRowCount(0);
        totalField.setText("0.00");
        customerComboBox.setSelectedIndex(-1);
    }
    
    // Các phương thức set ActionListener
    public void setAddItemButtonListener(ActionListener listener) {
        addItemButton.addActionListener(listener);
    }
    
    public void setRemoveItemButtonListener(ActionListener listener) {
        removeItemButton.addActionListener(listener);
    }
    
    public void setCheckoutButtonListener(ActionListener listener) {
        checkoutButton.addActionListener(listener);
    }
    
    public void setCancelButtonListener(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }
    
    // Getter
    public JTable getProductTable() {
        return productTable;
    }
    
    public Customer getSelectedCustomer() {
        return (Customer) customerComboBox.getSelectedItem();
    }
}
