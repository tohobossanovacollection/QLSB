package view;

//import controller.SettingsController;
import view.components.DialogComponent;

import javax.swing.*;
import java.awt.*;
import java.util.Properties;

public class SettingsView extends JPanel {
    //private SettingsController settingsController;
    private JTabbedPane tabbedPane;
    private JPanel generalPanel, databasePanel, backupPanel;

    /*public SettingsView(SettingsController settingsController) {
        this.settingsController = settingsController;
        initComponents();
        layoutComponents();
        loadSettings();
    }*/
    public SettingsView() {
        initComponents();
        layoutComponents();
    }

    private void initComponents() {
        tabbedPane = new JTabbedPane();
        generalPanel = createGeneralPanel();
        databasePanel = createDatabasePanel();
        backupPanel = createBackupPanel();
    }

    private JPanel createGeneralPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Cài đặt tên hệ thống
        panel.add(new JLabel("Tên hệ thống:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField systemNameField = new JTextField(20);
        panel.add(systemNameField, gbc);
        
        // Cài đặt địa chỉ
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        panel.add(new JLabel("Địa chỉ:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JTextField addressField = new JTextField(20);
        panel.add(addressField, gbc);
        
        // Cài đặt số điện thoại
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        panel.add(new JLabel("Số điện thoại:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JTextField phoneField = new JTextField(20);
        panel.add(phoneField, gbc);
        
        // Cài đặt email
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        panel.add(new JLabel("Email:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JTextField emailField = new JTextField(20);
        panel.add(emailField, gbc);
        
        // Cài đặt giờ mở cửa
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        panel.add(new JLabel("Giờ mở cửa:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPanel timePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        
        String[] hours = new String[24];
        for (int i = 0; i < 24; i++) {
            hours[i] = String.format("%02d:00", i);
        }
        
        JComboBox<String> openTimeCombo = new JComboBox<>(hours);
        JComboBox<String> closeTimeCombo = new JComboBox<>(hours);
        
        timePanel.add(openTimeCombo);
        timePanel.add(new JLabel(" - "));
        timePanel.add(closeTimeCombo);
        
        panel.add(timePanel, gbc);
        
        // Nút lưu
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 5, 5, 5);
        
        JButton saveButton = new JButton("Lưu thay đổi");
        saveButton.addActionListener(e -> {
            Properties props = new Properties();
            props.setProperty("system.name", systemNameField.getText());
            props.setProperty("system.address", addressField.getText());
            props.setProperty("system.phone", phoneField.getText());
            props.setProperty("system.email", emailField.getText());
            props.setProperty("system.openTime", (String) openTimeCombo.getSelectedItem());
            props.setProperty("system.closeTime", (String) closeTimeCombo.getSelectedItem());
            
            //settingsController.saveGeneralSettings(props);
            JOptionPane.showMessageDialog(this, "Đã lưu cài đặt thành công!");
        });
        
        panel.add(saveButton, gbc);
        
        return panel;
    }

    private JPanel createDatabasePanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Cài đặt loại CSDL
        panel.add(new JLabel("Loại CSDL:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        String[] dbTypes = {"MySQL", "PostgreSQL", "SQLite"};
        JComboBox<String> dbTypeCombo = new JComboBox<>(dbTypes);
        panel.add(dbTypeCombo, gbc);
        
        // Cài đặt host
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        panel.add(new JLabel("Host:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JTextField hostField = new JTextField(20);
        panel.add(hostField, gbc);
        
        // Cài đặt port
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        panel.add(new JLabel("Port:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JTextField portField = new JTextField(20);
        panel.add(portField, gbc);
        
        // Cài đặt tên database
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        panel.add(new JLabel("Database:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JTextField dbNameField = new JTextField(20);
        panel.add(dbNameField, gbc);
        
        // Cài đặt username
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        panel.add(new JLabel("Username:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JTextField usernameField = new JTextField(20);
        panel.add(usernameField, gbc);
        
        // Cài đặt password
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.weightx = 0;
        panel.add(new JLabel("Password:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        JPasswordField passwordField = new JPasswordField(20);
        panel.add(passwordField, gbc);
        
        // Nút test kết nối
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        gbc.insets = new Insets(20, 5, 5, 5);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        JButton testButton = new JButton("Kiểm tra kết nối");
        testButton.addActionListener(e -> {
            /*boolean success = settingsController.testDatabaseConnection(
                (String) dbTypeCombo.getSelectedItem(),
                hostField.getText(),
                portField.getText(),
                dbNameField.getText(),
                usernameField.getText(),
                new String(passwordField.getPassword())
            );*/

            boolean success = true; // Giả lập thành công kết nối
            
            if (success) {
                JOptionPane.showMessageDialog(this, "Kết nối thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Không thể kết nối đến CSDL. Vui lòng kiểm tra lại thông tin.", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton saveButton = new JButton("Lưu thay đổi");
        saveButton.addActionListener(e -> {
            Properties props = new Properties();
            props.setProperty("db.type", (String) dbTypeCombo.getSelectedItem());
            props.setProperty("db.host", hostField.getText());
            props.setProperty("db.port", portField.getText());
            props.setProperty("db.name", dbNameField.getText());
            props.setProperty("db.username", usernameField.getText());
            props.setProperty("db.password", new String(passwordField.getPassword()));
            
            //settingsController.saveDatabaseSettings(props);
            JOptionPane.showMessageDialog(this, "Đã lưu cài đặt CSDL thành công!");
        });
        
        buttonPanel.add(testButton);
        buttonPanel.add(saveButton);
        
        panel.add(buttonPanel, gbc);
        
        return panel;
    }

    private JPanel createBackupPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Đường dẫn backup
        panel.add(new JLabel("Thư mục backup:"), gbc);
        
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        JTextField backupPathField = new JTextField(20);
        panel.add(backupPathField, gbc);
        
        gbc.gridx = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        JButton browseButton = new JButton("Chọn");
        browseButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                backupPathField.setText(fileChooser.getSelectedFile().getPath());
            }
        });
        panel.add(browseButton, gbc);
        
        // Backup tự động
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        JCheckBox autoBackupCheck = new JCheckBox("Backup dữ liệu tự động");
        panel.add(autoBackupCheck, gbc);
        
        // Tần suất backup
        gbc.gridy++;
        gbc.gridwidth = 1;
        panel.add(new JLabel("Tần suất backup:"), gbc);
        
        gbc.gridx = 1;
        String[] frequencies = {"Hàng ngày", "Hàng tuần", "Hàng tháng"};
        JComboBox<String> frequencyCombo = new JComboBox<>(frequencies);
        panel.add(frequencyCombo, gbc);
        
        // Giữ backup bao lâu
        gbc.gridx = 0;
        gbc.gridy++;
        panel.add(new JLabel("Giữ backup:"), gbc);
        
        gbc.gridx = 1;
        String[] retentions = {"1 tháng", "3 tháng", "6 tháng", "1 năm", "Vĩnh viễn"};
        JComboBox<String> retentionCombo = new JComboBox<>(retentions);
        panel.add(retentionCombo, gbc);
        
        // Nút backup ngay
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 3;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(20, 5, 5, 5);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        
        JButton backupNowButton = new JButton("Backup ngay");
        backupNowButton.addActionListener(e -> {
            //boolean success = settingsController.backupDatabase(backupPathField.getText());
            boolean success = true; // Giả lập thành công backup
            if (success) {
                JOptionPane.showMessageDialog(this, "Backup thành công!");
            } else {
                JOptionPane.showMessageDialog(this, "Không thể backup CSDL. Vui lòng kiểm tra lại thông tin.", 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        JButton saveButton = new JButton("Lưu cài đặt");
        saveButton.addActionListener(e -> {
            Properties props = new Properties();
            props.setProperty("backup.path", backupPathField.getText());
            props.setProperty("backup.auto", String.valueOf(autoBackupCheck.isSelected()));
            props.setProperty("backup.frequency", (String) frequencyCombo.getSelectedItem());
            props.setProperty("backup.retention", (String) retentionCombo.getSelectedItem());
            
            //settingsController.saveBackupSettings(props);
            JOptionPane.showMessageDialog(this, "Đã lưu cài đặt backup thành công!");
        });
        
        buttonPanel.add(backupNowButton);
        buttonPanel.add(saveButton);
        
        panel.add(buttonPanel, gbc);
        
        return panel;
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());
        
        tabbedPane.addTab("Chung", new JScrollPane(generalPanel));
        tabbedPane.addTab("Cơ sở dữ liệu", new JScrollPane(databasePanel));
        tabbedPane.addTab("Backup", new JScrollPane(backupPanel));
        
        add(tabbedPane, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    /* 
    private void loadSettings() {
        // Tải cài đặt từ controller
        Properties generalSettings = settingsController.getGeneralSettings();
        Properties dbSettings = settingsController.getDatabaseSettings();
        Properties backupSettings = settingsController.getBackupSettings();
        
        // Cập nhật UI với cài đặt đã tải
        // Cài đặt chung
        JTextField systemNameField = (JTextField) findComponentByName(generalPanel, "Tên hệ thống:");
        if (systemNameField != null && generalSettings != null) {
            systemNameField.setText(generalSettings.getProperty("system.name", ""));
        }
        
        // Tương tự cho các cài đặt khác...
    }*/
    
    private Component findComponentByName(Container container, String name) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JLabel && ((JLabel) comp).getText().equals(name)) {
                // Lấy component kế tiếp (thường là field tương ứng)
                int index = -1;
                Component[] components = container.getComponents();
                for (int i = 0; i < components.length; i++) {
                    if (components[i] == comp) {
                        index = i;
                        break;
                    }
                }
                
                if (index >= 0 && index + 1 < components.length) {
                    return components[index + 1];
                }
            } else if (comp instanceof Container) {
                Component result = findComponentByName((Container) comp, name);
                if (result != null) {
                    return result;
                }
            }
        }
        return null;
    }
}