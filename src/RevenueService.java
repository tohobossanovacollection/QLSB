import java.time.LocalDateTime;
import java.util.List;

public class RevenueService {
    public double calculateDailyRevenue(List<Sale> sales, List<Booking> bookings, LocalDateTime date) {
        double total = 0;
        for (Sale sale : sales) {
            if (sale.getSaleTime().toLocalDate().equals(date.toLocalDate())) {
                total += sale.getTotalPrice();
            }
        }
        for (Booking booking : bookings) {
            if (booking.getStartTime().toLocalDate().equals(date.toLocalDate())) {
                total += 100000; // Giả định giá thuê sân
            }
        }
        return total;
    }

    public double calculateMonthlyRevenue(List<Sale> sales, List<Booking> bookings, int month, int year) {
        double total = 0;
        for (Sale sale : sales) {
            if (sale.getSaleTime().getMonthValue() == month && sale.getSaleTime().getYear() == year) {
                total += sale.getTotalPrice();
            }
        }
        return total; // Thêm logic cho bookings nếu cần
    }
}