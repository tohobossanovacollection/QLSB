public class Product {
    private int productId;
    private String name;
    private double price;
    private int stock; // Số lượng tồn kho

    public Product(int productId, String name, double price, int stock) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // Getters và Setters
    public int getProductId() {
        return productId;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    @Override
    public String toString() {
        return "Sản phẩm: " + name + " (ID: " + productId + ") - Giá: " + price + " - Tồn kho: " + stock;
    }
}