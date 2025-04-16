package DAO.impl;

import DAO.InvoiceItemDAO;
import model.InvoiceItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceItemDAOImpl implements InvoiceItemDAO {

    @Override
    public InvoiceItem findById(int id) {
        String sql = "SELECT * FROM invoice_items WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToInvoiceItem(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<InvoiceItem> findAll() {
        List<InvoiceItem> invoiceItems = new ArrayList<>();
        String sql = "SELECT * FROM invoice_items";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                invoiceItems.add(mapResultSetToInvoiceItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoiceItems;
    }

    @Override
    public boolean save(InvoiceItem entity) {
        String sql = "INSERT INTO invoice_items (invoice_id, item_type, item_id, description, unit_price, quantity, total) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, entity.getId());
            stmt.setString(2, entity.getItemType());
            stmt.setInt(3, entity.getItemId());
            stmt.setString(4, entity.getDescription());
            stmt.setDouble(5, entity.getUnitPrice());
            stmt.setInt(6, entity.getQuantity());
            stmt.setDouble(7, entity.getTotal());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(InvoiceItem entity) {
        String sql = "UPDATE invoice_items SET item_type = ?, item_id = ?, description = ?, unit_price = ?, quantity = ?, total = ? WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, entity.getItemType());
            stmt.setInt(2, entity.getItemId());
            stmt.setString(3, entity.getDescription());
            stmt.setDouble(4, entity.getUnitPrice());
            stmt.setInt(5, entity.getQuantity());
            stmt.setDouble(6, entity.getTotal());
            stmt.setInt(7, entity.getId());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM invoice_items WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<InvoiceItem> findByType(String type) {
        List<InvoiceItem> invoiceItems = new ArrayList<>();
        String sql = "SELECT * FROM invoice_items WHERE item_type = ?";
        try (Connection conn = DatabaseConnector.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                invoiceItems.add(mapResultSetToInvoiceItem(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoiceItems;
    }

    private InvoiceItem mapResultSetToInvoiceItem(ResultSet rs) throws SQLException {
        return new InvoiceItem(
                rs.getInt("invoice_id"),
                rs.getString("item_type"),
                rs.getInt("item_id"),
                rs.getString("description"),
                rs.getDouble("unit_price"),
                rs.getInt("quantity")
        );
    }
}
