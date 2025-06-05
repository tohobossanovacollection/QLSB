package controller;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import model.Pitch;
import service.BookingService;
import service.CustomerService;
import service.PitchService;
import view.ManageFieldsView;
import view.PitchListView;

public class PitchController {
    private ManageFieldsView manageFieldsView;
    private PitchListView pitchListView;
    private PitchService pitchService = new PitchService();
    private BookingService bookingService = new BookingService();
    private CustomerService customerService = new CustomerService();

    public PitchController(PitchListView pitchListView, ManageFieldsView manageFieldsView) {
        this.pitchListView = pitchListView;
        this.manageFieldsView = manageFieldsView;
    }

    public void reloadTimeslotTable() {
        List<Map<String, Object>> data = bookingService.getAllBookingsMap()
            .stream()
            .filter(b -> (int) b.get("pitchId") == manageFieldsView.getselectedPitch().getId())
            .collect(Collectors.toList());
        manageFieldsView.reloadTable(data);
    }

    public void loadDataForManageFieldsView() {
        manageFieldsView.loadPitches(pitchService.getAllPitches());
        manageFieldsView.loadCustomers(customerService.getAllCustomers());
        reloadTimeslotTable();
    }
}