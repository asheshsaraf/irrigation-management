package com.irrigation.services.irrigationservice.api.common;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.Arrays;

public final class ResourceUtils {

    private ResourceUtils() {
    }

    public static void validate(@NonNull final String... ids) {
        if (Arrays.stream(ids).anyMatch(ResourceUtils::isEmpty)) {
            throw new IllegalArgumentException("id field should not be null or empty");
        }
    }

    public static boolean isEmpty(@Nullable Object str) {
        return str == null || "".equals(str);
    }

}
