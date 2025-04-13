package controller;

import java.util.List;
import model.Customer;
import service.CustomerService;
import view.CustomerView;

public class CustomerController {
    private CustomerService customerService;
    private CustomerView customerView;
    
    public CustomerController(CustomerService customerService, CustomerView customerView) {
        this.customerService = customerService;
        this.customerView = customerView;
    }
    
    public void displayAllCustomers() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            customerView.displayCustomerList(customers);
        } catch (Exception e) {
            customerView.displayError("Error retrieving customers: " + e.getMessage());
        }
    }
    
    public void processNewCustomer() {
        Customer customerData = customerView.getCustomerData();
        
        try {
            Customer savedCustomer = customerService.createCustomer(customerData);
            customerView.displayCustomerCreationSuccess(savedCustomer);
        } catch (Exception e) {
            customerView.displayError("Error creating customer: " + e.getMessage());
        }
    }
    
    public void updateCustomer() {
        int customerId = customerView.getCustomerIdForUpdate();
        
        try {
            Customer existingCustomer = customerService.getCustomerById(customerId);
            if (existingCustomer != null) {
                Customer updatedData = customerView.getUpdatedCustomerData(existingCustomer);
                Customer updatedCustomer = customerService.updateCustomer(updatedData);
                customerView.displayCustomerUpdateSuccess(updatedCustomer);
            } else {
                customerView.displayError("Customer not found");
            }
        } catch (Exception e) {
            customerView.displayError("Error updating customer: " + e.getMessage());
        }
    }
    
    public void searchCustomerByPhone() {
        String phone = customerView.getPhoneForSearch();
        
        try {
            Customer customer = customerService.findCustomerByPhone(phone);
            if (customer != null) {
                customerView.displayCustomerDetails(customer);
            } else {
                customerView.displayCustomerNotFound();
            }
        } catch (Exception e) {
            customerView.displayError("Error searching customer: " + e.getMessage());
        }
    }
    
    public Customer selectCustomer() {
        try {
            List<Customer> customers = customerService.getAllCustomers();
            return customerView.selectCustomer(customers);
        } catch (Exception e) {
            customerView.displayError("Error retrieving customers: " + e.getMessage());
            return null;
        }
    }
}