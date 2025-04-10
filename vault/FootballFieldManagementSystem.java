

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FootballFieldManagementSystem {
    public static void main(String[] args) {
        DatabaseManager dbManager = new DatabaseManager();
        MenuManager menuManager = new MenuManager(dbManager);
        menuManager.displayMainMenu();
    }
}

class MenuManager {
    private DatabaseManager dbManager;
    private Scanner scanner;
    private FieldManager fieldManager;
    private BookingManager bookingManager;
    private MonthlyBookingManager monthlyBookingManager;
    private CustomerManager customerManager;
    private InventoryManager inventoryManager;
    private RevenueManager revenueManager;
    private FinanceManager financeManager;
    private BranchManager branchManager;

    public MenuManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
        this.scanner = new Scanner(System.in);
        this.fieldManager = new FieldManager(dbManager);
        this.bookingManager = new BookingManager(dbManager);
        this.monthlyBookingManager = new MonthlyBookingManager(dbManager);
        this.customerManager = new CustomerManager(dbManager);
        this.inventoryManager = new InventoryManager(dbManager);
        this.revenueManager = new RevenueManager(dbManager);
        this.financeManager = new FinanceManager(dbManager);
        this.branchManager = new BranchManager(dbManager);
    }

    public void displayMainMenu() {
        int choice = 0;
        do {
            System.out.println("\n===== HỆ THỐNG QUẢN LÝ SÂN BÓNG ĐÁ =====");
            System.out.println("1. Xem trạng thái sân");
            System.out.println("2. Quản lý lịch đặt sân");
            System.out.println("3. Quản lý đơn tháng");
            System.out.println("4. Quản lý khách hàng");
            System.out.println("5. Bán hàng & Quản lý kho");
            System.out.println("6. Thống kê doanh thu - báo cáo");
            System.out.println("7. Quản lý thu chi");
            System.out.println("8. Quản lý chi nhánh");
            System.out.println("0. Thoát");
            System.out.print("Nhập lựa chọn của bạn: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        fieldManager.viewFieldStatus();
                        break;
                    case 2:
                        bookingManager.manageBookings();
                        break;
                    case 3:
                        monthlyBookingManager.manageMonthlyBookings();
                        break;
                    case 4:
                        customerManager.manageCustomers();
                        break;
                    case 5:
                        inventoryManager.manageSalesAndInventory();
                        break;
                    case 6:
                        revenueManager.viewRevenueReports();
                        break;
                    case 7:
                        financeManager.manageFinances();
                        break;
                    case 8:
                        branchManager.manageBranches();
                        break;
                    case 0:
                        System.out.println("Cảm ơn bạn đã sử dụng hệ thống!");
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ. Vui lòng thử lại.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số.");
            }
        } while (choice != 0);
        
        scanner.close();
    }
}

