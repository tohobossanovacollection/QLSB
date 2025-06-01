package view;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SupermarketPOSPanel extends JPanel {
    // #region Fields
    private Map<String, Product> products;
    private List<CartItem> cart;
    private NumberFormat currencyFormat;

    // GUI Components
    private JTextField searchField;
    private JLabel productNameLabel, productPriceLabel, productBarcodeLabel;
    private view.components.TableComponent<CartItem> cartTable;
    private JLabel subtotalLabel, taxLabel, totalLabel, itemCountLabel;
    private JButton scanBtn, searchBtn, addBtn, clearBtn, checkoutBtn;
    private JPanel productDisplayPanel;
    private JLabel iconLabel;
    // #endregion

    // #region Constructor
    public SupermarketPOSPanel() {
        initializeData();
        initializeGUI();
        setupEventListeners();
    }
    // #endregion

    // #region Data Initialization
    private void initializeData() {
        products = new HashMap<>();
        products.put("1001", new Product("1001", "Coca Cola 330ml", 15000, "Nước uống"));
        products.put("1002", new Product("1002", "Bánh mì sandwich", 25000, "Thực phẩm"));
        products.put("1003", new Product("1003", "Sữa tươi TH 1L", 32000, "Sữa"));
        products.put("1004", new Product("1004", "Kẹo Alpenliebe", 8000, "Kẹo"));
        products.put("1005", new Product("1005", "Nước suối Lavie 500ml", 8000, "Nước uống"));

        cart = new ArrayList<>();
        currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    }
    // #endregion

    // #region GUI Initialization
    private void initializeGUI() {
        setLayout(new BorderLayout());

        // Tiêu đề "BÁN HÀNG" căn giữa
        JLabel titleLabel = new JLabel("BÁN HÀNG", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(16, 0, 16, 0));
        add(titleLabel, BorderLayout.NORTH);

        // Main content: chia làm 2 phần ngang
        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Panel bên trái: tìm kiếm & sản phẩm
        JPanel leftPanel = new JPanel(new BorderLayout(0, 10));
        leftPanel.add(createTopSection(), BorderLayout.NORTH);
        leftPanel.add(createProductDisplayPanel(), BorderLayout.CENTER);

        // Panel bên phải: giỏ hàng
        JPanel rightPanel = createCartSection();

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(mainPanel, BorderLayout.CENTER);

        updateCartDisplay();
    }

    private JPanel createTopSection() {
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JPanel searchSection = new JPanel(new BorderLayout());
        searchSection.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Tìm kiếm / Quét mã vạch",
            TitledBorder.LEFT, TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 12)
        ));
        searchSection.setBackground(new Color(248, 249, 250));

        JPanel searchInputPanel = new JPanel(new BorderLayout(5, 0));
        searchInputPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchInputPanel.setOpaque(false);

        searchField = new JTextField();
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(221, 221, 221), 2),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        buttonPanel.setOpaque(false);

        scanBtn = new JButton("📱 Quét mã");
        searchBtn = new JButton("🔍 Tìm");
        scanBtn.setBackground(new Color(39, 174, 96));
        scanBtn.setForeground(Color.WHITE);
        scanBtn.setFocusPainted(false);
        scanBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        searchBtn.setBackground(new Color(39, 174, 96));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));

        buttonPanel.add(scanBtn);
        buttonPanel.add(searchBtn);

        searchInputPanel.add(searchField, BorderLayout.CENTER);
        searchInputPanel.add(buttonPanel, BorderLayout.EAST);

        searchSection.add(searchInputPanel, BorderLayout.CENTER);

        //productDisplayPanel = createProductDisplayPanel();

        topSection.add(searchSection, BorderLayout.NORTH);
        //topSection.add(productDisplayPanel, BorderLayout.CENTER);

        return topSection;
    }

    private Map<JLabel, Integer> initcreateInventory() {
        Map<JLabel, Product> inventory = new HashMap<>();
        // Thêm các sản phẩm vào inventory
        //TODO:sdasdaasdasdasdasdasdasdasdasdasdasaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa
        return null;
    }

    private JPanel createProductDisplayPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(0, 120));

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);

        ImageIcon icon = new ImageIcon("ref\\coca.png");
        // if (icon.getIconWidth() > 100 || icon.getIconHeight() > 100) {
        //     icon = new ImageIcon(icon.getImage().getScaledInstance(105, 300, Image.SCALE_SMOOTH));
            
        // }
        // JLabel iconLabel = new JLabel(icon, SwingConstants.CENTER);
        // iconLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        iconLabel = new JLabel("📦");
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 40));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        contentPanel.add(iconLabel, gbc);

        productNameLabel = new JLabel("Chọn sản phẩm để hiển thị thông tin", SwingConstants.CENTER);
        productNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        productNameLabel.setForeground(new Color(127, 140, 141));
        gbc.gridy = 1;
        contentPanel.add(productNameLabel, gbc);

        productPriceLabel = new JLabel("", SwingConstants.CENTER);
        productPriceLabel.setFont(new Font("Arial", Font.BOLD, 16));
        productPriceLabel.setForeground(new Color(231, 76, 60));
        gbc.gridy = 2;
        contentPanel.add(productPriceLabel, gbc);

        productBarcodeLabel = new JLabel("", SwingConstants.CENTER);
        productBarcodeLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        productBarcodeLabel.setForeground(new Color(127, 140, 141));
        gbc.gridy = 3;
        contentPanel.add(productBarcodeLabel, gbc);

        addBtn = new JButton("➕ Thêm vào giỏ");
        addBtn.setBackground(new Color(39, 174, 96));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));
        addBtn.setVisible(false);
        gbc.gridy = 4; gbc.insets = new Insets(10, 10, 10, 10);
        contentPanel.add(addBtn, gbc);

        panel.add(contentPanel, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createCartSection() {
        JPanel cartSection = new JPanel(new BorderLayout());
        cartSection.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "👜 Giỏ hàng",
            TitledBorder.LEFT, TitledBorder.TOP
            //new Font("Arial", Font.BOLD, 12)
        ));

        JPanel cartHeader = new JPanel(new BorderLayout());
        cartHeader.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JLabel cartTitle = new JLabel("Danh sách sản phẩm");
        cartTitle.setFont(new Font("Arial", Font.BOLD, 14));

        itemCountLabel = new JLabel("0 sản phẩm");
        itemCountLabel.setForeground(new Color(127, 140, 141));

        cartHeader.add(cartTitle, BorderLayout.WEST);
        cartHeader.add(itemCountLabel, BorderLayout.EAST);

        String[] columnNames = {"Tên sản phẩm", "Giá", "SL", "Thành tiền"};
        cartTable = new view.components.TableComponent<>(columnNames);

        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setPreferredSize(new Dimension(0, 200));

        JPanel totalsPanel = createTotalsPanel();
        JPanel actionPanel = createActionPanel();

        cartSection.add(cartHeader, BorderLayout.NORTH);
        cartSection.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(totalsPanel, BorderLayout.NORTH);
        bottomPanel.add(actionPanel, BorderLayout.SOUTH);

        cartSection.add(bottomPanel, BorderLayout.SOUTH);

        return cartSection;
    }

    private JPanel createTotalsPanel() {
        JPanel totalsPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        totalsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        totalsPanel.add(new JLabel("Tạm tính:"));
        subtotalLabel = new JLabel("0₫");
        subtotalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        totalsPanel.add(subtotalLabel);

        totalsPanel.add(new JLabel("Thuế (10%):"));
        taxLabel = new JLabel("0₫");
        taxLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        totalsPanel.add(taxLabel);

        JLabel totalTextLabel = new JLabel("TỔNG CỘNG:");
        totalTextLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalsPanel.add(totalTextLabel);

        totalLabel = new JLabel("0₫");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(new Color(231, 76, 60));
        totalLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        totalsPanel.add(totalLabel);

        return totalsPanel;
    }

    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        actionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));

        checkoutBtn = new JButton("✔️ Thanh toán");
        checkoutBtn.setBackground(new Color(231, 76, 60));
        checkoutBtn.setForeground(Color.WHITE);
        checkoutBtn.setFocusPainted(false);
        //checkoutBtn.setFont(new Font("Arial", Font.BOLD, 14));

        clearBtn = new JButton("✖️ Xóa hết");
        clearBtn.setBackground(new Color(149, 165, 166));
        clearBtn.setForeground(Color.WHITE);
        clearBtn.setFocusPainted(false);
        //clearBtn.setFont(new Font("Arial", Font.BOLD, 14));

        JButton increaseBtn = new JButton("➕ Tăng SL");
        increaseBtn.setBackground(new Color(52, 152, 219));
        increaseBtn.setForeground(Color.WHITE);
        increaseBtn.setFocusPainted(false);

        JButton decreaseBtn = new JButton("➖ Giảm SL");
        decreaseBtn.setBackground(new Color(52, 152, 219));
        decreaseBtn.setForeground(Color.WHITE);
        decreaseBtn.setFocusPainted(false);

        actionPanel.add(checkoutBtn);
        actionPanel.add(clearBtn);
        actionPanel.add(increaseBtn);
        actionPanel.add(decreaseBtn);

        // Add listeners for quantity buttons
        increaseBtn.addActionListener(e -> adjustQuantity(1));
        decreaseBtn.addActionListener(e -> adjustQuantity(-1));

        return actionPanel;
    }
    // #endregion

    // #region Event Listeners
    private void setupEventListeners() {
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchProduct();
                }
            }
        });

        scanBtn.addActionListener(e -> scanBarcode());
        searchBtn.addActionListener(e -> searchProduct());
        addBtn.addActionListener(e -> addToCart());
        clearBtn.addActionListener(e -> clearCart());
        checkoutBtn.addActionListener(e -> checkout());
    }
    // #endregion

    // #region Business Logic
    private void scanBarcode() {
        String[] barcodes = products.keySet().toArray(new String[0]);
        String randomBarcode = barcodes[new Random().nextInt(barcodes.length)];
        searchField.setText(randomBarcode);
        searchProduct();
    }

    private void searchProduct() {
        String input = searchField.getText().trim();
        Product product = findProduct(input);

        if (product != null) {
            displayProduct(product);
        } else {
            displayNoProduct();
        }
    }

    private Product findProduct(String input) {
        Product product = products.get(input);
        if (product != null) return product;
        for (Product p : products.values()) {
            if (p.getName().toLowerCase().contains(input.toLowerCase())) {
                return p;
            }
        }
        return null;
    }

    private void displayProduct(Product product) {
        iconLabel.setText(""); // Reset icon
        iconLabel.setIcon(new ImageIcon("ref\\ao1.png"));
        productNameLabel.setText(product.getName());
        productNameLabel.setForeground(Color.BLACK);
        productPriceLabel.setText(formatCurrency(product.getPrice()));
        productBarcodeLabel.setText("Mã: " + product.getBarcode());
        addBtn.setVisible(true);
        addBtn.setActionCommand(product.getBarcode());
    }

    private void displayNoProduct() {
        productNameLabel.setText("Không tìm thấy sản phẩm");
        productNameLabel.setForeground(new Color(231, 76, 60));
        productPriceLabel.setText("");
        productBarcodeLabel.setText("");
        addBtn.setVisible(false);
    }

    private void addToCart() {
        String barcode = addBtn.getActionCommand();
        Product product = products.get(barcode);

        if (product == null) return;

        CartItem existingItem = findCartItem(barcode);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
        } else {
            cart.add(new CartItem(product, 1));
        }

        updateCartDisplay();
        searchField.setText("");

        productNameLabel.setText("✅ Đã thêm vào giỏ hàng!");
        productNameLabel.setForeground(new Color(39, 174, 96));
        productPriceLabel.setText("");
        productBarcodeLabel.setText("");
        addBtn.setVisible(false);

        javax.swing.Timer timer = new javax.swing.Timer(2000, e -> {
            productNameLabel.setText("Chọn sản phẩm để hiển thị thông tin");
            productNameLabel.setForeground(new Color(127, 140, 141));
        });
        timer.setRepeats(false);
        timer.start();
    }

    private CartItem findCartItem(String barcode) {
        return cart.stream()
                .filter(item -> item.getProduct().getBarcode().equals(barcode))
                .findFirst()
                .orElse(null);
    }

    private void adjustQuantity(int change) {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < cart.size()) {
            CartItem item = cart.get(selectedRow);
            int newQuantity = item.getQuantity() + change;

            if (newQuantity <= 0) {
                cart.remove(selectedRow);
            } else {
                item.setQuantity(newQuantity);
            }

            updateCartDisplay();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sản phẩm cần điều chỉnh!",
                    "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void clearCart() {
        if (!cart.isEmpty()) {
            int result = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc muốn xóa tất cả sản phẩm trong giỏ hàng?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                cart.clear();
                updateCartDisplay();
            }
        }
    }

    private void checkout() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng trống!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        StringBuilder receipt = new StringBuilder();
        receipt.append("========================================\n");
        receipt.append("          🏪 SIÊU THỊ ABC\n");
        receipt.append("     123 Đường ABC, Quận 1, TP.HCM\n");
        receipt.append("         Tel: 028.1234.5678\n");
        receipt.append("========================================\n");
        receipt.append("Ngày: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))).append("\n");
        receipt.append("Nhân viên: Nguyễn Văn A | Quầy: 01\n");
        receipt.append("========================================\n");

        double subtotal = 0;
        for (CartItem item : cart) {
            double itemTotal = item.getProduct().getPrice() * item.getQuantity();
            subtotal += itemTotal;

            receipt.append(String.format("%-20s %2dx%s\n",
                    item.getProduct().getName(),
                    item.getQuantity(),
                    formatCurrency(item.getProduct().getPrice())));
            receipt.append(String.format("%30s %s\n", "", formatCurrency(itemTotal)));
        }

        double tax = subtotal * 0.1;
        double total = subtotal + tax;

        receipt.append("========================================\n");
        receipt.append(String.format("%-20s %s\n", "Tạm tính:", formatCurrency(subtotal)));
        receipt.append(String.format("%-20s %s\n", "Thuế (10%):", formatCurrency(tax)));
        receipt.append("----------------------------------------\n");
        receipt.append(String.format("%-20s %s\n", "TỔNG CỘNG:", formatCurrency(total)));
        receipt.append("========================================\n");
        receipt.append("           Cảm ơn quý khách!\n");
        receipt.append("            Hẹn gặp lại!\n");
        receipt.append("========================================\n");

        JTextArea receiptArea = new JTextArea(receipt.toString());
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        receiptArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(receiptArea);
        scrollPane.setPreferredSize(new Dimension(400, 500));

        int result = JOptionPane.showConfirmDialog(this, scrollPane,
                "Hóa đơn thanh toán - In hóa đơn?",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE);

        if (result == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Hóa đơn đã được in thành công!",
                    "Thành công", JOptionPane.INFORMATION_MESSAGE);

            cart.clear();
            updateCartDisplay();
        }
    }

    private void updateCartDisplay() {
        cartTable.clearTable();
        for (CartItem item : cart) {
            Object[] row = {
                item.getProduct().getName(),
                formatCurrency(item.getProduct().getPrice()),
                item.getQuantity(),
                formatCurrency(item.getProduct().getPrice() * item.getQuantity())
            };
            cartTable.addRow(row);
        }

        double subtotal = cart.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
        double tax = subtotal * 0.1;
        double total = subtotal + tax;

        subtotalLabel.setText(formatCurrency(subtotal));
        taxLabel.setText(formatCurrency(tax));
        totalLabel.setText(formatCurrency(total));

        int totalItems = cart.stream().mapToInt(CartItem::getQuantity).sum();
        itemCountLabel.setText(totalItems + " sản phẩm");
    }

    private String formatCurrency(double amount) {
        return String.format("%,.0f₫", amount);
    }
    // #endregion

    // #region Public API (optional: getter/setter, load data, etc.)
    // Bạn có thể bổ sung các phương thức public để load sản phẩm, lấy cart, v.v.
    // #endregion
}

// #region Product & CartItem Classes
class Product {
    private String barcode;
    private String name;
    private double price;
    private String category;

    public Product(String barcode, String name, double price, String category) {
        this.barcode = barcode;
        this.name = name;
        this.price = price;
        this.category = category;
    }

    public String getBarcode() { return barcode; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public String getCategory() { return category; }
}

class CartItem {
    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() { return product; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
}
// #endregion
