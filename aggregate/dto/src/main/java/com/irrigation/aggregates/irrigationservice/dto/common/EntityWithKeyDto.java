package com.irrigation.aggregates.irrigationservice.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.lang.Nullable;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class EntityWithKeyDto<Key extends BaseDto, Attribute extends BaseDto> implements Serializable {

    private final Key key;
    private final Attribute attributes;

    public EntityWithKeyDto(@Nullable final Key key,
                            @Nullable final Attribute attributes) {
        this.key = key;
        this.attributes = attributes;
    }

    @Nullable
    public Key getKey() {
        return key;
    }

    @Nullable
    public Attribute getAttributes() {
        return attributes;
    }

}