class DatabaseManager {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/football_manager";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "password";
    
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }
    
    public void initializeDatabase() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            
            // Tạo các bảng nếu chưa tồn tại
            createBranchesTable(stmt);
            createFieldsTable(stmt);
            createCustomersTable(stmt);
            createBookingsTable(stmt);
            createMonthlyBookingsTable(stmt);
            createProductsTable(stmt);
            createInventoryTable(stmt);
            createSalesTable(stmt);
            createSaleItemsTable(stmt);
            createFinancesTable(stmt);
            
            System.out.println("Cơ sở dữ liệu đã được khởi tạo thành công.");
        } catch (SQLException e) {
            System.out.println("Lỗi khi khởi tạo cơ sở dữ liệu: " + e.getMessage());
        }
    }
    
    private void createBranchesTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS branches (" +
                "branch_id INT AUTO_INCREMENT PRIMARY KEY," +
                "branch_name VARCHAR(100) NOT NULL," +
                "address VARCHAR(255)," +
                "phone VARCHAR(20)," +
                "manager VARCHAR(100)" +
                ")";
        stmt.executeUpdate(sql);
    }
    
    private void createFieldsTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS fields (" +
                "field_id INT AUTO_INCREMENT PRIMARY KEY," +
                "field_name VARCHAR(100) NOT NULL," +
                "type VARCHAR(50)," +
                "size VARCHAR(20)," +
                "price_per_hour DECIMAL(10,2)," +
                "status VARCHAR(20) DEFAULT 'AVAILABLE'," +
                "branch_id INT," +
                "FOREIGN KEY (branch_id) REFERENCES branches(branch_id)" +
                ")";
        stmt.executeUpdate(sql);
    }
    
    private void createCustomersTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS customers (" +
                "customer_id INT AUTO_INCREMENT PRIMARY KEY," +
                "name VARCHAR(100) NOT NULL," +
                "phone VARCHAR(20)," +
                "email VARCHAR(100)," +
                "customer_type VARCHAR(20) DEFAULT 'REGULAR'," +
                "balance DECIMAL(10,2) DEFAULT 0," +
                "registration_date DATETIME DEFAULT CURRENT_TIMESTAMP" +
                ")";
        stmt.executeUpdate(sql);
    }
    
    private void createBookingsTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS bookings (" +
                "booking_id INT AUTO_INCREMENT PRIMARY KEY," +
                "field_id INT," +
                "customer_id INT," +
                "booking_date DATE," +
                "start_time TIME," +
                "end_time TIME," +
                "amount DECIMAL(10,2)," +
                "payment_status VARCHAR(20) DEFAULT 'UNPAID'," +
                "booking_status VARCHAR(20) DEFAULT 'CONFIRMED'," +
                "periodic VARCHAR(20) DEFAULT 'NO'," +
                "periodic_end_date DATE," +
                "notes TEXT," +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (field_id) REFERENCES fields(field_id)," +
                "FOREIGN KEY (customer_id) REFERENCES customers(customer_id)" +
                ")";
        stmt.executeUpdate(sql);
    }
    
    private void createMonthlyBookingsTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS monthly_bookings (" +
                "monthly_booking_id INT AUTO_INCREMENT PRIMARY KEY," +
                "customer_id INT," +
                "month INT," +
                "year INT," +
                "sessions_per_month INT," +
                "price_per_session DECIMAL(10,2)," +
                "total_amount DECIMAL(10,2)," +
                "payment_status VARCHAR(20) DEFAULT 'UNPAID'," +
                "notes TEXT," +
                "created_at DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (customer_id) REFERENCES customers(customer_id)" +
                ")";
        stmt.executeUpdate(sql);
        
        sql = "CREATE TABLE IF NOT EXISTS monthly_booking_details (" +
                "detail_id INT AUTO_INCREMENT PRIMARY KEY," +
                "monthly_booking_id INT," +
                "field_id INT," +
                "day_of_week INT," +
                "start_time TIME," +
                "end_time TIME," +
                "FOREIGN KEY (monthly_booking_id) REFERENCES monthly_bookings(monthly_booking_id)," +
                "FOREIGN KEY (field_id) REFERENCES fields(field_id)" +
                ")";
        stmt.executeUpdate(sql);
    }
    
    private void createProductsTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS products (" +
                "product_id INT AUTO_INCREMENT PRIMARY KEY," +
                "product_name VARCHAR(100) NOT NULL," +
                "category VARCHAR(50)," +
                "unit_price DECIMAL(10,2)," +
                "description TEXT" +
                ")";
        stmt.executeUpdate(sql);
    }
    
    private void createInventoryTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS inventory (" +
                "inventory_id INT AUTO_INCREMENT PRIMARY KEY," +
                "product_id INT," +
                "branch_id INT," +
                "quantity INT," +
                "min_stock_level INT," +
                "last_updated DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY (product_id) REFERENCES products(product_id)," +
                "FOREIGN KEY (branch_id) REFERENCES branches(branch_id)" +
                ")";
        stmt.executeUpdate(sql);
    }
    
    private void createSalesTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS sales (" +
                "sale_id INT AUTO_INCREMENT PRIMARY KEY," +
                "customer_id INT," +
                "sale_date DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "total_amount DECIMAL(10,2)," +
                "payment_status VARCHAR(20) DEFAULT 'PAID'," +
                "branch_id INT," +
                "FOREIGN KEY (customer_id) REFERENCES customers(customer_id)," +
                "FOREIGN KEY (branch_id) REFERENCES branches(branch_id)" +
                ")";
        stmt.executeUpdate(sql);
    }
    
    private void createSaleItemsTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS sale_items (" +
                "item_id INT AUTO_INCREMENT PRIMARY KEY," +
                "sale_id INT," +
                "product_id INT," +
                "quantity INT," +
                "unit_price DECIMAL(10,2)," +
                "total_price DECIMAL(10,2)," +
                "FOREIGN KEY (sale_id) REFERENCES sales(sale_id)," +
                "FOREIGN KEY (product_id) REFERENCES products(product_id)" +
                ")";
        stmt.executeUpdate(sql);
    }
    
    private void createFinancesTable(Statement stmt) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS finances (" +
                "finance_id INT AUTO_INCREMENT PRIMARY KEY," +
                "transaction_date DATETIME DEFAULT CURRENT_TIMESTAMP," +
                "type VARCHAR(20)," +
                "amount DECIMAL(10,2)," +
                "category VARCHAR(50)," +
                "description TEXT," +
                "branch_id INT," +
                "FOREIGN KEY (branch_id) REFERENCES branches(branch_id)" +
                ")";
        stmt.executeUpdate(sql);
    }
}

class FieldManager {
    private DatabaseManager dbManager;
    private Scanner scanner = new Scanner(System.in);
    
    public FieldManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }
    
    public void viewFieldStatus() {
        System.out.println("\n===== XEM TRẠNG THÁI SÂN =====");
        System.out.println("1. Xem theo ngày");
        System.out.println("2. Xem theo tuần");
        System.out.println("3. Xem theo tháng");
        System.out.println("0. Quay lại");
        System.out.print("Nhập lựa chọn của bạn: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            
            switch (choice) {
                case 1:
                    viewFieldStatusByDay();
                    break;
                case 2:
                    viewFieldStatusByWeek();
                    break;
                case 3:
                    viewFieldStatusByMonth();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập một số.");
        }
    }
    
    private void viewFieldStatusByDay() {
        System.out.print("Nhập ngày (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();
        LocalDate date;
        
        try {
            date = LocalDate.parse(dateStr);
        } catch (Exception e) {
            System.out.println("Định dạng ngày không hợp lệ. Sử dụng yyyy-MM-dd.");
            return;
        }
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT f.field_id, f.field_name, b.booking_id, b.start_time, b.end_time, " +
                     "c.name AS customer_name, b.booking_status " +
                     "FROM fields f " +
                     "LEFT JOIN bookings b ON f.field_id = b.field_id AND b.booking_date = ? " +
                     "LEFT JOIN customers c ON b.customer_id = c.customer_id " +
                     "ORDER BY f.field_name, b.start_time")) {
            
            stmt.setDate(1, java.sql.Date.valueOf(date));
            ResultSet rs = stmt.executeQuery();
            
            String currentField = "";
            boolean hasData = false;
            
            System.out.println("\nTRẠNG THÁI SÂN NGÀY " + date);
            System.out.println("------------------------------------------------------");
            
            while (rs.next()) {
                hasData = true;
                String fieldName = rs.getString("field_name");
                
                if (!fieldName.equals(currentField)) {
                    currentField = fieldName;
                    System.out.println("\nSân: " + currentField);
                    System.out.println("------------------------------------------------------");
                    System.out.printf("%-15s %-15s %-25s %-15s\n", "Giờ bắt đầu", "Giờ kết thúc", "Khách hàng", "Trạng thái");
                    System.out.println("------------------------------------------------------");
                }
                
                Time startTime = rs.getTime("start_time");
                Time endTime = rs.getTime("end_time");
                String customerName = rs.getString("customer_name");
                String status = rs.getString("booking_status");
                
                if (startTime != null && endTime != null) {
                    System.out.printf("%-15s %-15s %-25s %-15s\n", 
                            startTime, endTime, 
                            (customerName != null ? customerName : "N/A"), 
                            (status != null ? status : "TRỐNG"));
                } else {
                    System.out.println("Không có lịch đặt sân cho ngày này.");
                }
            }
            
            if (!hasData) {
                System.out.println("Không có dữ liệu sân bóng hoặc lịch đặt sân cho ngày này.");
            }
            
        } catch (SQLException e) {
            System.out.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
        }
    }
    
    private void viewFieldStatusByWeek() {
        System.out.println("Tính năng xem theo tuần sẽ được phát triển trong phiên bản tiếp theo.");
    }
    
    private void viewFieldStatusByMonth() {
        System.out.println("Tính năng xem theo tháng sẽ được phát triển trong phiên bản tiếp theo.");
    }
}

