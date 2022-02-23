package com.irrigation.aggregates.irrigationservice.domain.schedule;

import com.irrigation.aggregates.irrigationservice.domain.plot.PlotKey;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class ScheduleKey extends PlotKey implements Serializable {

    private String scheduleId;

    public ScheduleKey(@NonNull final String customerId,
                       @NonNull final String plotId,
                       @NonNull final String scheduleId) {
        super(customerId, plotId);
        this.scheduleId = scheduleId;
    }

    @NonNull
    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(@NonNull final String scheduleId) {
        this.scheduleId = scheduleId;
    }

}
