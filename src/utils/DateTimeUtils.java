package utils;

//import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateTimeUtils {
    private static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss");
    /**
     * Format LocalDateTime to string using default format
     */
    public static String formatDateTime(LocalDateTime dateTime) {
        if (dateTime == null) return "";
        return dateTime.format(DEFAULT_FORMATTER);
    }

    public static String getDateFromDate(Date inputDate) {
        // Convert java.util.Date to String 
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String format = formatter.format(inputDate);
        return format;
    }
    public static String getTimeFromDate(Date inputDate) {
        // Convert java.util.Date to String 
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String format = formatter.format(inputDate);
        return format;
    }   
    /**
     * Parse string to LocalDateTime using default format
     */
    public static LocalDateTime parseDateTime(String dateTimeStr) throws DateTimeParseException {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) return null;
        return LocalDateTime.parse(dateTimeStr, DEFAULT_FORMATTER);
    }
    public static LocalDateTime parseDate(String dateStr) throws DateTimeParseException {
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
        return LocalDateTime.parse(dateStr, DATE_FORMATTER);
    }
    public static LocalDateTime parseTime(String timeStr) throws DateTimeParseException {
        if (timeStr == null || timeStr.trim().isEmpty()) return null;
        return LocalDateTime.parse(timeStr, TIME_FORMATTER);
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