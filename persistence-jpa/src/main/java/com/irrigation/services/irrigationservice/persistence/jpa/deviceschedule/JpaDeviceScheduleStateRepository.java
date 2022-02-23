package com.irrigation.services.irrigationservice.persistence.jpa.deviceschedule;

import com.irrigation.aggregates.irrigationservice.domain.deviceschedule.DeviceScheduleState;
import com.irrigation.aggregates.irrigationservice.domain.deviceschedule.DeviceScheduleStateKey;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaDeviceScheduleStateRepository extends JpaRepository<DeviceScheduleState, DeviceScheduleStateKey> {

    List<DeviceScheduleState> findAllByDeviceId(@NonNull final String deviceId);

    List<DeviceScheduleState> findAllByCustomerIdAndPlotId(@NonNull final String customerId,
                                                           @NonNull final String plotId);

}
