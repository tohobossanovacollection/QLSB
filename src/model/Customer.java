package model;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Customer {
    private int id;
    private String name;
    private String phone;
    private String email;
    private String address;
    private String customerType; // REGULAR, VIP, TEAM//TODO : in development
    private double totalSpent;
    private double debt;
    private LocalDateTime createdAt;
    private List<Booking> bookingHistory;
    private List<Invoice> invoiceHistory;

    public Customer(int id, String name, String phone, String email, String address, String customerType) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.customerType = customerType;
        this.totalSpent = 0;
        this.debt = 0;
        this.createdAt = LocalDateTime.now();
        this.bookingHistory = new ArrayList<>();
        this.invoiceHistory = new ArrayList<>();
    }

    public Customer() {
        this.totalSpent = 0;
        this.debt = 0;
        this.createdAt = LocalDateTime.now();
        this.bookingHistory = new ArrayList<>();
        this.invoiceHistory = new ArrayList<>();
    }

    // Getters và Setters
    public int getId() {
        return id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public double getTotalSpent() {
        return totalSpent;
    }

    public void addToTotalSpent(double amount) {
        this.totalSpent += amount;
    }

    public double getDebt() {
        return debt;
    }

    public void addToDebt(double amount) {
        this.debt += amount;
    }

    public void payDebt(double amount) {
        this.debt -= amount;
        if (this.debt < 0) {
            this.debt = 0;
        }
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<Booking> getBookingHistory() {
        return bookingHistory;
    }

    public void addBooking(Booking booking) {
        this.bookingHistory.add(booking);
    }

    public List<Invoice> getInvoiceHistory() {
        return invoiceHistory;
    }

    public void addInvoice(Invoice invoice) {
        this.invoiceHistory.add(invoice);
    }

    @Override
    public String toString() {
        return name + " (" + phone + ")";
    }
}
