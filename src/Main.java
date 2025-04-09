import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        // Khởi tạo các service
        CourtService courtService = new CourtService();
        BookingService bookingService = new BookingService();
        CustomerService customerService = new CustomerService();
        MonthlyOrderService monthlyOrderService = new MonthlyOrderService();
        InventoryService inventoryService = new InventoryService();
        RevenueService revenueService = new RevenueService();
        TransactionService transactionService = new TransactionService();
        BranchService branchService = new BranchService();

        // 8. Quản lý chi nhánh với phân quyền
        System.out.println("\n=== Kiểm tra phân quyền ===");
        
        // Đăng nhập với Admin
        User admin = branchService.login("admin", "admin123");
        if (admin != null) {
            System.out.println("Danh sách chi nhánh (Admin):");
            for (Branch branch : branchService.getAllBranches(admin)) {
                System.out.println(branch);
            }
            // Admin thêm chi nhánh mới
            branchService.addBranch(new Branch(3, "Chi nhánh 3", "Đà Nẵng"), admin);
        }

        // Đăng nhập với Manager chi nhánh 1
        User manager1 = branchService.login("manager1", "pass123");
        if (manager1 != null) {
            System.out.println("\nDanh sách chi nhánh (Manager 1):");
            for (Branch branch : branchService.getAllBranches(manager1)) {
                System.out.println(branch);
            }
            // Manager thử thêm chi nhánh (sẽ bị từ chối)
            branchService.addBranch(new Branch(4, "Chi nhánh 4", "Cần Thơ"), manager1);
        }

        // Xem chi nhánh cụ thể
        Branch branch2 = branchService.getBranchById(2, manager1);
        if (branch2 != null) {
            System.out.println("\nChi nhánh truy cập: " + branch2);
        }
    }
}