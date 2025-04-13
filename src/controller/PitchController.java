package controller;

import java.util.List;
import model.Pitch;
import service.PitchService;
import view.PitchView;

public class PitchController {
    private PitchService pitchService;
    private PitchView pitchView;
    
    public PitchController(PitchService pitchService, PitchView pitchView) {
        this.pitchService = pitchService;
        this.pitchView = pitchView;
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
            Pitch savedPitch = pitchService.createPitch(pitchData);
            pitchView.displayPitchCreationSuccess(savedPitch);
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
                Pitch updatedPitch = pitchService.updatePitch(updatedData);
                pitchView.displayPitchUpdateSuccess(updatedPitch);
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
            List<Pitch> availablePitches = pitchService.getAvailablePitches();
            pitchView.displayPitchList(availablePitches);
        } catch (Exception e) {
            pitchView.displayError("Error retrieving available pitches: " + e.getMessage());
        }
    }
}