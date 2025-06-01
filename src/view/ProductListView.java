package view;

import model.Product;
import java.util.List;
import view.components.TableComponent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ProductListView extends JPanel {
    private JTextField searchField;
    private JButton searchButton;
    private JButton addProductButton;
    private TableComponent<Object> productTable;
    private JButton editButton;
    private JButton deleteButton;
    private JButton refreshButton;
    private JButton saveAddButton = new JButton("Thêm");
    private JButton saveChangeButton = new JButton("Lưu");
    private JButton cancelButton = new JButton("Hủy");
    private view.components.DialogComponent dialog;

    public ProductListView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("DANH SÁCH SẢN PHẨM", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        searchButton = new JButton("Tìm");
        searchPanel.add(searchButton);

        addProductButton = new JButton("Thêm Sản Phẩm");
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButtonPanel.add(addProductButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(addButtonPanel, BorderLayout.EAST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Product table
        String[] columnNames = {"Mã sản phẩm", "Tên sản phẩm", "Giá", "Loại", "Đang bán"};
        productTable = new TableComponent<>(columnNames);
        productTable.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Button panel (right side)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        editButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        refreshButton = new JButton("Làm mới");
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        add(buttonPanel, BorderLayout.EAST);

        // Dialog thêm/sửa sản phẩm
        dialog = new view.components.DialogComponent("Thông tin sản phẩm", 400, 300);
    }

    public int getSelectedProductIndex() {
        return productTable.getSelectedRow();
    }

    public String getSearchText() {
        return searchField.getText();
    }

    public void setAddProductAction(ActionListener listener) {
        addProductButton.addActionListener(listener);
    }

    public void setEditAction(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void setDeleteAction(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void setRefreshAction(ActionListener listener) {
        refreshButton.addActionListener(listener);
    }

    public void setSearchAction(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public String getSelectedProductId() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            return productTable.getValueAt(selectedRow, 0).toString(); // Mã sản phẩm
        }
        return null;
    }

    public String getSelectedProductName() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            return productTable.getValueAt(selectedRow, 1).toString(); // Tên sản phẩm
        }
        return null;
    }

    public String getSelectedProductPrice() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            return productTable.getValueAt(selectedRow, 2).toString(); // Giá
        }
        return null;
    }

    public String getSelectedProductCategory() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            return productTable.getValueAt(selectedRow, 3).toString(); // Loại
        }
        return null;
    }

    public boolean getSelectedProductActive() {
        int selectedRow = productTable.getSelectedRow();
        if (selectedRow != -1) {
            Object value = productTable.getValueAt(selectedRow, 4);
            if (value instanceof Boolean) return (Boolean) value;
            return Boolean.parseBoolean(value.toString());
        }
        return false;
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    public void initDialog(String name, String price, String category, boolean active) {
        JPanel dialogPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField categoryField = new JTextField();
        JCheckBox activeCheck = new JCheckBox("Đang bán");
        nameField.setText(name);
        priceField.setText(price);
        categoryField.setText(category);
        activeCheck.setSelected(active);

        dialogPanel.add(new JLabel("Tên sản phẩm:"));
        dialogPanel.add(nameField);
        dialogPanel.add(new JLabel("Giá:"));
        dialogPanel.add(priceField);
        dialogPanel.add(new JLabel("Loại:"));
        dialogPanel.add(categoryField);
        dialogPanel.add(new JLabel("Trạng thái:"));
        dialogPanel.add(activeCheck);

        dialog.registerComponent("name", nameField);
        dialog.registerComponent("price", priceField);
        dialog.registerComponent("category", categoryField);
        dialog.registerComponent("active", activeCheck);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveChangeButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(dialogPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setContentPane(mainPanel);
    }

    public void showDialog(boolean value) {
        this.dialog.setVisible(value);
    }

    public String getUpdatedName() {
        return dialog.getFieldValue("name");
    }
    public String getUpdatedPrice() {
        return dialog.getFieldValue("price");
    }
    public String getUpdatedCategory() {
        return dialog.getFieldValue("category");
    }
    public boolean getUpdatedActive() {
        return dialog.getCheckBoxValue("active");
    }
    public void setSaveEditAction(ActionListener listener) {
        saveChangeButton.addActionListener(listener);
    }
    public void setCancelEditAction(ActionListener listener) {
        cancelButton.addActionListener(listener);
    }

    public void loadDataToTable(List<Product> products) {
        productTable.clearTable();
        for (Product product : products) {
            Object[] rowData = {
                product.getId(),
                product.getName(),
                //product.getPrice(),
                product.getCategory(),
                //product.isActive()
            };
            productTable.addRow(rowData);
        }
    }

    // public Product getUpdatedProduct() {
    //     return new Product(
    //         Integer.parseInt(getSelectedProductId()),
    //         getUpdatedName(),
    //         Double.parseDouble(getUpdatedPrice()),
    //         getUpdatedCategory(),
    //         getUpdatedActive()
    //     );
    // }

    // public Product getNewProduct() {
    //     return new Product(
    //         0,
    //         dialog.getFieldValue("name"),
    //         Double.parseDouble(dialog.getFieldValue("price")),
    //         dialog.getFieldValue("category"),
    //         dialog.getCheckBoxValue("active")
    //     );
    // }

    public void setSaveAddAction(ActionListener listener) {
        saveAddButton.addActionListener(listener);
    }
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    public void initDialogForAdding() {
        JPanel dialogPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField nameField = new JTextField();
        JTextField priceField = new JTextField();
        JTextField categoryField = new JTextField();
        JCheckBox activeCheck = new JCheckBox("Đang bán");
        activeCheck.setSelected(true);

        dialogPanel.add(new JLabel("Tên sản phẩm:"));
        dialogPanel.add(nameField);
        dialogPanel.add(new JLabel("Giá:"));
        dialogPanel.add(priceField);
        dialogPanel.add(new JLabel("Loại:"));
        dialogPanel.add(categoryField);
        dialogPanel.add(new JLabel("Trạng thái:"));
        dialogPanel.add(activeCheck);

        dialog.registerComponent("name", nameField);
        dialog.registerComponent("price", priceField);
        dialog.registerComponent("category", categoryField);
        dialog.registerComponent("active", activeCheck);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveAddButton);
        buttonPanel.add(cancelButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(dialogPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        dialog.setContentPane(mainPanel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView("ADMIN");
            mainView.addPanel(new ProductListView(), "product");
            mainView.setVisible(true);
            mainView.showPanel("product");
        });
    }
}
