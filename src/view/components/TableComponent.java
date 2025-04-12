package view.components;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;

public class TableComponent extends JTable {
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;

    public TableComponent(String[] columnNames) {
        // Tạo model với cột không cho phép chỉnh sửa
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        // Thiết lập model cho bảng
        setModel(tableModel);
        
        // Tạo sorter cho model
        sorter = new TableRowSorter<>(tableModel);
        setRowSorter(sorter);
        
        // Cấu hình giao diện
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        setFillsViewportHeight(true);
        
        // Tùy chỉnh header
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setResizingAllowed(true);
        getTableHeader().setFont(new Font(getFont().getName(), Font.BOLD, 12));
        
        // Tùy chỉnh hiển thị hàng
        setRowHeight(25);
        setShowGrid(true);
        setGridColor(new Color(230, 230, 230));
        
        // Thiết lập màu xen kẽ cho hàng
        setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, 
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(
                        table, value, isSelected, hasFocus, row, column);
                
                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(242, 242, 242));
                }
                
                return c;
            }
        });
    }

    public void addRow(Object[] rowData) {
        tableModel.addRow(rowData);
    }

    public void removeRow(int row) {
        tableModel.removeRow(row);
    }

    public void clearTable() {
        tableModel.setRowCount(0);
    }

    public void filter(String text) {
        if (text.trim().isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + text));
        }
    }
    
    public Object getValueAt(int row, int column) {
        return tableModel.getValueAt(convertRowIndexToModel(row), column);
    }
    
    public int getSelectedModelRow() {
        int viewRow = getSelectedRow();
        if (viewRow >= 0) {
            return convertRowIndexToModel(viewRow);
        }
        return -1;
    }
}