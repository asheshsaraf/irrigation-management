package com.irrigation.aggregates.irrigationservice.convertor.schedule;

import com.irrigation.aggregates.irrigationservice.convertor.common.VersionUtils;
import com.irrigation.aggregates.irrigationservice.domain.schedule.Schedule;
import com.irrigation.aggregates.irrigationservice.dto.common.EntityWithKeyDto;
import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleDto;
import com.irrigation.aggregates.irrigationservice.dto.schedule.ScheduleKeyDto;
import org.springframework.lang.NonNull;

import java.util.List;

import static com.irrigation.aggregates.irrigationservice.convertor.common.DateTimeConvertor.dateTimeConvertor;
import static java.util.stream.Collectors.toList;

public final class ScheduleDtoConvertor {

    private static final ScheduleDtoConvertor convertor = new ScheduleDtoConvertor();

    private ScheduleDtoConvertor() {
    }

    public static ScheduleDtoConvertor scheduleConvertor() {
        return convertor;
    }

    @NonNull
    public Schedule fromDto(@NonNull final ScheduleKeyDto keyDto,
                            @NonNull final ScheduleDto dto) {
        final Schedule schedule = new Schedule();
        schedule.setCustomerId(keyDto.getCustomerId());
        schedule.setPlotId(keyDto.getPlotId());
        schedule.setScheduleId(keyDto.getScheduleId());
        schedule.setName(dto.getName());
        schedule.setDescription(dto.getDescription());
        schedule.setStartDateTime(dateTimeConvertor().toLocalDateTime(dto.getStartDateTime()));
        schedule.setEndDateTime(dateTimeConvertor().toLocalDateTime(dto.getEndDateTime()));
        schedule.setVersion(VersionUtils.create());
        return schedule;
    }

    @NonNull
    public List<EntityWithKeyDto<ScheduleKeyDto, ScheduleDto>> toDtoList(@NonNull final List<Schedule> schedules) {
        return schedules.stream()
                .map(this::toDto)
                .collect(toList());
    }

    @NonNull
    public EntityWithKeyDto<ScheduleKeyDto, ScheduleDto> toDto(@NonNull final Schedule schedule) {
        final ScheduleKeyDto scheduleKeyDto =
                new ScheduleKeyDto(schedule.getCustomerId(), schedule.getPlotId(), schedule.getScheduleId());
        final ScheduleDto scheduleDto = new ScheduleDto(schedule.getName(),
                schedule.getDescription(),
                dateTimeConvertor().fromLocalDateTime(schedule.getStartDateTime()),
                dateTimeConvertor().fromLocalDateTime(schedule.getEndDateTime()),
                schedule.getVersion());
        return new EntityWithKeyDto<>(scheduleKeyDto, scheduleDto);
    }

}