class BookingManager {
    private DatabaseManager dbManager;
    private Scanner scanner = new Scanner(System.in);
    
    public BookingManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }
    
    public void manageBookings() {
        int choice = 0;
        do {
            System.out.println("\n===== QUẢN LÝ LỊCH ĐẶT SÂN =====");
            System.out.println("1. Xem tất cả lịch đặt sân");
            System.out.println("2. Thêm lịch đặt sân mới");
            System.out.println("3. Cập nhật lịch đặt sân");
            System.out.println("4. Hủy lịch đặt sân");
            System.out.println("5. Tìm kiếm lịch đặt sân");
            System.out.println("0. Quay lại");
            System.out.print("Nhập lựa chọn của bạn: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        viewAllBookings();
                        break;
                    case 2:
                        addNewBooking();
                        break;
                    case 3:
                        updateBooking();
                        break;
                    case 4:
                        cancelBooking();
                        break;
                    case 5:
                        searchBookings();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số.");
            }
        } while (choice != 0);
    }
    
    private void viewAllBookings() {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT b.booking_id, f.field_name, c.name AS customer_name, " +
                     "b.booking_date, b.start_time, b.end_time, b.amount, " +
                     "b.payment_status, b.booking_status, b.periodic " +
                     "FROM bookings b " +
                     "JOIN fields f ON b.field_id = f.field_id " +
                     "JOIN customers c ON b.customer_id = c.customer_id " +
                     "ORDER BY b.booking_date DESC, b.start_time")) {
            
            ResultSet rs = stmt.executeQuery();
            
            System.out.println("\nDanh sách lịch đặt sân:");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-5s %-15s %-20s %-12s %-10s %-10s %-10s %-15s %-15s\n", 
                    "ID", "Sân", "Khách hàng", "Ngày", "Bắt đầu", "Kết thúc", "Giá tiền", "Trạng thái TT", "Trạng thái đặt");
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
            
            boolean hasData = false;
            
            while (rs.next()) {
                hasData = true;
                int bookingId = rs.getInt("booking_id");
                String fieldName = rs.getString("field_name");
                String customerName = rs.getString("customer_name");
                Date bookingDate = rs.getDate("booking_date");
                Time startTime = rs.getTime("start_time");
                Time endTime = rs.getTime("end_time");
                double amount = rs.getDouble("amount");
                String paymentStatus = rs.getString("payment_status");
                String bookingStatus = rs.getString("booking_status");
                String periodic = rs.getString("periodic");
                
                System.out.printf("%-5d %-15s %-20s %-12s %-10s %-10s %-10.2f %-15s %-15s\n", 
                        bookingId, fieldName, customerName, bookingDate, startTime, endTime, 
                        amount, paymentStatus, bookingStatus);
            }
            
            if (!hasData) {
                System.out.println("Không có dữ liệu lịch đặt sân.");
            }
            
            System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
            
        } catch (SQLException e) {
            System.out.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
        }
    }
    
    private void addNewBooking() {
        try {
            // Hiển thị danh sách sân
            List<Field> fields = getAvailableFields();
            if (fields.isEmpty()) {
                System.out.println("Không có sân nào khả dụng.");
                return;
            }
            
            System.out.println("\nDanh sách sân:");
            for (Field field : fields) {
                System.out.println(field.getId() + ": " + field.getName());
            }
            
            System.out.print("Chọn ID sân: ");
            int fieldId = Integer.parseInt(scanner.nextLine());
            
            // Hiển thị danh sách khách hàng
            List<Customer> customers = getCustomers();
            if (customers.isEmpty()) {
                System.out.println("Không có khách hàng nào. Vui lòng thêm khách hàng trước.");
                return;
            }
            
            System.out.println("\nDanh sách khách hàng:");
            for (Customer customer : customers) {
                System.out.println(customer.getId() + ": " + customer.getName() + " - " + customer.getPhone());
            }
            
            System.out.print("Chọn ID khách hàng: ");
            int customerId = Integer.parseInt(scanner.nextLine());
            
            // Nhập thông tin đặt sân
            System.out.print("Nhập ngày đặt sân (yyyy-MM-dd): ");
            String dateStr = scanner.nextLine();
            LocalDate bookingDate = LocalDate.parse(dateStr);
            
            System.out.print("Nhập giờ bắt đầu (HH:mm): ");
            String startTimeStr = scanner.nextLine();
            LocalTime startTime = LocalTime.parse(startTimeStr);
            
            System.out.print("Nhập giờ kết thúc (HH:mm): ");
            String endTimeStr = scanner.nextLine();
            LocalTime endTime = LocalTime.parse(endTimeStr);
            
            // Kiểm tra xung đột thời gian
            if (checkTimeConflict(fieldId, bookingDate, startTime, endTime, 0)) {
                System.out.println("Xung đột thời gian đặt sân. Vui lòng chọn thời gian khác.");
                return;
            }
            
            System.out.print("Nhập số tiền: ");
            double amount = Double.parseDouble(scanner.nextLine());
            
            System.out.print("Trạng thái thanh toán (PAID/UNPAID): ");
            String paymentStatus = scanner.nextLine();
            
            System.out.print("Đặt định kỳ? (YES/NO): ");
            String periodic = scanner.nextLine();
            
            LocalDate periodicEndDate = null;
            if (periodic.equalsIgnoreCase("YES")) {
                System.out.print("Ngày kết thúc định kỳ (yyyy-MM-dd): ");
                String endDateStr = scanner.nextLine();
                periodicEndDate = LocalDate.parse(endDateStr);
            }
            
            System.out.print("Ghi chú: ");
            String notes = scanner.nextLine();
            
            // Thêm đặt sân vào database
            try (Connection conn = dbManager.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO bookings (field_id, customer_id, booking_date, start_time, " +
                         "end_time, amount, payment_status, booking_status, periodic, periodic_end_date, notes) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, 'CONFIRMED', ?, ?, ?)")) {
                
                stmt.setInt(1, fieldId);
                stmt.setInt(2, customerId);
                stmt.setDate(3, java.sql.Date.valueOf(bookingDate));
                stmt.setTime(4, java.sql.Time.valueOf(startTime));
                stmt.setTime(5, java.sql.Time.valueOf(endTime));
                stmt.setDouble(6, amount);
                stmt.setString(7, paymentStatus);
                stmt.setString(8, periodic);
                
                if (periodicEndDate != null) {
                    stmt.setDate(9, java.sql.Date.valueOf(periodicEndDate));
                } else {
                    stmt.setNull(9, Types.DATE);
                }
                
                stmt.setString(10, notes);
                
                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Đã thêm lịch đặt sân thành công!");
                    
                    // Nếu là đặt định kỳ, thêm các lịch đặt định kỳ
                    if (periodic.equalsIgnoreCase("YES") && periodicEndDate != null) {
                        addPeriodicBookings(fieldId, customerId, bookingDate, periodicEndDate, 
                                startTime, endTime, amount, paymentStatus, notes);
                    }
                } else {
                    System.out.println("Thêm lịch đặt sân thất bại.");
                }
            }
            
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm lịch đặt sân: " + e.getMessage());
        }
    }
    
    private void addPeriodicBookings(int fieldId, int customerId, LocalDate startDate, 
            LocalDate endDate, LocalTime startTime, LocalTime endTime, 
            double amount, String paymentStatus, String notes) {
        
        System.out.println("Đang thêm các lịch đặt sân định kỳ...");
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO bookings (field_id, customer_id, booking_date, start_time, " +
                     "end_time, amount, payment_status, booking_status, periodic, periodic_end_date, notes) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, 'CONFIRMED', 'YES', ?, ?)")) {
            
            LocalDate currentDate = startDate.plusDays(7);
            int count = 0;
            
            while (!currentDate.isAfter(endDate)) {
                // Kiểm tra xung đột thời gian
                if (!checkTimeConflict(fieldId, currentDate, startTime, endTime, 0)) {
                    stmt.setInt(1, fieldId);
                    stmt.setInt(2, customerId);
                    stmt.setDate(3, java.sql.Date.valueOf(currentDate));
                    stmt.setTime(4, java.sql.Time.valueOf(startTime));
                    stmt.setTime(5, java.sql.Time.valueOf(endTime));
                    stmt.setDouble(6, amount);
                    stmt.setString(7, paymentStatus);
                    stmt.setDate(8, java.sql.Date.valueOf(endDate));
                    stmt.setString(9, notes);
                    
                    stmt.executeUpdate();
                    count++;
                }
                
                currentDate = currentDate.plusDays(7);
            }
            
            System.out.println("Đã thêm " + count + " lịch đặt sân định kỳ.");
            
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm lịch đặt sân định kỳ: " + e.getMessage());
        }
    }
    
    private void updateBooking() {
        try {
            System.out.print("Nhập ID lịch đặt sân cần cập nhật: ");
            int bookingId = Integer.parseInt(scanner.nextLine());
            
            // Kiểm tra xem lịch đặt sân có tồn tại không
            Booking booking = getBookingById(bookingId);
            
            if (booking == null) {
                System.out.println("Không tìm thấy lịch đặt sân với ID này.");
                return;
            }
            
            System.out.println("\nThông tin lịch đặt sân hiện tại:");
            System.out.println("ID: " + booking.getId());
            System.out.println("Sân: " + booking.getFieldName());
            System.out.println("Khách hàng: " + booking.getCustomerName());
            System.out.println("Ngày: " + booking.getBookingDate());
            System.out.println("Giờ bắt đầu: " + booking.getStartTime());
            System.out.println("Giờ kết thúc: " + booking.getEndTime());
            System.out.println("Số tiền: " + booking.getAmount());
            System.out.println("Trạng thái thanh toán: " + booking.getPaymentStatus());
            System.out.println("Trạng thái đặt sân: " + booking.getBookingStatus());
            
            System.out.println("\nNhập thông tin mới (nhấn Enter để giữ nguyên):");
            
            System.out.System.out.println("\nNhập thông tin mới (nhấn Enter để giữ nguyên):");
            
            System.out.print("Ngày đặt sân mới (yyyy-MM-dd): ");
            String dateStr = scanner.nextLine();
            LocalDate newBookingDate = dateStr.isEmpty() ? booking.getBookingDate() : LocalDate.parse(dateStr);
            
            System.out.print("Giờ bắt đầu mới (HH:mm): ");
            String startTimeStr = scanner.nextLine();
            LocalTime newStartTime = startTimeStr.isEmpty() ? 
                    booking.getStartTime().toLocalTime() : LocalTime.parse(startTimeStr);
            
            System.out.print("Giờ kết thúc mới (HH:mm): ");
            String endTimeStr = scanner.nextLine();
            LocalTime newEndTime = endTimeStr.isEmpty() ? 
                    booking.getEndTime().toLocalTime() : LocalTime.parse(endTimeStr);
            
            // Kiểm tra xung đột thời gian nếu có thay đổi
            if (!newBookingDate.equals(booking.getBookingDate()) || 
                !newStartTime.equals(booking.getStartTime().toLocalTime()) || 
                !newEndTime.equals(booking.getEndTime().toLocalTime())) {
                
                if (checkTimeConflict(booking.getFieldId(), newBookingDate, newStartTime, newEndTime, bookingId)) {
                    System.out.println("Xung đột thời gian đặt sân. Vui lòng chọn thời gian khác.");
                    return;
                }
            }
            
            System.out.print("Số tiền mới: ");
            String amountStr = scanner.nextLine();
            double newAmount = amountStr.isEmpty() ? booking.getAmount() : Double.parseDouble(amountStr);
            
            System.out.print("Trạng thái thanh toán mới (PAID/UNPAID): ");
            String newPaymentStatus = scanner.nextLine();
            if (newPaymentStatus.isEmpty()) {
                newPaymentStatus = booking.getPaymentStatus();
            }
            
            System.out.print("Trạng thái đặt sân mới (CONFIRMED/CANCELLED): ");
            String newBookingStatus = scanner.nextLine();
            if (newBookingStatus.isEmpty()) {
                newBookingStatus = booking.getBookingStatus();
            }
            
            System.out.print("Ghi chú mới: ");
            String newNotes = scanner.nextLine();
            
            // Cập nhật vào database
            try (Connection conn = dbManager.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "UPDATE bookings SET booking_date = ?, start_time = ?, end_time = ?, " +
                         "amount = ?, payment_status = ?, booking_status = ?, notes = ? " +
                         "WHERE booking_id = ?")) {
                
                stmt.setDate(1, java.sql.Date.valueOf(newBookingDate));
                stmt.setTime(2, java.sql.Time.valueOf(newStartTime));
                stmt.setTime(3, java.sql.Time.valueOf(newEndTime));
                stmt.setDouble(4, newAmount);
                stmt.setString(5, newPaymentStatus);
                stmt.setString(6, newBookingStatus);
                stmt.setString(7, newNotes.isEmpty() ? booking.getNotes() : newNotes);
                stmt.setInt(8, bookingId);
                
                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    System.out.println("Cập nhật lịch đặt sân thành công!");
                } else {
                    System.out.println("Cập nhật lịch đặt sân thất bại.");
                }
            }
            
        } catch (Exception e) {
            System.out.println("Lỗi khi cập nhật lịch đặt sân: " + e.getMessage());
        }
    }
    
    private void cancelBooking() {
        try {
            System.out.print("Nhập ID lịch đặt sân cần hủy: ");
            int bookingId = Integer.parseInt(scanner.nextLine());
            
            // Kiểm tra xem lịch đặt sân có tồn tại không
            Booking booking = getBookingById(bookingId);
            
            if (booking == null) {
                System.out.println("Không tìm thấy lịch đặt sân với ID này.");
                return;
            }
            
            System.out.println("\nThông tin lịch đặt sân:");
            System.out.println("ID: " + booking.getId());
            System.out.println("Sân: " + booking.getFieldName());
            System.out.println("Khách hàng: " + booking.getCustomerName());
            System.out.println("Ngày: " + booking.getBookingDate());
            System.out.println("Giờ: " + booking.getStartTime() + " - " + booking.getEndTime());
            
            System.out.print("\nBạn có chắc chắn muốn hủy lịch đặt sân này? (Y/N): ");
            String confirm = scanner.nextLine();
            
            if (confirm.equalsIgnoreCase("Y")) {
                try (Connection conn = dbManager.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(
                             "UPDATE bookings SET booking_status = 'CANCELLED' WHERE booking_id = ?")) {
                    
                    stmt.setInt(1, bookingId);
                    
                    int rowsAffected = stmt.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        System.out.println("Đã hủy lịch đặt sân thành công!");
                    } else {
                        System.out.println("Hủy lịch đặt sân thất bại.");
                    }
                }
            } else {
                System.out.println("Đã hủy thao tác.");
            }
            
        } catch (Exception e) {
            System.out.println("Lỗi khi hủy lịch đặt sân: " + e.getMessage());
        }
    }
    
    private void searchBookings() {
        System.out.println("\n===== TÌM KIẾM LỊCH ĐẶT SÂN =====");
        System.out.println("1. Tìm theo khách hàng");
        System.out.println("2. Tìm theo ngày");
        System.out.println("3. Tìm theo sân");
        System.out.println("0. Quay lại");
        System.out.print("Nhập lựa chọn của bạn: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            
            switch (choice) {
                case 1:
                    searchBookingsByCustomer();
                    break;
                case 2:
                    searchBookingsByDate();
                    break;
                case 3:
                    searchBookingsByField();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("Lựa chọn không hợp lệ.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Vui lòng nhập một số.");
        }
    }
    
    private void searchBookingsByCustomer() {
        System.out.print("Nhập tên khách hàng: ");
        String customerName = scanner.nextLine();
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT b.booking_id, f.field_name, c.name AS customer_name, " +
                     "b.booking_date, b.start_time, b.end_time, b.amount, " +
                     "b.payment_status, b.booking_status " +
                     "FROM bookings b " +
                     "JOIN fields f ON b.field_id = f.field_id " +
                     "JOIN customers c ON b.customer_id = c.customer_id " +
                     "WHERE c.name LIKE ? " +
                     "ORDER BY b.booking_date, b.start_time")) {
            
            stmt.setString(1, "%" + customerName + "%");
            
            ResultSet rs = stmt.executeQuery();
            
            displayBookingResults(rs);
            
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm kiếm: " + e.getMessage());
        }
    }
    
    private void searchBookingsByDate() {
        System.out.print("Nhập ngày (yyyy-MM-dd): ");
        String dateStr = scanner.nextLine();
        
        try {
            LocalDate date = LocalDate.parse(dateStr);
            
            try (Connection conn = dbManager.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "SELECT b.booking_id, f.field_name, c.name AS customer_name, " +
                         "b.booking_date, b.start_time, b.end_time, b.amount, " +
                         "b.payment_status, b.booking_status " +
                         "FROM bookings b " +
                         "JOIN fields f ON b.field_id = f.field_id " +
                         "JOIN customers c ON b.customer_id = c.customer_id " +
                         "WHERE b.booking_date = ? " +
                         "ORDER BY b.start_time")) {
                
                stmt.setDate(1, java.sql.Date.valueOf(date));
                
                ResultSet rs = stmt.executeQuery();
                
                displayBookingResults(rs);
                
            } catch (SQLException e) {
                System.out.println("Lỗi khi tìm kiếm: " + e.getMessage());
            }
            
        } catch (Exception e) {
            System.out.println("Định dạng ngày không hợp lệ. Sử dụng yyyy-MM-dd.");
        }
    }
    
    private void searchBookingsByField() {
        System.out.print("Nhập tên sân: ");
        String fieldName = scanner.nextLine();
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT b.booking_id, f.field_name, c.name AS customer_name, " +
                     "b.booking_date, b.start_time, b.end_time, b.amount, " +
                     "b.payment_status, b.booking_status " +
                     "FROM bookings b " +
                     "JOIN fields f ON b.field_id = f.field_id " +
                     "JOIN customers c ON b.customer_id = c.customer_id " +
                     "WHERE f.field_name LIKE ? " +
                     "ORDER BY b.booking_date, b.start_time")) {
            
            stmt.setString(1, "%" + fieldName + "%");
            
            ResultSet rs = stmt.executeQuery();
            
            displayBookingResults(rs);
            
        } catch (SQLException e) {
            System.out.println("Lỗi khi tìm kiếm: " + e.getMessage());
        }
    }
    
    private void displayBookingResults(ResultSet rs) throws SQLException {
        System.out.println("\nKết quả tìm kiếm:");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s %-15s %-20s %-12s %-10s %-10s %-10s %-15s %-15s\n", 
                "ID", "Sân", "Khách hàng", "Ngày", "Bắt đầu", "Kết thúc", "Giá tiền", "Trạng thái TT", "Trạng thái đặt");
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
        
        boolean hasData = false;
        
        while (rs.next()) {
            hasData = true;
            int bookingId = rs.getInt("booking_id");
            String fieldName = rs.getString("field_name");
            String customerName = rs.getString("customer_name");
            Date bookingDate = rs.getDate("booking_date");
            Time startTime = rs.getTime("start_time");
            Time endTime = rs.getTime("end_time");
            double amount = rs.getDouble("amount");
            String paymentStatus = rs.getString("payment_status");
            String bookingStatus = rs.getString("booking_status");
            
            System.out.printf("%-5d %-15s %-20s %-12s %-10s %-10s %-10.2f %-15s %-15s\n", 
                    bookingId, fieldName, customerName, bookingDate, startTime, endTime, 
                    amount, paymentStatus, bookingStatus);
        }
        
        if (!hasData) {
            System.out.println("Không tìm thấy lịch đặt sân phù hợp.");
        }
        
        System.out.println("----------------------------------------------------------------------------------------------------------------------------------");
    }
    
    private List<Field> getAvailableFields() {
        List<Field> fields = new ArrayList<>();
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT field_id, field_name, type, size, price_per_hour FROM fields")) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Field field = new Field();
                field.setId(rs.getInt("field_id"));
                field.setName(rs.getString("field_name"));
                field.setType(rs.getString("type"));
                field.setSize(rs.getString("size"));
                field.setPricePerHour(rs.getDouble("price_per_hour"));
                
                fields.add(field);
            }
            
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách sân: " + e.getMessage());
        }
        
        return fields;
    }
    
    private List<Customer> getCustomers() {
        List<Customer> customers = new ArrayList<>();
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT customer_id, name, phone FROM customers")) {
            
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getInt("customer_id"));
                customer.setName(rs.getString("name"));
                customer.setPhone(rs.getString("phone"));
                
                customers.add(customer);
            }
            
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy danh sách khách hàng: " + e.getMessage());
        }
        
        return customers;
    }
    
    private Booking getBookingById(int bookingId) {
        Booking booking = null;
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT b.booking_id, b.field_id, f.field_name, b.customer_id, c.name AS customer_name, " +
                     "b.booking_date, b.start_time, b.end_time, b.amount, " +
                     "b.payment_status, b.booking_status, b.notes " +
                     "FROM bookings b " +
                     "JOIN fields f ON b.field_id = f.field_id " +
                     "JOIN customers c ON b.customer_id = c.customer_id " +
                     "WHERE b.booking_id = ?")) {
            
            stmt.setInt(1, bookingId);
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                booking = new Booking();
                booking.setId(rs.getInt("booking_id"));
                booking.setFieldId(rs.getInt("field_id"));
                booking.setFieldName(rs.getString("field_name"));
                booking.setCustomerId(rs.getInt("customer_id"));
                booking.setCustomerName(rs.getString("customer_name"));
                booking.setBookingDate(rs.getDate("booking_date").toLocalDate());
                booking.setStartTime(rs.getTime("start_time"));
                booking.setEndTime(rs.getTime("end_time"));
                booking.setAmount(rs.getDouble("amount"));
                booking.setPaymentStatus(rs.getString("payment_status"));
                booking.setBookingStatus(rs.getString("booking_status"));
                booking.setNotes(rs.getString("notes"));
            }
            
        } catch (SQLException e) {
            System.out.println("Lỗi khi lấy thông tin lịch đặt sân: " + e.getMessage());
        }
        
        return booking;
    }
    
    private boolean checkTimeConflict(int fieldId, LocalDate date, 
            LocalTime startTime, LocalTime endTime, int excludeBookingId) {
        
        boolean hasConflict = false;
        
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT COUNT(*) AS conflict_count FROM bookings " +
                     "WHERE field_id = ? AND booking_date = ? AND booking_status <> 'CANCELLED' " +
                     "AND booking_id <> ? " +
                     "AND ((start_time <= ? AND end_time > ?) OR " +
                     "(start_time < ? AND end_time >= ?) OR " +
                     "(start_time >= ? AND end_time <= ?))")) {
            
            stmt.setInt(1, fieldId);
            stmt.setDate(2, java.sql.Date.valueOf(date));
            stmt.setInt(3, excludeBookingId);
            stmt.setTime(4, java.sql.Time.valueOf(endTime));
            stmt.setTime(5, java.sql.Time.valueOf(startTime));
            stmt.setTime(6, java.sql.Time.valueOf(endTime));
            stmt.setTime(7, java.sql.Time.valueOf(startTime));
            stmt.setTime(8, java.sql.Time.valueOf(startTime));
            stmt.setTime(9, java.sql.Time.valueOf(endTime));
            
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next() && rs.getInt("conflict_count") > 0) {
                hasConflict = true;
            }
            
        } catch (SQLException e) {
            System.out.println("Lỗi khi kiểm tra xung đột thời gian: " + e.getMessage());
        }
        
        return hasConflict;
    }
}

