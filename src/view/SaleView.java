package view;

import view.components.TableComponent;
import service.*;
import model.*;
import utils.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import controller.SaleController;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class SaleView extends JPanel {
    private Map<String, Product> products = new HashMap<>(); // Product
    private List<InvoiceItem> cart;

    // GUI Components
    private JTextField searchField;
    private JLabel productNameLabel, productPriceLabel, productidproductLabel;
    private TableComponent<InvoiceItem> cartTable;
    private JLabel subtotalLabel, taxLabel, totalLabel, itemCountLabel;
    private JButton showlistBtn, searchBtn, addBtn, clearBtn, checkoutBtn;
    private CardLayout cardLayout = new CardLayout();
    private JPanel productDisplayPanel = new JPanel(cardLayout);
    private JLabel iconLabel;
    private JScrollPane ListscrollPane;
    private Map<String, ImageIcon> productIcons;
    private TableComponent<Product> productTable;
    private JComboBox<Customer> customerComboBox;
    private JButton addCustomerBtn;
    private JTextArea noteField;
    private JTextField discountField;
    private JComboBox <Pitch> Pitch_IdComboBox;
    

    JButton btnOk = new JButton("Ok");
    JButton btnCancel = new JButton("Cancel");



    
    public SaleView() {
        //initializeData();
        initializeGUI();
        setupEventListeners();
        productIcons = createProductIcons();
    }
    private SaleController saleController;
    public void setSaleController(SaleController controller) {
        this.saleController = controller;
    }


    // #region Data Initialization
    public void initializeData(List<Product> products) {
        //InventoryService productService = new InventoryService();
        for (Product product : products) {
            this.products.put(String.valueOf(product.getId()), product);
        }
        cart = new ArrayList<>();
    }

    public void loadProductList(List<Product> dbproducts){
        for( Product product : dbproducts) {
            Object[] row = {
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getCurrentStock(),
                product.getUnit()
            };
            productTable.addRow(row);
        }
    }
    // Khởi tạo map ánh xạ idproduct sản phẩm với icon tương ứng
    private Map<String, ImageIcon> createProductIcons() {
        Map<String, ImageIcon> iconMap = new HashMap<>();
        iconMap.put("1", new ImageIcon("ref\\aqua.png"));         // Nước khoáng Aquafina
        iconMap.put("2", new ImageIcon("ref\\coca.png"));         // Nước ngọt Coca-Cola
        iconMap.put("3", new ImageIcon("ref\\redbull.png"));      // Nước tăng lực Redbull
        iconMap.put("4", new ImageIcon("ref\\ao1.png"));          // Áo đá bóng loại 1
        iconMap.put("5", new ImageIcon("ref\\ao2.png"));          // Áo đá bóng loại 2
        iconMap.put("6", new ImageIcon("ref\\quan1.png"));        // Quần đá bóng loại 1
        iconMap.put("7", new ImageIcon("ref\\tat.png"));          // Tất bóng đá cao cấp
        iconMap.put("8", new ImageIcon("ref\\bangkeo.png"));      // Băng keo thể thao
        iconMap.put("9", new ImageIcon("ref\\gang.png"));     // Găng tay thủ môn loại 1
        iconMap.put("10", new ImageIcon("ref\\bong.png"));     // Bóng đá số 5 Adidas
        return iconMap;
    }

    public void loadCustomerCb(List<Customer> customers) {
        customerComboBox.removeAllItems();
        for (Customer customer : customers) {
            customerComboBox.addItem(customer);
        }
        if (customerComboBox.getItemCount() > 0) {
            customerComboBox.setSelectedIndex(0);
        }
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
        productDisplayPanel.add(createProductDisplayPanel(), "default");
        productDisplayPanel.add(createProductList(), "productList");
        JPanel leftPanel = new JPanel(new BorderLayout(0, 10));
        leftPanel.add(createTopSection(), BorderLayout.NORTH);
        leftPanel.add(productDisplayPanel, BorderLayout.CENTER);

        // Panel bên phải: giỏ hàng
        JPanel rightPanel = createCartSection();

        mainPanel.add(leftPanel);
        mainPanel.add(rightPanel);

        add(mainPanel, BorderLayout.CENTER);

        //updateCartDisplay();
    }

    private void defaultSelectItems(){
        iconLabel.setIcon(null);
        iconLabel.setText("📦");
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 40));
        productNameLabel.setText("Chọn sản phẩm để hiển thị thông tin");
        productNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        productNameLabel.setForeground(new Color(127, 140, 141));
    }

    private JPanel createTopSection() {
        JPanel topSection = new JPanel(new BorderLayout());
        topSection.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JPanel searchSection = new JPanel(new BorderLayout());
        searchSection.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Tìm kiếm / Xem danh sách sản phẩm",
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
        showlistBtn = new JButton("📱 Xem danh sách");
        showlistBtn.setBackground(new Color(39, 174, 96));
        showlistBtn.setForeground(Color.WHITE);
        showlistBtn.setFocusPainted(false);
        showlistBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        searchBtn = new JButton("🔍 Tìm");
        searchBtn.setBackground(new Color(39, 174, 96));
        searchBtn.setForeground(Color.WHITE);
        searchBtn.setFocusPainted(false);
        searchBtn.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        buttonPanel.add(showlistBtn);
        buttonPanel.add(searchBtn);

        searchInputPanel.add(searchField, BorderLayout.CENTER);
        searchInputPanel.add(buttonPanel, BorderLayout.EAST);

        searchSection.add(searchInputPanel, BorderLayout.CENTER);
        topSection.add(searchSection, BorderLayout.NORTH);
        return topSection;
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

        iconLabel = new JLabel();
        iconLabel.setText("📦");
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

        productidproductLabel = new JLabel("", SwingConstants.CENTER);
        productidproductLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        productidproductLabel.setForeground(new Color(127, 140, 141));
        gbc.gridy = 3;
        contentPanel.add(productidproductLabel, gbc);

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
        ));

        // Thêm panel chọn khách hàng vào đầu giỏ hàng (dễ thấy)
        JPanel customerPanel = new JPanel(new BorderLayout(5, 0));
        customerComboBox = new JComboBox<>();
        customerComboBox.setPreferredSize(new Dimension(200, 32));
        addCustomerBtn = new JButton("+ Thêm KH");
        addCustomerBtn.setPreferredSize(new Dimension(110, 32));
        customerPanel.add(new JLabel("Khách hàng:"), BorderLayout.WEST);
        customerPanel.add(customerComboBox, BorderLayout.CENTER);
        customerPanel.add(addCustomerBtn, BorderLayout.EAST);
        customerPanel.setOpaque(false);
        customerPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel pitchRow = new JPanel(new BorderLayout(5, 0));
        Pitch_IdComboBox = new JComboBox<>();
        Pitch_IdComboBox.setPreferredSize(new Dimension(200, 32));
        pitchRow.add(new JLabel("Sân bóng:"), BorderLayout.WEST);
        pitchRow.add(Pitch_IdComboBox, BorderLayout.CENTER);
        pitchRow.add(new JLabel(""), BorderLayout.EAST); // giữ căn chỉnh
        pitchRow.setOpaque(false);

        JPanel cartHeader = new JPanel(new BorderLayout());
        cartHeader.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        JLabel cartTitle = new JLabel("Danh sách sản phẩm");
        cartTitle.setFont(new Font("Arial", Font.BOLD, 14));
        itemCountLabel = new JLabel("0 sản phẩm");
        itemCountLabel.setForeground(new Color(127, 140, 141));
        cartHeader.add(cartTitle, BorderLayout.WEST);
        cartHeader.add(itemCountLabel, BorderLayout.EAST);

        String[] columnNames = {"Tên sản phẩm", "Giá", "SL", "Thành tiền"};
        cartTable = new TableComponent<>(columnNames);
        JScrollPane scrollPane = new JScrollPane(cartTable);

        JPanel totalsPanel = createTotalsPanel();
        JPanel actionPanel = createActionPanel();

        // Đặt panel khách hàng ở trên cùng, sau đó đến header và bảng
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(customerPanel, BorderLayout.NORTH);
        topPanel.add(cartHeader, BorderLayout.SOUTH);
        topPanel.add(pitchRow, BorderLayout.CENTER);
        cartSection.add(topPanel, BorderLayout.NORTH);
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

    
    private JScrollPane createProductList() {
             productTable = new TableComponent<>(
            new String[]{"Mã", "Tên sản phẩm", "Danh mục","Số Lượng","Đại lượng"});
        
        ListscrollPane = new JScrollPane(productTable); 
        productTable.getSelectionModel().addListSelectionListener(e->{
            int selectedRow = productTable.getSelectedRow();
            if(selectedRow != -1){  
                cardLayout.show(productDisplayPanel, "default");
                displayProduct(products.get(String.valueOf(productTable.getValueAt(selectedRow, 0))));  
            }
        });
        return ListscrollPane; 
    }
    private void setupEventListeners() {
        searchField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    searchProduct();
                }
            }
        });

        showlistBtn.addActionListener(e -> {
            cardLayout.show(productDisplayPanel, "productList");
        });
        searchBtn.addActionListener(e -> searchProduct());
        addBtn.addActionListener(e -> addToCart());
        clearBtn.addActionListener(e -> clearCart());
        checkoutBtn.addActionListener(e -> checkout());
    }

    public void setCheckoutAction(ActionListener listener) {
        checkoutBtn.addActionListener(listener);
    }

    public void setAddCustomerAction(ActionListener listener) {
        addCustomerBtn.addActionListener(listener);
    }
    // #endregion

    // #region Business Logic

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
        cardLayout.show(productDisplayPanel, "default");
        iconLabel.setText(""); // Reset icon
        if( productIcons.containsKey(String.valueOf(product.getId()))) {
            iconLabel.setIcon(productIcons.get(String.valueOf(product.getId())));
        } else {
            iconLabel.setIcon(new ImageIcon("📦")); 
        }
        productNameLabel.setText("<html><div style='text-align:center;'>" + product.getName() 
        + "<br>" + product.getDescription() + "</div></html>");
        productNameLabel.setForeground(Color.BLACK);
        productPriceLabel.setText(ConvertToVnd.formatCurrency((long)product.getSellPrice())); // Sử dụng getSellPrice()
        productidproductLabel.setText("Mã: " + product.getId()); // Sử dụng getId() làm idproduct
        addBtn.setVisible(true);
        addBtn.setActionCommand(String.valueOf(product.getId())); // Sử dụng getId() làm action command
    }

    private void displayNoProduct() {
        cardLayout.show(productDisplayPanel, "default");
        defaultSelectItems();
        productNameLabel.setText("Không tìm thấy sản phẩm");
        productNameLabel.setForeground(new Color(231, 76, 60));
        productPriceLabel.setText("");
        productidproductLabel.setText("");
        addBtn.setVisible(false);
        Timer timer = new Timer(1000, e -> {
            defaultSelectItems();
        });
        timer.setRepeats(false);
        timer.start();
    }

    private void addToCart() {
        String idproduct = addBtn.getActionCommand();
        Product product = products.get(idproduct);
        if (product == null) return;
        InvoiceItem existingItem = findCartItem(idproduct);
        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + 1);
            existingItem.setTotal(product.getSellPrice() * existingItem.getQuantity());
        } else {
            InvoiceItem invoiceItem = new InvoiceItem(0, 0, product.getId(), 1, product.getSellPrice());
            cart.add(invoiceItem);
        }
        updateCartDisplay();
        searchField.setText("");
        iconLabel.setIcon(null);
        iconLabel.setText("✅");
        
        productNameLabel.setText("✅ Đã thêm vào giỏ hàng!");
        productNameLabel.setForeground(new Color(39, 174, 96));
        productPriceLabel.setText("");
        productidproductLabel.setText("");
        addBtn.setVisible(false);
    }

    private InvoiceItem findCartItem(String idproduct) {
        return cart.stream()
                .filter(item -> String.valueOf(item.getItemId()).equals(idproduct))
                .findFirst()
                .orElse(null);
    }

    private void adjustQuantity(int change) {
        int selectedRow = cartTable.getSelectedRow();
        if (selectedRow >= 0 && selectedRow < cart.size()) {
            InvoiceItem item = cart.get(selectedRow);
            int newQuantity = item.getQuantity() + change;
            Product product = products.get(String.valueOf(item.getItemId()));
            if (newQuantity <= 0) {
                cart.remove(selectedRow);
            } else {
                item.setQuantity(newQuantity);
                if (product != null) {
                    item.setTotal(product.getSellPrice() * newQuantity);
                }
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

    public int checkout() {
        if (cart.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Giỏ hàng trống!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
            return -1;
        }

        // Panel nhập ghi chú và giảm giá
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridx = 0; gbc.gridy = 0; gbc.anchor = GridBagConstraints.WEST;
        inputPanel.add(new JLabel("Ghi chú (note):"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
        noteField = new JTextArea(4, 30);
        inputPanel.add(noteField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        inputPanel.add(new JLabel("Giảm giá (₫):"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.gridwidth = 2;
        discountField = new JTextField("0");
        discountField.setPreferredSize(new Dimension(120, 32));
        inputPanel.add(discountField, gbc);

        int inputResult = JOptionPane.showConfirmDialog(this, inputPanel, "Nhập ghi chú và giảm giá", JOptionPane.OK_CANCEL_OPTION);
        if (inputResult != JOptionPane.OK_OPTION) {
            return -1; // Người dùng hủy bỏ
        }

        String note = noteField.getText().trim();
        double discount = 0;
        try {
            discount = Double.parseDouble(discountField.getText().replaceAll("[^\\d]", ""));
            if (discount < 0) discount = 0;
        } catch (NumberFormatException e) {
            discount = 0;
        }

        List<InvoiceItem> invoiceItems = new ArrayList<>(cart);
        StringBuilder receipt = new StringBuilder();
        receipt.append("========================================\n");
        receipt.append("          🏪 Sân bóng\n");
        receipt.append("     123 Đường ABC, Quận 1, TP.HCM\n");
        receipt.append("         Tel: 028.1234.5678\n");
        receipt.append("========================================\n");
        receipt.append("Ngày: ").append(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))).append("\n");
        receipt.append("Nhân viên: Nguyễn Văn A | Quầy: 01\n");
        if (!note.isEmpty()) {
            receipt.append("Ghi chú: ").append(note).append("\n");
        }
        receipt.append("========================================\n");

        double subtotal = 0;
        for (InvoiceItem item : invoiceItems) {
            Product p = products.get(String.valueOf(item.getItemId()));
            double itemTotal = item.getTotal();
            subtotal += itemTotal;
            receipt.append(String.format("%-20s %2dx %-5s\n",
                    p != null ? p.getName() : "?",
                    item.getQuantity(),
                    ConvertToVnd.formatCurrency((long)(p != null ? p.getSellPrice() : 0))));
            receipt.append(String.format("%-24s %15s\n", "", ConvertToVnd.formatCurrency((long)itemTotal)));
        }

        if (discount > 0) {
            receipt.append(String.format("%-20s -%s\n", "Giảm giá:", ConvertToVnd.formatCurrency((long)discount)));
        }

        double afterDiscount = subtotal - discount;
        if (afterDiscount < 0) afterDiscount = 0;
        double tax = afterDiscount * 0.1;
        double total = afterDiscount + tax;

        receipt.append("========================================\n");
        receipt.append(String.format("%-20s %s\n", "Tạm tính:", ConvertToVnd.formatCurrency((long)subtotal)));
        if (discount > 0) {
            receipt.append(String.format("%-20s -%s\n", "Giảm giá:", ConvertToVnd.formatCurrency((long)discount)));
        }
        receipt.append(String.format("%-20s %s\n", "Thuế (10%):", ConvertToVnd.formatCurrency((long)tax)));
        receipt.append("----------------------------------------\n");
        receipt.append(String.format("%-20s %s\n", "TỔNG CỘNG:", ConvertToVnd.formatCurrency((long)total)));
        receipt.append("========================================\n");
        receipt.append("           Cảm ơn quý khách!\n");
        receipt.append("            Hẹn gặp lại!\n");
        receipt.append("========================================\n");

        // Hiển thị hóa đơn trong dialog với 2 nút tùy chỉnh
        JTextArea receiptArea = new JTextArea(receipt.toString());
        receiptArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        receiptArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(receiptArea);
        scrollPane.setPreferredSize(new Dimension(400, 500));

        JOptionPane pane = new JOptionPane(scrollPane,
                JOptionPane.INFORMATION_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null);

        JDialog dialog = pane.createDialog(this, "Hóa đơn thanh toán");
        dialog.setVisible(true);
        return 0;
    }
    // public void setOkButtonActionListener(ActionListener listener) {
    //     btnOk.addActionListener(listener);
    // }
    // public void setCancelButtonActionListener(ActionListener listener) {
    //     btnCancel.addActionListener(listener);
    // }


    // Cập nhật lại giao diện giỏ hàng sau khi thanh toán hoặc thay đổi
    private void updateCartDisplay() {
            cartTable.clearTable();
            for (InvoiceItem item : cart) {
                Product p = products.get(String.valueOf(item.getItemId()));
            Object[] row = {
                    p != null ? p.getName() : "?",
                    ConvertToVnd.formatCurrency((long)(p != null ? p.getSellPrice() : 0)),
                    item.getQuantity(),
                    ConvertToVnd.formatCurrency((long)item.getTotal())
            };
            cartTable.addRow(row);
            }
        double subtotal = cart.stream()
                .mapToDouble(InvoiceItem::getTotal)
                .sum();
        double tax = subtotal * 0.1;
        double total = subtotal + tax;
        subtotalLabel.setText(ConvertToVnd.formatCurrency((long)subtotal));
        taxLabel.setText(ConvertToVnd.formatCurrency((long)tax));
        totalLabel.setText(ConvertToVnd.formatCurrency((long)total));
        int totalItems = cart.stream().mapToInt(InvoiceItem::getQuantity).sum();
        itemCountLabel.setText(totalItems + " sản phẩm");
    }
    public String getDiscount(){
        return discountField.getText().trim();
    }
    public String  getNote(){
        return noteField.getText().trim();
    }
    public List<InvoiceItem> getCart() {
        return cart;
    }
    public JComboBox<Customer> getCustomerComboBox() {
        return customerComboBox;
    }
    public Integer getSelectedPitchId(){
        Pitch selected = (Pitch) Pitch_IdComboBox.getSelectedItem();
       return selected != null ? selected.getId() : null;
    }
     
    // public void hideDialog(){
    //     Window window = SwingUtilities.getWindowAncestor(btnOk);  // khi ấn ok ở JDialog nhập ghi chú và giảm giá thì nó sẽ đóng lại 
    //     if (window instanceof JDialog) {
    //         ((JDialog) window).dispose();
    //     } else if (window instanceof JFrame) {
    //         ((JFrame) window).dispose();
    //     }
    // }
    public void ClearCart(){
        cart.clear();
        updateCartDisplay();
        defaultSelectItems();
        iconLabel.setIcon(null);
        iconLabel.setText("📦");
        productNameLabel.setText("Chọn sản phẩm để hiển thị thông tin");
        productNameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        productNameLabel.setForeground(new Color(127, 140, 141));
        addBtn.setVisible(false);
    }
    public void setPitchOptions(List<Pitch> pitches) {
    Pitch_IdComboBox.removeAllItems(); // Xóa các item cũ nếu có
    for (Pitch pitch : pitches) {
        Pitch_IdComboBox.addItem(pitch); // Pitch cần có phương thức toString() hợp lý
    }
   
}

    // #endregion
    public static void main(String[] args) {
        JFrame frame = new JFrame("Sale View");
        SaleView saleView = new SaleView();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(saleView);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }
}
