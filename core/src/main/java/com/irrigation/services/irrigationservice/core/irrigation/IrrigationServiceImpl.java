package com.irrigation.services.irrigationservice.core.irrigation;

import com.irrigation.aggregates.irrigationservice.domain.deviceschedule.DeviceScheduleState;
import com.irrigation.aggregates.irrigationservice.dto.common.EntityWithKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.irrigationstatus.IrrigationState;
import com.irrigation.aggregates.irrigationservice.dto.irrigationstatus.IrrigationStatusDto;
import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleDto;
import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleKeyDto;
import com.irrigation.services.irrigationservice.core.deviceschedule.DeviceScheduleStateService;
import com.irrigation.services.irrigationservice.core.schedule.ScheduleService;
import com.irrigation.services.irrigationservice.integration.DeviceCommunicationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import static com.irrigation.aggregates.irrigationservice.convertor.common.DateTimeConvertor.dateTimeConvertor;
import static com.irrigation.aggregates.irrigationservice.dto.irrigationstatus.IrrigationState.*;
import static java.util.Objects.nonNull;
import static java.util.stream.Collectors.toMap;

@Slf4j
@Service
@RequiredArgsConstructor
public class IrrigationServiceImpl implements IrrigationService {

    @Value("${configuration.retry-count}")
    private Integer maxRetryCount;
    private final ScheduleService scheduleService;
    private final DeviceScheduleStateService stateService;
    private final DeviceCommunicationService deviceCommunicationService;

    @Override
    @Scheduled(fixedDelay = 60000)
    public void scheduleIrrigation() {
        log.error("Scheduler Started for sending command to device integration");
        final List<DeviceScheduleState> states = stateService.getAllStates(CONFIGURED, FAILED);
        final List<ScheduleKeyDto> scheduleKeyDtoList = states.stream()
                .map(state -> new ScheduleKeyDto(state.getCustomerId(), state.getPlotId(), state.getScheduleId()))
                .toList();
        final Map<String, ScheduleDto> allSchedulesMap =
                scheduleService.getAll(scheduleKeyDtoList).stream()
                        .filter(entity -> nonNull(entity.getKey()) && nonNull(entity.getAttributes()))
                        .collect(toMap(entity -> entity.getKey().getScheduleId(), EntityWithKeyDto::getAttributes));
        states.forEach(state ->
                CompletableFuture.runAsync(() ->
                        scheduleIrrigation(state, allSchedulesMap.get(state.getScheduleId()))));
    }

    @Override
    public List<IrrigationStatusDto> getAll(@NonNull final String customerId,
                                            @NonNull final String plotId) {
        return stateService.getAll(customerId, plotId);
    }

    private void scheduleIrrigation(@NonNull final DeviceScheduleState state,
                                    @NonNull final ScheduleDto scheduleDto) {
        final LocalDateTime scheduledStartTime = dateTimeConvertor().toLocalDateTime(scheduleDto.getStartDateTime());
        final LocalDateTime scheduledEndTime = dateTimeConvertor().toLocalDateTime(scheduleDto.getEndDateTime());
        final LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        //TODO: Use Executor framework for scheduling
        if (now.plusMinutes(2).isAfter(scheduledStartTime)) {
            preProcess(state);
            process(state, Duration.between(scheduledStartTime, scheduledEndTime));
            postProcess(state, COMPLETED);
        }
    }

    private void preProcess(@NonNull final DeviceScheduleState state) {
        state.setIrrigationState(IrrigationState.SCHEDULED.name());
        if (state.getRetryCount() < maxRetryCount) {
            state.setRetryCount(state.getRetryCount() + 1);
            stateService.update(state);
        } else {
            log.error("Retry count limit: {} reached for device: {} with schedule: {} for plot: {}",
                    state.getRetryCount(), state.getDeviceId(), state.getScheduleId(), state.getPlotId());
        }
    }

    private void process(@NonNull final DeviceScheduleState state,
                         @NonNull final Duration duration) {
        final ScheduleKeyDto scheduleKeyDto =
                new ScheduleKeyDto(state.getCustomerId(), state.getPlotId(), state.getScheduleId());
        try {
            deviceCommunicationService.startIrrigation(state.getDeviceId(), scheduleKeyDto, duration.toMinutes());
        } catch (Exception e) {
            log.error("Error occurred while sending request to device: {} for schedule: {} with exception: {}",
                    state.getDeviceId(), scheduleKeyDto, e.getMessage());
            postProcess(state, FAILED);
        }
    }

    private void postProcess(@NonNull final DeviceScheduleState state,
                             @NonNull final IrrigationState irrigationState) {
        state.setIrrigationState(irrigationState.name());
        stateService.update(state);
    }

}
