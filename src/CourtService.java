import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CourtService {
    private List<Court> courts;

    public CourtService() {
        courts = new ArrayList<>();
        courts.add(new Court(1, "Sân 1", "Trống"));
        courts.add(new Court(2, "Sân 2", "Đặt trước"));
    }

    public List<Court> getAllCourts() { return courts; }
    public void updateCourtStatus(int courtId, String newStatus) {
        for (Court court : courts) {
            if (court.getCourtId() == courtId) {
                court.setStatus(newStatus);
                return;
            }
        }
    }

    // Xem trạng thái theo ngày
    public List<Court> getCourtStatusByDate(LocalDateTime date) {
        return courts; // Sau này dùng JDBC để lọc theo ngày
    }
}