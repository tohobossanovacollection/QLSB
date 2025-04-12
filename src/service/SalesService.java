package service;

import DAO.InvoiceDAO;
import DAO.ProductDAO;
import DAO.CustomerDAO;
import DAO.TransactionDAO;
import DAO.impl.InvoiceDAOImpl;
import DAO.impl.ProductDAOImpl;
import DAO.impl.CustomerDAOImpl;
import DAO.impl.TransactionDAOImpl;
import model.Invoice;
import model.InvoiceItem;
import model.Product;
import model.Customer;
import model.Transaction;

import java.util.List;
import java.util.Date;

public class SalesService {
    private InvoiceDAO invoiceDAO;
    private ProductDAO productDAO;
    private CustomerDAO customerDAO;
    private TransactionDAO transactionDAO;
    private InventoryService inventoryService;

    public SalesService() {
        this.invoiceDAO = new InvoiceDAOImpl();
        this.productDAO = new ProductDAOImpl();
        this.customerDAO = new CustomerDAOImpl();
        this.transactionDAO = new TransactionDAOImpl();
        this.inventoryService = new InventoryService();
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

        double totalAmount = calculateTotalAmount(items);

        Invoice invoice = new Invoice(id, pitchId, customerId, type, discount, note);
        for (InvoiceItem item : items) {
            invoice.addItem(item);
        }

        if (invoiceDAO.save(invoice)) {
            updateInventory(items);
            createTransaction(invoice);
        }

        return invoice;
    }

    private double calculateTotalAmount(List<InvoiceItem> items) {
        double total = 0;
        for (InvoiceItem item : items) {
            total += item.getQuantity() * item.getUnitPrice();
        }
        return total;
    }

    private void updateInventory(List<InvoiceItem> items) throws Exception {
        for (InvoiceItem item : items) {
            inventoryService.updateStock(item.getProduct().getId(), -item.getQuantity());
        }
    }

    private void createTransaction(Invoice invoice) throws Exception {
        Transaction transaction = new Transaction();
        transaction.setType("INCOME");
        transaction.setAmount(invoice.getTotalAmount());
        transaction.setDate(new Date());
        transaction.setDescription("Payment for invoice #" + invoice.getId());
        transactionDAO.save(transaction);
    }

    public List<Invoice> getAllInvoices() {
        return invoiceDAO.findAll();
    }

    public List<Invoice> getInvoicesByCustomer(int customerId) {
        return invoiceDAO.findByCustomer(customerId);
    }

    public List<Invoice> searchInvoicesByDate(Date startDate, Date endDate) {
        return invoiceDAO.findByDateRange(startDate, endDate);
    }

    public Invoice cancelInvoice(int invoiceId, String reason) throws Exception {
        Invoice invoice = invoiceDAO.findById(invoiceId);
        if (invoice == null) {
            throw new Exception("Invoice does not exist.");
        }

        invoice.setStatus("CANCELLED");
        invoice.setCancelReason(reason);

        for (InvoiceItem item : invoice.getItems()) {
            inventoryService.updateStock(item.getProduct().getId(), item.getQuantity());
        }

        if (invoiceDAO.update(invoice)) {
            Transaction transaction = new Transaction();
            transaction.setType("EXPENSE");
            transaction.setAmount(invoice.getTotalAmount());
            transaction.setDate(new Date());
            transaction.setDescription("Refund for cancelled invoice #" + invoice.getId() + " - Reason: " + reason);
            transactionDAO.save(transaction);
        }

        return invoice;
    }

    public double getRevenueByDateRange(Date startDate, Date endDate) {
        List<Invoice> invoices = invoiceDAO.findByDateRange(startDate, endDate);
        double revenue = 0;

        for (Invoice invoice : invoices) {
            if (!"CANCELLED".equals(invoice.getStatus())) {
                revenue += invoice.getTotalAmount();
            }
        }

        return revenue;
    }

    public List<Object[]> getTopSellingProducts(int limit) {
        return invoiceDAO.getTopSellingProducts(limit);
    }
}