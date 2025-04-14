package service;

import DAO.InvoiceDAO;
import DAO.ProductDAO;
import DAO.CustomerDAO;
//import DAO.TransactionDAO;
import DAO.impl.InvoiceDAOImpl;
import DAO.impl.ProductDAOImpl;
import DAO.impl.CustomerDAOImpl;
//import DAO.impl.TransactionDAOImpl;
import model.Invoice;
import model.InvoiceItem;
import model.Product;
import model.Customer;
//import model.Transaction;
//tao invoice cho booking
//tao invoice cho monthly booking
//tao invoice cho product
import java.util.List;
//import java.util.Date;

public class SalesService {
    private InvoiceDAO invoiceDAO;
    private ProductDAO productDAO;
    private CustomerDAO customerDAO;
    //private TransactionDAO transactionDAO;
    private InventoryService inventoryService;
    private InvoiceService invoiceService;
    //private CustomerService customerService;

    public SalesService() {
        this.invoiceDAO = new InvoiceDAOImpl();
        this.productDAO = new ProductDAOImpl();
        this.customerDAO = new CustomerDAOImpl();
        //this.transactionDAO = new TransactionDAOImpl();
        this.inventoryService = new InventoryService();
        this.invoiceService = new InvoiceService();
    }

    public Invoice createInvoice(int id, int pitchId, int customerId, String type, double discount, String note, List<InvoiceItem> items) throws Exception {
        Customer customer = customerDAO.findById(customerId);
        if (customer == null) {
            throw new Exception("Customer does not exist.");
        }

        for (InvoiceItem item : items) {
            Product product = productDAO.findById(item.getItemId());
            if (product == null) {
                throw new Exception("Product does not exist: " + item.getItemId());
            }
            if (product.getCurrentStock() < item.getQuantity()) {
                throw new Exception("Insufficient stock for product: " + product.getName());
            }
        }

        Invoice invoice = new Invoice(id, pitchId, customerId, type, discount, note);
        for (InvoiceItem item : items) {
            invoice.addItem(item);
        }

        boolean isValid = invoiceService.doCreateInvoice(invoice);
        if (isValid) {
            updateInventory(items);
            //createTransaction(invoice);
        }

        return invoice;
    }

    private void updateInventory(List<InvoiceItem> items) throws Exception {
        for (InvoiceItem item : items) {
            inventoryService.updateStock(item.getItemId(), -item.getQuantity());
        }
    }

    /*private void createTransaction(Invoice invoice) throws Exception {
        Transaction transaction = new Transaction(invoice.getTotal(),invoice.getId(),invoice.getPichId());
        transaction.setType("INCOME");
        
        

        transaction.setDescription("Payment for invoice #" + invoice.getId());
        transactionDAO.save(transaction);
    }*/

    //HUY invoice cua booking
    public boolean cancelInvoice(int invoiceId, String reason) throws Exception {
        Invoice invoice = invoiceDAO.findById(invoiceId);
        if (invoice == null) {
            throw new Exception("Invoice does not exist.");
        }

        //invoice.setStatus("CANCELLED");
        //invoice.setCancelReason(reason);

        for (InvoiceItem item : invoice.getItems()) {
            inventoryService.updateStock(item.getItemId(), -item.getQuantity());
        }
        // Refund the customer
        Customer customer = customerDAO.findById(invoice.getCustomerId());
        customer.addToDebt(-(customer.getDebt() - invoice.getTotal()));


        invoiceDAO.delete(invoiceId);

        return true;
    }
}