class MonthlyBookingManager {
    private DatabaseManager dbManager;
    private Scanner scanner = new Scanner(System.in);
    
    public MonthlyBookingManager(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }
    
    public void manageMonthlyBookings() {
        int choice = 0;
        do {
            System.out.println("\n===== QUẢN LÝ ĐƠN THÁNG =====");
            System.out.println("1. Xem tất cả đơn tháng");
            System.out.println("2. Thêm đơn tháng mới");
            System.out.println("3. Cập nhật đơn tháng");
            System.out.println("4. Hủy đơn tháng");
            System.out.println("5. Tạo hóa đơn từ đơn tháng");
            System.out.println("0. Quay lại");
            System.out.print("Nhập lựa chọn của bạn: ");
            
            try {
                choice = Integer.parseInt(scanner.nextLine());
                
                switch (choice) {
                    case 1:
                        viewAllMonthlyBookings();
                        break;
                    case 2:
                        addNewMonthlyBooking();
                        break;
                    case 3:
                        updateMonthlyBooking();
                        break;
                    case 4:
                        cancelMonthlyBooking();
                        break;
                    case 5:
                        createInvoiceFromMonthlyBooking();
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Lựa chọn không hợp lệ.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Vui lòng nhập một số.");
            }
        } while (choice != 0);
    }
    
    private void viewAllMonthlyBookings() {
        try (Connection conn = dbManager.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT mb.monthly_booking_id, c.name AS customer_name, " +
                     "mb.month, mb.year, mb.sessions_per_month, mb.price_per_session, " +
                     "mb.total_amount, mb.payment_status " +
                     "FROM monthly_bookings mb " +
                     "JOIN customers c ON mb.customer_id = c.customer_id " +
                     "ORDER BY mb.year DESC, mb.month DESC")) {
            
            ResultSet rs = stmt.executeQuery();
            
            System.out.println("\nDanh sách đơn tháng:");
            System.out.println("-------------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-5s %-20s %-10s %-15s %-10s %-15s %-15s\n", 
                    "ID", "Khách hàng", "Tháng/Năm", "Số buổi/tháng", "Giá/buổi", "Tổng tiền", "Trạng thái TT");
            System.out.println("-------------------------------------------------------------------------------------------------------------------");
            
            boolean hasData = false;
            
            while (rs.next()) {
                hasData = true;
                int id = rs.getInt("monthly_booking_id");
                String customerName = rs.getString("customer_name");
                int month = rs.getInt("month");
                int year = rs.getInt("year");
                int sessions = rs.getInt("sessions_per_month");
                double pricePerSession = rs.getDouble("price_per_session");
                double totalAmount = rs.getDouble("total_amount");
                String paymentStatus = rs.getString("payment_status");
                
                System.out.printf("%-5d %-20s %-10s %-15d %-10.2f %-15.2f %-15s\n", 
                        id, customerName, month + "/" + year, sessions, pricePerSession, totalAmount, paymentStatus);
            }
            
            if (!hasData) {
                System.out.println("Không có dữ liệu đơn tháng.");
            }
            
            System.out.println("-------------------------------------------------------------------------------------------------------------------");
            
        } catch (SQLException e) {
            System.out.println("Lỗi khi truy vấn dữ liệu: " + e.getMessage());
        }
    }
    
