package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtils {
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    
    /**
     * Format LocalDateTime to string using default format
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(DEFAULT_FORMATTER);
    }
    
    /**
     * Parse string to LocalDateTime using default format
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) throws DateTimeParseException {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) return null;
        return LocalDateTime.parse(dateTimeStr, DEFAULT_FORMATTER);
    }
    
    /**
     * Check if two time ranges overlap
     */
    public static boolean timeRangesOverlap(
            LocalDateTime start1, LocalDateTime end1,
            LocalDateTime start2, LocalDateTime end2) {
        
        return (start1.isBefore(end2) && start2.isBefore(end1));
    }
    
    /**
     * Calculate duration in hours between two times
     */
    public static double calculateHoursBetween(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) return 0;
        
        // Calculate duration in seconds then convert to hours
        long seconds = java.time.Duration.between(start, end).getSeconds();
        return seconds / 3600.0;
    }
}