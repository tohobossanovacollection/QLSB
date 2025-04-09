import java.util.ArrayList;
import java.util.List;

public class BranchService {
    private List<Branch> branches;
    private List<User> users; // Danh sách người dùng để kiểm tra quyền

    public BranchService() {
        branches = new ArrayList<>();
        users = new ArrayList<>();
        // Dữ liệu mẫu
        branches.add(new Branch(1, "Chi nhánh 1", "Hà Nội"));
        branches.add(new Branch(2, "Chi nhánh 2", "TP.HCM"));
        users.add(new User(1, "admin", "admin123", "Admin", 0)); // Admin quản lý tất cả
        users.add(new User(2, "manager1", "pass123", "Manager", 1)); // Manager chi nhánh 1
        users.add(new User(3, "manager2", "pass456", "Manager", 2)); // Manager chi nhánh 2
    }

    // Thêm chi nhánh (kiểm tra quyền)
    public void addBranch(Branch branch, User user) {
        if (user.getRole().equals("Admin")) {
            branches.add(branch);
            System.out.println("Thêm chi nhánh thành công!");
            // Gọi JDBC để lưu vào MySQL
        } else {
            System.out.println("Bạn không có quyền thêm chi nhánh!");
        }
    }

    // Xem danh sách chi nhánh (kiểm tra quyền)
    public List<Branch> getAllBranches(User user) {
        List<Branch> accessibleBranches = new ArrayList<>();
        for (Branch branch : branches) {
            if (user.hasAccessToBranch(branch.getBranchId())) {
                accessibleBranches.add(branch);
            }
        }
        return accessibleBranches; // Trả về danh sách chi nhánh mà user có quyền truy cập
    }

    // Xem chi nhánh cụ thể (kiểm tra quyền)
    public Branch getBranchById(int branchId, User user) {
        if (!user.hasAccessToBranch(branchId)) {
            System.out.println("Bạn không có quyền truy cập chi nhánh này!");
            return null;
        }
        for (Branch branch : branches) {
            if (branch.getBranchId() == branchId) {
                return branch;
            }
        }
        return null;
    }

    // Thêm người dùng mới (chỉ Admin mới được thêm)
    public void addUser(User newUser, User currentUser) {
        if (currentUser.getRole().equals("Admin")) {
            users.add(newUser);
            System.out.println("Thêm người dùng thành công!");
            // Gọi JDBC để lưu vào MySQL
        } else {
            System.out.println("Bạn không có quyền thêm người dùng!");
        }
    }

    // Đăng nhập
    public User login(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                System.out.println("Đăng nhập thành công!");
                return user;
            }
        }
        System.out.println("Sai tên đăng nhập hoặc mật khẩu!");
        return null;
    }
}