    private void addNewMonthlyBooking() {
        try {
            // Hiển thị danh sách khách hàng
            List<Customer> customers = getCustomers();
            if (customers.isEmpty()) {
                System.out.println("Không có khách hàng nào. Vui lòng thêm khách hàng trước.");
                return;
            }
            
            System.out.println("\nDanh sách khách hàng:");
            for (Customer customer : customers) {
                System.out.println(customer.getId() + ": " + customer.getName() + " - " + customer.getPhone());
            }
            
            System.out.print("Chọn ID khách hàng: ");
            int customerId = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Nhập tháng (1-12): ");
            int month = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Nhập năm: ");
            int year = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Nhập số buổi/tháng: ");
            int sessionsPerMonth = Integer.parseInt(scanner.nextLine());
            
            System.out.print("Nhập giá/buổi: ");
            double pricePerSession = Double.parseDouble(scanner.nextLine());
            
            double totalAmount = sessionsPerMonth * pricePerSession;
            
            System.out.println("Tổng tiền: " + totalAmount);
            
            System.out.print("Trạng thái thanh toán (PAID/UNPAID): ");
            String paymentStatus = scanner.nextLine();
            
            System.out.print("Ghi chú: ");
            String notes = scanner.nextLine();
            
            // Thêm đơn tháng vào database
            int monthlyBookingId = -1;
            
            try (Connection conn = dbManager.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(
                         "INSERT INTO monthly_bookings (customer_id, month, year, " +
                         "sessions_per_month, price_per_session, total_amount, payment_status, notes) " +
                         "VALUES (?, ?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
                
                stmt.setInt(1, customerId);
                stmt.setInt(2, month);
                stmt.setInt(3, year);
                stmt.setInt(4, sessionsPerMonth);
                stmt.setDouble(5, pricePerSession);
                stmt.setDouble(6, totalAmount);
                stmt.setString(7, paymentStatus);
                stmt.setString(8, notes);
                
                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    ResultSet rs = stmt.getGeneratedKeys();
                    if (rs.next()) {
                        monthlyBookingId = rs.getInt(1);
                        System.out.println("Đã thêm đơn tháng thành công! ID: " + monthlyBookingId);
                    }
                } else {
                    System.out.println("Thêm đơn tháng thất bại.");
                    return;
                }
            }
            
            // Nếu thêm thành công, nhập chi tiết lịch định kỳ
            if (monthlyBookingId > 0) {
                addMonthlyBookingDetails(monthlyBookingId);
            }
            
        } catch (Exception e) {
            System.out.println("Lỗi khi thêm đơn tháng: " + e.getMessage());
        }
    }
    
