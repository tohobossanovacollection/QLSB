import java.util.ArrayList;
import java.util.List;

public class CustomerService {
    private List<Customer> customers;

    public CustomerService() {
        customers = new ArrayList<>();
        customers.add(new Customer(1, "Nguyễn Văn A", "0901234567", "Thường"));
    }

    public void addCustomer(Customer customer) {
        customers.add(customer);
    }

    public List<Customer> getAllCustomers() {
        return customers;
    }

    public List<Booking> getBookingHistory(int customerId, BookingService bookingService) {
        return bookingService.getBookingsByCourt(customerId); // Lịch sử đặt sân
    }

    public double getDebt(int customerId) {
        return 0; // Tạm thời trả về 0, sau này dùng JDBC để tính công nợ
    }
}