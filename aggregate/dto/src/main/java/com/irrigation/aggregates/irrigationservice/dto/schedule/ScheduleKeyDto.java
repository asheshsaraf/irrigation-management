package com.irrigation.aggregates.irrigationservice.dto.schedule;

import com.irrigation.aggregates.irrigationservice.dto.plot.PlotKeyDto;
import org.springframework.lang.NonNull;

public class ScheduleKeyDto extends PlotKeyDto {

    private String scheduleId;

    public ScheduleKeyDto() {
        super(null, null);
    }

    public ScheduleKeyDto(@NonNull final String customerId,
                          @NonNull final String plotId,
                          @NonNull final String scheduleId) {
        super(customerId, plotId);
        this.scheduleId = scheduleId;
    }

    @NonNull
    public String getScheduleId() {
        return scheduleId;
    }

}
