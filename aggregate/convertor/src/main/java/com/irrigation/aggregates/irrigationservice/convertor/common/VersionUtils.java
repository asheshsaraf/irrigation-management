package com.irrigation.aggregates.irrigationservice.convertor.common;

import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;

@Slf4j
public final class VersionUtils {

    private VersionUtils() {
    }

    public static String create() {
        return String.valueOf(LocalDateTime.now(ZoneId.of("UTC")).toEpochSecond(ZoneOffset.UTC));
    }

}
