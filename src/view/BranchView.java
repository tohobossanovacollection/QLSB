package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import model.Branch;

public class BranchView extends JFrame {
    private JLabel titleLabel;
    private JTable branchTable;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, deleteButton, viewDetailsButton;
    
    // For add/edit branch
    private JDialog branchDialog;
    private JTextField nameField, addressField;
    private JButton saveButton, cancelDialogButton;
    
    public BranchView() {
        initComponents();
        setupLayout();
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setTitle("Quản lý chi nhánh");
    }
    
    private void initComponents() {
        titleLabel = new JLabel("Quản lý chi nhánh");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Bảng chi nhánh
        String[] columnNames = {"Mã", "Tên chi nhánh", "Địa chỉ", "Số lượng sân"};
        tableModel = new DefaultTableModel(columnNames, 0);
        branchTable = new JTable(tableModel);
        
        // Action buttons
        addButton = new JButton("Thêm mới");
        editButton = new JButton("Chỉnh sửa");
        deleteButton = new JButton("Xóa");
        viewDetailsButton = new JButton("Xem chi tiết");
        
        // Initialize dialog components
        initDialogComponents();
    }
    
    private void initDialogComponents() {
        branchDialog = new JDialog(this, "Thông tin chi nhánh", true);
        branchDialog.setSize(400, 200);
        branchDialog.setLocationRelativeTo(this);
        
        JPanel dialogPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        
        JLabel nameLabel = new JLabel("Tên chi nhánh:");
        JLabel addressLabel = new JLabel("Địa chỉ:");
        
        nameField = new JTextField(20);
        addressField = new JTextField(20);
        
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
        dialogPanel.add(addressLabel, gbc);
        
        gbc.gridx = 1;
        dialogPanel.add(addressField, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelDialogButton);
        
        branchDialog.setLayout(new BorderLayout());
        branchDialog.add(dialogPanel, BorderLayout.CENTER);
        branchDialog.add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        headerPanel.add(titleLabel);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(viewDetailsButton);
        
        add(headerPanel, BorderLayout.NORTH);
        add(new JScrollPane(branchTable), BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    // Phương thức để load danh sách chi nhánh
    public void loadBranches(List<Branch> branches) {
        tableModel.setRowCount(0);
        for (Branch branch : branches) {
            Object[] row = {
                branch.getId(),
                branch.getName(),
                branch.getAddress(),
                branch.getPitches().size()
            };
            tableModel.addRow(row);
        }
    }
    
    // Phương thức để hiển thị dialog thêm/sửa chi nhánh
    public void showBranchDialog(Branch branch, boolean isEdit) {
        if (isEdit && branch != null) {
            nameField.setText(branch.getName());
            addressField.setText(branch.getAddress());
            branchDialog.setTitle("Chỉnh sửa chi nhánh");
        } else {
            nameField.setText("");
            addressField.setText("");
            branchDialog.setTitle("Thêm chi nhánh mới");
        }
        
        branchDialog.setVisible(true);
    }
    
    // Phương thức để lấy thông tin chi nhánh từ form
    public Branch getBranchFromForm() {
        Branch branch = new Branch(1, null, null, null,null); // ID sẽ được tạo trong DB
        branch.setName(nameField.getText());
        branch.setAddress(addressField.getText());
        return branch;
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
    
    public void setViewDetailsButtonListener(ActionListener listener) {
        viewDetailsButton.addActionListener(listener);
    }
    
    public void setSaveButtonListener(ActionListener listener) {
        saveButton.addActionListener(listener);
    }
    
    public void setCancelDialogButtonListener(ActionListener listener) {
        cancelDialogButton.addActionListener(listener);
    }
    
    // Getter
    public JTable getBranchTable() {
        return branchTable;
    }
    
    public void closeDialog() {
        branchDialog.setVisible(false);
    }
}
