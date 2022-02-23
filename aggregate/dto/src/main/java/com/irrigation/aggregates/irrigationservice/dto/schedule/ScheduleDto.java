package com.irrigation.aggregates.irrigationservice.dto.schedule;

import com.irrigation.aggregates.irrigationservice.dto.common.NamedDto;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

public class ScheduleDto extends NamedDto {

    private final String startDateTime;
    private final String endDateTime;

    public ScheduleDto(@NonNull final String name,
                       @Nullable final String description,
                       @NonNull final String startDateTime,
                       @NonNull final String endDateTime,
                       @Nullable final String version) {
        super(name, description, version);
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    @NonNull
    public String getStartDateTime() {
        return startDateTime;
    }

    @NonNull
    public String getEndDateTime() {
        return endDateTime;
    }
}
