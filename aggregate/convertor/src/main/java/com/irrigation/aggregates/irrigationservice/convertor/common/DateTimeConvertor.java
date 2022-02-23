package com.irrigation.aggregates.irrigationservice.convertor.common;

import org.apache.commons.lang3.StringUtils;
import org.springframework.lang.NonNull;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public final class DateTimeConvertor {

    private static final DateTimeConvertor convertor = new DateTimeConvertor();
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    private DateTimeConvertor() {
    }

    public static DateTimeConvertor dateTimeConvertor() {
        return convertor;
    }

    @NonNull
    public LocalDateTime toLocalDateTime(@NonNull final String dateTime) {
        if (StringUtils.isEmpty(dateTime)) {
            throw new IllegalArgumentException("Date time can't be null or empty");
        }
        try {
            return LocalDateTime.parse(dateTime, formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date time format");
        }
    }

    @NonNull
    public String fromLocalDateTime(@NonNull final LocalDateTime dateTime) {
        try {
            return formatter.format(dateTime);
        } catch (DateTimeException e) {
            throw new IllegalArgumentException("Invalid date time format");
        }
    }

}
