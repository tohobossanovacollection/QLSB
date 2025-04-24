package service;

import DAO.CustomerDAO;
import DAO.impl.CustomerDAOImpl;
import model.Booking;
import model.Customer;
//import model.Invoice;

import java.util.List;

public class CustomerService {
    private CustomerDAO customerDAO;
    private BookingService bookingService;
    
    public CustomerService() {
        this.customerDAO = new CustomerDAOImpl();
        this.bookingService = new BookingService();
    }
    
    public Customer getCustomerById(int id) {
        Customer customer = customerDAO.findById(id);
        if (customer != null) {
            // Lấy lịch sử đặt sân
            List<Booking> bookings = bookingService.getBookingsByCustomer(id);
            for (Booking booking : bookings) {
                customer.addBooking(booking);
            }

            
            // TODO: Lấy lịch sử hóa đơn
        }
        return customer;
    }
    
    public Customer getCustomerByPhone(String phone) {
        return customerDAO.findByPhone(phone);
    }
    
    public List<Customer> getAllCustomers() {
        return customerDAO.findAll();
    }
    
    /*public List<Customer> getCustomersByType(String type) {
        return customerDAO.findByType(type);
    }
    
    public List<Customer> getCustomersWithDebt() {
        return customerDAO.findByDebt();
    }*/
    
    public List<Customer> searchCustomersByName(String keyword) {
        return customerDAO.searchByName(keyword);
    }
    
    public boolean addCustomer(Customer customer) {
        return customerDAO.save(customer);
    }
    
    public boolean updateCustomer(Customer customer) {
        return customerDAO.update(customer);
    }
    
    public boolean deleteCustomer(int id) {
        return customerDAO.delete(id);
    }
    
    public boolean recordPayment(int customerId, double amount) {
        Customer customer = getCustomerById(customerId);
        if (customer != null) {
            //customer.payDebt(amount);
            return customerDAO.update(customer);
        }
        return false;
    }
}
