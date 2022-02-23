package com.irrigation.aggregates.irrigationservice.domain.deviceschedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "device_schedule_state")
@IdClass(DeviceScheduleStateKey.class)
public class DeviceScheduleState implements Serializable {

    @Id
    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Id
    @Column(name = "plot_id", nullable = false)
    private String plotId;

    @Id
    @Column(name = "schedule_id", nullable = false)
    private String scheduleId;

    @Id
    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "device_state", nullable = false)
    private String deviceState;

    @Column(name = "irrigation_state", nullable = false)
    private String irrigationState;

    @Column(name = "retry_count", nullable = false)
    private Integer retryCount;
}
