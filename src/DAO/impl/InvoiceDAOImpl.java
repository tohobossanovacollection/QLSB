package DAO.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import DAO.InvoiceDAO;
import model.Invoice;
//import model.InvoiceItem;

public class InvoiceDAOImpl implements InvoiceDAO {

    @Override
    public Invoice findById(int id) {
        String sql = "SELECT * FROM Invoices WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return mapResultSetToInvoice(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Invoice> findAll() {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoices";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                invoices.add(mapResultSetToInvoice(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    @Override
    public boolean save(Invoice invoice) {
        String sql = "INSERT INTO Invoices (customerId, PichId, createdAt, discount, total, status, note) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, invoice.getCustomerId());
            stmt.setInt(2, invoice.getPichId());
            stmt.setString(3, invoice.getCreatedAt().toString());
           // stmt.setString(4, invoice.getType());
            //stmt.setDouble(5, invoice.getSubtotal());
            stmt.setDouble(6, invoice.getDiscount());
            stmt.setDouble(7, invoice.getTotal());
            //stmt.setDouble(8, invoice.getPaid());
            //stmt.setDouble(9, invoice.getDebt());
            stmt.setString(10, invoice.getStatus());
            stmt.setString(11, invoice.getNote());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean update(Invoice invoice) {
        String sql = "UPDATE Invoices SET customerId = ?, PichId = ?, type = ?, subtotal = ?, discount = ?, total = ?, paid = ?, debt = ?, status = ?, note = ? WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, invoice.getCustomerId());
            stmt.setInt(2, invoice.getPichId());
            //stmt.setString(3, invoice.getType());
            //stmt.setDouble(4, invoice.getSubtotal());
            stmt.setDouble(5, invoice.getDiscount());
            stmt.setDouble(6, invoice.getTotal());
            //stmt.setDouble(7, invoice.getPaid());
            //stmt.setDouble(8, invoice.getDebt());
            stmt.setString(9, invoice.getStatus());
            stmt.setString(10, invoice.getNote());
            stmt.setInt(11, invoice.getId());

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM Invoices WHERE id = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public List<Invoice> findByCustomer(int customerId) {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoices WHERE customerId = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, customerId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                invoices.add(mapResultSetToInvoice(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    @Override
    public List<Invoice> findByDate(LocalDate date) {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoices WHERE DATE(createdAt) = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, date.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                invoices.add(mapResultSetToInvoice(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    @Override
    public List<Invoice> findByDateRange(LocalDate startDate, LocalDate endDate) {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoices WHERE DATE(createdAt) BETWEEN ? AND ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, startDate.toString());
            stmt.setString(2, endDate.toString());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                invoices.add(mapResultSetToInvoice(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    @Override
    public List<Invoice> findByStatus(String status) {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoices WHERE status = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                invoices.add(mapResultSetToInvoice(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    @Override
    public List<Invoice> findByType(String type) {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoices WHERE type = ?";
        try (Connection conn = DatabaseConnector.connect("QuanLySanBong");
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, type);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                invoices.add(mapResultSetToInvoice(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }

    private Invoice mapResultSetToInvoice(ResultSet rs) throws SQLException {
        Invoice invoice = new Invoice(
                rs.getInt("id"),
                rs.getInt("customerId"),
                rs.getInt("PichId"),
                
                
                rs.getDouble("discount"),
                rs.getString("note")
        );
        //invoice.addPayment(rs.getDouble("paid"));
        return invoice;
    }
}
