//import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MonthlyOrderService {
    private List<MonthlyOrder> monthlyOrders;

    public MonthlyOrderService() {
        monthlyOrders = new ArrayList<>();
    }

    public void addMonthlyOrder(MonthlyOrder order) {
        monthlyOrders.add(order);
        // Gọi JDBC để lưu
    }

    public double calculateMonthlyTotal(int orderId) {
        for (MonthlyOrder order : monthlyOrders) {
            if (order.getOrderId() == orderId) {
                return order.getTotalAmount() * (1 - order.getDiscount());
            }
        }
        return 0;
    }

    public List<MonthlyOrder> getAllMonthlyOrders() {
        return monthlyOrders; // Sau này thay bằng JDBC
    }
}