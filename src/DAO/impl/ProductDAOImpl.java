package DAO.impl;

import java.util.List;

import DAO.ProductDAO;
import model.Product;
public class ProductDAOImpl implements ProductDAO{
    @Override
    public Product findById(int id) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Product> findAll() {
        // Implementation code here
        return null;
    }

    @Override
    public boolean save(Product entity) {
        // Implementation code here
        return false;
    }

    @Override
    public boolean update(Product entity) {
        // Implementation code here
        return false;
    }

    @Override
    public boolean delete(int id) {
        // Implementation code here
        return false;
    }

    @Override
    public List<Product> findByCategory(String category) {
        // Implementation code here
        return null;
    }

    @Override
    public List<Product> findLowStock() {
        // Implementation code here
        return null;
    }

    @Override
    public List<Product> searchByName(String keyword) {
        // Implementation code here
        return null;
    }

    
}
