package com.irrigation.aggregates.irrigationservice.dto.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class NamedDto extends VersionedDto {

    private final String name;
    private final String description;

    public NamedDto(@NonNull final String name,
                    @Nullable final String description,
                    @Nullable final String version) {
        super(version);
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
