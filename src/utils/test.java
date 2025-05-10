package utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class test {
    public static void main(String[] args) {
         String inputDate = "2025-05-09";
         String inputDate2 = "2025-05-16";
        LocalDateTime test = DateTimeUtils.parseDate(inputDate);
        LocalDateTime tet = DateTimeUtils.parseDate(inputDate2);
        String inputTime = "12:30:00";
        String inputTime2 = "11:30:00";
        LocalDateTime test2 = DateTimeUtils.parseTime(inputTime);
        LocalDateTime test3 = DateTimeUtils.parseTime(inputTime2);
        LocalDate localDate = LocalDate.of(2016, 8, 19);
        LocalTime localTime = LocalTime.of(12, 30, 15);
        System.out.println(DateTimeUtils.formatDate(test));
        System.out.println(test2);
        System.out.println(localDate);
        System.out.println(localTime);
        System.out.println(DateTimeUtils.calculateDaysBetween(test, tet));
        System.out.println(DateTimeUtils.calculateHoursBetween(test2, test3));
    }
}
