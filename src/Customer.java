public class Customer {
    private int customerId;
    private String name;
    private String phone;
    private String type; // "Thường", "VIP"

    public Customer(int customerId, String name, String phone, String type) {
        this.customerId = customerId;
        this.name = name;
        this.phone = phone;
        this.type = type;
    }

    // Getters và Setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Khách hàng: " + name + " (ID: " + customerId + ") - SĐT: " + phone + " - Loại: " + type;
    }
}