    private void addMonthlyBookingDetails(int monthlyBookingId) {
        System.out.println("\nThêm chi tiết lịch định kỳ cho đơn tháng:");
        
        // Hiển thị danh sách sân
        List<Field> fields = getAvailableFields();
        if (fields.isEmpty()) {
            System.out.println("Không có sân nào khả dụng.");
            return;
        }
        
        boolean continueAdding = true;
        
        while (continueAdding) {
            try {
                System.out.println("\nDanh sách sân:");
                for (Field field : fields) {
                    System.out.println(field.getId() + ": " + field.getName());
                }
                
                System.out.print("Chọn ID sân: ");
                int fieldId = Integer.parseInt(scanner.nextLine());
                
                System.out.println("Chọn ngày trong tuần (1: Thứ Hai, 2: Thứ Ba, ..., 7: Chủ Nhật):");
                int dayOfWeek = Integer.parseInt(scanner.nextLine());
                
                System.out.print("Nhập giờ bắt đầu (HH:mm): ");
                String startTimeStr = scanner.nextLine();
                LocalTime startTime = LocalTime.parse(startTimeStr);
                
                System.out.print("Nhập giờ kết thúc (HH:mm): ");
                String endTimeStr = scanner.nextLine();
                LocalTime endTime = LocalTime.parse(endTimeStr);
                
                // Thêm chi tiết vào database
                try (Connection conn = dbManager.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(
                             "INSERT INTO monthly_booking_details (monthly_booking_id, field_id, " +
                             "day_of_week, start_time, end_time) VALUES (?, ?, ?, ?, ?)")) {
                    
                    stmt.setInt(1, monthlyBookingId);
                    stmt.setInt(2, fieldId);
                    stmt.setInt(3, dayOfWeek);
                    stmt.setTime(4, java.sql.Time.valueOf(startTime));
                    stmt.setTime(5, java.sql.Time.valueOf(endTime));
                    
                    int rowsAffected = stmt.executeUpdate();
                    
                    if (rowsAffected > 0) {
                        System.out.println("Đã thêm chi tiết lịch định kỳ thành công!");
                    } else {
                        System.out.println("Thêm chi tiết lịch định kỳ thất bại.");
                    }
                }
                
                System.out.print("Bạn có muốn thêm lịch định kỳ khác? (Y/N): ");
                String choice = scanner.nextLine();
                continueAdding = choice.equalsIgnoreCase("Y");
                
            } catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
                System.out.print("Bạn có muốn thử lại? (Y/N): ");
                String choice = scanner.nextLine();
                continueAdding = choice.equalsIgnoreCase("Y");
            }
        }
    }
    
    private void updateMonthlyBooking() {
        System.out.println("Tính năng cập nhật đơn tháng sẽ được phát triển trong phiên bản tiếp theo.");
    }
    
    private void cancelMonthlyBooking() {
        System.out.println("Tính năng hủy đơn tháng sẽ được phát triển trong phiên bản tiếp theo.");
    }
    
    private void createInvoiceFromMonthlyBooking() {
        System.out.println("Tính năng tạo hóa đơn từ đơn tháng sẽ được phát triển trong phiên bản tiếp theo.");
    }
}