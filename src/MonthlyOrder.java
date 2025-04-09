import java.time.LocalDateTime;

public class MonthlyOrder {
    private int orderId;
    private int customerId;
    private int courtId;
    private int sessionsPerMonth; // Số buổi/tháng
    private LocalDateTime fixedTimeStart; // Thời gian cố định
    private double discount; // Ưu đãi
    private double totalAmount; // Tổng tiền

    public MonthlyOrder(
        int orderId, 
        int customerId, 
        int courtId, 
        int sessionsPerMonth, 
        LocalDateTime fixedTimeStart, 
        double discount, 
        double totalAmount
    ) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.courtId = courtId;
        this.sessionsPerMonth = sessionsPerMonth;
        this.fixedTimeStart = fixedTimeStart;
        this.discount = discount;
        this.totalAmount = totalAmount;
    }

    // Getters và Setters
    public int getOrderId() {
        return orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public int getCourtId() {
        return courtId;
    }

    public int getSessionsPerMonth() {
        return sessionsPerMonth;
    }

    public LocalDateTime getFixedTimeStart() {
        return fixedTimeStart;
    }

    public double getDiscount() {
        return discount;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    @Override
    public String toString() {
        return "Đơn tháng ID: " + orderId + " - Sân: " + courtId + " - Số buổi: " + sessionsPerMonth +
               " - Tổng tiền: " + totalAmount;
    }
}