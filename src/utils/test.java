package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class test {
    public static void main(String[] args) {
         String inputDate = "2025-04-27 00:46:58";
        LocalDateTime dateTime = DateTimeUtils.parseDateTime(inputDate);

        // Format LocalDateTime to string without "T"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDate = dateTime.format(formatter);

        System.out.println(formattedDate);
    }
}
