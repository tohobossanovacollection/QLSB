public class Court {
    private int courtId;
    private String courtName;
    private String status; // "Trống", "Đang sử dụng", "Đặt trước"

    public Court(int courtId, String courtName, String status) {
        this.courtId = courtId;
        this.courtName = courtName;
        this.status = status;
    }

    // Getters and Setters
    public int getCourtId() {
        return courtId;
    }

    public void setCourtId(int courtId) {
        this.courtId = courtId;
    }

    public String getCourtName() {
        return courtName;
    }

    public void setCourtName(String courtName) {
        this.courtName = courtName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Sân " + courtName + " (ID: " + courtId + ") - Trạng thái: " + status;
    }
}