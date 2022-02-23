package com.irrigation.services.irrigationservice.integration;

import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleKeyDto;
import org.springframework.lang.NonNull;

public interface DeviceCommunicationService {

    void startIrrigation(@NonNull final String deviceId,
                         @NonNull final ScheduleKeyDto scheduleKeyDto,
                         @NonNull final Long durationInMinutes) throws InterruptedException;

}
