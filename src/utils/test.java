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
        String LocalDatetime = "2025-05-18 00:00:00";
        LocalDateTime test = DateTimeUtils.parseDateTime(LocalDatetime);
        Date test2 = DateTimeUtils.toDate(test);
        System.out.println(test2);
    }
}
