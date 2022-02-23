package com.irrigation.aggregates.irrigationservice.domain.schedule;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "schedule")
@IdClass(ScheduleKey.class)
public class Schedule implements Serializable {

    @Id
    @Column(name = "customer_id", nullable = false)
    private String customerId;

    @Id
    @Column(name = "plot_id", nullable = false)
    private String plotId;

    @Id
    @Column(name = "schedule_id", nullable = false)
    private String scheduleId;

    @Column(name = "schedule_name", nullable = false)
    private String name;

    @Column
    private String description;

    @Column(name = "start_time")
    private LocalDateTime startDateTime;

    @Column(name = "end_time")
    private LocalDateTime endDateTime;

    @Column(nullable = false)
    private String version;
}
