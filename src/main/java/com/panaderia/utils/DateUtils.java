package com.panaderia.utils;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

public class DateUtils {
    public static Timestamp parseTimestamp(String s) {
        if (s == null) return new Timestamp(System.currentTimeMillis());
        try {
            if (s.contains("T")) {
                LocalDateTime ldt = LocalDateTime.parse(s);
                return Timestamp.valueOf(ldt);
            } else if (s.length() == 10) { // yyyy-MM-dd
                LocalDate ld = LocalDate.parse(s);
                return Timestamp.valueOf(ld.atStartOfDay());
            } else {
                LocalDateTime ldt = LocalDateTime.parse(s);
                return Timestamp.valueOf(ldt);
            }
        } catch (DateTimeParseException ex) {
            return new Timestamp(System.currentTimeMillis());
        }
    }
}
