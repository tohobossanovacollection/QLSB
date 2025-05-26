package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class test {
    public static void main(String[] args) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        System.out.println(formatter);
         String inputDate = "2025-05-09";
         String inputDate2 = "2025-05-16";
        String LocalDatetime1 = "2025-05-18 05:00:00";
        String LocalDateTime2 = "2025-05-18 04:00:00";
        LocalDateTime test = DateTimeUtils.parseDateTime(LocalDatetime1);
        LocalDateTime test2 = DateTimeUtils.parseDateTime(LocalDateTime2);
        
        System.out.println(DateTimeUtils.calculateMinutesBetween(test, test2));
    }
}
