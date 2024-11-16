package com.nhatanh.centerlearn.common.utils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class DateFormatter {
    public static LocalDateTime toDate(LocalDate date) {
        if (date == null) {
            return null; // Trả về null nếu date là null
        }
        return date.atTime(LocalTime.MIDNIGHT);
    }
}
