package DAO;

import model.Booking;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface BookingDAO extends GenericDAO<Booking> {
    List<Booking> findByPitch(int pitchId);
    List<Booking> findByCustomer(int customerId);
    List<Booking> findByDate(LocalDateTime date);
    List<Booking> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);
    List<Booking> findByPitchAndDateRange(int pitchId, LocalDateTime start, LocalDateTime end);
    //boolean checkConflict(int pitch_Id,LocalDateTime date ,LocalDateTime startTime,LocalDateTime endTime);
}
