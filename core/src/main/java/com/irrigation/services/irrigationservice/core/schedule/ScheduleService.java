package com.irrigation.services.irrigationservice.core.schedule;

import com.irrigation.aggregates.irrigationservice.dto.common.EntityWithKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.plot.PlotKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleDto;
import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleKeyDto;
import org.springframework.lang.NonNull;

import java.util.List;

public interface ScheduleService {

    ScheduleKeyDto create(@NonNull final PlotKeyDto plotKeyDto, @NonNull final ScheduleDto scheduleDto);

    List<EntityWithKeyDto<ScheduleKeyDto, ScheduleDto>> getAll(@NonNull final PlotKeyDto plotKeyDto);

    List<EntityWithKeyDto<ScheduleKeyDto, ScheduleDto>> getAll(@NonNull final List<ScheduleKeyDto> scheduleKeyDtos);

    EntityWithKeyDto<ScheduleKeyDto, ScheduleDto> get(@NonNull final ScheduleKeyDto scheduleKeyDto);

    void update(@NonNull final ScheduleKeyDto scheduleKeyDto, @NonNull final ScheduleDto scheduleDto);

    void delete(@NonNull final ScheduleKeyDto scheduleKeyDto);

}
