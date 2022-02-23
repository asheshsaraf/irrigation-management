package com.irrigation.services.irrigationservice.core.deviceschedule;

import com.irrigation.aggregates.irrigationservice.domain.deviceschedule.DeviceScheduleState;
import com.irrigation.aggregates.irrigationservice.dto.device.DeviceState;
import com.irrigation.aggregates.irrigationservice.dto.irrigationstatus.IrrigationState;
import com.irrigation.aggregates.irrigationservice.dto.irrigationstatus.IrrigationStatusDto;
import com.irrigation.aggregates.irrigationservice.dto.plotconfiguration.PlotConfigurationDto;
import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleKeyDto;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.List;

@SuppressWarnings("unused")
public interface DeviceScheduleStateService {

    void create(@NonNull final PlotConfigurationDto configurationDto);

    void create(@NonNull final String deviceId,
                @NonNull final ScheduleKeyDto scheduleKeyDto,
                @NonNull final DeviceState deviceState,
                @NonNull final IrrigationState state,
                @NonNull final Integer retryCount);

    void update(@NonNull final DeviceScheduleState state);

    void updateIrrigationState(@NonNull final String deviceId,
                               @NonNull final ScheduleKeyDto scheduleKeyDto,
                               @NonNull final IrrigationState state);

    void updateRetryCount(@NonNull final String deviceId,
                          @NonNull final ScheduleKeyDto scheduleKeyDto,
                          @NonNull final Integer retryCount);

    void updateDeviceState(@NonNull final String deviceId,
                           @NonNull final DeviceState deviceState);

    List<IrrigationStatusDto> getAll(@NonNull final String customerId, @NonNull final String plotId);

    List<DeviceScheduleState> getAllStates(@Nullable final IrrigationState... irrigationStates);
}
