package com.irrigation.services.irrigationservice.core.deviceschedule;

import com.irrigation.aggregates.irrigationservice.domain.deviceschedule.DeviceScheduleState;
import com.irrigation.aggregates.irrigationservice.domain.deviceschedule.DeviceScheduleStateKey;
import com.irrigation.aggregates.irrigationservice.dto.device.DeviceState;
import com.irrigation.aggregates.irrigationservice.dto.irrigationstatus.IrrigationState;
import com.irrigation.aggregates.irrigationservice.dto.irrigationstatus.IrrigationStatusDto;
import com.irrigation.aggregates.irrigationservice.dto.plotconfiguration.PlotConfigurationDto;
import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleKeyDto;
import com.irrigation.services.irrigationservice.persistence.jpa.deviceschedule.JpaDeviceScheduleStateRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

import static com.irrigation.services.irrigationservice.core.deviceschedule.DeviceScheduleStateUtils.buildDeviceScheduleState;
import static com.irrigation.services.irrigationservice.core.deviceschedule.DeviceScheduleStateUtils.buildDeviceScheduleStateKey;
import static java.util.Objects.isNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class DeviceScheduleStateServiceImpl implements DeviceScheduleStateService {

    private final JpaDeviceScheduleStateRepository scheduleStateRepository;

    @Override
    public void create(@NonNull final PlotConfigurationDto configurationDto) {
        log.info("Creating device schedule states with details: {}", configurationDto);
        scheduleStateRepository.saveAllAndFlush(buildDeviceScheduleState(configurationDto));
    }

    @Override
    public void create(@NonNull final String deviceId,
                       @NonNull final ScheduleKeyDto scheduleKeyDto,
                       @NonNull final DeviceState deviceState,
                       @NonNull final IrrigationState state,
                       @NonNull final Integer retryCount) {
        final DeviceScheduleState deviceScheduleState =
                buildDeviceScheduleState(deviceId, scheduleKeyDto, deviceState, state, retryCount);
        scheduleStateRepository.saveAndFlush(deviceScheduleState);
    }

    @Override
    public void update(@NonNull final DeviceScheduleState state) {
        log.info("Updating irrigation state to {} and retry count to {} for device: {} having schedule: {}",
                state.getIrrigationState(), state.getRetryCount(), state.getDeviceId(), state.getScheduleId());
        scheduleStateRepository.saveAndFlush(state);
    }

    @Override
    public void updateIrrigationState(@NonNull final String deviceId,
                                      @NonNull final ScheduleKeyDto scheduleKeyDto,
                                      @NonNull final IrrigationState state) {
        log.info("Updating irrigation state to {} for device: {} having schedule: {}",
                state.name(), deviceId, scheduleKeyDto);
        final DeviceScheduleStateKey stateKey = buildDeviceScheduleStateKey(deviceId, scheduleKeyDto);
        scheduleStateRepository.findById(stateKey)
                .ifPresent(deviceScheduleState -> {
                    deviceScheduleState.setIrrigationState(state.name());
                    scheduleStateRepository.saveAndFlush(deviceScheduleState);
                });
    }

    @Override
    public void updateRetryCount(@NonNull final String deviceId,
                                 @NonNull final ScheduleKeyDto scheduleKeyDto,
                                 @NonNull final Integer retryCount) {
        log.info("Updating retry count to {} for device: {} having schedule: {}",
                retryCount, deviceId, scheduleKeyDto);
        final DeviceScheduleStateKey stateKey = buildDeviceScheduleStateKey(deviceId, scheduleKeyDto);
        scheduleStateRepository.findById(stateKey)
                .ifPresent(deviceScheduleState -> {
                    deviceScheduleState.setRetryCount(retryCount);
                    scheduleStateRepository.saveAndFlush(deviceScheduleState);
                });
    }

    @Override
    public void updateDeviceState(@NonNull final String deviceId,
                                  @NonNull final DeviceState deviceState) {
        log.info("Updating device state to {} for device: {}", deviceState.name(), deviceId);
        final List<DeviceScheduleState> stateList = scheduleStateRepository.findAllByDeviceId(deviceId).stream()
                .peek(state -> state.setDeviceState(deviceState.name()))
                .toList();
        scheduleStateRepository.saveAllAndFlush(stateList);
    }

    @Override
    public List<DeviceScheduleState> getAllStates(@Nullable final IrrigationState... irrigationStates) {
        return scheduleStateRepository.findAll().stream()
                .filter(state -> isNull(irrigationStates)
                        || Arrays.asList(irrigationStates).contains(IrrigationState.valueOf(state.getIrrigationState())))
                .toList();
    }

    @Override
    public List<IrrigationStatusDto> getAll(@NonNull final String customerId, @NonNull final String plotId) {
        return scheduleStateRepository.findAllByCustomerIdAndPlotId(customerId, plotId).stream()
                .map(state -> new IrrigationStatusDto(state.getDeviceId(),
                        new ScheduleKeyDto(state.getCustomerId(), state.getPlotId(), state.getScheduleId()),
                        state.getIrrigationState(),
                        state.getRetryCount()))
                .toList();
    }
}
