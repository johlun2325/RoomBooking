package com.example.roombooking.utilities;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateConverter implements Converter {

    @Override
    public LocalDate convertToLocalDate(String date) {
        Pattern datePattern = Pattern.compile("^(\\d{4})-(\\d{2})-(\\d{2})$");

        Matcher matcher = datePattern.matcher(date);
        if (!matcher.find()) throw new RuntimeException("Illegal date format");

        int yyyy = Integer.parseInt(matcher.group(1));
        int mm = Integer.parseInt(matcher.group(2));
        int dd = Integer.parseInt(matcher.group(3));

        return LocalDate.of(yyyy, mm, dd);
    }
}
