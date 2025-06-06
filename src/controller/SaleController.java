package controller;

import java.util.List;
import model.Product;
import service.CustomerService;
import service.PitchService;
import service.ProductService;
import view.SaleView;

public class SaleController {
    private ProductService productService = new ProductService(); 
    private PitchService pitchService = new PitchService(); 
    private CustomerService customerService = new CustomerService();
    private SaleView saleView; // Assuming you have a SaleView class to interact with the UI
    public SaleController(SaleView saleView) {
        this.saleView = saleView;
        // Initialize any necessary services or views here
    }

    public void loadProducts(int branchId) {
        List<Product> productdata = productService.getAllProducts();
        saleView.loadProductList(productdata);
        saleView.initializeData(productdata);
        saleView.loadCustomerCb(customerService.getAllCustomers());
        
        if (branchId != 0) {//neu la admin tong thi hien thi tat ca pitch
            saleView.loadPitchCb(pitchService.getPitchesByBranch(branchId));
        } else {
            saleView.loadPitchCb(pitchService.getAllPitches());
        }
    }

    public void processSaveInvoice(){
        
    }
}
