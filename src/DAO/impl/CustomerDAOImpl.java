package DAO.impl;

import java.util.List;

import DAO.CustomerDAO;
import model.Customer;
public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public Customer findById(int id) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Customer> findAll() {
        // Implementation code here
        return null;
    }

    @Override
    public boolean save(Customer entity) {
        // Implementation code here
        return false;
    }

    @Override
    public boolean update(Customer entity) {
        // Implementation code here
        return false;
    }

    @Override
    public boolean delete(int id) {
        // Implementation code here
        return false;
    }
    
    @Override
    public Customer findByPhone (String phone) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Customer> findByType(String type) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Customer> findByDebt() {
        // Implementation code here
        return null;
    }

    @Override
    public List<Customer> searchByName(String keyword) {
        // Implementation code here
        return null;
    }
}
