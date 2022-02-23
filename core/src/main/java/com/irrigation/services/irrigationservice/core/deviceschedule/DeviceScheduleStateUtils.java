package com.irrigation.services.irrigationservice.core.deviceschedule;

import com.irrigation.aggregates.irrigationservice.domain.deviceschedule.DeviceScheduleState;
import com.irrigation.aggregates.irrigationservice.domain.deviceschedule.DeviceScheduleStateKey;
import com.irrigation.aggregates.irrigationservice.dto.device.DeviceState;
import com.irrigation.aggregates.irrigationservice.dto.irrigation.IrrigationDto;
import com.irrigation.aggregates.irrigationservice.dto.irrigation.WaterScheduleDto;
import com.irrigation.aggregates.irrigationservice.dto.irrigationstatus.IrrigationState;
import com.irrigation.aggregates.irrigationservice.dto.plotconfiguration.PlotConfigurationDto;
import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleKeyDto;
import lombok.NonNull;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class DeviceScheduleStateUtils {

    private DeviceScheduleStateUtils() {
    }

    @NonNull
    public static DeviceScheduleState buildDeviceScheduleState(@NonNull final String deviceId,
                                                               @NonNull final ScheduleKeyDto scheduleKeyDto,
                                                               @NonNull final DeviceState deviceState,
                                                               @NonNull final IrrigationState state,
                                                               @NonNull final Integer retryCount) {
        final DeviceScheduleState deviceScheduleState = new DeviceScheduleState();
        deviceScheduleState.setCustomerId(scheduleKeyDto.getCustomerId());
        deviceScheduleState.setPlotId(scheduleKeyDto.getPlotId());
        deviceScheduleState.setScheduleId(scheduleKeyDto.getScheduleId());
        deviceScheduleState.setDeviceId(deviceId);
        deviceScheduleState.setDeviceState(deviceState.name());
        deviceScheduleState.setIrrigationState(state.name());
        deviceScheduleState.setRetryCount(retryCount);
        return deviceScheduleState;
    }

    @NonNull
    public static DeviceScheduleStateKey buildDeviceScheduleStateKey(@NonNull final String deviceId,
                                                                     @NonNull final ScheduleKeyDto scheduleKeyDto) {
        final DeviceScheduleStateKey stateKey = new DeviceScheduleStateKey();
        stateKey.setCustomerId(scheduleKeyDto.getCustomerId());
        stateKey.setPlotId(scheduleKeyDto.getPlotId());
        stateKey.setScheduleId(scheduleKeyDto.getScheduleId());
        stateKey.setDeviceId(deviceId);
        return stateKey;
    }

    @NonNull
    public static List<DeviceScheduleState> buildDeviceScheduleState(
            @NonNull final PlotConfigurationDto configurationDto) {
        final Map<String, List<ScheduleKeyDto>> mapping = configurationDto.getIrrigation().stream()
                .collect(Collectors.toMap(IrrigationDto::getDeviceId,
                        DeviceScheduleStateUtils::getScheduleKeyDtoList));
        return mapping.entrySet().stream()
                .flatMap(entry -> buildState(entry.getKey(), entry.getValue()).stream())
                .toList();
    }

    @NonNull
    public static List<DeviceScheduleState> buildState(@NonNull final String deviceId,
                                                       @NonNull final List<ScheduleKeyDto> scheduleKeyDtos) {
        return scheduleKeyDtos.stream()
                .map(key -> buildDeviceScheduleState(deviceId, key))
                .collect(Collectors.toList());
    }

    @NonNull
    public static DeviceScheduleState buildDeviceScheduleState(@NonNull final String deviceId,
                                                               @NonNull final ScheduleKeyDto keyDto) {
        final DeviceScheduleState state = new DeviceScheduleState();
        state.setDeviceId(deviceId);
        state.setCustomerId(keyDto.getCustomerId());
        state.setPlotId(keyDto.getPlotId());
        state.setScheduleId(keyDto.getScheduleId());
        state.setDeviceState(DeviceState.ONLINE.name());
        state.setIrrigationState(IrrigationState.CONFIGURED.name());
        state.setRetryCount(0);
        return state;
    }

    @NonNull
    private static List<ScheduleKeyDto> getScheduleKeyDtoList(@NonNull final IrrigationDto irrigationDto) {
        return irrigationDto.getWaterSchedules()
                .stream()
                .map(WaterScheduleDto::getScheduleKeyDto)
                .collect(Collectors.toList());
    }
}
