import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class InventoryService {
    private List<Product> products;
    private List<Sale> sales;

    public InventoryService() {
        products = new ArrayList<>();
        sales = new ArrayList<>();
        products.add(new Product(1, "Nước suối", 5000, 100));
        products.add(new Product(2, "Khăn", 10000, 50));
    }

    public void addProduct(Product product) { products.add(product); }

    public void sellProduct(int productId, int quantity, LocalDateTime saleTime) {
        for (Product product : products) {
            if (product.getProductId() == productId) {
                if (product.getStock() < quantity) {
                    System.out.println("Không đủ hàng trong kho!");
                    return;
                }
                product.setStock(product.getStock() - quantity);
                sales.add(new Sale(sales.size() + 1, productId, quantity, product.getPrice() * quantity, saleTime));
                System.out.println("Bán hàng thành công!");
                if (product.getStock() < 10) {
                    System.out.println("Cảnh báo: " + product.getName() + " sắp hết hàng!");
                }
                return;
            }
        }
    }

    public List<Product> getAllProducts() { return products; }
    public List<Sale> getAllSales() { return sales; }
}