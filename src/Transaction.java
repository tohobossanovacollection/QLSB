import java.time.LocalDateTime;

public class Transaction {
    private int transactionId;
    private String type; // "Thu" hoặc "Chi"
    private double amount;
    private String description;
    private LocalDateTime transactionTime;

    public Transaction(int transactionId, String type, double amount, String description, LocalDateTime transactionTime) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.description = description;
        this.transactionTime = transactionTime;
    }

    // Getters
    public int getTransactionId() {
        return transactionId;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getTransactionTime() {
        return transactionTime;
    }

    @Override
    public String toString() {
        return "Giao dịch ID: " + transactionId + " - Loại: " + type + " - Số tiền: " + amount +
               " - Mô tả: " + description;
    }
}