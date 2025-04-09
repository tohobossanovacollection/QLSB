import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookingService {
    private List<Booking> bookings;

    public BookingService() {
        bookings = new ArrayList<>();
    }

    public void addBooking(Booking booking) {
        for (Booking existing : bookings) {
            if (existing.getCourtId() == booking.getCourtId() &&
                existing.getStartTime().isBefore(booking.getEndTime()) &&
                existing.getEndTime().isAfter(booking.getStartTime())) {
                System.out.println("Trùng lịch đặt sân!");
                return;
            }
        }
        bookings.add(booking);
        System.out.println("Đặt sân thành công!");
    }

    public void addRecurringBooking(int courtId, int customerId, LocalDateTime startTime, 
                                   LocalDateTime endTime, int weeks) {
        for (int i = 0; i < weeks; i++) {
            LocalDateTime newStart = startTime.plusWeeks(i);
            LocalDateTime newEnd = endTime.plusWeeks(i);
            addBooking(new Booking(bookings.size() + 1, courtId, customerId, newStart, newEnd, "Đặt trước"));
        }
    }

    public List<Booking> getBookingsByCourt(int courtId) {
        List<Booking> result = new ArrayList<>();
        for (Booking booking : bookings) {
            if (booking.getCourtId() == courtId) {
                result.add(booking);
            }
        }
        return result;
    }
}