package com.irrigation.aggregates.irrigationservice.domain.deviceschedule;

import com.irrigation.aggregates.irrigationservice.domain.schedule.ScheduleKey;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class DeviceScheduleStateKey extends ScheduleKey implements Serializable {

    private String deviceId;

    public DeviceScheduleStateKey(@NonNull final String customerId,
                                  @NonNull final String plotId,
                                  @NonNull final String scheduleId,
                                  @NonNull final String deviceId) {
        super(customerId, plotId, scheduleId);
        this.deviceId = deviceId;
    }

    @NonNull
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(@NonNull final String deviceId) {
        this.deviceId = deviceId;
    }

}
