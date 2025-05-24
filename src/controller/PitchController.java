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
import view.PitchView;

public class PitchController {
    private ManageFieldsView manageFieldsView;
    private PitchListView pitchListView;
    private PitchService pitchService = new PitchService();
    private PitchView pitchView;
    private BookingService bookingService = new BookingService();
    private CustomerService customerService = new CustomerService();
    public PitchController(PitchListView pitchListView, ManageFieldsView manageFieldsView) {
        this.pitchListView = pitchListView;
        //this.pitchService = new PitchService();
        this.manageFieldsView =manageFieldsView;
    }
    
    public void displayAllPitches() {
        try {
            List<Pitch> pitches = pitchService.getAllPitches();
            pitchView.displayPitchList(pitches);
        } catch (Exception e) {
            pitchView.displayError("Error retrieving pitches: " + e.getMessage());
        }
    }
    
    public void processNewPitch() {
        Pitch pitchData = pitchView.getPitchData();
        
        try {
            if(pitchService.addPitch(pitchData))
            {
                pitchView.displayPitchCreationSuccess(pitchData);
            }
            else
            {
                pitchView.displayError("Error creating pitch: Pitch already exists");
            }
            //pitchView.displayPitchCreationSuccess(savedPitch);
        } catch (Exception e) {
            pitchView.displayError("Error creating pitch: " + e.getMessage());
        }
    }
    
    public void updatePitch() {
        int pitchId = pitchView.getPitchIdForUpdate();
        
        try {
            Pitch existingPitch = pitchService.getPitchById(pitchId);
            if (existingPitch != null) {
                Pitch updatedData = pitchView.getUpdatedPitchData(existingPitch);
                if(pitchService.updatePitch(updatedData))
                {
                    pitchView.displayPitchUpdateSuccess(updatedData);
                }
                else
                {
                    pitchView.displayError("Error updating pitch: Pitch not found");
                }
                //pitchView.displayPitchUpdateSuccess(updatedPitch);
            } else {
                pitchView.displayError("Pitch not found");
            }
        } catch (Exception e) {
            pitchView.displayError("Error updating pitch: " + e.getMessage());
        }
    }
    
    public void deletePitch() {
        int pitchId = pitchView.getPitchIdForDeletion();
        
        try {
            boolean deleted = pitchService.deletePitch(pitchId);
            if (deleted) {
                pitchView.displayPitchDeletionSuccess();
            } else {
                pitchView.displayError("Pitch could not be deleted");
            }
        } catch (Exception e) {
            pitchView.displayError("Error deleting pitch: " + e.getMessage());
        }
    }
    
    public void displayAvailablePitches() {
        try {
            Pitch pitchData = pitchView.getPitchData();
            List<Pitch> availablePitches = pitchService.getActivePitches(pitchData.getBranchId());
            pitchView.displayPitchList(availablePitches);
        } catch (Exception e) {
            pitchView.displayError("Error retrieving available pitches: " + e.getMessage());
        }
    }

    public void reloadTimeslotTable(){
        List<Map<String,Object>> data = bookingService.getAllBookingsMap()
        .stream().filter(b->(int) b.get("pitchId") == manageFieldsView.getselectedPitch().getId()).collect(Collectors.toList());
        manageFieldsView.reloadTable(data);
    }
    
    public void loadDataForManageFieldsView() {
        //loadPitches();
        manageFieldsView.loadPitches(pitchService.getAllPitches());
        manageFieldsView.loadCustomers(customerService.getAllCustomers());
        reloadTimeslotTable();
    }
}