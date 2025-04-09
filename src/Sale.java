import java.time.LocalDateTime;

public class Sale {
    private int saleId;
    private int productId;
    private int quantity;
    private double totalPrice;
    private LocalDateTime saleTime;

    public Sale(int saleId, int productId, int quantity, double totalPrice, LocalDateTime saleTime) {
        this.saleId = saleId;
        this.productId = productId;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.saleTime = saleTime;
    }

    // Getters
    public int getSaleId() {
        return saleId;
    }

    public int getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public LocalDateTime getSaleTime() {
        return saleTime;
    }

    @Override
    public String toString() {
        return "Đơn bán ID: " + saleId + " - Sản phẩm ID: " + productId + " - Số lượng: " + quantity +
               " - Tổng: " + totalPrice;
    }
}