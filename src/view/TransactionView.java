package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;

public class TransactionView extends JFrame {
    private JLabel titleLabel;
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, deleteButton, filterButton, exportButton;
    private JLabel fromDateLabel, toDateLabel, typeLabel;
    private JSpinner fromDateSpinner, toDateSpinner;
    private JComboBox<String> typeComboBox;
    
    // For add/edit transaction
    private JDialog transactionDialog;
    private JComboBox<String> transactionTypeComboBox;
    private JTextField amountField, descriptionField;
    private JSpinner dateSpinner;
    private JButton saveButton, cancelDialogButton;
    
    public TransactionView() {
        initComponents();
        setupLayout();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
        setTitle("Quản lý thu chi");
    }
    
    private void initComponents() {
        titleLabel = new JLabel("Quản lý thu chi");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Bảng giao dịch
        String[] columnNames = {"Mã", "Loại", "Số tiền", "Ngày", "Mô tả", "Người thực hiện"};
        tableModel = new DefaultTableModel(columnNames, 0);
        transactionTable = new JTable(tableModel);
        
        // Filter components
        fromDateLabel = new JLabel("Từ ngày:");
        toDateLabel = new JLabel("Đến ngày:");
        typeLabel = new JLabel("Loại giao dịch:");
        
        SpinnerDateModel fromModel = new SpinnerDateModel();
        fromDateSpinner = new JSpinner(fromModel);
        fromDateSpinner.setEditor(new JSpinner.DateEditor(fromDateSpinner, "dd/MM/yyyy"));
        
        SpinnerDateModel toModel = new SpinnerDateModel();
        toDateSpinner = new JSpinner(toModel);
        toDateSpinner.setEditor(new JSpinner.DateEditor(toDateSpinner, "dd/MM/yyyy"));
        
        String[] transactionTypes = {"Tất cả", "Thu", "Chi"};
        typeComboBox = new JComboBox<>(transactionTypes);
        
        // Action buttons
        addButton = new JButton("Thêm mới");
        editButton = new JButton("Chỉnh sửa");
        deleteButton = new JButton("Xóa");
        filterButton = new JButton("Lọc");
        exportButton = new JButton("Xuất báo cáo");
        
        // Initialize dialog components
        initDialogComponents();
    }
    
    private void initDialogComponents() {
        transactionDialog = new JDialog(this, "Thông tin giao dịch", true);
        transactionDialog.setSize(400, 300);
        transactionDialog.setLocationRelativeTo(this);
        
        JPanel dialogPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel typeLabel = new JLabel("Loại giao dịch:");
        JLabel amountLabel = new JLabel("Số tiền:");
        JLabel dateLabel = new JLabel("Ngày:");
        JLabel descriptionLabel = new JLabel("Mô tả:");
        
        String[] types = {"Thu", "Chi"};
        transactionTypeComboBox = new JComboBox<>(types);
        amountField = new JTextField(20);
        
        SpinnerDateModel model = new SpinnerDateModel();
        dateSpinner = new JSpinner(model);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy"));
        
        descriptionField = new JTextField(20);
        
        saveButton = new JButton("Lưu");
        cancelDialogButton = new JButton("Hủy");
        
        // Add components to dialog
        gbc.gridx = 0;
        gbc.gridy = 0;
        dialogPanel.add(typeLabel, gbc);
        
        gbc.gridx = 1;
        dialogPanel.add(transactionTypeComboBox, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        dialogPanel.add(amountLabel, gbc);
        
        gbc.gridx = 1;
        dialogPanel.add(amountField, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        dialogPanel.add(dateLabel, gbc);
        
        gbc.gridx = 1;
        dialogPanel.add(dateSpinner, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        dialogPanel.add(descriptionLabel, gbc);
        
        gbc.gridx = 1;
        dialogPanel.add(descriptionField, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelDialogButton);
        
        transactionDialog.setLayout(new BorderLayout());
        transactionDialog.add(dialogPanel, BorderLayout.CENTER);
        transactionDialog.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.add(titleLabel);
        
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(fromDateLabel);
        filterPanel.add(fromDateSpinner);
        filterPanel.add(toDateLabel);
        filterPanel.add(toDateSpinner);
        filterPanel.add(typeLabel);
        filterPanel.add(typeComboBox);
        filterPanel.add(filterButton);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exportButton);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(filterPanel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(transactionTable), BorderLayout.CENTER);
        add(topPanel, BorderLayout.SOUTH);
    }
    
    // Phương thức để load danh sách giao dịch
    public void loadTransactions(List<Transaction> transactions) {
        tableModel.setRowCount(0);
        for (Transaction transaction : transactions) {
            Object[] row = {
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getDate(),
                transaction.getDescription(),
                transaction.getUser().getName()
            };
            tableModel.addRow(row);
        }
    }
    
    // Phương thức để hiển thị dialog thêm/sửa giao dịch
    public void showTransactionDialog(Transaction transaction, boolean isEdit) {
        if (isEdit && transaction != null) {
            transactionTypeComboBox.setSelectedItem(transaction.getType());
            amountField.setText(String.valueOf(transaction.getAmount()));
            dateSpinner.setValue(transaction.getDate());
            descriptionField.setText(transaction.getDescription());
            transactionDialog.setTitle("Chỉnh sửa giao dịch");
        } else {
            transactionTypeComboBox.setSelectedIndex(0);
            amountField.setText("");
            dateSpinner.setValue(new Date());
            descriptionField.setText("");
            transactionDialog.setTitle("Thêm giao dịch mới");
        }
        
        transactionDialog.setVisible(true);
    }
    
    // Phương thức để lấy thông tin giao dịch từ form
    public Transaction getTransactionFromForm() {
        Transaction transaction = new Transaction();
        transaction.setType((String) transactionTypeComboBox.getSelectedItem());
        
        try {
            transaction.setAmount(Double.parseDouble(amountField.getText()));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số tiền hợp lệ");
            return null;
        }
        
        transaction.setDate((Date) dateSpinner.getValue());
        transaction.setDescription(descriptionField.getText());
        
        return transaction;
    }
    
    // Các phương thức lấy thông tin lọc
    public Date getFilterFromDate() {
        return (Date) fromDateSpinner.getValue();
    }
    
    public Date getFilterToDate() {
        return (Date) toDateSpinner.getValue();
    }
    
    public String getFilterType() {
        return (String) typeComboBox.getSelectedItem();
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
    
    public void setFilterButtonListener(ActionListener listener) {
        filterButton.addActionListener(listener);
    }
    
    public void setExportButtonListener(ActionListener listener) {
        exportButton.addActionListener(listener);
    }
    
    public void setSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
    
    public void setCancelDialogButtonListener(ActionListener listener) {
        cancelDialogButton.addActionListener(listener);
    }
    
    // Getter
    public JTable getTransactionTable() {
        return transactionTable;
    }
    
    public void closeDialog() {
        transactionDialog.setVisible(false);
    }
}
