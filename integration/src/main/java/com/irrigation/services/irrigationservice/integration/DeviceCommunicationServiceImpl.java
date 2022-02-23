package com.irrigation.services.irrigationservice.integration;

import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleKeyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceCommunicationServiceImpl implements DeviceCommunicationService {

    @Override
    public void startIrrigation(@NonNull final String deviceId,
                                @NonNull final ScheduleKeyDto scheduleKeyDto,
                                @NonNull final Long durationInMinutes) throws InterruptedException {
        log.info("----------------- Irrigation Started ------------------");
        log.info("Plot with id: {} having device: {} and schedule: {} is getting irrigated.",
                scheduleKeyDto.getPlotId(), deviceId, scheduleKeyDto.getScheduleId());
        Thread.sleep(durationInMinutes*60*1000);
        log.info("----------------- Irrigation Completed ------------------");
    }

}
