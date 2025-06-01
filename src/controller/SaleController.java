package controller;

import java.util.List;
import model.Product;
import service.CustomerService;
import service.ProductService;
import view.SaleView;

public class SaleController {
    private ProductService productService = new ProductService(); 
    private CustomerService customerService = new CustomerService(); // Assuming you have a CustomerService class
    private SaleView saleView; // Assuming you have a SaleView class to interact with the UI
    public SaleController(SaleView saleView) {
        this.saleView = saleView;
        // Initialize any necessary services or views here
    }

    public void loadProducts() {
        List<Product> productdata = productService.getAllProducts();
        saleView.loadProductList(productdata);
        saleView.initializeData(productdata);
        saleView.loadCustomerCb(customerService.getAllCustomers());
    }

    public void processSaveInvoice(){
        
    }
}
