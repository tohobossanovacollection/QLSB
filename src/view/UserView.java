package view;

//import controller.UserController;
import model.User;
import view.components.TableComponent;
import view.components.DialogComponent;
import service. UserService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserView extends JPanel {
    //private UserController userController;
    private TableComponent userTable;
    private JButton addButton, editButton, deleteButton, refreshButton;
    private JTextField searchField;
    private DialogComponent userDialog;
    private UserService userService; // Thay thế UserController bằng UserService

    /*public UserView(UserController userController) {
        this.userController = userController;
        initComponents();
        layoutComponents();
        initEventHandlers();
        loadUserData();
    }*/

    public UserView(){
        initComponents();
        layoutComponents();
        initEventHandlers();
    }

    private void initComponents() {
        // Khởi tạo các thành phần giao diện
        userTable = new TableComponent(new String[]{"ID", "Tên người dùng", "Username", "Vai trò"});
        addButton = new JButton("Thêm người dùng");
        editButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        refreshButton = new JButton("Làm mới");
        searchField = new JTextField(20);
        
        // Khởi tạo dialog thêm/sửa người dùng
        userDialog = new DialogComponent("Thông tin người dùng", 400, 300);
        initUserDialog();
    }

    private void initUserDialog() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextField nameField = new JTextField();
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JComboBox<String> roleComboBox = new JComboBox<>(new String[]{"Admin", "Manager", "Staff"});
        
        panel.add(new JLabel("Tên:"));
        panel.add(nameField);
        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Vai trò:"));
        panel.add(roleComboBox);
        
        JButton saveButton = new JButton("Lưu");
        JButton cancelButton = new JButton("Hủy");
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        
        // Xử lý sự kiện nút Lưu
        /*saveButton.addActionListener(e -> {
            // Lấy thông tin từ form
            String name = nameField.getText();
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String role = (String) roleComboBox.getSelectedItem();
            
            // Gọi controller để lưu
            if (userDialog.getTag() == null) {
                // Thêm mới
                userController.addUser(name, username, password, role);
            } else {
                // Cập nhật
                Long userId = (Long) userDialog.getTag();
                userController.updateUser(userId, name, username, password, role);
            }
            
            userDialog.setVisible(false);
            loadUserData();
        });*/
        
        // Xử lý sự kiện nút Hủy
        cancelButton.addActionListener(e -> userDialog.setVisible(false));
        
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(panel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        userDialog.setContentPane(mainPanel);
    }

    private void layoutComponents() {
        setLayout(new BorderLayout(10, 10));
        
        // Panel tìm kiếm và nút thêm mới
        JPanel topPanel = new JPanel(new BorderLayout(5, 0));
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchPanel.add(searchField);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(addButton);
        
        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(buttonPanel, BorderLayout.EAST);
        
        // Panel bảng dữ liệu
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.add(new JScrollPane(userTable), BorderLayout.CENTER);
        
        // Panel nút tác vụ
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        actionPanel.add(editButton);
        actionPanel.add(deleteButton);
        actionPanel.add(refreshButton);
        
        // Thêm vào panel chính
        add(topPanel, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);
        
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    }

    private void initEventHandlers() {
        // Xử lý sự kiện nút Thêm
        addButton.addActionListener(e -> {
            userDialog.setTitle("Thêm người dùng mới");
            userDialog.setTag(null);
            userDialog.clearFields();
            userDialog.setVisible(true);
        });
        
        // Xử lý sự kiện nút Sửa
        /*editButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                Long userId = (Long) userTable.getValueAt(selectedRow, 0);
                User user = userController.getUserById(userId);
                if (user != null) {
                    userDialog.setTitle("Sửa thông tin người dùng");
                    userDialog.setTag(userId);
                    
                    // Set giá trị cho các trường
                    userDialog.setFieldValue("Tên:", user.getName());
                    userDialog.setFieldValue("Username:", user.getUsername());
                    // Không set password vì lý do bảo mật
                    userDialog.setComboBoxValue("Vai trò:", user.getRole());
                    
                    userDialog.setVisible(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Vui lòng chọn người dùng để sửa", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });*/
        
        // Xử lý sự kiện nút Xóa
        deleteButton.addActionListener(e -> {
            int selectedRow = userTable.getSelectedRow();
            if (selectedRow >= 0) {
                Long userId = (Long) userTable.getValueAt(selectedRow, 0);
                int confirm = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa người dùng này?",
                    "Xác nhận xóa",
                    JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    //userController.deleteUser(userId);
                    loadUserData();
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Vui lòng chọn người dùng để xóa", 
                    "Thông báo", 
                    JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        // Xử lý sự kiện nút Làm mới
        refreshButton.addActionListener(e -> loadUserData());
        
        // Xử lý sự kiện tìm kiếm
        searchField.addActionListener(e -> {
            String keyword = searchField.getText().trim();
            if (!keyword.isEmpty()) {
                //userController.searchUsers(keyword);
            } else {
                loadUserData();
            }
        });
    }

    public void loadUserData() {
        // Gọi controller để lấy danh sách người dùng
        List<User> users = userService.getAllUsers();
        
        // Xóa dữ liệu cũ
        userTable.clearTable();
        
        // Thêm dữ liệu mới
        for (User user : users) {
            userTable.addRow(new Object[]{
                user.getId(),
                //user.getName(),
                user.getUsername(),
                user.getRole()
            });
        }
    }

    public void displayUsers(List<User> users) {
        // Xóa dữ liệu cũ
        userTable.clearTable();
        
        // Thêm dữ liệu mới
        for (User user : users) {
            userTable.addRow(new Object[]{
                user.getId(),
                //user.getName(),
                user.getUsername(),
                user.getRole()
            });
        }
    }
}

