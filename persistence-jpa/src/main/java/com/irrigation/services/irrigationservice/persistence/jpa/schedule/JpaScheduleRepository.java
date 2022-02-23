package com.irrigation.services.irrigationservice.persistence.jpa.schedule;

import com.irrigation.aggregates.irrigationservice.domain.schedule.Schedule;
import com.irrigation.aggregates.irrigationservice.domain.schedule.ScheduleKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaScheduleRepository extends JpaRepository<Schedule, ScheduleKey> {

    List<Schedule> findByCustomerIdAndPlotId(@NonNull final String customerId, @NonNull final String plotId);

}
