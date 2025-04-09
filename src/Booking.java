import java.time.LocalDateTime;

public class Booking {
    private int bookingId;
    private int courtId;
    private int customerId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status; // "Đặt trước", "Hoàn thành", "Hủy"

    public Booking(int bookingId, int courtId, int customerId, LocalDateTime startTime, LocalDateTime endTime, String status) {
        this.bookingId = bookingId;
        this.courtId = courtId;
        this.customerId = customerId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
    }

    // Getters and Setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getCourtId() {
        return courtId;
    }

    public void setCourtId(int courtId) {
        this.courtId = courtId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Đặt sân ID: " + bookingId + " - Sân: " + courtId + " - Từ: " + startTime + " đến " + endTime;
    }
}