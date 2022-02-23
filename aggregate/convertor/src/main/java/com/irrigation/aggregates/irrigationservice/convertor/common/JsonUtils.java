package com.irrigation.aggregates.irrigationservice.convertor.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.List;

@Slf4j
public final class JsonUtils {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private JsonUtils() {
    }

    @NonNull
    public static String toJson(@Nullable final Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Error parsing object to json", e);
            throw new IllegalArgumentException("Error parsing object to json", e);
        }
    }

    @NonNull
    public static <T extends Serializable> T toObject(@NonNull final String json,
                                                      @NonNull final Class<T> classType) {
        try {
            return OBJECT_MAPPER.readValue(json, classType);
        } catch (JsonProcessingException e) {
            log.error("Error parsing json to object", e);
            throw new IllegalArgumentException("Error parsing json to object", e);
        }
    }

    @NonNull
    public static <T extends Serializable> List<T> toObjectList(@NonNull final String json,
                                                                @NonNull final TypeReference<List<T>> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error("Error parsing json to object", e);
            throw new IllegalArgumentException("Error parsing json to object", e);
        }
    }
}
