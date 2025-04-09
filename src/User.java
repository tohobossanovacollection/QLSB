public class User {
    private int userId;
    private String username;
    private String password; // Trong thực tế, nên mã hóa password
    private String role; // "Admin" hoặc "Manager"
    private int branchId; // ID chi nhánh mà người dùng quản lý (nếu là Manager)

    public User(int userId, String username, String password, String role, int branchId) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.branchId = branchId;
    }

    // Getters
    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    public int getBranchId() {
        return branchId;
    }

    // Kiểm tra quyền truy cập chi nhánh
    public boolean hasAccessToBranch(int branchId) {
        if (role.equals("Admin")) {
            return true; // Admin có quyền truy cập tất cả chi nhánh
        } else if (role.equals("Manager") && this.branchId == branchId) {
            return true; // Manager chỉ truy cập chi nhánh của mình
        }
        return false;
    }

    @Override
    public String toString() {
        return "Người dùng: " + username + " (ID: " + userId + ") - Vai trò: " + role +
               " - Chi nhánh: " + (role.equals("Admin") ? "Tất cả" : branchId);
    }
}