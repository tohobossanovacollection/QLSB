package view;

import model.Booking;
import view.components.TableComponent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class BookingListView extends JPanel {
    private JTextField searchField;
    private JButton searchButton;
    private JButton addBookingButton;
    private TableComponent<Booking> bookingTable;
    private JButton editButton;
    private JButton deleteButton;
    private JButton printButton;

    public BookingListView() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Title
        JLabel titleLabel = new JLabel("DANH SÁCH ĐẶT SÂN", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        //add(titleLabel, BorderLayout.NORTH);

        // Search panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Tìm kiếm:"));
        searchField = new JTextField(20);
        searchPanel.add(searchField);
        searchButton = new JButton("Tìm");
        searchPanel.add(searchButton);

        addBookingButton = new JButton("Thêm Đặt Sân");
        JPanel addButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        addButtonPanel.add(addBookingButton);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(searchPanel, BorderLayout.WEST);
        topPanel.add(addButtonPanel, BorderLayout.EAST);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);

        // Booking table
        String[] columnNames = {"Mã đặt sân", "Sân", "Ngày", "Giờ bắt đầu", "Giờ kết thúc", "Khách hàng", "Trạng thái"};
        bookingTable = new TableComponent<>(columnNames);
        bookingTable.setFillsViewportHeight(true);
        JScrollPane tableScrollPane = new JScrollPane(bookingTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Button panel (right side)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        editButton = new JButton("Sửa");
        deleteButton = new JButton("Xóa");
        printButton = new JButton("In");
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(printButton);
        add(buttonPanel, BorderLayout.EAST);
    }

    public int getSelectedBookingIndex() {
        return bookingTable.getSelectedRow();
    }

    public String getSearchText() {
        return searchField.getText();
    }

    public void setAddBookingAction(ActionListener listener) {
        addBookingButton.addActionListener(listener);
    }

    public void setEditAction(ActionListener listener) {
        editButton.addActionListener(listener);
    }

    public void setDeleteAction(ActionListener listener) {
        deleteButton.addActionListener(listener);
    }

    public void setPrintAction(ActionListener listener) {
        printButton.addActionListener(listener);
    }

    public void setSearchAction(ActionListener listener) {
        searchButton.addActionListener(listener);
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainView mainView = new MainView();
            mainView.addPanel(new BookingListView(), "1");
            mainView.setVisible(true);
            mainView.showPanel("1");
    });
}
}