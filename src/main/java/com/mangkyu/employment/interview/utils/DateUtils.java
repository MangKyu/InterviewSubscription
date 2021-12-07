package com.mangkyu.employment.interview.utils;

import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
public final class DateUtils {

    public static String getCurrentDate() {
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return dateTimeFormatter.format(LocalDateTime.now());
    }

}
