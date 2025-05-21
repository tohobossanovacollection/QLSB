package view;

import model.Pitch;
import model.Customer;
import model.Booking;
import service.PitchService;
import service.CustomerService;
import service.BookingService;
import utils.DateTimeUtils;

import com.toedter.calendar.JCalendar;
import view.components.TimeSlotTablePanel;

import javax.swing.*;
import java.awt.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Date;

public class ManageFieldsView extends JPanel {
    private JComboBox<Pitch> pitchComboBox;
    private JComboBox<Customer> customerComboBox;
    private JCalendar calendar;
    private TimeSlotTablePanel timeSlotTablePanel;
    private JLabel statusLabel;

    private PitchService pitchService;
    private CustomerService customerService;
    private BookingService bookingService;

    private List<LocalDate> weekDates;
    private List<LocalTime> timeSlots;

    public ManageFieldsView() {
        pitchService = new PitchService();
        customerService = new CustomerService();
        bookingService = new BookingService();

        setLayout(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        pitchComboBox = new JComboBox<>();
        customerComboBox = new JComboBox<>();
        calendar = new JCalendar();
        statusLabel = new JLabel("Nhấn vào khung giờ để chọn, có thể kéo thả để đăt khung giờ mong muốn");

        topPanel.add(new JLabel("Chọn sân:"));
        topPanel.add(pitchComboBox);
        topPanel.add(new JLabel("Khách hàng:"));
        topPanel.add(customerComboBox);
        topPanel.add(new JLabel("Chọn tuần:"));
        topPanel.add(calendar);
        add(topPanel, BorderLayout.NORTH);
        add(statusLabel, BorderLayout.SOUTH);

        // Khởi tạo danh sách khung giờ và ngày trong tuần
        timeSlots = TimeSlotTablePanel.defaultTimeSlots();
        weekDates = getCurrentWeekDates();

        // Lấy booking ban đầu
        

        loadPitches();
        loadCustomers();
        //DataTableInit();

        List<Map<String,Object>> data = bookingService.getAllBookingsMap()
        .stream().filter(b->(int) b.get("pitchId") == getselectedPitch().getId())
        .collect(Collectors.toList());
        timeSlotTablePanel = new TimeSlotTablePanel(weekDates, timeSlots, data);
        JScrollPane scrollPane = new JScrollPane(timeSlotTablePanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(40);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(40);
        add(scrollPane, BorderLayout.CENTER);

        pitchComboBox.addActionListener(e -> reloadTable());
        //customerComboBox.addActionListener(e -> statusLabel.setText(""));
        calendar.getDayChooser().addPropertyChangeListener("day", evt -> reloadTable());

        
        
        
    }

    public void setAddAction(TimeSlotTablePanel.SlotClickListener listener) {
        timeSlotTablePanel.setSlotClickListener(listener);
    }

    public Booking getSelectedBooking(){
        Booking booking = new Booking();
        Pitch selectedPitch = (Pitch) pitchComboBox.getSelectedItem();

        Customer selectedCustomer = (Customer) customerComboBox.getSelectedItem();
        if (selectedPitch == null || selectedCustomer == null ) {
            statusLabel.setText("Vui lòng chọn đầy đủ thông tin!");
            return null;
        }
        LocalTime start = timeSlotTablePanel.getSelectedStartTime();
        LocalTime end = timeSlotTablePanel.getSelectedEndTime();
        if (start == null || end == null) {
            statusLabel.setText("Vui lòng chọn khung giờ!");
            return null;
        }
        LocalDateTime startTime = DateTimeUtils.parseTime(start.toString());
        LocalDateTime endTime =  DateTimeUtils.parseTime(end.toString());
        booking.setPitchId(selectedPitch.getId());
        booking.setCustomerId(selectedCustomer.getId());
        booking.setDate(DateTimeUtils.parseDate(DateTimeUtils.getDateFromDate(calendar.getDate())));
        booking.setStartTime(startTime);
        booking.setEndTime(endTime);
        booking.setTotalPrice(selectedPitch.getPricePerHour() * (30 / 60.0));
        booking.setStatus("CONFIRMED");
        booking.setNote("hehetest 123 ne ");
        System.out.println(booking);
        return booking;
    }

    private void loadPitches() {
        List<Pitch> pitches = pitchService.getAllPitches();
        pitchComboBox.removeAllItems();
        for (Pitch p : pitches) {
            pitchComboBox.addItem(p);
        }
    }

    private void loadCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        customerComboBox.removeAllItems();
        for (Customer c : customers) {
            customerComboBox.addItem(c);
        }
    }

    private List<LocalDate> getCurrentWeekDates() {
        Date selected = calendar.getDate();
        LocalDate selectedDate = selected.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        LocalDate monday = selectedDate;
        while (monday.getDayOfWeek() != DayOfWeek.MONDAY) {
            monday = monday.minusDays(1);
        }
        List<LocalDate> week = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            week.add(monday.plusDays(i));
        }
        return week;
    }
    //TODO : check this
    private List<Booking> getCurrentBookings() {
        Pitch selectedPitch = (Pitch) pitchComboBox.getSelectedItem();
        if (selectedPitch == null) return new ArrayList<>();
        return bookingService.getBookingsByPitch(selectedPitch.getId());
    }

    private void reloadTable() {
        weekDates = getCurrentWeekDates();
        List<Map<String,Object>> data = bookingService.getAllBookingsMap()
        .stream().filter(b->(int) b.get("pitchId") == getselectedPitch().getId()).collect(Collectors.toList());
        timeSlotTablePanel.updateData(weekDates, timeSlots, data);
        statusLabel.setText("");
    }

    public  Pitch getselectedPitch(){
        return (Pitch) pitchComboBox.getSelectedItem();
    }

    // private void handleBooking(LocalDate date, LocalTime slot) {
    //     Pitch selectedPitch = (Pitch) pitchComboBox.getSelectedItem();

    //     Customer selectedCustomer = (Customer) customerComboBox.getSelectedItem();
    //     if (selectedPitch == null || selectedCustomer == null || date == null || slot == null) {
    //         statusLabel.setText("Vui lòng chọn đầy đủ thông tin!");
    //         return;
    //     }
    //     LocalDateTime dateStart = date.atStartOfDay();
    //     LocalDateTime startTime = LocalDateTime.of(date, slot);
    //     LocalDateTime endTime = startTime.plusMinutes(30);
    //     boolean conflict = !bookingService.checkConflict(selectedPitch.getId(), dateStart, startTime, endTime);
    //     if (conflict) {
    //         statusLabel.setText("Khung giờ đã có người đặt!");
    //         return;
    //     }
    //     Booking booking = new Booking();
    //     booking.setPitchId(selectedPitch.getId());
    //     booking.setCustomerId(selectedCustomer.getId());
    //     booking.setDate(dateStart);
    //     booking.setStartTime(startTime);
    //     booking.setEndTime(endTime);
    //     booking.setTotalPrice(selectedPitch.getPricePerHour() * (30 / 60.0));
    //     booking.setStatus("CONFIRMED");
    //     booking.setNote("");
    //     boolean success = bookingService.addBooking(booking);
    //     if (success) {
    //         statusLabel.setText("Đặt sân thành công!");
    //         reloadTable();
    //     } else {
    //         statusLabel.setText("Lỗi khi đặt sân!");
    //     }
    // }

    
} 