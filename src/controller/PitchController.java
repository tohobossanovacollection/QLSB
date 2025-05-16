package controller;

import java.util.List;
import model.Booking;
import model.Pitch;
import service.PitchService;
import view.ManageFieldsView;
import view.PitchView;
import view.components.TimeSlotTablePanel;

public class PitchController {
    private ManageFieldsView manageFieldsView;
    private PitchService pitchService;
    private PitchView pitchView;
    private TimeSlotTablePanel timeSlotTablePanel;
    public PitchController(PitchService pitchService, PitchView pitchView) {
        this.pitchService = pitchService;
        this.pitchView = pitchView;
        this.manageFieldsView = new ManageFieldsView();
    }
    
    public void displayAllPitches() {
        try {
            List<Pitch> pitches = pitchService.getAllPitches();
            pitchView.displayPitchList(pitches);
        } catch (Exception e) {
            pitchView.displayError("Error retrieving pitches: " + e.getMessage());
        }
    }

    public void test(){
        Booking booking = manageFieldsView.getSelectedBooking();
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
}